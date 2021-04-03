package com.example.fastfoodapp.eugene.userprofile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
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
import com.example.fastfoodapp.databinding.EditUserProfileFragmentBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class EditUserProfileFragment extends Fragment {

    public static final String TAG = "EditUserProfileFragment";

    private EditUserProfileFragmentBinding mEditProfileBinding;

    private EditUserProfileViewModel mEditUserProfileViewModel;

    public static EditUserProfileFragment newInstance() {
        return new EditUserProfileFragment();
    }

    private EditUserProfileFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mEditProfileBinding = EditUserProfileFragmentBinding.inflate(inflater, container, false);
        mEditProfileBinding.setViewModel(mEditUserProfileViewModel);

        return mEditProfileBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupSnackBar();

        setupInputEditText();
    }

    public void setEditUserProfileViewModel(EditUserProfileViewModel data) {
        mEditUserProfileViewModel = data;
    }

    private void setupSnackBar() {
        mEditUserProfileViewModel.mSnackBarText.addOnPropertyChangedCallback(
                new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Snackbar.make(getView(), mEditUserProfileViewModel.mSnackBarText.get(),
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void setupInputEditText() {
        TextInputLayout passwordFieldLayout = mEditProfileBinding.userPasswordInputLayout;
        TextInputEditText passwordField = mEditProfileBinding.userPassword;

        passwordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() < 6) {
                    passwordFieldLayout.setError(getString(R.string.pasword_input_error));
                } else {
                    passwordFieldLayout.setError(null);
                }
            }
        });

    }
}
