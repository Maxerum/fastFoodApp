package com.example.fastfoodapp.userprofile.orderhistory;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.fastfoodapp.FastFoodApp;
import com.example.fastfoodapp.R;
import com.example.fastfoodapp.ViewModelHolder;
import com.example.fastfoodapp.data.UsersAndRestaurantsRemoteDataSource;
import com.example.fastfoodapp.util.ActivityUtils;

public class UserOrderHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_order_history_activity);

        setupToolbar();

        UserOrderHistoryViewModel viewModel = findOrCreateViewModel();

        UserOrderHistoryFragment fragment = findOrCreateFragment(viewModel);
    }

    private UserOrderHistoryFragment findOrCreateFragment(UserOrderHistoryViewModel viewModel) {
        UserOrderHistoryFragment orderHistoryFragment = (UserOrderHistoryFragment)
                getSupportFragmentManager().findFragmentById(R.id.container);
        if (orderHistoryFragment == null) {

            orderHistoryFragment = new UserOrderHistoryFragment(viewModel);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), orderHistoryFragment,
                    R.id.container, false);
        }
        return orderHistoryFragment;
    }

    private UserOrderHistoryViewModel findOrCreateViewModel() {
        @SuppressWarnings("unchecked")
        ViewModelHolder<UserOrderHistoryViewModel> holder = (ViewModelHolder<UserOrderHistoryViewModel>)
                getSupportFragmentManager().findFragmentByTag(UserOrderHistoryViewModel.TAG);

        if (holder != null) {
            return holder.getViewModel();
        } else {

            UsersAndRestaurantsRemoteDataSource dataSource = ((FastFoodApp) getApplication())
                    .appContainer.usersDataSource;

            UserOrderHistoryViewModel viewModel = new UserOrderHistoryViewModel(dataSource);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel), UserOrderHistoryViewModel.TAG, false);

            return viewModel;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.user_order_history_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
