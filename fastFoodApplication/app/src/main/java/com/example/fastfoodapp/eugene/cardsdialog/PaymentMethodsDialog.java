 package com.example.fastfoodapp.eugene.cardsdialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodapp.FastFoodApp;
import com.example.fastfoodapp.databinding.PaymentMethodItemBinding;
import com.example.fastfoodapp.databinding.PaymentMethodsFragmentBinding;
import com.example.fastfoodapp.eugene.data.Restaurant;
import com.example.fastfoodapp.eugene.data.UsersAndRestaurantsRemoteDataSource;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Objects;

public class PaymentMethodsDialog extends BottomSheetDialogFragment {

    public static final String TAG = "PaymentMethodsDialog";

    private PaymentMethodsFragmentBinding mBinding;

    private PaymentMethodsViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = PaymentMethodsFragmentBinding.inflate(inflater, container, false);
        mBinding.setViewModel(mViewModel);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();

        mViewModel.start();
    }

    public void setViewModel(PaymentMethodsViewModel viewModel) {
        mViewModel = viewModel;
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = mBinding.paymentMethodsRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        UsersAndRestaurantsRemoteDataSource dataSource = ((FastFoodApp) Objects.requireNonNull(getActivity())
                .getApplication()).appContainer.usersDataSource;
        PaymentMethodsAdapter adapter = new PaymentMethodsAdapter(new ArrayList<>(0),
                new ArrayList<>(0), dataSource, mViewModel);
        recyclerView.setAdapter(adapter);
    }

    public static class PaymentMethodsAdapter extends RecyclerView.Adapter<PaymentMethodHolder> {

        private final UsersAndRestaurantsRemoteDataSource mDataSource;

        private ArrayList<String> paymentMethodsNames;

        private PaymentMethodsViewModel mViewModel;

        private ArrayList<PaymentMethodViewModel> mViewModels;

        public PaymentMethodsAdapter(ArrayList<String> paymentMethodsNames,
                                     ArrayList<PaymentMethodViewModel> viewModels,
                                     UsersAndRestaurantsRemoteDataSource dataSource,
                                     PaymentMethodsViewModel viewModel) {
            mDataSource = dataSource;
            mViewModels = viewModels;
            mViewModel = viewModel;

            replaceData(paymentMethodsNames);
        }

        public void replaceData(ArrayList<String> paymentMethodsNames) {
            this.paymentMethodsNames = paymentMethodsNames;
            mViewModels = mViewModel.getViewModels();

            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public PaymentMethodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            PaymentMethodItemBinding binding = PaymentMethodItemBinding.inflate(LayoutInflater
                    .from(parent.getContext()));
            return new PaymentMethodHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull PaymentMethodHolder holder, int position) {
            PaymentMethodViewModel viewModel = mViewModels.get(position);

            holder.setViewModel(viewModel);
        }

        @Override
        public int getItemCount() {
            return paymentMethodsNames != null ? paymentMethodsNames.size() : 0;
        }
    }

    private static class PaymentMethodHolder extends RecyclerView.ViewHolder {
        private final PaymentMethodItemBinding mBinding;

        public PaymentMethodHolder(PaymentMethodItemBinding binding) {
            super(binding.getRoot());

            mBinding = binding;
        }

        public void setViewModel(PaymentMethodViewModel viewModel) {
            mBinding.setViewModel(viewModel);
            mBinding.executePendingBindings();
        }
    }
}
