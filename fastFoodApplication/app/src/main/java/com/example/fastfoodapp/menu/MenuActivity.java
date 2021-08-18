package com.example.fastfoodapp.menu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fastfoodapp.AppContainer;
import com.example.fastfoodapp.FastFoodApp;
import com.example.fastfoodapp.R;
import com.example.fastfoodapp.ViewModelHolder;
import com.example.fastfoodapp.orderdetails.ToastMessageChangedCallback;
import com.example.fastfoodapp.ordersummary.OrderSummaryActivity;
import com.example.fastfoodapp.userprofile.UserProfileInfoActivity;
import com.example.fastfoodapp.data.item.MenuItemMainInfo;
import com.example.fastfoodapp.util.ActivityUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;


public class MenuActivity extends AppCompatActivity implements ShoppingCartNavigator,
        UserProfileNavigator, ToastMessageChangedCallback {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        MenuFragment fragment = findOrCreateFragment();

        MenuViewModel viewModel = findOrCreateViewModel();
        viewModel.setShoppingCartNavigator(this);

        fragment.setViewModel(viewModel);
    }

    @Override
    public void openOrderSummaryActivity(Map<MenuItemMainInfo, Integer> selectedItems) {
        ((FastFoodApp) getApplication()).appContainer.selectedItems = selectedItems;
        Intent intent = new Intent(this, OrderSummaryActivity.class);

        startActivity(intent);
    }

    @Override
    public void openUserProfileActivity() {
        Intent intent = new Intent(this, UserProfileInfoActivity.class);
        startActivity(intent);
    }

    @Override
    public void onMessageChanged(String message) {
        if (message != null && !message.isEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    private MenuFragment findOrCreateFragment() {
        MenuFragment menuFragment = (MenuFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if (menuFragment == null) {

            menuFragment = MenuFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), menuFragment,
                    R.id.fragment_container, false);
        }
        return menuFragment;
    }

    private MenuViewModel findOrCreateViewModel() {
        @SuppressWarnings("unchecked")
        ViewModelHolder<MenuViewModel> holder = (ViewModelHolder<MenuViewModel>)
                getSupportFragmentManager().findFragmentByTag(MenuViewModel.TAG);

        if (holder != null) {
            return holder.getViewModel();
        } else {

            AppContainer container = ((FastFoodApp) getApplication()).appContainer;
            MenuViewModel viewModel = new MenuViewModel(container.dataSource);
            container.dataSource.prepopulateDatabaseIfNeeded();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel), MenuViewModel.TAG, false);

            return viewModel;
        }
    }
}
