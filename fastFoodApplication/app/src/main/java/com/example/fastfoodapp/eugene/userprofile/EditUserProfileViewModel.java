package com.example.fastfoodapp.eugene.userprofile;

import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

public class EditUserProfileViewModel extends BaseObservable {

    public static final String TAG = "UserProfileData";

    public final ObservableField<String> mUid = new ObservableField<>();

    private final ObservableField<String> mEmail = new ObservableField<>();

    private final ObservableField<String> mPassword = new ObservableField<>("");

    public final ObservableField<String> mSnackBarText = new ObservableField<>();

    private final String emailBeforeChange;

    private SaveUserProfileNavigator mNavigator;

    public EditUserProfileViewModel(String uid, String email) {
        mUid.set(uid);
        mEmail.set(email);

        emailBeforeChange = email;
    }

    @Bindable
    public String getEmail() {
        return mEmail.get();
    }

    public void setEmail(String email) {
        mEmail.set(email);
    }

    @Bindable
    public String getPassword() {
        return mPassword.get();
    }

    public void setPassword(String password) {
        mPassword.set(password);
    }

    public void saveNewUserProfileData() {
        String newPassword = null;
        String newEmail = null;

        if (mEmail != null && !mEmail.get().isEmpty()) {
            if (mPassword.get().length() >= 6) {
                newPassword = mPassword.get();
            }
            // TODO: need to put more constraints on email input
            if (!emailBeforeChange.equals(mEmail.get())) {
                newEmail = mEmail.get();
            }
            if (mPassword.get().length() == 0 && emailBeforeChange.equals(mEmail.get())) {
                mSnackBarText.set("");
                mSnackBarText.set("You didn't change anything");
            } else if (mPassword.get().length() < 6) {
                mSnackBarText.set("");
                mSnackBarText.set("Incorrect password");
            }

            if (mNavigator != null && (newEmail != null || newPassword != null)) {
                mNavigator.saveUserProfileNavigator(newEmail, newPassword);
            }
        }
    }

    public void setNavigator(SaveUserProfileNavigator navigator) {
        mNavigator = navigator;
    }
}
