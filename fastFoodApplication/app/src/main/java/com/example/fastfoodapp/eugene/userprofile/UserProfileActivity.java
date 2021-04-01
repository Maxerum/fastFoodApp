package com.example.fastfoodapp.eugene.userprofile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.fastfoodapp.R;
import com.example.fastfoodapp.eugene.ViewModelHolder;
import com.example.fastfoodapp.eugene.util.ActivityUtils;

import java.util.Objects;

public class UserProfileActivity extends AppCompatActivity {

    public static final String TAG = "UserProfileActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_activity);

        setupToolbar();

        UserProfileViewModel viewModel = findOrCreateViewModel();
        UserProfileFragment fragment = findOrCreateFragment();

        fragment.setViewModel(viewModel);
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
            UserProfileViewModel viewModel = new UserProfileViewModel();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel), UserProfileViewModel.TAG);

            return viewModel;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_profile_options_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.user_profile_save:
                Log.d(TAG, "Save button pressed");
                break;
        }
        return true;
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.user_profile_toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
}
