package com.example.fastfoodapp.eugene.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.fastfoodapp.eugene.data.order.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
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
    public void addNewCard(String uid, CardInfo card, String cardName, AddCardCallback callback) {
        mFirestoreDb.collection(USERS_COLLECTION).document(uid)
                .collection(PAYMENT_METHODS_COLLECTION).document(cardName).set(card)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onFailure();
                    }
                });
    }

    @Override
    public void getUserPaymentMethodsNames(String uid, GetUserPaymentMethodsNames callback) {
        Query query = mFirestoreDb.collection(USERS_COLLECTION).document(uid)
                .collection(PAYMENT_METHODS_COLLECTION).orderBy(FieldPath.documentId());

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<String> paymentMethodsNames = new ArrayList<>();
                for (QueryDocumentSnapshot snap : task.getResult()) {
                    paymentMethodsNames.add(snap.getId());
                }
                callback.onGetAllUserPaymentMethodsNames(paymentMethodsNames);
            } else {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void placeOrder(Order order, OrderPlaceCallback callback) {
        mFirestoreDb.collection(ORDERS_COLLECTION).add(order).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onSuccess();
            } else {
                callback.onFailure();
            }
        });
    }
}
