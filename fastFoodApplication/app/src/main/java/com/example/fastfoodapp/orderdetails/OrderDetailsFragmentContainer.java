package com.example.fastfoodapp.orderdetails;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.fastfoodapp.AppContainer;
import com.example.fastfoodapp.FastFoodApp;
import com.example.fastfoodapp.R;
import com.example.fastfoodapp.ordersummary.PaymentSheetController;
import com.example.fastfoodapp.util.ActivityUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class OrderDetailsFragmentContainer extends BottomSheetDialogFragment {

    public static final String TAG = "OrderDetailsFragmentContainer";

    private PaymentSheetController paymentSheetController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.order_details_fragment_container, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppContainer container = ((FastFoodApp) requireContext()
                .getApplicationContext()).appContainer;

        FragmentManager manager = getChildFragmentManager();
        OrderDetailsViewModel viewModel = new OrderDetailsViewModel(container.totalPrice, getContext());
        if (paymentSheetController != null) {
            viewModel.setPaymentSheetController(paymentSheetController);
        }
        OrderDetailsFragment fragment = new OrderDetailsFragment();
        fragment.setViewModel(viewModel);
        viewModel.setNavigator(fragment);
        viewModel.setMessageChangedCallback(fragment);

        ActivityUtils.addFragmentToActivity(manager, fragment, R.id.my_container, true);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new BottomSheetDialog(getContext(), getTheme()) {
            @Override
            public void onBackPressed() {
                Fragment fragment = getChildFragmentManager().findFragmentById(R.id.my_container);
                FragmentManager manager = getChildFragmentManager();
                if (fragment != null && manager.getBackStackEntryCount() > 1) {
                    manager.popBackStack();
                } else {
                    super.onBackPressed();
                }
            }
        };
    }

    public void setPaymentSheetController(PaymentSheetController controller) {
        paymentSheetController = controller;
    }
}
