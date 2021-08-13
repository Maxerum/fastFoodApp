package com.example.fastfoodapp.orderdetails;

import android.content.Context;

import androidx.databinding.Observable;
import androidx.databinding.ObservableField;

import com.example.fastfoodapp.AppContainer;
import com.example.fastfoodapp.FastFoodApp;
import com.example.fastfoodapp.data.Restaurant;
import com.example.fastfoodapp.data.UsersAndRestaurantsDataSource;
import com.example.fastfoodapp.data.item.MenuItemMainInfo;
import com.example.fastfoodapp.data.order.Order;
import com.example.fastfoodapp.data.order.PaymentMethod;
import com.example.fastfoodapp.data.order.PaymentStatus;
import com.example.fastfoodapp.ordersummary.PaymentSheetController;
import com.example.fastfoodapp.util.ConvertUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OrderDetailsViewModel implements PaymentSheetController.PaymentSheetCallback {

    public static final String TAG = "OrderDetailsViewModel";

    private static final String NOT_SELECTED = "Not selected";

    private static final String TOAST_MESSAGE = "Payment method or restaurant not selected";

    private OrderDetailsNavigator mNavigator;

    private ToastMessageChangedCallback messageChangedCallback;

    public final ObservableField<String> mSelectedRestaurantName = new ObservableField<>(NOT_SELECTED);

    public final ObservableField<String> mSelectedPaymentMethodName = new ObservableField<>(NOT_SELECTED);

    private final ObservableField<PaymentMethod> mSelectedPaymentMethod = new ObservableField<>();

    private final ObservableField<Restaurant> mSelectedRestaurant = new ObservableField<>();

    public final ObservableField<String> mTotalPrice = new ObservableField<>();

    public final ObservableField<String> mToastText = new ObservableField<>("");

    private PaymentSheetController paymentSheetController;

    private final Context mContext;

    private final UsersAndRestaurantsDataSource mDataSource;

    private final StripePaymentSheetConfigProvider configProvider =
            new StripePaymentSheetConfigProvider();

    public OrderDetailsViewModel(String totalPrice, Context context) {
        mContext = context;
        mDataSource = ((FastFoodApp) context.getApplicationContext()).appContainer.usersDataSource;
        mSelectedRestaurant.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mSelectedRestaurantName.set(Objects.requireNonNull(mSelectedRestaurant.get()).getName());
            }
        });
        mSelectedPaymentMethod.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mSelectedPaymentMethodName.set(Objects.requireNonNull(mSelectedPaymentMethod.get()).toString());
            }
        });
        mToastText.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (messageChangedCallback != null) {
                    messageChangedCallback.onMessageChanged(mToastText.get());
                }
            }
        });

        mTotalPrice.set(totalPrice);
    }

    public void setNavigator(OrderDetailsNavigator navigator) {
        mNavigator = navigator;
    }

    public void setMessageChangedCallback(ToastMessageChangedCallback callback) {
        messageChangedCallback = callback;
    }

    public void setPaymentSheetController(PaymentSheetController controller) {
        paymentSheetController = controller;
    }

    public void selectRestaurant() {
        if (mNavigator != null) {
            mNavigator.openRestaurantsList();
        }
    }

    public void selectPaymentMethod() {
        if (mNavigator != null) {
            mNavigator.openPaymentMethods();
        }
    }

    public void pay() {

        if (mSelectedRestaurantName.get().equals(NOT_SELECTED)
                || mSelectedPaymentMethodName.get().equals(NOT_SELECTED)) {
            mToastText.set("");
            mToastText.set(TOAST_MESSAGE);
        } else {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (mSelectedPaymentMethodName.get().equals(PaymentMethod.CASH.toString())) {

                Order order = constructOrder(mTotalPrice.get(), mSelectedRestaurantName.get(),
                        PaymentMethod.CASH, PaymentStatus.NOT_PAID);

                placeOrder(order, user.getUid());
            } else {

                int amountInCents = ConvertUtils.extractAmount(Objects.requireNonNull(mTotalPrice.get()));

                configProvider.fetchInitData(amountInCents, "usd", "2020-08-27")
                        .addOnCompleteListener(task -> {
                            try {
                                JSONObject object = new JSONObject(task.getResult());

                                String ephemeralKeySecret = object.optString("ephemeralKey");
                                String paymentIntentSecret = object.optString("paymentIntent");
                                String customerId = object.optString("customer");

                                paymentSheetController.setPaymentSheetCallback(this);
                                paymentSheetController.presentPaymentSheet(paymentIntentSecret,
                                        ephemeralKeySecret, customerId);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
            }
        }
    }

    private Order constructOrder(String orderTotal, String selectedRestaurant,
                                PaymentMethod paymentMethod, PaymentStatus paymentStatus) {
        AppContainer container = ((FastFoodApp) mContext.getApplicationContext()).appContainer;

        Map<MenuItemMainInfo, Integer> orderItems = container.selectedItems;
        Map<String, Integer> orderItemNames = new HashMap<>();
        for (MenuItemMainInfo info : orderItems.keySet()) {
            orderItemNames.put(info.title, orderItems.get(info));
        }

        Order order = new Order(orderItemNames, selectedRestaurant, orderTotal, paymentMethod, paymentStatus);
        return order;
    }

    private void placeOrder(Order order, String uid) {
        mDataSource.placeOrder(order, uid, new UsersAndRestaurantsDataSource.OrderPlaceCallback() {
            @Override
            public void onSuccess() {
                mToastText.set("");
                mToastText.set("Order placed successfully");
            }

            @Override
            public void onFailure() {
                mToastText.set("");
                mToastText.set("Failed to place the order");
            }
        });
    }

    public void updateRestaurant(Restaurant restaurant) {
        mSelectedRestaurant.set(restaurant);
    }

    public void updatePaymentMethod(PaymentMethod method) {
        mSelectedPaymentMethod.set(method);
    }

    @Override
    public void onPaymentSheetResult(PaymentSheetResult result) {
        if (result instanceof PaymentSheetResult.Completed) {
            // order is paid now
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            Order order = constructOrder(mTotalPrice.get(), mSelectedRestaurantName.get(),
                    PaymentMethod.CARD, PaymentStatus.PAID);

            placeOrder(order, user.getUid());

        } else if (result instanceof PaymentSheetResult.Failed) {


        }
    }
}
