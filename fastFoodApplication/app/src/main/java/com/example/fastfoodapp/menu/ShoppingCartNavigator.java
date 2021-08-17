package com.example.fastfoodapp.menu;

import com.example.fastfoodapp.data.item.MenuItemMainInfo;

import java.util.HashMap;
import java.util.Map;

public interface ShoppingCartNavigator {

    void openOrderSummaryActivity(Map<MenuItemMainInfo, Integer> selectedItems);

    void onMessageChanged(String message);
}
