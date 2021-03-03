package com.example.fastfoodapp.eugene.menu;

import android.content.Context;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodapp.eugene.data.MenuItem;

import java.util.ArrayList;

public class MenuPageViewModel {

    public final ObservableArrayList<MenuItem> mItems = new ObservableArrayList<>();

    private Context mContext;

    public MenuPageViewModel(Context context) {
        mContext = context;
    }

    public void start(String category) {
        loadData(category);
    }

    public void loadData(String category) {
        ArrayList<MenuItem> items = new ArrayList<>();

        items.add(new MenuItem("Cheeseburger", 0, 0, "2.40 $", null));
        items.add(new MenuItem("The Original Burger", 0, 0, "4.90 $", null));
        items.add(new MenuItem("Chargrilled Burger", 0, 0, "5.00 $", null));
        items.add(new MenuItem("Luger Burger", 0, 0, "1.70 $", null));

        mItems.clear();
        mItems.addAll(items);
    }

    @BindingAdapter("app:items")
    public static void setItems(RecyclerView recyclerView, ArrayList<MenuItem> items) {
        ((MenuPageFragment.MenuItemsAdapter) recyclerView.getAdapter()).replaceData(items);
    }
}
