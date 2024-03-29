package com.example.fastfoodapp.ordersummary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodapp.AppContainer;
import com.example.fastfoodapp.FastFoodApp;
import com.example.fastfoodapp.data.item.MenuItemMainInfo;
import com.example.fastfoodapp.data.item.ShoppingCartItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

        mSelectedItems.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<ShoppingCartItem>>() {
            @Override
            public void onChanged(ObservableList<ShoppingCartItem> sender) {
                Log.d(TAG, "onChanged!!!");
                if (mSelectedItems.size() == 0) {
                    if (mNavigator != null) {
                        mNavigator.continueShopping();
                    }
                }
            }

            @Override
            public void onItemRangeChanged(ObservableList<ShoppingCartItem> sender, int positionStart, int itemCount) {
            }

            @Override
            public void onItemRangeInserted(ObservableList<ShoppingCartItem> sender, int positionStart, int itemCount) {
            }

            @Override
            public void onItemRangeMoved(ObservableList<ShoppingCartItem> sender, int fromPosition, int toPosition, int itemCount) {
            }

            @Override
            public void onItemRangeRemoved(ObservableList<ShoppingCartItem> sender, int positionStart, int itemCount) {
                Log.d(TAG, "onRemoved!!!");
            }
        });
    }

    public void setNavigator(OrderSummaryNavigator navigator) {
        mNavigator = navigator;
    }

    public void continueShopping() {
        if (mNavigator != null) {
            mNavigator.continueShopping();
        }
    }

    public void proceedToPayment() {
        if (mNavigator != null) {
            mNavigator.proceedToPayment();
        }

        saveEditedOrder();
    }

    @BindingAdapter("app:selectedItems")
    public static void setItems(RecyclerView recyclerView, ArrayList<ShoppingCartItem> items) {
        OrderSummaryFragment.SelectedItemsAdapter adapter = (OrderSummaryFragment.SelectedItemsAdapter)
                recyclerView.getAdapter();
        if (adapter != null) {
            adapter.replaceData(items);
        }
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

    private void loadData() {
        AppContainer container = ((FastFoodApp) mContext.getApplicationContext()).appContainer;

        Map<MenuItemMainInfo, Integer> selectedItems = container.selectedItems;
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

    private void saveEditedOrder() {
        AppContainer container = ((FastFoodApp) mContext.getApplicationContext()).appContainer;
        Map<MenuItemMainInfo, Integer> savedItems = container.selectedItems;
        Map<MenuItemMainInfo, Integer> updatedItems = new HashMap<>();

        for (MenuItemMainInfo item : savedItems.keySet()) {

            String title = item.title;
            boolean removed = true;
            for (ShoppingCartItem cartItem : mSelectedItems) {
                if (cartItem.getItemName().equals(title)) {
                    removed = false;
                    break;
                }
            }
            if (!removed) {
                updatedItems.put(item, savedItems.get(item));
            }
        }
        container.selectedItems = updatedItems;
        container.totalPrice = mTotalPrice.get();
    }
}
