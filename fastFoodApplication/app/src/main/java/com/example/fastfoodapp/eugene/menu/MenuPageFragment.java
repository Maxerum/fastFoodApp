package com.example.fastfoodapp.eugene.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodapp.R;
import com.example.fastfoodapp.databinding.MenuPageFragmentBinding;

public class MenuPageFragment extends Fragment {

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO setup recyclerview adapter
    }

    public void setViewModel(MenuPageViewModel viewModel) {
        mMenuPageViewModel = viewModel;
    }

    public static class MenuItemsAdapter extends RecyclerView.Adapter<MenuItemHolder> {

        @NonNull
        @Override
        public MenuItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull MenuItemHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

    private static class MenuItemHolder extends RecyclerView.ViewHolder {

        public MenuItemHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
