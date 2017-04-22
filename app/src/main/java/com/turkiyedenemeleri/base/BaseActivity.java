package com.turkiyedenemeleri.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.turkiyedenemeleri.R;
import com.turkiyedenemeleri.base.listeners.ActionBarView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by safakesberk on 22/04/2017.
 */

public abstract class BaseActivity extends AppCompatActivity implements ActionBarView{
    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Unbinder unbinder;

    protected abstract void setInitialValues();
    protected abstract void initViews();
    public abstract int getLayoutId();


    protected void setListeners(){};
    protected void setFonts() {};
    protected void createCallBacks() {};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        initializeToolbar();
        initViews();
        setInitialValues();
        setListeners();
    }


    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }




    protected static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    protected int getActionBarSize() {
        Activity activity = this;
        if (activity == null) {
            return 0;
        }
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = activity.obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }

    protected int getScreenHeight() {
        Activity activity = this;
        if (activity == null) {
            return 0;
        }
        return activity.findViewById(android.R.id.content).getHeight();
    }


    protected void initializeToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("");
        }
    }

    @Override
    public void setUpIconVisibility(boolean visible) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(visible);
        }
    }

    @Override
    public void setTitle(String titleKey) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            TextView title = ButterKnife.findById(this, R.id.txt_toolbar_title);
            if (title != null) {
                title.setText(titleKey);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
