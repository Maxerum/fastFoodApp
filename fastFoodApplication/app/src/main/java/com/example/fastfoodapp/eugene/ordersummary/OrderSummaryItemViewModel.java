package com.example.fastfoodapp.eugene.ordersummary;

import android.annotation.SuppressLint;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;

import com.bumptech.glide.Glide;
import com.example.fastfoodapp.eugene.data.item.ShoppingCartItem;

import java.util.Objects;

public class OrderSummaryItemViewModel {

    public final ObservableField<String> mMenuItemName = new ObservableField<>();

    public final ObservableField<String> mPrice = new ObservableField<>();

    public final ObservableField<String> mQuantity = new ObservableField<>();

    public final ObservableField<String> mImageUri = new ObservableField<>();

    private final ObservableField<ShoppingCartItem> mItem = new ObservableField<>();

    private ItemsUpdater mUpdater;

    public OrderSummaryItemViewModel(ShoppingCartItem item) {

        mItem.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mMenuItemName.set(Objects.requireNonNull(mItem.get()).getItemName());
                mImageUri.set(Objects.requireNonNull(mItem.get()).getImageUri());

                String quantityStr = "x" + Objects.requireNonNull(mItem.get()).getQuantity();
                mQuantity.set(quantityStr);

                String priceForOneStr = Objects.requireNonNull(mItem.get()).getPriceForOne();
                double priceForOne = Double.parseDouble(priceForOneStr.substring(0, priceForOneStr.length() - 2));
                String price = calculatePriceStr(priceForOne, Objects.requireNonNull(mItem.get()).getQuantity());

                mPrice.set(price);
            }
        });

        mItem.set(item);
    }

    @BindingAdapter("app:src")
    public static void setImage(ImageView view, String imageUri) {
        Glide.with(view.getContext())
                .load(imageUri).into(view);
    }

    public void setUpdater(ItemsUpdater updater) {
        mUpdater = updater;
    }

    public void removeItem() {
        if (mUpdater != null) {
            mUpdater.itemRemoved(mItem.get());
        }
    }

    @SuppressLint("DefaultLocale")
    private String calculatePriceStr(double priceForOne, int quantity) {
        double price = priceForOne * quantity;
        return String.format("%.2f", price) + " $";
    }
}
