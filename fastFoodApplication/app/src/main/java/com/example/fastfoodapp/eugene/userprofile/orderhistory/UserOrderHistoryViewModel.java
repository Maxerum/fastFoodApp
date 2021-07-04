package com.example.fastfoodapp.eugene.userprofile.orderhistory;

import android.util.Log;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodapp.eugene.data.UsersAndRestaurantsDataSource;
import com.example.fastfoodapp.eugene.data.UsersAndRestaurantsRemoteDataSource;
import com.example.fastfoodapp.eugene.data.order.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class UserOrderHistoryViewModel {

    public static final String TAG = "UserOrderHistoryVM";

    public final ObservableArrayList<Order> mOrders = new ObservableArrayList<>();

    private final UsersAndRestaurantsRemoteDataSource mDataSource;

    public UserOrderHistoryViewModel(UsersAndRestaurantsRemoteDataSource dataSource) {
        mDataSource = dataSource;
    }

    public void start() {
        getAllOrders();
    }

    private void getAllOrders() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mDataSource.getAllUserOrderHistory(user.getUid(), new UsersAndRestaurantsDataSource.GetUserOrderHistoryCallback() {
            @Override
            public void onGetUserOrderHistory(ArrayList<Order> orders) {
                mOrders.clear();
                mOrders.addAll(orders);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(TAG, "data not available");
            }
        });
    }

    public void clearUserOrderHistory() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mDataSource.clearAllUserOrderHistory(user.getUid(), new UsersAndRestaurantsDataSource.ClearUserOrderHistoryCallback() {
            @Override
            public void onSuccess() {
                mOrders.clear();
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @BindingAdapter("app:items")
    public static void setItems(RecyclerView recyclerView, ArrayList<Order> items) {
        ((UserOrderHistoryFragment.OrdersAdapter) recyclerView.getAdapter()).replaceData(items);
    }
}
