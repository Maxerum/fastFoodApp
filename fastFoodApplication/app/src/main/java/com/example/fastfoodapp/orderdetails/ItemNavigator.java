package com.example.fastfoodapp.orderdetails;

import com.example.fastfoodapp.data.Restaurant;
import com.example.fastfoodapp.data.order.PaymentMethod;

public interface ItemNavigator {

    void restaurantSelected(Restaurant restaurant);

    void paymentMethodSelected(PaymentMethod paymentMethod);
}
