package com.example.fastfoodapp.orderdetails;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.fastfoodapp.AppContainer;
import com.example.fastfoodapp.FastFoodApp;
import com.example.fastfoodapp.R;
import com.example.fastfoodapp.databinding.OrderDetailsFragmentBinding;
import com.example.fastfoodapp.data.Restaurant;
import com.example.fastfoodapp.data.order.PaymentMethod;
import com.example.fastfoodapp.menu.MenuActivity;
import com.example.fastfoodapp.util.ActivityUtils;

public class OrderDetailsFragment extends Fragment implements OrderDetailsNavigator,
        ItemNavigator, ToastMessageChangedCallback{

    public static final String TAG = "OrderDetailsFragment";

    private OrderDetailsFragmentBinding mBinding;

    private OrderDetailsViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = OrderDetailsFragmentBinding.inflate(inflater, container, false);
        mBinding.setViewModel(mViewModel);

        return mBinding.getRoot();
    }

    public void setViewModel(OrderDetailsViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void restaurantSelected(Restaurant restaurant) {
        getParentFragmentManager().popBackStack();
        mViewModel.updateRestaurant(restaurant);
    }

    @Override
    public void paymentMethodSelected(PaymentMethod paymentMethod) {
        getParentFragmentManager().popBackStack();
        mViewModel.updatePaymentMethod(paymentMethod);
    }

    @Override
    public void openRestaurantsList() {
        FragmentManager manager = getParentFragmentManager();
        RestaurantsListFragment fragment = new RestaurantsListFragment();

        AppContainer container = ((FastFoodApp) requireContext()
                .getApplicationContext()).appContainer;
        RestaurantsListViewModel viewModel = new RestaurantsListViewModel(container.usersDataSource);
        fragment.setViewModel(viewModel);
        fragment.setNavigator(this);

        ActivityUtils.addFragmentToActivity(manager, fragment, R.id.my_container, true);
    }

    @Override
    public void openPaymentMethods() {
        FragmentManager manager = getParentFragmentManager();
        PaymentMethodFragment fragment = new PaymentMethodFragment();

        PaymentMethodViewModel viewModel = new PaymentMethodViewModel();
        viewModel.setNavigator(this);

        fragment.setViewModel(viewModel);

        ActivityUtils.addFragmentToActivity(manager, fragment, R.id.my_container, true);
    }

    @Override
    public void onMessageChanged(String newMessage) {
        if (newMessage != null && !newMessage.isEmpty()) {

            Toast.makeText(getContext(), newMessage, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void returnToHomeScreen() {
        Intent intent = new Intent(getActivity(), MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        requireContext().startActivity(intent);
    }
}
