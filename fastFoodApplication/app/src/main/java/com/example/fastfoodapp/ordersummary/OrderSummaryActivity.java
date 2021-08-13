package com.example.fastfoodapp.ordersummary;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.example.fastfoodapp.R;
import com.example.fastfoodapp.ViewModelHolder;
import com.example.fastfoodapp.orderdetails.OrderDetailsFragmentContainer;
import com.example.fastfoodapp.util.ActivityUtils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import java.util.Objects;

public class OrderSummaryActivity extends AppCompatActivity implements OrderSummaryNavigator,
        PaymentSheetController {

    private static final String TAG = "OrderSummaryActivity";

    private PaymentSheet paymentSheet;
    private PaymentSheetCallback paymentSheetCallback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_summary_activity);

        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);

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
        OrderDetailsFragmentContainer fragment = new OrderDetailsFragmentContainer();
        fragment.setPaymentSheetController(this);

        fragment.show(getSupportFragmentManager(), OrderDetailsFragmentContainer.TAG);
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
        BottomSheetDialogFragment fragment = (BottomSheetDialogFragment)
                manager.findFragmentByTag(OrderDetailsFragmentContainer.TAG);

        if (fragment != null) {
            Objects.requireNonNull(fragment.getDialog()).onBackPressed();
            return;
        }

        super.onBackPressed();
    }

    private OrderSummaryFragment findOrCreateFragment() {
        OrderSummaryFragment fragment = (OrderSummaryFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        if (fragment == null) {

            fragment = new OrderSummaryFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment,
                    R.id.fragment_container, false);
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
                    ViewModelHolder.createContainer(viewModel), OrderSummaryViewModel.TAG, false);

            return viewModel;
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.order_summary_toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setPaymentSheetCallback(PaymentSheetCallback callback) {
        paymentSheetCallback = callback;
    }

    @Override
    public void presentPaymentSheet(String paymentIntentSecret, String ephemeralKey, String customerId) {
        paymentSheet.presentWithPaymentIntent(paymentIntentSecret,
                new PaymentSheet.Configuration("Example, Inc.",
                        new PaymentSheet.CustomerConfiguration(customerId, ephemeralKey)));
    }

    private void onPaymentSheetResult(PaymentSheetResult result) {
        if (paymentSheetCallback != null) {
            paymentSheetCallback.onPaymentSheetResult(result);
        }
    }
}
