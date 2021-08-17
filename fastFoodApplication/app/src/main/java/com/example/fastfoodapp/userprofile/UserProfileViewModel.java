package com.example.fastfoodapp.userprofile;


import android.util.Log;

import androidx.databinding.ObservableField;

import com.example.fastfoodapp.data.UsersAndRestaurantsDataSource;
import com.example.fastfoodapp.data.UsersAndRestaurantsRemoteDataSource;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;

public class UserProfileViewModel {
    public static final String TAG = "UserProfileViewModel";

    public final ObservableField<String> mEmail = new ObservableField<>();

    public final ObservableField<String> mUid = new ObservableField<>();

    public final ObservableField<String> mSnackBarText = new ObservableField<>("");

    private final UsersAndRestaurantsRemoteDataSource mUsersDataSource;

    private final FirebaseUser mUser;

    private UserProfileInfoNavigator mNavigator;

    public UserProfileViewModel(UsersAndRestaurantsRemoteDataSource usersAndRestaurantsRemoteDataSource, FirebaseUser user) {
        mUsersDataSource = usersAndRestaurantsRemoteDataSource;
        mUser = user;

        mUid.set(mUser.getUid());
        mEmail.set(mUser.getEmail());
    }

    public void start() {
        saveUserIfNeeded();
    }

    private void saveUserIfNeeded() {
        FirebaseUserMetadata metadata = mUser.getMetadata();

        mUsersDataSource.checkIfUserExists(mUser.getUid(), new UsersAndRestaurantsDataSource.CheckIfUserExistsCallback() {
            @Override
            public void onUserChecked(boolean exists) {
                if (metadata != null && !exists) {
                } else {
                    Log.d(TAG, "This user already exists");
                }
            }

            @Override
            public void onDataNotAvailable() {
            }
        });
    }

    public void updateUserEmail(String newEmail) {
        mUser.updateEmail(newEmail).addOnSuccessListener(aVoid -> {
            mSnackBarText.set("");
            mSnackBarText.set("User's email successfully updated");
            mEmail.set(mUser.getEmail());
        }).addOnFailureListener(e -> {
            mSnackBarText.set("");
            mSnackBarText.set("Fail to update user email");
        });
    }

    public void updateUserPassword(String newPassword) {
        mUser.updatePassword(newPassword).addOnSuccessListener(aVoid -> {
            mSnackBarText.set("");
            mSnackBarText.set("User's password successfully updated");
        }).addOnFailureListener(e -> {
            mSnackBarText.set("");
            mSnackBarText.set("Fail to update user password");
        });
    }

    public void openEditUserProfile() {
        if (mNavigator != null) {
            mNavigator.openEditUserProfile(mUid.get(), mEmail.get());
        }
    }

    public void openUserOrderHistory() {
        if (mNavigator != null) {
            mNavigator.openUserOrderHistory();
        }
    }

    public void signOut() {
        if (mNavigator != null) {
            mNavigator.signOut();
        }
    }

    public void setNavigator(UserProfileInfoNavigator navigator) {
        mNavigator = navigator;
    }
}
