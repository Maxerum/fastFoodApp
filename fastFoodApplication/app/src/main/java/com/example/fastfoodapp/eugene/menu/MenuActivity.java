package com.example.fastfoodapp.eugene.menu;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fastfoodapp.AppContainer;
import com.example.fastfoodapp.FastFoodApp;
import com.example.fastfoodapp.Maxerum.OrderPayingActivity;
import com.example.fastfoodapp.R;
import com.example.fastfoodapp.eugene.ViewModelHolder;
import com.example.fastfoodapp.eugene.ordersummary.OrderSummaryActivity;
import com.example.fastfoodapp.eugene.userprofile.UserProfileInfoActivity;
import com.example.fastfoodapp.eugene.data.item.MenuItemMainInfo;
import com.example.fastfoodapp.eugene.util.ActivityUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;


public class MenuActivity extends AppCompatActivity implements ShoppingCartNavigator,
        UserProfileNavigator{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        MenuFragment fragment = findOrCreateFragment();

        MenuViewModel viewModel = findOrCreateViewModel();
        viewModel.setShoppingCartNavigator(this);

        fragment.setViewModel(viewModel);
    }

    private MenuFragment findOrCreateFragment() {
        MenuFragment menuFragment = (MenuFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if (menuFragment == null) {

            menuFragment = MenuFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), menuFragment,
                    R.id.fragment_container);
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
                    ViewModelHolder.createContainer(viewModel), MenuViewModel.TAG);

            return viewModel;
        }
    }

    @Override
    public void openOrderSummaryActivity(HashMap<MenuItemMainInfo, Integer> selectedItems) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            ((FastFoodApp) getApplication()).appContainer.selectedItems = selectedItems;

            Intent intent = new Intent(this, OrderSummaryActivity.class);

            startActivity(intent);
        } else {
            Snackbar.make(findViewById(android.R.id.content), "You have signed out",
                    Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void openUserProfileActivity() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, UserProfileInfoActivity.class);
            startActivity(intent);
        } else {
            Snackbar.make(findViewById(android.R.id.content), "You have signed out",
                    Snackbar.LENGTH_LONG).show();
        }
    }
}
