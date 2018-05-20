package com.arttodevelop.whattocookapp.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import com.arttodevelop.whattocookapp.R;
import com.arttodevelop.whattocookapp.fragments.BudgetFragment;
import com.arttodevelop.whattocookapp.fragments.CurrentDishesFragment;
import com.arttodevelop.whattocookapp.fragments.FindRecipeFragment;
import com.arttodevelop.whattocookapp.fragments.GroupVKFragment;
import com.arttodevelop.whattocookapp.fragments.HomeFragment;
import com.arttodevelop.whattocookapp.fragments.PlaningFragment;
import com.arttodevelop.whattocookapp.fragments.RemindersFragment;
import com.arttodevelop.whattocookapp.fragments.SettingsAppFragment;
import com.arttodevelop.whattocookapp.fragments.ShoppingListFragment;
import com.arttodevelop.whattocookapp.fragments.StatisticsFragment;
import com.arttodevelop.whattocookapp.fragments.YouDishesFragment;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends AppCompatActivity {

    private final int drawer_item_home = 1;
    private final int drawer_item_find_recipe = 2;
    private final int drawer_item_you_dishes = 3;
    private final int drawer_item_shopping_list = 4;
    private final int drawer_item_current_dishes = 5;
    private final int drawer_item_statistics = 6;
    private final int drawer_item_planning = 7;
    private final int drawer_item_budget = 8;
    private final int drawer_item_reminders = 9;
    private final int drawer_item_settings_app = 10;
    private final int drawer_item_group_vk = 11;

    private Toolbar toolbar;
    private Drawer.Result drawerResult;

    private HomeFragment homeFragment;
    private FindRecipeFragment findRecipeFragment;
    private YouDishesFragment youDishesFragment;
    private ShoppingListFragment shoppingListFragment;
    private CurrentDishesFragment currentDishesFragment;
    private StatisticsFragment statisticsFragment;
    private PlaningFragment planingFragment;
    private BudgetFragment budgetFragment;
    private RemindersFragment remindersFragment;
    private SettingsAppFragment settingsAppFragment;
    private GroupVKFragment groupVKFragment;

    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.main_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createFragments();
        initializeToolBar();

        if(savedInstanceState == null) {
            fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.main_container, homeFragment);
            fragmentTransaction.commit();
        }
    }

    private void createFragments()
    {
        homeFragment = new HomeFragment();
        findRecipeFragment = new FindRecipeFragment();
        youDishesFragment = new YouDishesFragment();
        shoppingListFragment = new ShoppingListFragment();
        currentDishesFragment = new CurrentDishesFragment();
        statisticsFragment = new StatisticsFragment();
        planingFragment = new PlaningFragment();
        budgetFragment = new BudgetFragment();
        remindersFragment = new RemindersFragment();
        settingsAppFragment = new SettingsAppFragment();
        groupVKFragment = new GroupVKFragment();
    }

    private void initializeToolBar()
    {
        drawerResult = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        InputMethodManager inputMethodManager = (InputMethodManager) MainActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), 0);
                    }
                    @Override
                    public void onDrawerClosed(View drawerView) {
                    }
                })
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home).withIdentifier(drawer_item_home),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_find_recipe).withIcon(FontAwesome.Icon.faw_search).withIdentifier(drawer_item_find_recipe),

                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_you_dishes).withIcon(FontAwesome.Icon.faw_star_o).withIdentifier(drawer_item_you_dishes),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_shopping_list).withIcon(FontAwesome.Icon.faw_list_ul).withIdentifier(drawer_item_shopping_list),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_current_dishes).withIcon(FontAwesome.Icon.faw_cutlery).withIdentifier(drawer_item_current_dishes),

                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_statistics).withIcon(FontAwesome.Icon.faw_pie_chart).withIdentifier(drawer_item_statistics),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_planning).withIcon(FontAwesome.Icon.faw_bar_chart).withIdentifier(drawer_item_planning),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_budget).withIcon(FontAwesome.Icon.faw_usd).withIdentifier(drawer_item_budget),

                        new SectionDrawerItem().withName(R.string.drawer_title_other),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_reminders).withIcon(FontAwesome.Icon.faw_bell).withIdentifier(drawer_item_reminders),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_settings_app).withIcon(FontAwesome.Icon.faw_cog).withIdentifier(drawer_item_settings_app),
                        new SectionDrawerItem().withName(R.string.drawer_title_contacts),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_group_vk).withIcon(FontAwesome.Icon.faw_vk).withBadge("12+").withIdentifier(drawer_item_group_vk)

                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        fragmentTransaction = getFragmentManager().beginTransaction();

                        if (drawerItem == null)
                        {
                            return;
                        }

                        switch (drawerItem.getIdentifier())
                        {
                            case drawer_item_home:
                                fragmentTransaction.replace(R.id.main_container, homeFragment);
                                break;

                            case drawer_item_find_recipe:
                                fragmentTransaction.replace(R.id.main_container, findRecipeFragment);
                                break;

                            case drawer_item_you_dishes:
                                fragmentTransaction.replace(R.id.main_container, youDishesFragment);
                                break;

                            case drawer_item_shopping_list:
                                fragmentTransaction.replace(R.id.main_container, shoppingListFragment);
                                break;

                            case drawer_item_current_dishes:
                                fragmentTransaction.replace(R.id.main_container, currentDishesFragment);
                                break;

                            case drawer_item_statistics:
                                fragmentTransaction.replace(R.id.main_container, statisticsFragment);
                                break;

                            case drawer_item_planning:
                                fragmentTransaction.replace(R.id.main_container, planingFragment);
                                break;

                            case drawer_item_budget:
                                fragmentTransaction.replace(R.id.main_container, budgetFragment);
                                break;

                            case drawer_item_reminders:
                                fragmentTransaction.replace(R.id.main_container, remindersFragment);
                                break;

                            case drawer_item_settings_app:
                                fragmentTransaction.replace(R.id.main_container, settingsAppFragment);
                                break;

                            case drawer_item_group_vk:
                                fragmentTransaction.replace(R.id.main_container, groupVKFragment);
                                break;
                        }

                        fragmentTransaction.commit();
                    }
                }).build();
    }

    @Override
    public void onBackPressed(){
        if(drawerResult.isDrawerOpen()){
            drawerResult.closeDrawer();
        }
        else{
            super.onBackPressed();
        }
    }

    // Заглушка, работа с меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(Menu.NONE, R.id.menu_create, Menu.NONE, R.string.menu_create);
        menu.add(Menu.NONE, R.id.menu_settings, Menu.NONE, R.string.menu_settings);
        return true;
    }

    // Заглушка, работа с меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id)
        {
            case R.id.menu_settings:

                break;

            case R.id.menu_create:

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
