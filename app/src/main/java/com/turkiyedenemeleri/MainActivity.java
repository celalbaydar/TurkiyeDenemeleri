package com.turkiyedenemeleri;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.turkiyedenemeleri.base.BaseActivity;
import com.turkiyedenemeleri.fragments.MainFragment;
import com.turkiyedenemeleri.fragments.SonucFragment;
import com.turkiyedenemeleri.presenter.MainPresenter;
import com.turkiyedenemeleri.util.ActivityUtil;

import butterknife.BindView;


public class MainActivity extends BaseActivity<MainPresenter> implements
        NavigationView.OnNavigationItemSelectedListener  {

    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    MainFragment mainFragment;
    SonucFragment sonucFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setInitialValues() {

        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);


        MainFragment newsFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (newsFragment == null) {
            newsFragment = MainFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), newsFragment, R.id.contentFrame);
            navigationView.getMenu().getItem(0).setChecked(true);
        }
    }

    @Override
    protected void initViews() {
        //   Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Sinavlarim");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void showError(int errorCode, String msg) {

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        if (id == R.id.nav_exams) {
            mainFragment = MainFragment.newInstance();
            ActivityUtil.addToBackStackFragmentToActivity(getSupportFragmentManager(), mainFragment, R.id.contentFrame, "main");
            navigationView.getMenu().getItem(0).setChecked(true);
        } else if (id == R.id.nav_results) {

        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
