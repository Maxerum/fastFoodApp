package com.example.fastfoodapp.eugene.ordersummary;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.fastfoodapp.R;
import com.example.fastfoodapp.eugene.ViewModelHolder;
import com.example.fastfoodapp.eugene.util.ActivityUtils;

import java.util.Objects;

public class OrderSummaryActivity extends AppCompatActivity implements OrderSummaryNavigator {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_summary_activity);

        setupToolbar();

        OrderSummaryFragment fragment = findOrCreateFragment();

        OrderSummaryViewModel viewModel = findOrCreateViewModel();
        viewModel.setNavigator(this);
        fragment.setViewModel(viewModel);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void continueShopping() {
        onBackPressed();
    }

    @Override
    public void proceedToPayment() {
        // TODO: proceed to select payment method and restaurant
    }

    private OrderSummaryFragment findOrCreateFragment() {
        OrderSummaryFragment fragment = (OrderSummaryFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        if (fragment == null) {

            fragment = new OrderSummaryFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment,
                    R.id.fragment_container);
        }
        return fragment;
    }

    private OrderSummaryViewModel findOrCreateViewModel() {
        @SuppressWarnings("unchecked")
        ViewModelHolder<OrderSummaryViewModel> holder = (ViewModelHolder<OrderSummaryViewModel>)
                getSupportFragmentManager().findFragmentByTag(OrderSummaryViewModel.TAG);

        if (holder != null) {
            return holder.getViewModel();
        } else {

            OrderSummaryViewModel viewModel = new OrderSummaryViewModel(getApplicationContext());

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel), OrderSummaryViewModel.TAG);

            return viewModel;
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.order_summary_toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
}
