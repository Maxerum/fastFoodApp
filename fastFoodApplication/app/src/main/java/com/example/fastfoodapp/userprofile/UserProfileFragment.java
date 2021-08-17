package com.example.fastfoodapp.userprofile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;

import com.example.fastfoodapp.databinding.UserProfileFragmentBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class UserProfileFragment extends Fragment {

    public static final String TAG = "UserProfileFragment";

    private UserProfileFragmentBinding mUserProfileBinding;

    private UserProfileViewModel mViewModel;

    public static UserProfileFragment newInstance() {
        return new UserProfileFragment();
    }

    public UserProfileFragment() { }

    private void setupSnackBar() {
        mViewModel.mSnackBarText.addOnPropertyChangedCallback(
                new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Snackbar.make(requireView(),
                        Objects.requireNonNull(mViewModel.mSnackBarText.get()), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mUserProfileBinding = UserProfileFragmentBinding.inflate(inflater, container, false);
        mUserProfileBinding.setViewModel(mViewModel);

        return mUserProfileBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupSnackBar();

        mViewModel.start();
    }

    public void setViewModel(UserProfileViewModel viewModel) {
        mViewModel = viewModel;
    }
}
