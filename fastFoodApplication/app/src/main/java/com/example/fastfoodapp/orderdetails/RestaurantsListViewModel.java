package com.example.fastfoodapp.orderdetails;

import android.util.Log;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodapp.data.Restaurant;
import com.example.fastfoodapp.data.UsersAndRestaurantsDataSource;
import com.example.fastfoodapp.data.UsersAndRestaurantsRemoteDataSource;

import java.util.ArrayList;

public class RestaurantsListViewModel {

    public static final String TAG = "RestaurantsListVM";

    private final UsersAndRestaurantsDataSource mDataSource;

    public final ObservableArrayList<Restaurant> mRestaurants = new ObservableArrayList<>();

    public RestaurantsListViewModel(UsersAndRestaurantsRemoteDataSource dataSource) {
        mDataSource = dataSource;
    }

    public void start() {
        loadData();
    }

    @BindingAdapter("app:restaurants")
    public static void setupTabs(RecyclerView recyclerView, ArrayList<Restaurant> items) {
        RestaurantsListFragment.RestaurantsAdapter adapter = (RestaurantsListFragment.RestaurantsAdapter)
                recyclerView.getAdapter();
        if (adapter != null) {
            adapter.replaceData(items);
        }
    }

    private void loadData() {
        mDataSource.getAllRestaurants(new UsersAndRestaurantsDataSource.GetAllRestaurantsCallback() {
            @Override
            public void onGetAllRestaurants(ArrayList<Restaurant> restaurants) {
                mRestaurants.clear();
                mRestaurants.addAll(restaurants);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(TAG, "onDataNotAvailable");
            }
        });
    }
}
