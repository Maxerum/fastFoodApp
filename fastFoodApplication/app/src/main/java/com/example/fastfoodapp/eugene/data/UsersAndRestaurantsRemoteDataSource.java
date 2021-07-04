package com.example.fastfoodapp.eugene.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.fastfoodapp.eugene.data.order.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UsersAndRestaurantsRemoteDataSource implements
        UsersAndRestaurantsDataSource {

    public static final String TAG = "UsersRemoteDataSource";

    private static final String USERS_COLLECTION = "users";

    private static final String RESTAURANTS_COLLECTION = "restaurants";

    private static final String PAYMENT_METHODS_COLLECTION = "payment methods";

    private static final String ORDERS_COLLECTION = "orders";

    private static final String ORDERS_HISTORY_COLLECTION = "orders history";

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
    public void getUserPaymentMethodsNames(String uid, GetUserPaymentMethodsNamesCallback callback) {
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
    public void placeOrder(Order order, String uid, OrderPlaceCallback callback) {
        mFirestoreDb.collection(ORDERS_COLLECTION).add(order).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentReference ref = task.getResult();
                Map<String, Timestamp> orderTimestamp = new HashMap<>();
                orderTimestamp.put("timestamp", order.getTimestamp());

                mFirestoreDb.collection(USERS_COLLECTION).document(uid)
                        .collection(ORDERS_HISTORY_COLLECTION).document(ref.getId())
                        .set(orderTimestamp).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                callback.onSuccess();
                            } else {
                                callback.onFailure();
                            }
                        });
            } else {
                callback.onFailure();
            }
        });
    }

    @Override
    public void getAllUserOrderHistory(String uid, GetUserOrderHistoryCallback callback) {
        Query query = mFirestoreDb.collection(USERS_COLLECTION).document(uid)
                .collection(ORDERS_HISTORY_COLLECTION).orderBy("timestamp");

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String[] orderIds = new String[task.getResult().size()];
                int i = 0;
                for (QueryDocumentSnapshot snap : task.getResult()) {
                    orderIds[i] = snap.getId();
                    i++;
                }

                if (orderIds.length > 0) {
                    Query ordersQuery = mFirestoreDb.collection(ORDERS_COLLECTION)
                            .whereIn(FieldPath.documentId(), Arrays.asList(orderIds));

                    ordersQuery.get().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            ArrayList<Order> orders = new ArrayList<>();
                            for (QueryDocumentSnapshot querySnap : task1.getResult()) {
                                orders.add(querySnap.toObject(Order.class));
                            }

                            callback.onGetUserOrderHistory(orders);
                        } else {
                            callback.onDataNotAvailable();
                        }
                    });
                }
            } else {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void clearAllUserOrderHistory(String uid, ClearUserOrderHistoryCallback callback) {
        Query query = mFirestoreDb.collection(USERS_COLLECTION).document(uid)
                .collection(ORDERS_HISTORY_COLLECTION);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot snap : task.getResult()) {
                    mFirestoreDb.collection(USERS_COLLECTION).document(uid)
                            .collection(ORDERS_HISTORY_COLLECTION).document(snap.getId()).delete()
                            .addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                } else {

                                }
                            });
                }
                callback.onSuccess();

            } else {
                callback.onFailure();
            }
        });
    }
}
