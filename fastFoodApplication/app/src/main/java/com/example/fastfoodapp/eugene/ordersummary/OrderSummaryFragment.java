package com.example.fastfoodapp.eugene.ordersummary;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodapp.R;
import com.example.fastfoodapp.databinding.OrderSummaryFragmentBinding;
import com.example.fastfoodapp.databinding.OrderSummaryItemBinding;
import com.example.fastfoodapp.eugene.data.item.ShoppingCartItem;
import com.example.fastfoodapp.eugene.decorator.SpacingItemDecoration;

import java.util.ArrayList;

public class OrderSummaryFragment extends Fragment {

    public static final String TAG = "OrderSummaryFragment";

    public static final int SPAN_COUNT = 1;

    private OrderSummaryViewModel mViewModel;

    private OrderSummaryFragmentBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = OrderSummaryFragmentBinding.inflate(inflater, container, false);
        mBinding.setViewModel(mViewModel);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();

        mViewModel.start();
    }

    public void setViewModel(OrderSummaryViewModel viewModel) {
        mViewModel = viewModel;
    }

    static class SelectedItemsAdapter extends RecyclerView.Adapter<SelectedItemHolder>
            implements ItemsUpdater {

        private ArrayList<ShoppingCartItem> items;

        private final OrderSummaryViewModel mViewModel;

        public SelectedItemsAdapter(ArrayList<ShoppingCartItem> items,
                                    OrderSummaryViewModel viewModel) {

            mViewModel = viewModel;

            replaceData(items);
        }

        @NonNull
        @Override
        public SelectedItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            OrderSummaryItemBinding binding = OrderSummaryItemBinding.inflate(LayoutInflater
                    .from(parent.getContext()));

            return new SelectedItemHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull SelectedItemHolder holder, int position) {
            ShoppingCartItem item = items.get(position);

            if (item != null) {
                OrderSummaryItemViewModel viewModel = new OrderSummaryItemViewModel(item);
                viewModel.setUpdater(this);

                holder.bindData(viewModel);
            }
        }

        @Override
        public int getItemCount() {
            return items != null ? items.size() : 0;
        }

        @SuppressLint("NotifyDataSetChanged")
        public void replaceData(ArrayList<ShoppingCartItem> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public void itemRemoved(ShoppingCartItem item) {
//            int position = items.indexOf(item);

            mViewModel.mSelectedItems.remove(item);
            mViewModel.calculateTotalPrice();
        }
    }

    private static class SelectedItemHolder extends RecyclerView.ViewHolder {
        private final OrderSummaryItemBinding mBinding;

        public SelectedItemHolder(OrderSummaryItemBinding binding) {
            super(binding.getRoot());

            mBinding = binding;
        }

        public void bindData(OrderSummaryItemViewModel viewModel) {
            mBinding.setViewModel(viewModel);
            mBinding.executePendingBindings();
        }
    }

    private void setupRecyclerView() {

        RecyclerView recyclerView = mBinding.selectedItemsRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SelectedItemsAdapter adapter = new SelectedItemsAdapter(new ArrayList<>(0), mViewModel);
        recyclerView.setAdapter(adapter);

        SpacingItemDecoration decoration = new SpacingItemDecoration(
                (int) getResources().getDimension(R.dimen.shopping_cart_item_spacing), SPAN_COUNT);
        recyclerView.addItemDecoration(decoration);
    }
}
