package com.example.fastfoodapp.eugene.menu;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
    public static final String TAG = "MenuPageFragment";

    private static final String QUANTITY_KEY = "quantity arg";

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
        if (mMenuPageViewModel.getViewModels() == null) {
            mMenuPageViewModel.start();
            Log.d(TAG, "we are here");
        }
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = mMenuPageFragmentBinding.recyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), SPAN_COUNT));

        AppContainer container = ((FastFoodApp) getActivity().getApplication()).appContainer;
        ArrayList<MenuItemViewModel> itemViewModels = mMenuPageViewModel.getViewModels();

        MenuItemsAdapter adapter = new MenuItemsAdapter(new ArrayList<>(0), itemViewModels,
                getContext(), container.dataSource, mMenuPageViewModel);
        recyclerView.setAdapter(adapter);


        SpacingItemDecoration decoration = new SpacingItemDecoration((int) getResources().getDimension(R.dimen.grid_item_spacing),
                SPAN_COUNT);
        recyclerView.addItemDecoration(decoration);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void setViewModel(MenuPageViewModel viewModel) {
        mMenuPageViewModel = viewModel;
    }

    public MenuPageViewModel getViewModel() { return mMenuPageViewModel; }

    public static class MenuItemsAdapter extends RecyclerView.Adapter<MenuItemHolder> {

        private ArrayList<MenuItemMainInfo> mMenuItems;
        private ArrayList<MenuItemViewModel> mViewModels;

        private final Context mContext;

        private final MenuItemsLocalDataSource mDataSource;

        private final MenuPageViewModel mMenuPageViewModel;

        public MenuItemsAdapter(ArrayList<MenuItemMainInfo> menuItems, ArrayList<MenuItemViewModel> viewModels,
                                Context context, MenuItemsLocalDataSource dataSource,
                                MenuPageViewModel menuPageViewModel) {
            mContext = context;
            mDataSource = dataSource;
            mMenuPageViewModel = menuPageViewModel;

            mViewModels = new ArrayList<>();
            replaceData(menuItems, viewModels);
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
            if (menuItem != null && mViewModels != null && position < mViewModels.size()) {
                holder.bindData(mViewModels.get(position));
            }

        }

        @Override
        public int getItemCount() { return mMenuItems != null ? mMenuItems.size() : 0; }

        public void replaceData(ArrayList<MenuItemMainInfo> menuItems,
                                ArrayList<MenuItemViewModel> itemViewModels) {
            if (menuItems != null && itemViewModels == null) {
                mMenuItems = menuItems;
                ((FastFoodApp) mContext.getApplicationContext()).appContainer.mainThreadHandler.post(() -> {
                    if (mViewModels == null || mViewModels.isEmpty()) {
                        mViewModels = createViewModels(menuItems);
                    }

                    ArrayList<MenuItemViewModel> viewModels = mMenuPageViewModel.getViewModels();
                    if (viewModels == null || viewModels.isEmpty()) {
                        mMenuPageViewModel.setViewModels(mViewModels);
                    }
                    notifyDataSetChanged();
                });
            } else {
                ArrayList<MenuItemMainInfo> itemsInfo = new ArrayList<>();
                for (MenuItemViewModel itemViewModel : itemViewModels) {
                    itemsInfo.add(itemViewModel.mMenuItem.get());
                }

                mMenuItems = itemsInfo;
                mViewModels = itemViewModels;
                notifyDataSetChanged();
            }
        }

        private ArrayList<MenuItemViewModel> createViewModels(ArrayList<MenuItemMainInfo> menuItems) {
            ArrayList<MenuItemViewModel> viewModels = new ArrayList<>();

            for (MenuItemMainInfo info : menuItems) {
                MenuItemViewModel viewModel = new MenuItemViewModel(mContext, mDataSource);
                viewModel.setMenuItem(info);

                viewModels.add(viewModel);
            }
            return viewModels;
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
