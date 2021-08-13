package com.example.fastfoodapp.menu;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.fastfoodapp.R;
import com.example.fastfoodapp.data.MenuItemsDataSource;
import com.example.fastfoodapp.data.MenuItemsLocalDataSource;
import com.example.fastfoodapp.data.item.MenuItem;
import com.example.fastfoodapp.data.item.MenuItemMainInfo;

public class MenuItemViewModel {
    public static final String TAG = "MenuItemViewModel";

    public final ObservableField<String> mTitle = new ObservableField<>();

    public final ObservableField<String> mPrice = new ObservableField<>();

    public final ObservableField<String> mImageUri = new ObservableField<>();

    public final ObservableField<Integer> mQuantity = new ObservableField<>();

    public final ObservableField<MenuItemMainInfo> mMenuItem = new ObservableField<>();

    private final MenuItemsLocalDataSource mDataSource;

    private final Context mContext;

    public MenuItemViewModel(Context context, MenuItemsLocalDataSource dataSource) {
        mContext = context;
        mDataSource = dataSource;

        mMenuItem.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                MenuItemMainInfo menuItem = mMenuItem.get();
                if (menuItem != null) {
                    mTitle.set(menuItem.title);
                    mPrice.set(menuItem.price);
                    mImageUri.set(menuItem.imageUri);
                } else {
                    mTitle.set(mContext.getResources().getString(R.string.no_data_error_message));
                    mPrice.set(mContext.getResources().getString(R.string.no_data_error_message));
                }
                mQuantity.set(mContext.getResources().getInteger(R.integer.initial_menu_item_quantity));
            }
        });
    }

    public void setMenuItem(MenuItemMainInfo menuItem) {
        mMenuItem.set(menuItem);
    }

    @BindingAdapter("android:src")
    public static void setImage(ImageView view, String imageUri) {
        Glide.with(view.getContext()).load(imageUri)
                .apply(new RequestOptions().centerCrop()).into(view);
    }

    public void onPlusButtonClicked(View view) {
        mQuantity.set(mQuantity.get() + 1);

        AnimatorSet reduceAndRestoreSize = (AnimatorSet) AnimatorInflater
                .loadAnimator(mContext, R.animator.reduce_and_restore_size);
        reduceAndRestoreSize.setTarget(view);
        reduceAndRestoreSize.start();
    }

    public void onMinusButtonClicked(View view) {
        if (mQuantity.get() > 0) {
            mQuantity.set(mQuantity.get() - 1);
        }

        AnimatorSet reduceAndRestoreSize = (AnimatorSet) AnimatorInflater
                .loadAnimator(mContext, R.animator.reduce_and_restore_size);
        reduceAndRestoreSize.setTarget(view);
        reduceAndRestoreSize.start();
    }

    public void onMenuItemClicked(View view) {
        mDataSource.getMenuItemByTitle(mTitle.get(), new MenuItemsDataSource.GetMenuItemCallback() {
            @Override
            public void onMenuItemLoaded(MenuItem item) {
                InfoDialogViewModel viewModel = new InfoDialogViewModel();
                viewModel.setMenuItem(item);

                MenuItemInfoDialog dialog = new MenuItemInfoDialog();
                dialog.setViewModel(viewModel);

                FragmentManager manager = ((AppCompatActivity) mContext).getSupportFragmentManager();
                dialog.show(manager, MenuItemInfoDialog.TAG);
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(TAG, "Data not available");
            }
        });
    }
}
