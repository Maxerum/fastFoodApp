package com.example.fastfoodapp.menu;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.fastfoodapp.databinding.MenuItemInfoDialogBinding;

public class MenuItemInfoDialog extends DialogFragment {
    public static final String TAG = "MenuItemInfoDialog";

    private MenuItemInfoDialogBinding mDataBinding;

    private InfoDialogViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDataBinding = MenuItemInfoDialogBinding.inflate(inflater, container, false);
        mDataBinding.setViewModel(mViewModel);

        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageButton dismissButton = mDataBinding.dismissDialog;
        dismissButton.setOnClickListener(v -> {
            dismiss();
        });
    }

    public void setViewModel(InfoDialogViewModel viewModel) {
        mViewModel = viewModel;
    }
}
