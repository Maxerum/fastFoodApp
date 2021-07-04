package com.example.fastfoodapp.eugene.userprofile;


public interface UserProfileInfoNavigator {

    void openEditUserProfile(String uid, String currentEmail);

    void openUserOrderHistory();

    void signOut();
}
