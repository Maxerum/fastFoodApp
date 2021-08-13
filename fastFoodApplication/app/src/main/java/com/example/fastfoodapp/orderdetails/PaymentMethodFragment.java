package com.example.fastfoodapp.orderdetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.fastfoodapp.databinding.PaymentMethodsFragmentBinding;

import java.util.Objects;

public class PaymentMethodFragment extends Fragment {

    private PaymentMethodsFragmentBinding mBinding;

    private PaymentMethodViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = PaymentMethodsFragmentBinding.inflate(inflater, container, false);
        mBinding.setViewModel(mViewModel);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupToolbar();
    }

    public void setViewModel(PaymentMethodViewModel viewModel) {
        mViewModel = viewModel;
    }

    private void setupToolbar() {
        Toolbar toolbar = mBinding.paymentMethodsToolbar;
        ((AppCompatActivity) Objects.requireNonNull(getContext()))
                .setSupportActionBar(toolbar);

        Objects.requireNonNull(((AppCompatActivity) getContext())
                .getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
}
