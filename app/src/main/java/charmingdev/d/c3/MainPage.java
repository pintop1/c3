package charmingdev.d.c3;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import charmingdev.d.c3.fragments.CategoryList;
import charmingdev.d.c3.prefs.C3UserSession;
import charmingdev.d.c3.prefs.UserInfo;
import ir.apend.slider.model.Slide;
import ir.apend.slider.ui.Slider;

public class MainPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    MaterialSearchView searchView;
    FragmentManager fragmentManager;
    TabLayout tabLayout;
    private int[] tabIcons = {
            R.drawable.ic_home_white_24dp,
            R.drawable.ic_favorite_border_white_24dp,
            R.drawable.ic_shopping_cart_white_24dp
    };

    C3UserSession c3UserSession;
    UserInfo userInfo;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        c3UserSession = new C3UserSession(this);
        userInfo = new UserInfo(this);
        progressDialog = new ProgressDialog(this);

        hideMenu();

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(true); //or false
        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container_root,
                new HomeFragment()).addToBackStack("shopSearch").commit();

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("Saved"));
        tabLayout.addTab(tabLayout.newTab().setText("Cart"));
        setupTabIcons();
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tabLayout.getSelectedTabPosition() == 0){
                    fragmentManager.beginTransaction().replace(R.id.container_root,
                            new HomeFragment()).addToBackStack("shopSearch").commit();
                }else if(tabLayout.getSelectedTabPosition() == 1){
                    fragmentManager.beginTransaction().replace(R.id.container_root,
                            new SavedFragment()).addToBackStack("shopSearch").commit();
                }else if(tabLayout.getSelectedTabPosition() == 2){
                    fragmentManager.beginTransaction().replace(R.id.container_root,
                            new CartFragment()).addToBackStack("shopSearch").commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }


    // for search
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_page, menu);

        MenuItem item = menu.findItem(R.id.search);
        searchView.setMenuItem(item);

        if(c3UserSession.isUserLoggedIn()){
            MenuItem newMenu = menu.findItem(R.id.settings);
            newMenu.setVisible(true);
        }else {
            MenuItem newMenu = menu.findItem(R.id.settings);
            newMenu.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settings) {
            startActivity(new Intent(MainPage.this, Settings.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            fragmentManager.beginTransaction().replace(R.id.container_root,
                    new HomeFragment()).addToBackStack("shopSearch").commit();
        }else if (id == R.id.nav_books) {
            CategoryList categoryList = new CategoryList();
            Bundle bundle = new Bundle();
            bundle.putString("CAT", "books");
            categoryList.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.container_root,
                    categoryList).addToBackStack("shopSearch").commit();
        }else if(id == R.id.nav_devotionals){
            CategoryList categoryList = new CategoryList();
            Bundle bundle = new Bundle();
            bundle.putString("CAT", "devotionals");
            categoryList.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.container_root,
                    categoryList).addToBackStack("shopSearch").commit();
        }else if(id == R.id.nav_audio){
            CategoryList categoryList = new CategoryList();
            Bundle bundle = new Bundle();
            bundle.putString("CAT", "audio visuals");
            categoryList.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.container_root,
                    categoryList).addToBackStack("shopSearch").commit();
        }else if(id == R.id.nav_bibles){
            CategoryList categoryList = new CategoryList();
            Bundle bundle = new Bundle();
            bundle.putString("CAT", "bibles");
            categoryList.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.container_root,
                    categoryList).addToBackStack("shopSearch").commit();
        }else if(id == R.id.others){
            CategoryList categoryList = new CategoryList();
            Bundle bundle = new Bundle();
            bundle.putString("CAT", "others");
            categoryList.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.container_root,
                    categoryList).addToBackStack("shopSearch").commit();
        }else if(id == R.id.nav_login){
            startActivity(new Intent(MainPage.this, Login.class));
        }else if(id == R.id.nav_logout){
            if(c3UserSession.isUserLoggedIn()){
                c3UserSession.setLoggedIn(false);
                userInfo.clearUserInfo();
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Clearing cached data.....");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        startActivity(new Intent(MainPage.this, MainPage.class));
                        finish();

                    }
                }, 4000);
            }
        }else if(id == R.id.nav_register){
            startActivity(new Intent(MainPage.this, Register.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void Search(){
        MaterialSearchView searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
    }

    private void hideMenu(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_menu = navigationView.getMenu();
        if(c3UserSession.isUserLoggedIn()){
            nav_menu.findItem(R.id.nav_login).setVisible(false);
            nav_menu.findItem(R.id.nav_register).setVisible(false);
            nav_menu.findItem(R.id.nav_logout).setVisible(true);
        }else {
            nav_menu.findItem(R.id.nav_login).setVisible(true);
            nav_menu.findItem(R.id.nav_register).setVisible(true);
            nav_menu.findItem(R.id.nav_logout).setVisible(false);
        }
    }
}
