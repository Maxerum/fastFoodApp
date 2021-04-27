package com.example.fastfoodapp.Dim4es.PayPage;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastfoodapp.AppContainer;
import com.example.fastfoodapp.Dim4es.CreditCard.AddFormatCreditCard;
import com.example.fastfoodapp.Dim4es.SuccessPay.SuccessPayPage;
import com.example.fastfoodapp.FastFoodApp;
import com.example.fastfoodapp.Maxerum.OrderPayingActivity;
import com.example.fastfoodapp.R;
import com.example.fastfoodapp.eugene.ViewModelHolder;
import com.example.fastfoodapp.eugene.cardsdialog.PaymentMethodViewModel;
import com.example.fastfoodapp.eugene.cardsdialog.PaymentMethodsDialog;
import com.example.fastfoodapp.eugene.cardsdialog.PaymentMethodsViewModel;
import com.example.fastfoodapp.eugene.data.Restaurant;
import com.example.fastfoodapp.eugene.data.UsersAndRestaurantsDataSource;
import com.example.fastfoodapp.eugene.data.UsersAndRestaurantsRemoteDataSource;
import com.example.fastfoodapp.eugene.data.item.MenuItemMainInfo;
import com.example.fastfoodapp.eugene.data.order.Order;
import com.example.fastfoodapp.eugene.menu.MenuFragment;
import com.example.fastfoodapp.eugene.menu.MenuViewModel;
import com.example.fastfoodapp.eugene.util.ActivityUtils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class PayMainActivity extends AppCompatActivity {

    public static final String TAG = "PayMainActivity";

    ImageView arrowBackPayPage;
    TextView textViewToChooseSpinner;
    Spinner spinnerRestaurant;

    private UsersAndRestaurantsRemoteDataSource mDataSource;

    private HashMap<MenuItemMainInfo, Integer> selectedItems;

    //Buttons
    Button userCreditCard, addUserCard, finalSendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dim4es_activity_pay_main);

        //define all buttons with id in xml
        userCreditCard = findViewById(R.id.userCreditCard);
        addUserCard = findViewById(R.id.addUserCard);
        finalSendButton = findViewById(R.id.finalSendButton);


        //define arrow in toolbar
        arrowBackPayPage = findViewById(R.id.arrowBackOnPayPage);

        //INfo about "Choose restaurant"
        textViewToChooseSpinner = findViewById(R.id.textViewToChooseSpinner);

        //define spinner
        spinnerRestaurant = findViewById(R.id.restaurantSpinner);
        //spinner contains TextView (to choose)

        mDataSource = ((FastFoodApp) getApplication()).appContainer.usersDataSource;

        // fetching all available restaurants
         mDataSource.getAllRestaurants(new UsersAndRestaurantsDataSource.GetAllRestaurantsCallback() {
            @Override
            public void onGetAllRestaurants(ArrayList<Restaurant> restaurants) {
                String[] restaurantsArr = new String[restaurants.size()];
                for (int i = 0; i < restaurants.size(); i++) {
                    restaurantsArr[i] = restaurants.get(i).getName();
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                        R.layout.support_simple_spinner_dropdown_item, restaurantsArr);
                //adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinnerRestaurant.setAdapter(adapter);
            }

            @Override
            public void onDataNotAvailable() { }
        });

        //select item on spinner
        spinnerRestaurant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(0xffffffff);
                textViewToChooseSpinner.setText(onItemSelectedHandler(parent, view, position, id));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //arrow back on toolbar event listener
        arrowBackPayPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Arrow back to return to previous page
                //Intent intent = new Intent(PayMainActivity.this, OrderPayingActivity.class);
                //startActivity(intent);
                onBackPressed();
            }
        });

        // get order items info
        selectedItems = ((FastFoodApp) getApplication()).appContainer.selectedItems;

        //user credit card (which already exist), **mb show?**

        Timestamp timestamp = new Timestamp(new Date());
        Log.d(TAG, timestamp.toDate().toString());

        userCreditCard.setOnClickListener(v -> {
            PaymentMethodsViewModel viewModel = findOrCreateViewModel();

            FragmentManager manager = getSupportFragmentManager();
            PaymentMethodsDialog dialogFragment = (PaymentMethodsDialog) manager
                    .findFragmentByTag(PaymentMethodsDialog.TAG);
            if (dialogFragment == null) {
                dialogFragment = new PaymentMethodsDialog();
                dialogFragment.setViewModel(viewModel);
                showBottomSheetDialog(dialogFragment, PaymentMethodsDialog.TAG);
            } else {
                dialogFragment.setViewModel(viewModel);
                dialogFragment.showNow(manager, PaymentMethodsDialog.TAG);
            }
        });

        //add user credit card (? new page with formatted input?)
        addUserCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayMainActivity.this, AddFormatCreditCard.class);
                startActivity(intent);
            }
        });

        // success or not, this order(new page mb?)
        finalSendButton.setOnClickListener(v -> {
            placeOrder(new UsersAndRestaurantsDataSource.OrderPlaceCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(PayMainActivity.this, "Order successfully placed",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure() {
                    Toast.makeText(PayMainActivity.this, "Fail to place the order",
                            Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
    
    private void placeOrder(UsersAndRestaurantsDataSource.OrderPlaceCallback callback) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            PaymentMethodsViewModel viewModel = findOrCreateViewModel();
            String paymentMethodName = viewModel.getCheckedPaymentMethodName();
            if (paymentMethodName != null) {
                String restaurant = spinnerRestaurant.getSelectedItem().toString();

                HashMap<MenuItemMainInfo, Integer> selectedItems = ((FastFoodApp) getApplication())
                        .appContainer.selectedItems;
                HashMap<String, Integer> selectedItemNames = new HashMap<>();
                for (MenuItemMainInfo menuItem : selectedItems.keySet()) {
                    selectedItemNames.put(menuItem.title, selectedItems.get(menuItem));
                }

                Order order = new Order(user.getUid(), paymentMethodName, selectedItemNames, restaurant);
                mDataSource.placeOrder(order, callback);
            } else {
                Toast.makeText(this, "Payment method is not selected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showBottomSheetDialog(BottomSheetDialogFragment fragment, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(fragment, tag).addToBackStack(null).commit();
    }

    private PaymentMethodsViewModel findOrCreateViewModel() {
        @SuppressWarnings("unchecked")
        ViewModelHolder<PaymentMethodsViewModel> holder = (ViewModelHolder<PaymentMethodsViewModel>)
                getSupportFragmentManager().findFragmentByTag(PaymentMethodsViewModel.TAG);

        if (holder != null) {
            Log.d(TAG, "ViewModel retained");
            return holder.getViewModel();
        } else {
            UsersAndRestaurantsRemoteDataSource dataSource = ((FastFoodApp) getApplication())
                    .appContainer.usersDataSource;
            PaymentMethodsViewModel viewModel = new PaymentMethodsViewModel(dataSource);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel), PaymentMethodsViewModel.TAG);
            Log.d(TAG, "ViewModel created");
            return viewModel;
        }
    }

    private String onItemSelectedHandler(AdapterView<?> adapterView, View view, int position, long id){
        Adapter adapter = adapterView.getAdapter();
        String choose = adapter.getItem(position).toString();

        return choose;
    }

}