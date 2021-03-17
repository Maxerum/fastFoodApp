package com.example.fastfoodapp.eugene.menu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fastfoodapp.Maxerum.OrderPayingActivity;
import com.example.fastfoodapp.R;
import com.example.fastfoodapp.eugene.ViewModelHolder;
import com.example.fastfoodapp.eugene.data.MenuItemsDatabase;
import com.example.fastfoodapp.eugene.data.MenuItemsLocalDataSource;
import com.example.fastfoodapp.eugene.data.category.MenuCategories;
import com.example.fastfoodapp.eugene.data.category.MenuCategory;
import com.example.fastfoodapp.eugene.data.item.MenuItem;
import com.example.fastfoodapp.eugene.util.ActivityUtils;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MenuActivity extends AppCompatActivity implements ShoppingCartNavigator {

    private ExecutorService executorService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        executorService = Executors.newFixedThreadPool(4);

        MenuFragment fragment = findOrCreateFragment();

        MenuViewModel viewModel = findOrCreateViewModel();
        viewModel.setShoppingCartNavigator(this);
        fragment.setViewModel(viewModel);
    }

    private MenuFragment findOrCreateFragment() {
        MenuFragment menuFragment = (MenuFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if (menuFragment == null) {

            menuFragment = MenuFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), menuFragment,
                    R.id.fragment_container);
        }
        return menuFragment;
    }

    private MenuViewModel findOrCreateViewModel() {
        @SuppressWarnings("unchecked")
        ViewModelHolder<MenuViewModel> holder = (ViewModelHolder<MenuViewModel>)
                getSupportFragmentManager().findFragmentByTag(MenuViewModel.TAG);

        if (holder != null) {
            return holder.getViewModel();
        } else {
            MenuItemsDatabase db = MenuItemsDatabase.newInstance(this);
            MenuItemsLocalDataSource dataSource = MenuItemsLocalDataSource.newInstance(executorService,
                    db.menuItemsDao(), db.menuCategoryDao());

            executorService.execute(() -> {
                if (db.menuCategoryDao().getAll().isEmpty()) {
                    prepopulateDatabase(dataSource);
                }
            });
            MenuViewModel viewModel = new MenuViewModel(dataSource);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel), MenuViewModel.TAG);

            return viewModel;
        }
    }

    private void prepopulateDatabase(MenuItemsLocalDataSource dataSource) {
        ArrayList<MenuCategory> categories = new ArrayList<>();
        categories.add(new MenuCategory(MenuCategories.BURGERS.toString()));
        categories.add(new MenuCategory(MenuCategories.SNACKS_AND_SIDES.toString()));
        categories.add(new MenuCategory(MenuCategories.BEVERAGES.toString()));
        categories.add(new MenuCategory(MenuCategories.DESSERTS_AND_SHAKES.toString()));
        categories.add(new MenuCategory(MenuCategories.BREAKFAST.toString()));

        dataSource.addMenuCategories(categories);

//        dataSource.deleteAllCategories();
//        dataSource.deleteAllMenuItems();

        String uriFormat = "android.resource://com.example.fastfoodapp/";

        dataSource.addNewMenuItem(new MenuItem("Double Half Pounder", "4.50 $",
                uriFormat + R.drawable.double_half_pounder,
                "740Cal.", "42g", "43g", "48g", MenuCategories.BURGERS.toString()));
        dataSource.addNewMenuItem(new MenuItem("Double Burger", "3.90 $",
                uriFormat + R.drawable.double_burger,
                "400Cal.", "20g", "33g", "22g", MenuCategories.BURGERS.toString()));
        dataSource.addNewMenuItem(new MenuItem("Pounder with Bacon", "4.30 $",
                uriFormat + R.drawable.pounder_with_bacon,
                "630Cal.", "35g", "43g", "36g", MenuCategories.BURGERS.toString()));
        dataSource.addNewMenuItem(new MenuItem("Pounder with Cheese", "3.90 $",
                uriFormat + R.drawable.pounder_with_cheese,
                "630Cal.", "37g", "44g", "30g", MenuCategories.BURGERS.toString()));
        dataSource.addNewMenuItem(new MenuItem("Chicken Sandwich", "3.20 $",
                uriFormat + R.drawable.chicken_sandwich,
                "530Cal.", "26g", "47g", "27g", MenuCategories.BURGERS.toString()));
        dataSource.addNewMenuItem(new MenuItem("Chicken Sandwich Deluxe", "3.75 $",
                uriFormat + R.drawable.chicken_sandwich_deluxe,
                "5300Cal.", "26g", "47g", "27g", MenuCategories.BURGERS.toString()));


        dataSource.addNewMenuItem(new MenuItem("Fries Small", "1.10 $", uriFormat + R.drawable.fries_small,
                "220Cal.", "10g", "29g", "3g", MenuCategories.SNACKS_AND_SIDES.toString()));
        dataSource.addNewMenuItem(new MenuItem("Fries Medium", "1.30 $", uriFormat + R.drawable.fries_medium,
                "320Cal.", "15g", "43g", "5g", MenuCategories.SNACKS_AND_SIDES.toString()));
        dataSource.addNewMenuItem(new MenuItem("Fries Large", "1.60 $", uriFormat + R.drawable.fries_large,
                "490Cal.", "23g", "66g", "7g", MenuCategories.SNACKS_AND_SIDES.toString()));
        dataSource.addNewMenuItem(new MenuItem("Apple Slices", "1.00 $", uriFormat + R.drawable.apple_slices,
                "15Cal.", "0g", "4g", "0g", MenuCategories.SNACKS_AND_SIDES.toString()));


        dataSource.addNewMenuItem(new MenuItem("Coca-Cola Small", "1.20 $", uriFormat + R.drawable.coca_cola,
                "150Cal.", "0g", "39g", "0g", MenuCategories.BEVERAGES.toString()));
        dataSource.addNewMenuItem(new MenuItem("Sprite Small", "1.20 $", uriFormat + R.drawable.sprite,
                "140Cal.", "0g", "35g", "0g", MenuCategories.BEVERAGES.toString()));
        dataSource.addNewMenuItem(new MenuItem("Fanta Small", "1.20 $", uriFormat + R.drawable.fanta,
                "150Cal.", "0g", "39g", "0g", MenuCategories.BEVERAGES.toString()));


        dataSource.addNewMenuItem(new MenuItem("Chocolate Shake Small", "3.00 $", uriFormat + R.drawable.chocolate_shake,
                "520Cal.", "14g", "85g", "12g", MenuCategories.DESSERTS_AND_SHAKES.toString()));
        dataSource.addNewMenuItem(new MenuItem("Strawberry Shake Small", "3.00 $", uriFormat + R.drawable.strawberry_shake,
                "500Cal.", "14g", "80g", "11g", MenuCategories.DESSERTS_AND_SHAKES.toString()));
        dataSource.addNewMenuItem(new MenuItem("Vanilla Shake Small", "3.00 $", uriFormat + R.drawable.vanilla_shake,
                "490Cal.", "13g", "79g", "11g", MenuCategories.DESSERTS_AND_SHAKES.toString()));


        dataSource.addNewMenuItem(new MenuItem("Muffin with Egg", "2.90 $", uriFormat + R.drawable.muffin_with_egg,
                "480Cal.", "31g", "30g", "20g", MenuCategories.BREAKFAST.toString()));
        dataSource.addNewMenuItem(new MenuItem("Sausage Muffin", "2.40 $", uriFormat + R.drawable.muffin_with_sausage,
                "400Cal.", "26g", "29g", "14g", MenuCategories.BREAKFAST.toString()));
        dataSource.addNewMenuItem(new MenuItem("Double Fresh Muffin", "3.10 $", uriFormat + R.drawable.double_fresh_muffin,
                "506Cal.", "31g", "32g", "26g", MenuCategories.BREAKFAST.toString()));
        dataSource.addNewMenuItem(new MenuItem("Pancakes", "2.05 $", uriFormat + R.drawable.pancakes,
                "580Cal.", "15g", "101g", "9g", MenuCategories.BREAKFAST.toString()));

    }

    @Override
    public void openOrderPayingActivity() {
        Intent intent = new Intent(this, OrderPayingActivity.class);
        startActivity(intent);
    }
}
