package com.example.fastfoodapp.eugene.menu;

import android.content.Context;

import androidx.databinding.Observable;
import androidx.databinding.ObservableField;

import com.example.fastfoodapp.R;
import com.example.fastfoodapp.eugene.data.MenuItem;

public class MenuItemViewModel {

    public final ObservableField<String> mTitle = new ObservableField<>();

    public final ObservableField<String> mPrice = new ObservableField<>();

    public final ObservableField<Integer> mQuantity = new ObservableField<>();

    public final ObservableField<MenuItem> mMenuItem = new ObservableField<>();

    private Context mContext;

    public MenuItemViewModel(Context context) {
        mContext = context;

        mMenuItem.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                MenuItem menuItem = mMenuItem.get();
                if (menuItem != null) {
                    mTitle.set(menuItem.getTitle());
                    mPrice.set(menuItem.getPrice());
                } else {
                    mTitle.set(mContext.getResources().getString(R.string.no_data_error_message));
                    mPrice.set(mContext.getResources().getString(R.string.no_data_error_message));
                }
                mQuantity.set(mContext.getResources().getInteger(R.integer.initial_menu_item_quantity));
            }
        });
    }

    public void setMenuItem(MenuItem menuItem) {
        mMenuItem.set(menuItem);
    }
}
