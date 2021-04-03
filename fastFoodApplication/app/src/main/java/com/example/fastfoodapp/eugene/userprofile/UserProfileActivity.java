package com.example.fastfoodapp.eugene.userprofile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.fastfoodapp.FastFoodApp;
import com.example.fastfoodapp.R;
import com.example.fastfoodapp.eugene.ViewModelHolder;
import com.example.fastfoodapp.eugene.data.UsersRemoteDataSource;
import com.example.fastfoodapp.eugene.util.ActivityUtils;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class UserProfileActivity extends AppCompatActivity implements EditUserProfileNavigator {

    public static final String TAG = "UserProfileActivity1";

    private UserProfileViewModel mViewModel;

    public static final int EDIT_PROFILE_RC = 5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_activity);

        setupToolbar();

        mViewModel = findOrCreateViewModel();
        if (mViewModel != null) {
            mViewModel.setNavigator(this);
        }
        UserProfileFragment fragment = findOrCreateFragment();

        fragment.setViewModel(mViewModel);
    }

    private UserProfileFragment findOrCreateFragment() {
        UserProfileFragment userProfileFragment = (UserProfileFragment) getSupportFragmentManager()
                .findFragmentById(R.id.container);
        if (userProfileFragment == null) {

            userProfileFragment = UserProfileFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), userProfileFragment,
                    R.id.container);
        }
        return userProfileFragment;
    }

    private UserProfileViewModel findOrCreateViewModel() {
        @SuppressWarnings("unchecked")
        ViewModelHolder<UserProfileViewModel> holder = (ViewModelHolder<UserProfileViewModel>)
                getSupportFragmentManager().findFragmentByTag(UserProfileViewModel.TAG);

        if (holder != null) {
            return holder.getViewModel();
        } else {
            UsersRemoteDataSource dataSource = ((FastFoodApp) getApplication())
                    .appContainer.usersDataSource;
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            UserProfileViewModel viewModel = new UserProfileViewModel(dataSource, user);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel), UserProfileViewModel.TAG);

            return viewModel;
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.user_profile_toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void openEditUserProfile(String uid, String currentEmail) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, EditUserProfileActivity.class);

            intent.putExtra(EditUserProfileActivity.UID_KEY, uid);
            intent.putExtra(EditUserProfileActivity.EMAIL_KEY, currentEmail);

            startActivityForResult(intent, EDIT_PROFILE_RC);
        } else {
            Snackbar.make(findViewById(android.R.id.content), "You have signed out",
                    Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void signOut() {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Snackbar.make(findViewById(android.R.id.content), "Successfully signed out",
                        Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Failed to sign out",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_PROFILE_RC) {

            if (resultCode == RESULT_OK && data != null) {
                String newEmail = data.getStringExtra(EditUserProfileActivity.EMAIL_KEY);
                String newPassword = data.getStringExtra(EditUserProfileActivity.PASSWORD_KEY);

                if (newEmail != null) {
                    mViewModel.updateUserEmail(newEmail);
                }
                if (newPassword != null) {
                    mViewModel.updateUserPassword(newPassword);
                }
            }
        }
    }
}
