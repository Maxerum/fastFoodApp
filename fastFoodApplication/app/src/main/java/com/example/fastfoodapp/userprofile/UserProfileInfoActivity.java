package com.example.fastfoodapp.userprofile;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.fastfoodapp.FastFoodApp;
import com.example.fastfoodapp.MainActivity;
import com.example.fastfoodapp.R;
import com.example.fastfoodapp.ViewModelHolder;
import com.example.fastfoodapp.data.UsersAndRestaurantsRemoteDataSource;
import com.example.fastfoodapp.userprofile.editprofile.EditUserProfileActivity;
import com.example.fastfoodapp.userprofile.orderhistory.UserOrderHistoryActivity;
import com.example.fastfoodapp.util.ActivityUtils;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class UserProfileInfoActivity extends AppCompatActivity implements UserProfileInfoNavigator {

    public static final String TAG = "UserProfileActivity1";

    private UserProfileViewModel mViewModel;

    private final ActivityResultLauncher<Intent> mUpdateCredentials =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                Intent data = result.getData();
                if (data != null) {
                    String newEmail = data.getStringExtra(EditUserProfileActivity.EMAIL_KEY);
                    String newPassword = data.getStringExtra(EditUserProfileActivity.PASSWORD_KEY);

                    if (newEmail != null) {
                        mViewModel.updateUserEmail(newEmail);
                    }
                    if (newPassword != null) {
                        mViewModel.updateUserPassword(newPassword);
                    }
                }
            });

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    private UserProfileFragment findOrCreateFragment() {
        UserProfileFragment userProfileFragment = (UserProfileFragment) getSupportFragmentManager()
                .findFragmentById(R.id.container);
        if (userProfileFragment == null) {

            userProfileFragment = UserProfileFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), userProfileFragment,
                    R.id.container, false);
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
            UsersAndRestaurantsRemoteDataSource dataSource = ((FastFoodApp) getApplication())
                    .appContainer.usersDataSource;
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            UserProfileViewModel viewModel = new UserProfileViewModel(dataSource, user);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel), UserProfileViewModel.TAG, false);

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
        Intent intent = new Intent(this, EditUserProfileActivity.class);

        intent.putExtra(EditUserProfileActivity.UID_KEY, uid);
        intent.putExtra(EditUserProfileActivity.EMAIL_KEY, currentEmail);

        mUpdateCredentials.launch(intent);
    }

    @Override
    public void openUserOrderHistory() {
        Intent intent = new Intent(this, UserOrderHistoryActivity.class);
        startActivity(intent);
    }

    @Override
    public void signOut() {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Successfully signed out", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);

            } else {
                Toast.makeText(this, "Failed to sign out", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
