package com.example.fastfoodapp.orderdetails;

import androidx.databinding.Observable;
import androidx.databinding.ObservableField;

import com.example.fastfoodapp.data.Restaurant;

import java.util.Objects;

public class RestaurantItemViewModel {

    public static final String TAG = "RestaurantItemViewModel";

    public final ObservableField<String> mRestaurantName = new ObservableField<>();

    private final ObservableField<Restaurant> mRestaurant = new ObservableField<>();

    private ItemNavigator mNavigator;

    public RestaurantItemViewModel(Restaurant restaurant) {
        mRestaurant.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mRestaurantName.set(Objects.requireNonNull(mRestaurant.get()).getName());
            }
        });

        mRestaurant.set(restaurant);
    }

    public void selectRestaurant() {
        if (mNavigator != null) {
            mNavigator.restaurantSelected(mRestaurant.get());
        }
    }

    public void setNavigator(ItemNavigator navigator) {
        mNavigator = navigator;
    }
}
