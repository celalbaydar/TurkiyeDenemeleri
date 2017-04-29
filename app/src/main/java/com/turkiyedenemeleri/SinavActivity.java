package com.turkiyedenemeleri;

import android.util.Log;

import com.turkiyedenemeleri.base.BaseActivity;
import com.turkiyedenemeleri.fragments.SınavBolumler;
import com.turkiyedenemeleri.presenter.SınavPresenter;
import com.turkiyedenemeleri.presenter.contract.SınavContact;
import com.turkiyedenemeleri.util.ActivityUtil;

public class SinavActivity extends BaseActivity<SınavPresenter> implements SınavContact.View  {

    @Override
    protected void setInitialValues() {
       String sınavid= getIntent().getExtras().getString("sınavid");
        Log.e("deneme",sınavid);
        SınavBolumler newsFragment = (SınavBolumler) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (newsFragment == null) {
            newsFragment = SınavBolumler.newInstance(sınavid);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), newsFragment, R.id.contentFrame,"sinav");
        }
    }

    @Override
    protected void initViews() {
        mPresenter = new SınavPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sinav;
    }

    @Override
    public void showError(int errorCode, String msg) {

    }
}
