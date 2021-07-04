package com.example.fastfoodapp.eugene.ordersummary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Menu;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableArrayMap;
import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodapp.AppContainer;
import com.example.fastfoodapp.FastFoodApp;
import com.example.fastfoodapp.eugene.data.item.MenuItemMainInfo;
import com.example.fastfoodapp.eugene.data.item.ShoppingCartItem;
import com.example.fastfoodapp.eugene.menu.MenuPageFragment;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderSummaryViewModel {

    public static final String TAG = "OrderSummaryViewModel";

    private final Context mContext;

    public final ObservableArrayList<ShoppingCartItem> mSelectedItems = new ObservableArrayList<>();

    public final ObservableField<String> mTotalPrice = new ObservableField<>();

    private OrderSummaryNavigator mNavigator;

    public OrderSummaryViewModel(Context context) {
        mContext = context;
    }

    public void start() {

        loadData();
        calculateTotalPrice();
    }

    public void setNavigator(OrderSummaryNavigator navigator) {
        mNavigator = navigator;
    }

    public void continueShopping() {
        if (mNavigator != null) {
            mNavigator.continueShopping();
        }
    }

    @BindingAdapter("app:selectedItems")
    public static void setItems(RecyclerView recyclerView, ArrayList<ShoppingCartItem> items) {

        OrderSummaryFragment.SelectedItemsAdapter adapter = (OrderSummaryFragment.SelectedItemsAdapter)
                recyclerView.getAdapter();
        if (adapter != null) {
            adapter.replaceData(items);
        }
    }

    private void loadData() {
        AppContainer container = ((FastFoodApp) mContext.getApplicationContext()).appContainer;

        HashMap<MenuItemMainInfo, Integer> selectedItems = container.selectedItems;
        ArrayList<ShoppingCartItem> shoppingCartItems = new ArrayList<>();

        for (MenuItemMainInfo item : selectedItems.keySet()) {

            int quantity = selectedItems.get(item);
            ShoppingCartItem shoppingCartItem = new ShoppingCartItem(item.title, item.price,
                    item.imageUri, quantity);
            shoppingCartItems.add(shoppingCartItem);
        }

        mSelectedItems.clear();
        mSelectedItems.addAll(shoppingCartItems);
    }

    public void calculateTotalPrice() {

        double totalPrice = 0;
        for (ShoppingCartItem item : mSelectedItems) {

            String priceForOneStr = item.getPriceForOne();
            double priceForOne = Double.parseDouble(priceForOneStr.substring(0, priceForOneStr.length() - 2));

            totalPrice += priceForOne * item.getQuantity();
        }

        @SuppressLint("DefaultLocale") String totalPriceStr = String.format("%.2f", totalPrice) + " $";
        mTotalPrice.set(totalPriceStr);
    }
}
