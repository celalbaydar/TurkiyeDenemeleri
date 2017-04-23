package com.turkiyedenemeleri;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.turkiyedenemeleri.base.BaseActivity;

public class MainActivity extends BaseActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Deneme");
    }

    @Override
    protected void setInitialValues() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
}
