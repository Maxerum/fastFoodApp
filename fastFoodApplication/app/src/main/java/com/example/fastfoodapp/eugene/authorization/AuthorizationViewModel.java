package com.example.fastfoodapp.eugene.authorization;

public class AuthorizationViewModel {
    public static final String TAG = "AuthorizationViewModel";

    private AuthorizationNavigator mNavigator;

    public AuthorizationViewModel() {
    }

    public void setNavigator(AuthorizationNavigator navigation) {
        mNavigator = navigation;
    }

    public void openLogInWindow() {
        if (mNavigator != null) {
            mNavigator.openLogInWindow();
        }
    }

    public void openSignUpWindow() {
        if (mNavigator != null) {
            mNavigator.openSignUpWindow();
        }
    }
}
