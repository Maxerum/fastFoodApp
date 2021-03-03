package com.example.fastfoodapp.eugene.menu;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodapp.R;
import com.example.fastfoodapp.databinding.MenuItemBinding;
import com.example.fastfoodapp.databinding.MenuPageFragmentBinding;
import com.example.fastfoodapp.eugene.data.MenuItem;
import com.example.fastfoodapp.eugene.util.SpacingItemDecoration;

import java.util.ArrayList;

public class MenuPageFragment extends Fragment {
    private static final String TAG = "MenuPageFragment";

    private static final int SPAN_COUNT = 2;

    private MenuPageFragmentBinding mMenuPageFragmentBinding;

    private MenuPageViewModel mMenuPageViewModel;

    @Override
    public void onResume() {
        super.onResume();
        mMenuPageViewModel.start(null);
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
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = mMenuPageFragmentBinding.recyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), SPAN_COUNT));
        recyclerView.setAdapter(new MenuItemsAdapter(new ArrayList<>(0), getContext()));
        SpacingItemDecoration decoration = new SpacingItemDecoration((int) getResources().getDimension(R.dimen.grid_item_spacing),
                SPAN_COUNT);
        recyclerView.addItemDecoration(decoration);
    }

    public void setViewModel(MenuPageViewModel viewModel) {
        mMenuPageViewModel = viewModel;
    }

    public static class MenuItemsAdapter extends RecyclerView.Adapter<MenuItemHolder> {

        private ArrayList<MenuItem> mMenuItems;

        private Context mContext;

        // TODO: need to create MenuItem class, and pass list of MenuItem objects as a parameter
        public MenuItemsAdapter(ArrayList<MenuItem> menuItems, Context context) {
            mContext = context;

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
            MenuItem menuItem = null;
            if (mMenuItems != null && !mMenuItems.isEmpty()) {
                menuItem = mMenuItems.get(position);
            }
            if (menuItem != null) {
                MenuItemViewModel viewModel = new MenuItemViewModel(mContext);
                viewModel.setMenuItem(menuItem);

                holder.bindData(viewModel);
            }
        }

        @Override
        public int getItemCount() {
            return 4;
        }

        public void replaceData(ArrayList<MenuItem> menuItems) {
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
