package com.example.fastfoodapp.eugene.account;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.fastfoodapp.R;
import com.example.fastfoodapp.eugene.ViewModelHolder;
import com.example.fastfoodapp.eugene.menu.MenuFragment;
import com.example.fastfoodapp.eugene.menu.MenuViewModel;
import com.example.fastfoodapp.eugene.util.ActivityUtils;
import com.google.android.material.appbar.MaterialToolbar;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity);

        setupToolbar();

        AccountViewModel viewModel = findOrCreateViewModel();
        AccountFragment fragment = findOrCreateFragment();

        fragment.setViewModel(viewModel);
    }

    private AccountFragment findOrCreateFragment() {
        AccountFragment accountFragment = (AccountFragment) getSupportFragmentManager()
                .findFragmentById(R.id.container);
        if (accountFragment == null) {

            accountFragment = AccountFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), accountFragment,
                    R.id.container);
        }
        return accountFragment;
    }

    private AccountViewModel findOrCreateViewModel() {
        @SuppressWarnings("unchecked")
        ViewModelHolder<AccountViewModel> holder = (ViewModelHolder<AccountViewModel>)
                getSupportFragmentManager().findFragmentByTag(AccountViewModel.TAG);

        if (holder != null) {
            return holder.getViewModel();
        } else {
            AccountViewModel viewModel = new AccountViewModel();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel), AccountViewModel.TAG);

            return viewModel;
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.account_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
