package com.example.fastfoodapp.cardsdialog;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodapp.data.UsersAndRestaurantsRemoteDataSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class PaymentMethodsViewModel implements UncheckPaymentMethod {

    public static final String TAG = "PaymentMethodsViewModel";

    private final UsersAndRestaurantsRemoteDataSource mDataSource;

    public final ObservableArrayList<String> mPaymentMethodsNames = new ObservableArrayList<>();

    private ArrayList<PaymentMethodViewModel> mViewModels;

    private final PaymentMethodsViewModel instance;

    private DismissDialog dismissDialog;

    public PaymentMethodsViewModel(UsersAndRestaurantsRemoteDataSource dataSource) {
        mDataSource = dataSource;
        mViewModels = new ArrayList<>();
        instance = this;
    }

    public void start() {
        getPaymentMethodsNames();
    }

    private void getPaymentMethodsNames() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

//        mDataSource.getUserPaymentMethodsNames(user.getUid(), new UsersAndRestaurantsDataSource.GetUserPaymentMethodsNamesCallback() {
//            @Override
//            public void onGetAllUserPaymentMethodsNames(ArrayList<String> paymentMethodsNames) {
//                if (mViewModels.isEmpty()) {
//                    for (String name : paymentMethodsNames) {
//                        PaymentMethodViewModel viewModel = new PaymentMethodViewModel();
//                        viewModel.setPaymentMethodName(name);
//                        viewModel.setUncheckPaymentMethod(instance);
//                        mViewModels.add(viewModel);
//                    }
//                }
//
//                mPaymentMethodsNames.clear();
//                mPaymentMethodsNames.addAll(paymentMethodsNames);
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//                Log.d(TAG, "Get payment methods failed");
//            }
//        });
    }

    public ArrayList<PaymentMethodViewModel> getViewModels() {
        return mViewModels;
    }

    @Override
    public void uncheckPaymentMethod() {
        if (mViewModels != null) {
            for (PaymentMethodViewModel viewModel : mViewModels) {
                if (viewModel.checked.get()) {
                    viewModel.checked.set(false);
                }
            }
        }
    }

    public String getCheckedPaymentMethodName() {
        if (mViewModels != null) {
            for (PaymentMethodViewModel viewModel : mViewModels) {
                if (viewModel.checked.get()) {
                    return viewModel.paymentMethodName.get();
                }
            }
        }
        return null;
    }

    @BindingAdapter("app:items")
    public static void setItems(RecyclerView recyclerView, ArrayList<String> items) {
//        ((PaymentMethodsDialog.PaymentMethodsAdapter) recyclerView.getAdapter())
//                .replaceData(items);
    }

    public void dismissDialog() {
        if (dismissDialog != null) {
            dismissDialog.dismissDialog();
        }
    }

    public void setDismissDialog(DismissDialog d) {
        dismissDialog = d;
    }
}
