package com.example.fastfoodapp.eugene.data;


import com.example.fastfoodapp.eugene.data.order.Order;

import java.util.ArrayList;

public interface UsersAndRestaurantsDataSource {

    interface CheckIfUserExistsCallback {

        void onUserChecked(boolean exists);

        void onDataNotAvailable();
    }

    interface GetAllRestaurantsCallback {

        void onGetAllRestaurants(ArrayList<Restaurant> restaurants);

        void onDataNotAvailable();
    }

    interface AddCardCallback {

        void onSuccess();

        void onFailure();
    }

    interface GetUserPaymentMethodsNamesCallback {

        void onGetAllUserPaymentMethodsNames(ArrayList<String> paymentMethodsNames);

        void onDataNotAvailable();
    }

    interface OrderPlaceCallback {

        void onSuccess();

        void onFailure();
    }

    interface GetUserOrderHistoryCallback {

        void onGetUserOrderHistory(ArrayList<Order> orders);

        void onDataNotAvailable();
    }

    interface ClearUserOrderHistoryCallback {

        void onSuccess();

        void onFailure();
    }

    void addNewUser(String uid);

    void checkIfUserExists(String uid, CheckIfUserExistsCallback callback);

    void getAllRestaurants(GetAllRestaurantsCallback callback);

    void addNewCard(String uid, CardInfo card, String cardName, AddCardCallback callback);

    void getUserPaymentMethodsNames(String uid, GetUserPaymentMethodsNamesCallback callback);

    void placeOrder(Order order, String uid, OrderPlaceCallback callback);

    void getAllUserOrderHistory(String uid, GetUserOrderHistoryCallback callback);

    void clearAllUserOrderHistory(String uid, ClearUserOrderHistoryCallback callback);
}
