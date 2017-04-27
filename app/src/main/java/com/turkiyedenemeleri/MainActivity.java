package com.turkiyedenemeleri;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.turkiyedenemeleri.adapter.Sınavlar;
import com.turkiyedenemeleri.app.MyApp;
import com.turkiyedenemeleri.base.BaseActivity;
import com.turkiyedenemeleri.model.http.Sınav;
import com.turkiyedenemeleri.presenter.MainPresenter;
import com.turkiyedenemeleri.presenter.contract.MainContract;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity  extends BaseActivity<MainPresenter> implements MainContract.View  {
   @BindView(R.id.rcView)
    RecyclerView rcView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setInitialValues() {
        mPresenter= new MainPresenter();
        mPresenter.getSınav(MyApp.loggedUserId);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        rcView.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void initViews() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void showError(int errorCode,String msg) {

    }

    @Override
    public void sınavCompleted(ArrayList<Sınav> sınavlar) {
        Sınavlar adapter=new Sınavlar(sınavlar);
        rcView.setAdapter(adapter);
    }
}
