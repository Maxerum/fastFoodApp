package com.example.fastfoodapp.data;

import com.example.fastfoodapp.R;
import com.example.fastfoodapp.data.category.MenuCategories;
import com.example.fastfoodapp.data.category.MenuCategoriesDao;
import com.example.fastfoodapp.data.category.MenuCategory;
import com.example.fastfoodapp.data.item.MenuItem;
import com.example.fastfoodapp.data.item.MenuItemMainInfo;
import com.example.fastfoodapp.data.item.MenuItemsDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class MenuItemsLocalDataSource implements MenuItemsDataSource {

    private final Executor executor;
    private final MenuItemsDao menuItemsDao;
    private final MenuCategoriesDao menuCategoriesDao;

    private MenuItemsLocalDataSource(Executor executor, MenuItemsDao menuItemsDao,
                                     MenuCategoriesDao menuCategoriesDao) {
        this.executor = executor;
        this.menuItemsDao = menuItemsDao;
        this.menuCategoriesDao = menuCategoriesDao;
    }

    public static MenuItemsLocalDataSource newInstance(Executor executor, MenuItemsDao menuItemsDao,
                                                       MenuCategoriesDao menuCategoriesDao) {
        return new MenuItemsLocalDataSource(executor, menuItemsDao, menuCategoriesDao);
    }

    @Override
    public void addNewMenuItem(MenuItem menuItem) {
        executor.execute(() -> menuItemsDao.insertAll(menuItem));
    }

    @Override
    public void getMenuItemsMainInfo(String category, LoadMenuItemsMainInfoCallback callback) {
        executor.execute(() -> {
            final List<MenuItemMainInfo> items = menuItemsDao.getMenuItemsMainInfo(category);
            if (!items.isEmpty()) {
                callback.onMenuItemsLoaded(items);
            } else {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getMenuItemByTitle(String title, GetMenuItemCallback callback) {
        executor.execute(() -> {
            final MenuItem item = menuItemsDao.getMenuItemByTitle(title);
            if (item != null) {
                callback.onMenuItemLoaded(item);
            } else {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void deleteAllMenuItems() {
        executor.execute(() -> {
            menuItemsDao.deleteAll();
        });
    }

    @Override
    public void addMenuCategories(ArrayList<MenuCategory> categories) {
        MenuCategory[] categoriesArray = new MenuCategory[categories.size()];
        for (int i = 0; i < categories.size(); i++) {
            categoriesArray[i] = categories.get(i);
        }
        executor.execute(() -> { menuCategoriesDao.insertAll(categoriesArray); });
    }

    @Override
    public void getMenuCategories(LoadMenuCategoriesCallback callback) {
        executor.execute(() -> {
            final List<MenuCategory> categories = menuCategoriesDao.getAll();
            if (!categories.isEmpty()) {
                callback.onMenuCategoriesLoaded(categories);
            } else {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void deleteAllCategories() {
        executor.execute(() -> {
            menuCategoriesDao.deleteAll();
        });
    }

    @Override
    public void prepopulateDatabaseIfNeeded() {
        executor.execute(() -> {
            if (menuCategoriesDao.getAll().isEmpty()) {
//                prepopulateDatabase();
            } else {
//                menuCategoriesDao.deleteAll();
//                menuItemsDao.deleteAll();
            }
        });
    }

    private void prepopulateDatabase() {

        ArrayList<MenuCategory> categories = new ArrayList<>();
        categories.add(new MenuCategory(MenuCategories.BURGERS.toString()));
        categories.add(new MenuCategory(MenuCategories.SNACKS_AND_SIDES.toString()));
        categories.add(new MenuCategory(MenuCategories.BEVERAGES.toString()));
        categories.add(new MenuCategory(MenuCategories.DESSERTS_AND_SHAKES.toString()));
        categories.add(new MenuCategory(MenuCategories.BREAKFAST.toString()));

        addMenuCategories(categories);
        String uriFormat = "android.resource://com.example.fastfoodapp/";

        addNewMenuItem(new MenuItem("Double Half Pounder", "4.50 $",
                uriFormat + R.drawable.double_half_pounder,
                "740Cal.", "42g", "43g", "48g", MenuCategories.BURGERS.toString()));
        addNewMenuItem(new MenuItem("Double Burger", "3.90 $",
                uriFormat + R.drawable.double_burger,
                "400Cal.", "20g", "33g", "22g", MenuCategories.BURGERS.toString()));
        addNewMenuItem(new MenuItem("Pounder with Bacon", "4.30 $",
                uriFormat + R.drawable.pounder_with_bacon,
                "630Cal.", "35g", "43g", "36g", MenuCategories.BURGERS.toString()));
        addNewMenuItem(new MenuItem("Pounder with Cheese", "3.90 $",
                uriFormat + R.drawable.pounder_with_cheese,
                "630Cal.", "37g", "44g", "30g", MenuCategories.BURGERS.toString()));
        addNewMenuItem(new MenuItem("Chicken Sandwich", "3.20 $",
                uriFormat + R.drawable.chicken_sandwich,
                "530Cal.", "26g", "47g", "27g", MenuCategories.BURGERS.toString()));
        addNewMenuItem(new MenuItem("Chicken Sandwich Deluxe", "3.75 $",
                uriFormat + R.drawable.chicken_sandwich_deluxe,
                "5300Cal.", "26g", "47g", "27g", MenuCategories.BURGERS.toString()));


        addNewMenuItem(new MenuItem("Fries Small", "1.10 $", uriFormat + R.drawable.fries_small,
                "220Cal.", "10g", "29g", "3g", MenuCategories.SNACKS_AND_SIDES.toString()));
        addNewMenuItem(new MenuItem("Fries Medium", "1.30 $", uriFormat + R.drawable.fries_medium,
                "320Cal.", "15g", "43g", "5g", MenuCategories.SNACKS_AND_SIDES.toString()));
        addNewMenuItem(new MenuItem("Fries Large", "1.60 $", uriFormat + R.drawable.fries_large,
                "490Cal.", "23g", "66g", "7g", MenuCategories.SNACKS_AND_SIDES.toString()));
        addNewMenuItem(new MenuItem("Apple Slices", "1.00 $", uriFormat + R.drawable.apple_slices,
                "15Cal.", "0g", "4g", "0g", MenuCategories.SNACKS_AND_SIDES.toString()));


        addNewMenuItem(new MenuItem("Coca-Cola Small", "1.20 $", uriFormat + R.drawable.coca_cola,
                "150Cal.", "0g", "39g", "0g", MenuCategories.BEVERAGES.toString()));
        addNewMenuItem(new MenuItem("Sprite Small", "1.20 $", uriFormat + R.drawable.sprite,
                "140Cal.", "0g", "35g", "0g", MenuCategories.BEVERAGES.toString()));
        addNewMenuItem(new MenuItem("Fanta Small", "1.20 $", uriFormat + R.drawable.fanta,
                "150Cal.", "0g", "39g", "0g", MenuCategories.BEVERAGES.toString()));


        addNewMenuItem(new MenuItem("Chocolate Shake Small", "3.00 $", uriFormat + R.drawable.chocolate_shake,
                "520Cal.", "14g", "85g", "12g", MenuCategories.DESSERTS_AND_SHAKES.toString()));
        addNewMenuItem(new MenuItem("Strawberry Shake Small", "3.00 $", uriFormat + R.drawable.strawberry_shake,
                "500Cal.", "14g", "80g", "11g", MenuCategories.DESSERTS_AND_SHAKES.toString()));
        addNewMenuItem(new MenuItem("Vanilla Shake Small", "3.00 $", uriFormat + R.drawable.vanilla_shake,
                "490Cal.", "13g", "79g", "11g", MenuCategories.DESSERTS_AND_SHAKES.toString()));


        addNewMenuItem(new MenuItem("Muffin with Egg", "2.90 $", uriFormat + R.drawable.muffin_with_egg,
                "480Cal.", "31g", "30g", "20g", MenuCategories.BREAKFAST.toString()));
        addNewMenuItem(new MenuItem("Sausage Muffin", "2.40 $", uriFormat + R.drawable.muffin_with_sausage,
                "400Cal.", "26g", "29g", "14g", MenuCategories.BREAKFAST.toString()));
        addNewMenuItem(new MenuItem("Double Fresh Muffin", "3.10 $", uriFormat + R.drawable.double_fresh_muffin,
                "506Cal.", "31g", "32g", "26g", MenuCategories.BREAKFAST.toString()));
        addNewMenuItem(new MenuItem("Pancakes", "2.05 $", uriFormat + R.drawable.pancakes,
                "580Cal.", "15g", "101g", "9g", MenuCategories.BREAKFAST.toString()));
    }
}
