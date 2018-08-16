package banyan.com.e_commerce_app.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.view.MenuInflater;
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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import banyan.com.e_commerce_app.Adapter.SectionListDataAdapter;
import banyan.com.e_commerce_app.Converter;
import banyan.com.e_commerce_app.Fragment.Fragment_Vegitable;
import banyan.com.e_commerce_app.Fragment.ProductViewSearch_Fragment;
import banyan.com.e_commerce_app.Fragment.MainPagefragment;
import banyan.com.e_commerce_app.Fragment.Product_Fragment;
import banyan.com.e_commerce_app.Fragment.Profile_Fragment;
import banyan.com.e_commerce_app.Model.SectionDataModel;
import banyan.com.e_commerce_app.Model.SingleItemModel;
import banyan.com.e_commerce_app.R;
import banyan.com.e_commerce_app.SupportInterface.AddorRemoveCallbacks;

public class MainActivity extends AppCompatActivity implements AddorRemoveCallbacks,View.OnClickListener{
    MainPagefragment mfragment;
    public static int navItemIndex = 0;
    private boolean shouldLoadHomeFragOnBackPress = true;
    private ImageView img_nav_header_bg, img_profile; // nav header image
    private TextView profile_text,myProfile,trackOrder,logOut;  // nav header text
    SectionListDataAdapter searchAdapter;
    private ArrayList<SingleItemModel> llist;
    private static final String TAG_HOME = "HOME";
    private static final String TAG_GROCESSORIES = "GROCESSORIES";
    private static final String TAG_FRUITS = "FRUITS";
    private static final String TAG_VEGITABLE = "VEGITABLES";
    private static final String TAG_MYPROFILE = "MY PROFILE";
    private static final String TAG_TRACKORDER ="TRACK ORDER";
    private static final String TAG_LOGOUT = "LOGOUT";
    public static String TAG_CURRENT = TAG_HOME;
    NavigationView navigationView;
    private View navHeader;
    String str_user_name = "";
    private static int cart_count=0;

    final Handler handler = new Handler();
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private String[] activityTitles;
    LinearLayout headerClick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        llist = new ArrayList<>();
        System.out.println("GETDATA_FROMLIST :"+llist);

        searchAdapter = new SectionListDataAdapter(getApplicationContext(),llist);

            FragmentManager fmanager = this.getSupportFragmentManager();
            FragmentTransaction fTransaction = fmanager.beginTransaction();
            mfragment = new MainPagefragment();
            fTransaction.add(R.id.frame, mfragment);
            fTransaction.addToBackStack("MainPage");
            fTransaction.commit();
            activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

         navigationView = (NavigationView) findViewById(R.id.nav_view);
         navHeader = navigationView.getHeaderView(0);
         headerClick = (LinearLayout)navHeader.findViewById(R.id.header_click);
         headerClick.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(MainActivity.this,Profile_Activity.class);
                 intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY);
                 startActivity(intent);
             }
         });
         profile_text = (TextView)navHeader.findViewById(R.id.profile_name);
         img_profile = (ImageView)navHeader.findViewById(R.id.img_profile);

         /*myProfile = (TextView)navHeader.findViewById(R.id.myProfile);
         myProfile.setOnClickListener(this);
         trackOrder = (TextView)navHeader.findViewById(R.id.trackOrder);
         trackOrder.setOnClickListener(this);
         logOut = (TextView)navHeader.findViewById(R.id.trackOrder);
         logOut.setOnClickListener(this);*/


        loadNavHeader();
        setUpNavigationView();

        if (savedInstanceState == null) {

            navItemIndex = 0;
            TAG_CURRENT = TAG_HOME;
            loadHomeFragment();

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawers();
            return;

        }if (shouldLoadHomeFragOnBackPress) {

            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {

                navItemIndex = 0;
                TAG_CURRENT = TAG_HOME;
                loadHomeFragment();
                return;

            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        if (navItemIndex == 0) {

            getMenuInflater().inflate(R.menu.main, menu);

        }

        // show menu only when home fragment is selected
        if (navItemIndex == 1) {

            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.grocessories_menu, menu);
            MenuItem menuItem = menu.findItem(R.id.cart_action);
            menuItem.setIcon(Converter.convertLayoutToImage(MainActivity.this,cart_count,R.drawable.ic_shopping_cart));

            MenuItem searchItem = menu.findItem(R.id.action_search);

            SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

            SearchView searchView = null;
            if (searchItem != null) {
                searchView = (SearchView) searchItem.getActionView();
            }
            if (searchView != null) {
                searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
            }
        }

        // show menu only when category click
        if(navItemIndex == 2)
        {
            //getMenuInflater().inflate(R.menu.product_spinner, menu);

            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.product_spinner, menu);
            MenuItem mitem=menu.findItem(R.id.search);
            SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

            SearchView searchView = null;
            if (mitem != null) {
                searchView = (SearchView) mitem.getActionView();
            }
            if (searchView != null) {
                searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
            }


            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    return false;
                }
            });
        }

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }/*else if(id == R.id.action_search)
        {
            Toast.makeText(this, "Search Option Clicked", Toast.LENGTH_SHORT).show();
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    // add product count and remove
    @Override
    public void onAddProduct() {
        cart_count++;
        invalidateOptionsMenu();

    }

    @Override
    public void onRemoveProduct() {
        cart_count--;
        invalidateOptionsMenu();

    }

 // nav menu select
    private void selectNavMenu() {

        navigationView.getMenu().getItem(navItemIndex).setChecked(true);


    }


    private void loadNavHeader() {
        // name, website
        if (str_user_name != null)
            profile_text.setText("Profile Name ");

        // Loading profile image
        img_profile.setImageDrawable(getResources().getDrawable(R.drawable.profileimg));

    }

    // convert round image in navigation image
    public static Bitmap getRoundedRectBitmap(Bitmap bitmap, int pixels) {
        Bitmap result = null;
        try {
            result = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);

            int color = 0xff424242;
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, 200, 200);

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawCircle(50, 50, 50, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

        } catch (NullPointerException e) {
        } catch (OutOfMemoryError o) {
        }
        return result;
    }
    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(TAG_CURRENT) != null) {
            drawer.closeDrawers();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {

                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, TAG_CURRENT);
                fragmentTransaction.commitAllowingStateLoss();

            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            handler.post(mPendingRunnable);
        }

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }


    // select navigation menu
    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
               MainPagefragment fragmentHome = new MainPagefragment();
                return fragmentHome;

            case 1:
                // photos
                Fragment_Vegitable vegFragment = new Fragment_Vegitable();
                return vegFragment;

            case 2:
                ProductViewSearch_Fragment categoriesSearch=new ProductViewSearch_Fragment();
                return categoriesSearch;

            case 3:
                Product_Fragment pfragment = new Product_Fragment();
                return pfragment;

            case 4:

                Profile_Fragment profile_fragment = new Profile_Fragment();
                return profile_fragment;

            case 5:
                Intent intent= new Intent(getApplicationContext(),Demo_Activity.class);
                startActivity(intent);
                break;
            case 6:
                break;

            default:
                return new MainPagefragment();
        }
        return new MainPagefragment();
    }

    private void setUpNavigationView() {
     navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {

            case R.id.nav_home:
                navItemIndex = 0;
                TAG_CURRENT = TAG_HOME;
                break;
            case R.id.nav_grocery:
                navItemIndex = 1;
                TAG_CURRENT = TAG_GROCESSORIES;
                break;
            case R.id.nav_fruits:
                navItemIndex = 2;
                TAG_CURRENT = TAG_FRUITS;
                break;
            case R.id.nav_vegitables:
                navItemIndex = 3;
                TAG_CURRENT = TAG_VEGITABLE;
                break;
            case R.id.my_profile:
                navItemIndex = 4;
                TAG_CURRENT = TAG_MYPROFILE;
                break;
            case R.id.track_order:
                navItemIndex = 5;
                TAG_CURRENT = TAG_TRACKORDER;
                break;
            case R.id.logout:
                navItemIndex = 6;
                TAG_CURRENT = TAG_LOGOUT;
                break;
                default:
               navItemIndex = 0;

        }

        if(item.isChecked())
        {
          item.setChecked(false);
        }
        else
        {
            item.setChecked(true);
        }
        item.setChecked(true);

        loadHomeFragment();
        return true;
    }
   });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {

                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerOpened(View drawerView) {

                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);

            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.myProfile:
                /*FragmentManager fm = this.getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Profile_Fragment profile_fragment = new Profile_Fragment();
                ft.replace(R.id.frame,profile_fragment);
                ft.addToBackStack(null);
                ft.commit();*/
                break;
            case R.id.trackOrder:

                break;
            case R.id.logOut:

                break;
                default:
                    break;
        }
    }
}
