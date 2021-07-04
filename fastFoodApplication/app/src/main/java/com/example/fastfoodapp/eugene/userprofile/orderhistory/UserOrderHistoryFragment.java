package com.example.fastfoodapp.eugene.userprofile.orderhistory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodapp.databinding.OrderListItemBinding;
import com.example.fastfoodapp.databinding.PaymentMethodItemBinding;
import com.example.fastfoodapp.databinding.UserOrderHistoryFragmentBinding;
import com.example.fastfoodapp.eugene.cardsdialog.PaymentMethodViewModel;
import com.example.fastfoodapp.eugene.cardsdialog.PaymentMethodsDialog;
import com.example.fastfoodapp.eugene.cardsdialog.PaymentMethodsViewModel;
import com.example.fastfoodapp.eugene.data.UsersAndRestaurantsRemoteDataSource;
import com.example.fastfoodapp.eugene.data.order.Order;

import java.util.ArrayList;

public class UserOrderHistoryFragment extends Fragment {

    private UserOrderHistoryViewModel mViewModel;

    private UserOrderHistoryFragmentBinding mBinding;

    public UserOrderHistoryFragment(UserOrderHistoryViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = UserOrderHistoryFragmentBinding.inflate(inflater, container, false);
        mBinding.setViewModel(mViewModel);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupRecyclerView();
        mViewModel.start();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = mBinding.ordersRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(new OrdersAdapter(new ArrayList<>(0)));
    }

    public static class OrdersAdapter extends RecyclerView.Adapter<OrderViewHolder> {

        private ArrayList<Order> mOrders;

        public OrdersAdapter(ArrayList<Order> orders) {
            mOrders = orders;
        }

        @NonNull
        @Override
        public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            OrderListItemBinding binding = OrderListItemBinding
                    .inflate(LayoutInflater.from(parent.getContext()));

            return new OrderViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
            if (position < mOrders.size()) {
                Order order = mOrders.get(position);
                OrderRecordViewModel viewModel = new OrderRecordViewModel();
                viewModel.mOrder.set(order);

                holder.setViewModel(viewModel);
            }
        }

        @Override
        public int getItemCount() {
            return mOrders != null ? mOrders.size() : 0;
        }

        public void replaceData(ArrayList<Order> orders) {
            mOrders = orders;
            notifyDataSetChanged();
        }


    }

    private static class OrderViewHolder extends RecyclerView.ViewHolder {
        private final OrderListItemBinding mBinding;

        public OrderViewHolder(OrderListItemBinding binding) {
            super(binding.getRoot());

            mBinding = binding;
        }

        public void setViewModel(OrderRecordViewModel viewModel) {
            mBinding.setViewModel(viewModel);
            mBinding.executePendingBindings();
        }
    }
}
