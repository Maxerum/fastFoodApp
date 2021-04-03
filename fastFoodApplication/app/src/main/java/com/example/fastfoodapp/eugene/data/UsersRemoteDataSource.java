package com.example.fastfoodapp.eugene.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class UsersRemoteDataSource implements UsersDataSource {

    public static final String TAG = "UsersRemoteDataSource";

    private static final String USERS_COLLECTION = "users";

    private static final String ORDERS_COLLECTION = "orders";

    private final FirebaseFirestore mFirestoreDb;

    public UsersRemoteDataSource(FirebaseFirestore firestoreDb) {
        mFirestoreDb = firestoreDb;
    }

    @Override
    public void addNewUser(String uid) {
        User user = new User(uid);

        mFirestoreDb.collection(USERS_COLLECTION).add(user).addOnSuccessListener(documentReference -> {
            Log.d(TAG, "New user successfully added.");
        }).addOnFailureListener(e -> {
            Log.d(TAG, "Fail to add new user");
        });
    }

    @Override
    public void checkIfUserExists(String uid, CheckIfUserExistsCallback callback) {
        Query query = mFirestoreDb.collection(USERS_COLLECTION).whereEqualTo("id", uid);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onUserChecked(!task.getResult().isEmpty());
            } else {
                Log.d(TAG, "Query failed");
            }
        });
    }
}
