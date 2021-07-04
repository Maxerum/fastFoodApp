package com.example.fastfoodapp.eugene.menu;

import com.example.fastfoodapp.eugene.data.item.MenuItemMainInfo;

import java.util.HashMap;

public interface ShoppingCartNavigator {

    void openOrderSummaryActivity(HashMap<MenuItemMainInfo, Integer> selectedItems);
}
