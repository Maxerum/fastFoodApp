package com.example.fastfoodapp.eugene.data;

import com.google.firebase.auth.FirebaseUser;

public interface UsersDataSource {

    interface CheckIfUserExistsCallback {

        void onUserChecked(boolean exists);
    }

    void addNewUser(String uid);

    void checkIfUserExists(String uid, CheckIfUserExistsCallback callback);
}
