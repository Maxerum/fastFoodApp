package com.example.fastfoodapp.data;

import android.util.Log;

import com.example.fastfoodapp.data.order.Order;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UsersAndRestaurantsRemoteDataSource implements UsersAndRestaurantsDataSource {

    public static final String TAG = "UsersRemoteDataSource";

    private static final String CUSTOMERS_COLLECTION = "customers";

    private static final String RESTAURANTS_COLLECTION = "restaurants";

    private static final String ORDERS_SUBCOLLECTION = "orders";

    private final FirebaseFirestore mFirestoreDb;

    public UsersAndRestaurantsRemoteDataSource(FirebaseFirestore firestoreDb) {
        mFirestoreDb = firestoreDb;
    }

    @Override
    public void checkIfUserExists(String uid, CheckIfUserExistsCallback callback) {
        DocumentReference ref = mFirestoreDb.collection(CUSTOMERS_COLLECTION).document(uid);

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
    public void placeOrder(Order order, String uid, OrderPlaceCallback callback) {
        mFirestoreDb.collection(CUSTOMERS_COLLECTION)
                .document(uid)
                .collection(ORDERS_SUBCOLLECTION)
                .add(order)
                .addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onSuccess();
            } else {
                callback.onFailure();
            }
        });
    }

    @Override
    public void getUserOrderHistory(String uid, GetUserOrderHistoryCallback callback) {
        Query query = mFirestoreDb.collection(CUSTOMERS_COLLECTION).document(uid)
                .collection(ORDERS_SUBCOLLECTION).orderBy("timestamp", Query.Direction.DESCENDING);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                List<Order> orders = new ArrayList<>();
                for (QueryDocumentSnapshot snap : task.getResult()) {
                    Order order = snap.toObject(Order.class);
                    orders.add(order);
                }
                callback.onGetUserOrderHistory(orders);

            } else {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void clearUserOrderHistory(String uid, ClearUserOrderHistoryCallback callback) {
        Query query = mFirestoreDb.collection(CUSTOMERS_COLLECTION).document(uid)
                .collection(ORDERS_SUBCOLLECTION);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot snap : task.getResult()) {
                    snap.getReference().delete();
                }
                callback.onSuccess();

            } else {
                callback.onFailure();
            }
        });
    }
}
