package com.example.fastfoodapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.fastfoodapp.menu.MenuActivity;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MyMainActivity";

    private static final int RC_SIGN_IN = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startAuthenticationFlow();

//        Intent intent = new Intent(this, MenuActivity.class);
//        startActivity(intent);
    }

    private void startAuthenticationFlow() {
        List<AuthUI.IdpConfig> idProviders = Arrays.asList(new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.EmailBuilder().build());
        AuthUI auth = AuthUI.getInstance();

        AuthMethodPickerLayout customLayout = new AuthMethodPickerLayout
                .Builder(R.layout.auth_screen).setGoogleButtonId(R.id.log_in_google_button)
                .setEmailButtonId(R.id.log_in_email_button).build();

        startActivityForResult(auth.createSignInIntentBuilder()
                .setAvailableProviders(idProviders).setTheme(R.style.Theme_FastFoodApp)
                        .setAuthMethodPickerLayout(customLayout).setLockOrientation(true)
                        .setIsSmartLockEnabled(false).build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult");

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in

                // Go to menu
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);

            } else {
                if (response != null) {
                    Log.d(TAG, "Error code: " + Objects.requireNonNull(response.getError()).getErrorCode());
                }
            }
            finish();
        }
    }
}