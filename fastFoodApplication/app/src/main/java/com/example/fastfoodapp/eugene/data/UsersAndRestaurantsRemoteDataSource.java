package com.example.fastfoodapp.eugene.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UsersAndRestaurantsRemoteDataSource implements
        UsersAndRestaurantsDataSource {

    public static final String TAG = "UsersRemoteDataSource";

    private static final String USERS_COLLECTION = "users";

    private static final String RESTAURANTS_COLLECTION = "restaurants";

    private static final String PAYMENT_METHODS_COLLECTION = "payment methods";

    private static final String ORDERS_COLLECTION = "orders";

    private final FirebaseFirestore mFirestoreDb;

    public UsersAndRestaurantsRemoteDataSource(FirebaseFirestore firestoreDb) {
        mFirestoreDb = firestoreDb;
    }

    @Override
    public void addNewUser(String uid) {
//        User user = new User(uid);
        Log.d(TAG, "addNewUser");

        mFirestoreDb.collection(USERS_COLLECTION).document(uid).set(new HashMap<>()).addOnSuccessListener(documentReference -> {
            Log.d(TAG, "New user successfully added");
        }).addOnFailureListener(e -> {
            Log.d(TAG, "Fail to add new user");
        });
    }

    @Override
    public void checkIfUserExists(String uid, CheckIfUserExistsCallback callback) {
        DocumentReference ref = mFirestoreDb.collection(USERS_COLLECTION).document(uid);

        ref.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onUserChecked(Objects.requireNonNull(task.getResult()).exists());
            } else {
                Log.d(TAG, "Query failed");
            }
        });
    }

    @Override
    public void getAllRestaurants(GetAllRestaurantsCallback callback) {
        Query query = mFirestoreDb.collection(RESTAURANTS_COLLECTION).orderBy("name");

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult() != null) {
                    ArrayList<Restaurant> restaurants = new ArrayList<>();
                    for (QueryDocumentSnapshot snap : task.getResult()) {
                        restaurants.add(snap.toObject(Restaurant.class));
                    }
                    callback.onGetAllRestaurants(restaurants);
                }
            } else {
                Log.d(TAG, "get failed with " + task.getException());
            }
        });
    }

    @Override
    public void addNewCard(String uid, CardInfo card, String cardName) {
        mFirestoreDb.collection(USERS_COLLECTION).document(uid)
                .collection(PAYMENT_METHODS_COLLECTION).document(cardName).set(card)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Card successfully added");
                }).addOnFailureListener(e -> {
                    Log.d(TAG, "Failed to add a card");
                });
    }
}
