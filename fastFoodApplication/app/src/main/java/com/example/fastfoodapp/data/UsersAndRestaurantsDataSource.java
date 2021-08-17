package com.example.fastfoodapp.data;


import com.example.fastfoodapp.data.order.Order;

import java.util.ArrayList;
import java.util.List;

public interface UsersAndRestaurantsDataSource {

    interface CheckIfUserExistsCallback {

        void onUserChecked(boolean exists);

        void onDataNotAvailable();
    }

    interface GetAllRestaurantsCallback {

        void onGetAllRestaurants(List<Restaurant> restaurants);

        void onDataNotAvailable();
    }

    interface OrderPlaceCallback {

        void onSuccess();

        void onFailure();
    }

    interface GetUserOrderHistoryCallback {

        void onGetUserOrderHistory(List<Order> orders);

        void onDataNotAvailable();
    }

    interface ClearUserOrderHistoryCallback {

        void onSuccess();

        void onFailure();
    }

    void checkIfUserExists(String uid, CheckIfUserExistsCallback callback);

    void getAllRestaurants(GetAllRestaurantsCallback callback);

    void placeOrder(Order order, String uid, OrderPlaceCallback callback);

    void getUserOrderHistory(String uid, GetUserOrderHistoryCallback callback);

    void clearUserOrderHistory(String uid, ClearUserOrderHistoryCallback callback);
}
