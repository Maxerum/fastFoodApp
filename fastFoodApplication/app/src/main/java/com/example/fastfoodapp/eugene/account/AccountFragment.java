package com.example.fastfoodapp.eugene.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fastfoodapp.databinding.AccountFragmentBinding;
import com.example.fastfoodapp.eugene.menu.MenuFragment;

public class AccountFragment extends Fragment {

    private AccountFragmentBinding mAccountFragmentBinding;

    private AccountViewModel mViewModel;

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    public AccountFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAccountFragmentBinding = AccountFragmentBinding.inflate(inflater, container, false);
        mAccountFragmentBinding.setViewModel(mViewModel);

        return mAccountFragmentBinding.getRoot();
    }

    public void setViewModel(AccountViewModel viewModel) {
        mViewModel = viewModel;
    }
}
