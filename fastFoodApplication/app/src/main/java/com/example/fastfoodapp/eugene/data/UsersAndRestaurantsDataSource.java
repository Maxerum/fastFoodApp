package com.example.fastfoodapp.eugene.data;


import java.util.ArrayList;
import java.util.List;

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

    void addNewUser(String uid);

    void checkIfUserExists(String uid, CheckIfUserExistsCallback callback);

    void getAllRestaurants(GetAllRestaurantsCallback callback);

    void addNewCard(String uid, CardInfo card, String cardName, AddCardCallback callback);
}
