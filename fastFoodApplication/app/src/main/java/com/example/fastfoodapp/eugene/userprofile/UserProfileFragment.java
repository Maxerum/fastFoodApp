package com.example.fastfoodapp.eugene.userprofile;

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
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;

import com.example.fastfoodapp.R;
import com.example.fastfoodapp.databinding.UserProfileFragmentBinding;
import com.google.android.material.snackbar.Snackbar;

public class UserProfileFragment extends Fragment {

    public static final String TAG = "UserProfileFragment";

    private UserProfileFragmentBinding mUserProfileBinding;

    private UserProfileViewModel mViewModel;

    public static UserProfileFragment newInstance() {
        return new UserProfileFragment();
    }

    public UserProfileFragment() { }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel.start();
    }

    private void setupSnackBar() {
        mViewModel.mSnackBarText.addOnPropertyChangedCallback(
                new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Snackbar.make(getView(), mViewModel.mSnackBarText.get(), Snackbar.LENGTH_LONG).show();
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
    }

    public void setViewModel(UserProfileViewModel viewModel) {
        mViewModel = viewModel;
    }
}
