package com.example.fastfoodapp.eugene.menu;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fastfoodapp.R;
import com.example.fastfoodapp.eugene.ViewModelHolder;
import com.example.fastfoodapp.eugene.menu.ActivityUtils;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        MenuFragment fragment = findOrCreateFragment();

        MenuViewModel viewModel = findOrCreateViewModel();
        fragment.setViewModel(viewModel);

    }

    private MenuFragment findOrCreateFragment() {
        MenuFragment menuFragment = (MenuFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if (menuFragment == null) {

            menuFragment = MenuFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), menuFragment,
                    R.id.fragment_container);
        }
        return menuFragment;
    }

    private MenuViewModel findOrCreateViewModel() {
        @SuppressWarnings("unchecked")
        ViewModelHolder<MenuViewModel> holder = (ViewModelHolder<MenuViewModel>)
                getSupportFragmentManager().findFragmentByTag(MenuViewModel.TAG);

        if (holder != null) {
            return holder.getViewModel();
        } else {
            MenuViewModel viewModel = new MenuViewModel(this);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel), MenuViewModel.TAG);

            return viewModel;
        }
    }
}
