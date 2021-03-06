package com.example.fastfoodapp.eugene.menu;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.fastfoodapp.R;
import com.example.fastfoodapp.eugene.data.item.MenuItem;


public class InfoDialogViewModel {

    public ObservableField<String> mTitle = new ObservableField<>();

    public ObservableField<String> mImageUri = new ObservableField<>();

    public ObservableField<String> mCalories = new ObservableField<>();

    public ObservableField<String> mTotalFat = new ObservableField<>();

    public ObservableField<String> mTotalCarbs = new ObservableField<>();

    public ObservableField<String> mProtein = new ObservableField<>();

    public ObservableField<String> mIngredients = new ObservableField<>();

    public ObservableField<MenuItem> mMenuItem = new ObservableField<>();

    public InfoDialogViewModel() {
        mMenuItem.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mTitle.set(mMenuItem.get().getTitle());
                mImageUri.set(mMenuItem.get().getImageUri());
                mCalories.set(mMenuItem.get().getCalories());
                mTotalFat.set(mMenuItem.get().getTotalFat());
                mTotalCarbs.set(mMenuItem.get().getTotalCarbs());
                mProtein.set(mMenuItem.get().getProtein());
                mIngredients.set("*Some ingredients*");
            }
        });
    }

    public void setMenuItem(MenuItem item) {
        mMenuItem.set(item);
    }

    @BindingAdapter("app:source")
    public static void setImage(ImageView view, String imageUri) {
        Glide.with(view.getContext()).load(imageUri).placeholder(R.drawable.burger_image)
                .apply(new RequestOptions().centerCrop()).into(view);

        Log.d("TAG", "setImage " + imageUri);
    }
}
