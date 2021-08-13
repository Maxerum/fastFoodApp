package com.example.fastfoodapp.userprofile.editprofile;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;

import com.example.fastfoodapp.R;
import com.example.fastfoodapp.databinding.EditUserProfileFragmentBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class EditUserProfileFragment extends Fragment {

    public static final String TAG = "EditUserProfileFragment";

    private EditUserProfileFragmentBinding mEditProfileBinding;

    private EditUserProfileViewModel mEditUserProfileViewModel;

    public static EditUserProfileFragment newInstance() {
        return new EditUserProfileFragment();
    }

    private EditUserProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mEditProfileBinding = EditUserProfileFragmentBinding.inflate(inflater, container, false);
        mEditProfileBinding.setViewModel(mEditUserProfileViewModel);

        return mEditProfileBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupToastMessages();

        setupInputEditText();
    }

    public void setEditUserProfileViewModel(EditUserProfileViewModel data) {
        mEditUserProfileViewModel = data;
    }

    private void setupToastMessages() {
        mEditUserProfileViewModel.mToastText.addOnPropertyChangedCallback(
                new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                String newText = mEditUserProfileViewModel.mToastText.get();
                if (newText != null && !newText.isEmpty()) {
                    Toast.makeText(getContext(), mEditUserProfileViewModel.mToastText.get(),
                            Toast.LENGTH_SHORT).show();
                }
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
