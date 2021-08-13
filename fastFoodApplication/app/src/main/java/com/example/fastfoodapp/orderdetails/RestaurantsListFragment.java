package com.example.fastfoodapp.orderdetails;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodapp.databinding.RestaurantListItemBinding;
import com.example.fastfoodapp.databinding.RestaurantsListFragmentBinding;
import com.example.fastfoodapp.data.Restaurant;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RestaurantsListFragment extends BottomSheetDialogFragment {

    public static final String TAG = "RestaurantsListFragment";

    private RestaurantsListFragmentBinding mBinding;

    private RestaurantsListViewModel mViewModel;

    private ItemNavigator mNavigator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = RestaurantsListFragmentBinding.inflate(inflater, container, false);
        mBinding.setViewModel(mViewModel);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupToolbar();

        setupRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();

        mViewModel.start();
    }

    public void setViewModel(RestaurantsListViewModel viewModel) {
        mViewModel = viewModel;
    }

    public void setNavigator(ItemNavigator navigator) {
        mNavigator = navigator;
    }

    public static class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantHolder> {

        private List<Restaurant> mRestaurants;

        private ItemNavigator mNavigator;

        public RestaurantsAdapter(List<Restaurant> restaurants, ItemNavigator navigator) {
            mNavigator = navigator;

            replaceData(restaurants);
        }

        @NonNull
        @Override
        public RestaurantHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            RestaurantListItemBinding binding = RestaurantListItemBinding
                    .inflate(LayoutInflater.from(parent.getContext()));

            return new RestaurantHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull RestaurantHolder holder, int position) {
            Restaurant restaurant = mRestaurants.get(position);
            RestaurantItemViewModel viewModel = new RestaurantItemViewModel(restaurant);
            viewModel.setNavigator(mNavigator);

            holder.bindData(viewModel);
        }

        @Override
        public int getItemCount() {
            return mRestaurants != null ? mRestaurants.size() : 0;
        }

        @SuppressLint("NotifyDataSetChanged")
        public void replaceData(List<Restaurant> restaurants) {
            mRestaurants = restaurants;
            notifyDataSetChanged();
        }
    }

    private static class RestaurantHolder extends RecyclerView.ViewHolder {
        private final RestaurantListItemBinding mBinding;

        public RestaurantHolder(RestaurantListItemBinding binding) {
            super(binding.getRoot());

            mBinding = binding;
        }

        public void bindData(RestaurantItemViewModel viewModel) {
            mBinding.setViewModel(viewModel);
            mBinding.executePendingBindings();
        }
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = mBinding.restaurantsRecyclerView;

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        RestaurantsAdapter adapter = new RestaurantsAdapter(new ArrayList<>(), mNavigator);
        recyclerView.setAdapter(adapter);
    }

    private void setupToolbar() {
        Toolbar toolbar = mBinding.restaurantsToolbar;
        ((AppCompatActivity) Objects.requireNonNull(getContext()))
                .setSupportActionBar(toolbar);

        Objects.requireNonNull(((AppCompatActivity) getContext())
                .getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
}
