package com.example.fastfoodapp.eugene.menu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fastfoodapp.R;
import com.example.fastfoodapp.databinding.MenuFragmentBinding;

import java.util.ArrayList;

public class MenuFragment extends Fragment {
    public static final String TAG = "MenuFragment";

    private MenuViewModel mMenuViewModel;

    private MenuFragmentBinding mMenuFragmentBinding;

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    public MenuFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMenuFragmentBinding = MenuFragmentBinding.inflate(inflater, container, false);
        mMenuFragmentBinding.setViewModel(mMenuViewModel);

        return mMenuFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupToolbar(view);

        setupViewPager();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_options_menu, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_account:
                // TODO go to account info page
                break;
        }
        return true;
    }

    private void setupToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getContext()).setSupportActionBar(toolbar);

        setHasOptionsMenu(true);
    }

    public void setViewModel(MenuViewModel viewModel) {
        mMenuViewModel = viewModel;
    }

    private void setupViewPager() {
        ViewPager2 viewPager = mMenuFragmentBinding.viewPager;
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, new ArrayList<>(0));
        viewPager.setAdapter(viewPagerAdapter);
    }

    public static class ViewPagerAdapter extends FragmentStateAdapter {

        private ArrayList<Fragment> fragments;

        public ViewPagerAdapter(@NonNull MenuFragment fragmentActivity,
                                ArrayList<Fragment> fragments) {
            super(fragmentActivity);

            this.fragments = fragments;
        }

        public void replaceData(ArrayList<Fragment> fragments) {
            this.fragments = fragments;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragments.get(position);
        }

        @Override
        public int getItemCount() {
            return fragments.size();
        }
    }
}
