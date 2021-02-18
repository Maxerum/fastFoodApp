package com.example.fastfoodapp.eugene;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ViewModelHolder<VM> extends Fragment {

    private VM mViewModel;

    public static <M> ViewModelHolder createContainer(M viewModel) {
        ViewModelHolder<M> viewModelHolder = new ViewModelHolder<>();
        viewModelHolder.setViewModel(viewModel);
        return viewModelHolder;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public VM getViewModel() {
        return mViewModel;
    }

    public void setViewModel(VM mViewModel) {
        this.mViewModel = mViewModel;
    }
}
