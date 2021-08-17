package com.example.fastfoodapp.userprofile.orderhistory;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodapp.R;
import com.example.fastfoodapp.data.order.OrderStatus;
import com.example.fastfoodapp.databinding.OrderListItemBinding;
import com.example.fastfoodapp.databinding.UserOrderHistoryFragmentBinding;
import com.example.fastfoodapp.data.order.Order;
import com.example.fastfoodapp.decorator.SpacingItemDecoration;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UserOrderHistoryFragment extends Fragment {

    private static final int SPAN_COUNT = 1;

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

    private void setupRecyclerView() {
        RecyclerView recyclerView = mBinding.ordersRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(new OrdersAdapter(new ArrayList<>(0)));

        SpacingItemDecoration decoration = new SpacingItemDecoration(
                (int) getResources().getDimension(R.dimen.order_history_item_spacing), SPAN_COUNT);
        recyclerView.addItemDecoration(decoration);
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
                OrderRecordViewModel viewModel = new OrderRecordViewModel(order);

                holder.bindData(viewModel);
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

        @RequiresApi(api = Build.VERSION_CODES.M)
        public void bindData(OrderRecordViewModel viewModel) {
            mBinding.setViewModel(viewModel);

            TextView orderStatus = mBinding.orderStatus;
            if (!viewModel.mOrderStatus.get().equals(OrderStatus.COOKING.name())) {
                orderStatus.setTextAppearance(R.style.FastFoodApp_TextAppearance_Big_Green);
            } else {
                orderStatus.setTextAppearance(R.style.FastFoodApp_TextAppearance_Big_Red);
            }

            mBinding.executePendingBindings();
        }
    }
}
