package com.example.fastfoodapp.userprofile.orderhistory;

import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.fastfoodapp.BR;
import com.example.fastfoodapp.data.UsersAndRestaurantsDataSource;
import com.example.fastfoodapp.data.UsersAndRestaurantsRemoteDataSource;
import com.example.fastfoodapp.data.order.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;


public class UserOrderHistoryViewModel extends BaseObservable {

    public static final String TAG = "UserOrderHistoryVM";

    public final ObservableArrayList<Order> mOrders = new ObservableArrayList<>();

    public final ObservableBoolean mDataLoading = new ObservableBoolean(false);

    private final UsersAndRestaurantsRemoteDataSource mDataSource;

    public UserOrderHistoryViewModel(UsersAndRestaurantsRemoteDataSource dataSource) {
        mDataSource = dataSource;
    }

    public void start() {
        loadAllOrders();
    }

    @Bindable
    public boolean isEmpty() {
        return mOrders.isEmpty();
    }

    private void loadAllOrders() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mDataLoading.set(true);
        mDataSource.getUserOrderHistory(user.getUid(), new UsersAndRestaurantsDataSource.GetUserOrderHistoryCallback() {
            @Override
            public void onGetUserOrderHistory(List<Order> orders) {
                mOrders.clear();
                mOrders.addAll(orders);

                mDataLoading.set(false);
                notifyPropertyChanged(BR.empty);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(TAG, "User order history data not available");

                mDataLoading.set(false);
            }
        });
    }

    public void clearUserOrderHistory() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mDataLoading.set(true);
        mDataSource.clearUserOrderHistory(user.getUid(), new UsersAndRestaurantsDataSource.ClearUserOrderHistoryCallback() {
            @Override
            public void onSuccess() {
                mOrders.clear();

                mDataLoading.set(false);
                notifyPropertyChanged(BR.empty);
            }

            @Override
            public void onFailure() {
                Log.d(TAG, "Failed to clear user order history");
                mDataLoading.set(false);
            }
        });
    }

    @BindingAdapter("app:items")
    public static void setItems(RecyclerView recyclerView, ArrayList<Order> items) {
        ((UserOrderHistoryFragment.OrdersAdapter) recyclerView.getAdapter()).replaceData(items);
    }

    @BindingAdapter("app:onRefresh")
    public static void setRefreshLayoutListener(SwipeRefreshLayout view, UserOrderHistoryViewModel viewModel) {
        view.setOnRefreshListener(viewModel::loadAllOrders);
    }
}
