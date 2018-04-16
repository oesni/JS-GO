package com.example.inseo.js_go;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,TabLayout.OnTabSelectedListener,ViewPager.OnPageChangeListener,
            NavigationView.OnNavigationItemSelectedListener,GestureDetector.OnDoubleTapListener{
    Toolbar toolbar = null;
    TabLayout tabLayout = null;
    ViewPager viewPager = null;

    FPagerAdapter pagerAdapter = null;
    static NavigationView navigationView = null;
    DrawerLayout drawerLayout = null;
    FloatingActionButton fab = null;
    CoordinatorLayout coordinatorLayout = null;
    CollapsingToolbarLayout collapsingToolbarLayout = null;
    WebFragment webFragment = null;
    HomeFragment homeFragment = null;
    static TextView drawerHeader_id = null;
    static TextView drawerHeader_name = null;
    static TextView drawerHeader_email = null;
    static TextView drawerHeader_dept = null;
    private static Context appContext = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appContext = this.getApplicationContext();

        //init
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        pagerAdapter = new FPagerAdapter(getSupportFragmentManager());
        navigationView = (NavigationView)findViewById(R.id.navigationView);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        fab = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinatorLayout);
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);

        View headerView = navigationView.getHeaderView(0);
        drawerHeader_id = (TextView) headerView.findViewById(R.id.drawerHeader_id);
        drawerHeader_name = (TextView)headerView.findViewById(R.id.drawerHeader_name);
        drawerHeader_email = (TextView)headerView.findViewById(R.id.drawerHeader_email);
        drawerHeader_dept = (TextView)headerView.findViewById(R.id.drawerHeader_dept);
        navigationView.setNavigationItemSelectedListener(this);

        //set ActionBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("정석 Go");
//        getSupportActionBar().setHomeAsUpIndicator(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(this);

        collapsingToolbarLayout.setTitleEnabled(false);

        fab.setOnClickListener(this);

        JSPreference.setContext(getApplicationContext());

        if(JSPreference.getValue(JSPreference.isLogin,false)) {
            setDrawer(false);

        }

        //Cookie Sync
//        CookieManager cookieManager = CookieManager.getInstance();
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            CookieSyncManager.createInstance(this);
//        }
//        cookieManager.setAcceptCookie(true);


    }// onCreate

    @Override
    protected void onStart() {
        super.onStart();
//        if(JSPreference.getValue(JSPreference.isLogin,false)) {
//            //if logged in
//            String eid,epwd;
//            eid = JSPreference.getValue(JSPreference.eid,"");
//            epwd = JSPreference.getValue(JSPreference.epwd,"");
//            Log.d("eid",eid);
//            Log.d("epwd",epwd);
//            Thread loginThread =
//                    LoginManager.getLoginThread(eid,epwd,true);
//            loginThread.start();
//            this.setDrawer();
//            try {
//                loginThread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            int id = viewPager.getCurrentItem();

//
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.floatingActionButton:
                Intent intent1 = new Intent(MainActivity.this,BarcodeActivity.class);
                startActivity(intent1);
                break;
        }

    }


    //OnTabSelectedListener
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        Log.d("onTabReselected","onTabReselected");
        if(tab.getText().equals("모바일 웹")) {
            Log.d("resel","resle");
            if(webFragment == null) {
                webFragment = (WebFragment) viewPager.getAdapter().instantiateItem(viewPager,viewPager.getCurrentItem());
            }
            try {
                webFragment.reloadHome();
            }catch (Exception e){
                Log.d("onTabReselected","reloadHome()");
            }
//            Log.d("onTabReselected","webFragmentReselected!");
        } else if(tab.getText().equals("홈화면")) {
            if(JSPreference.getValue(JSPreference.isLogin,false)) {
                homeFragment.loadDataFromServer();
            }
        }
    }
    //OnPageChangeListener
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.drawer_home:
                viewPager.setCurrentItem(0);
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.drawer_study:
                viewPager.setCurrentItem(1);
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.drawer_book:
                viewPager.setCurrentItem(2);
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.drawer_web:
                viewPager.setCurrentItem(3);
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.drawer_login:
                if(JSPreference.getValue(JSPreference.isLogin,false)) {
                    Thread logoutThread = LoginManager.getLogoutThread();
                    logoutThread.start();
                    setDrawer(true);

                } else {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
//                    finish();
                }
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
        }
        return false;
    }

    public static Context getApplicationContextFromMain()
    {
        return appContext;
    }



    public static void setDrawer(boolean asDefault) {
        if(asDefault) {
            drawerHeader_id.setText("학번");
            drawerHeader_name.setText("이름");
            drawerHeader_email.setText("이메일");
            drawerHeader_dept.setText("학과");

            navigationView.getMenu().findItem(R.id.drawer_login).setTitle("로그인").setIcon(R.drawable.drawer_login);
        } else {
            drawerHeader_id.setText(JSPreference.getValue(JSPreference.id, "학번"));
            drawerHeader_name.setText(JSPreference.getValue(JSPreference.name, "이름"));
            drawerHeader_email.setText(JSPreference.getValue(JSPreference.email, "이메일"));
            drawerHeader_dept.setText(JSPreference.getValue(JSPreference.dept, "학과"));

            navigationView.getMenu().findItem(R.id.drawer_login).setTitle("로그아웃").setIcon(R.drawable.drawer_logout);
        }
    }

    public static void afterLogin() {
        setDrawer(false);
    }

    public static void afterLogout() {}


    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }
}//MainActivity
