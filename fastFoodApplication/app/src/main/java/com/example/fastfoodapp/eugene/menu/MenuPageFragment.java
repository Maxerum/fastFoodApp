package com.example.fastfoodapp.eugene.menu;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodapp.AppContainer;
import com.example.fastfoodapp.FastFoodApp;
import com.example.fastfoodapp.R;
import com.example.fastfoodapp.databinding.MenuItemBinding;
import com.example.fastfoodapp.databinding.MenuPageFragmentBinding;
import com.example.fastfoodapp.eugene.data.MenuItemsLocalDataSource;
import com.example.fastfoodapp.eugene.data.item.MenuItemMainInfo;
import com.example.fastfoodapp.eugene.util.SpacingItemDecoration;

import java.util.ArrayList;

public class MenuPageFragment extends Fragment {
    private static final String TAG = "MenuPageFragment";

    private static final int SPAN_COUNT = 2;

    private MenuPageFragmentBinding mMenuPageFragmentBinding;

    private MenuPageViewModel mMenuPageViewModel;

    public MenuPageFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMenuPageFragmentBinding = MenuPageFragmentBinding.inflate(inflater, container, false);
        mMenuPageFragmentBinding.setViewModel(mMenuPageViewModel);

        return mMenuPageFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
        mMenuPageViewModel.start();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = mMenuPageFragmentBinding.recyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), SPAN_COUNT));

        AppContainer container = ((FastFoodApp) getActivity().getApplication()).appContainer;
        recyclerView.setAdapter(new MenuItemsAdapter(new ArrayList<>(0), getContext(),
                container.dataSource));

        SpacingItemDecoration decoration = new SpacingItemDecoration((int) getResources().getDimension(R.dimen.grid_item_spacing),
                SPAN_COUNT);
        recyclerView.addItemDecoration(decoration);
    }

    public void setViewModel(MenuPageViewModel viewModel) {
        mMenuPageViewModel = viewModel;
    }

    public static class MenuItemsAdapter extends RecyclerView.Adapter<MenuItemHolder> {

        private ArrayList<MenuItemMainInfo> mMenuItems;

        private final Context mContext;

        private final MenuItemsLocalDataSource mDataSource;

        // TODO: need to create MenuItem class, and pass list of MenuItem objects as a parameter
        public MenuItemsAdapter(ArrayList<MenuItemMainInfo> menuItems, Context context,
                                MenuItemsLocalDataSource dataSource) {
            mContext = context;
            mDataSource = dataSource;

            replaceData(menuItems);
        }

        @NonNull
        @Override
        public MenuItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            MenuItemBinding menuItemBinding = MenuItemBinding.inflate(LayoutInflater.from(parent.getContext()));
            return new MenuItemHolder(menuItemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull MenuItemHolder holder, int position) {
            MenuItemMainInfo menuItem = null;
            if (mMenuItems != null && position < mMenuItems.size()) {
                menuItem = mMenuItems.get(position);
            }
            if (menuItem != null) {
                MenuItemViewModel viewModel = new MenuItemViewModel(mContext, mDataSource);
                viewModel.setMenuItem(menuItem);

                holder.bindData(viewModel);
            }
        }

        @Override
        public int getItemCount() {
            return mMenuItems.size();
        }

        public void replaceData(ArrayList<MenuItemMainInfo> menuItems) {
            mMenuItems = menuItems;
            notifyDataSetChanged();
        }
    }

    private static class MenuItemHolder extends RecyclerView.ViewHolder {
        private final MenuItemBinding mBinding;

        public MenuItemHolder(MenuItemBinding binding) {
            super(binding.getRoot());

            mBinding = binding;
        }

        public void bindData(MenuItemViewModel viewModel) {
            mBinding.setViewModel(viewModel);
            mBinding.executePendingBindings();
        }
    }
}
