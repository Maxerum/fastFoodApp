package com.example.fastfoodapp.menu;

import com.example.fastfoodapp.data.item.MenuItemMainInfo;

import java.util.HashMap;

public interface ShoppingCartNavigator {

    void openOrderSummaryActivity(HashMap<MenuItemMainInfo, Integer> selectedItems);
}
