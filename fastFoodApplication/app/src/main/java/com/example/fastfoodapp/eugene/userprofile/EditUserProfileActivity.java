package com.example.fastfoodapp.eugene.userprofile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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


public class EditUserProfileActivity extends AppCompatActivity implements SaveUserProfileNavigator {

    public static final String UID_KEY = "uid key";

    public static final String EMAIL_KEY = "email key";

    public static final String PASSWORD_KEY = "password key";

    private EditUserProfileViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_profile_activity);

        setupToolbar();

        mViewModel = findOrCreateViewModel();
        mViewModel.setNavigator(this);
        EditUserProfileFragment fragment = findOrCreateFragment();

        fragment.setEditUserProfileViewModel(mViewModel);
    }

    private EditUserProfileFragment findOrCreateFragment() {
        EditUserProfileFragment fragment = (EditUserProfileFragment) getSupportFragmentManager()
                .findFragmentById(R.id.edit_profile_fragment_container);
        if (fragment == null) {

            fragment = EditUserProfileFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment,
                    R.id.edit_profile_fragment_container);
        }
        return fragment;
    }

    private EditUserProfileViewModel findOrCreateViewModel() {
        @SuppressWarnings("unchecked")
        ViewModelHolder<EditUserProfileViewModel> holder = (ViewModelHolder<EditUserProfileViewModel>)
                getSupportFragmentManager().findFragmentByTag(EditUserProfileViewModel.TAG);

        if (holder != null) {
            return holder.getViewModel();
        } else {
            Intent intent = getIntent();
            String uid = intent.getStringExtra(UID_KEY);
            String email = intent.getStringExtra(EMAIL_KEY);

            EditUserProfileViewModel editUserProfileViewModel = new EditUserProfileViewModel(uid, email);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    ViewModelHolder.createContainer(editUserProfileViewModel), UserProfileViewModel.TAG);

            return editUserProfileViewModel;
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.edit_user_profile_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                mViewModel.saveNewUserProfileData();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void saveUserProfileNavigator(String newEmail, String newPassword) {
        Intent intent = new Intent();
        if (newEmail != null) {
            intent.putExtra(EMAIL_KEY, newEmail);
        }
        if (newPassword != null) {
            intent.putExtra(PASSWORD_KEY, newPassword);
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}
