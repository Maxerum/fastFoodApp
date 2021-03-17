package com.example.fastfoodapp.eugene.authorization;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fastfoodapp.R;
import com.example.fastfoodapp.databinding.AuthorizationActivityBinding;
import com.example.fastfoodapp.eugene.ViewModelHolder;
import com.example.fastfoodapp.eugene.util.ActivityUtils;

public class AuthorizationActivity extends AppCompatActivity implements AuthorizationNavigator {
    private static final String TAG = "AuthorizationActivity";

    private AuthorizationActivityBinding mAuthorizationActivityBinding;

    private AuthorizationViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuthorizationActivityBinding =
                AuthorizationActivityBinding.inflate(getLayoutInflater());
        setContentView(mAuthorizationActivityBinding.getRoot());

        mViewModel = findOrCreateViewModel();
        mViewModel.setNavigator(this);

        mAuthorizationActivityBinding.setViewModel(mViewModel);
    }

    private AuthorizationViewModel findOrCreateViewModel() {
        @SuppressWarnings("unchecked")
        ViewModelHolder<AuthorizationViewModel> holder = (ViewModelHolder<AuthorizationViewModel>)
                getSupportFragmentManager().findFragmentByTag(AuthorizationViewModel.TAG);

        if (holder != null) {
            return holder.getViewModel();
        } else {
            AuthorizationViewModel viewModel = new AuthorizationViewModel();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel), AuthorizationViewModel.TAG);

            return viewModel;
        }
    }

    @Override
    public void openLogInWindow() {
        Log.d(TAG, "openLogInWindow");
    }

    @Override
    public void openSignUpWindow() {
        Log.d(TAG, "openSignUpWindow");
    }
}
