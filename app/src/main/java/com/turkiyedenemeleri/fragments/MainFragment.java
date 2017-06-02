package com.turkiyedenemeleri.fragments;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.turkiyedenemeleri.ButtonClickListener;
import com.turkiyedenemeleri.R;
import com.turkiyedenemeleri.SinavActivity;
import com.turkiyedenemeleri.adapter.Sınavlar;
import com.turkiyedenemeleri.app.MyApp;
import com.turkiyedenemeleri.base.BaseFragment;
import com.turkiyedenemeleri.model.MyHttpResponseNoBody;
import com.turkiyedenemeleri.model.Sınav;
import com.turkiyedenemeleri.presenter.MainPresenter;
import com.turkiyedenemeleri.presenter.contract.MainContract;
import com.turkiyedenemeleri.util.ActivityUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends BaseFragment<MainPresenter> implements MainContract.View,ButtonClickListener {

    @BindView(R.id.rcView)
    RecyclerView rcView;

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;



    public MainFragment() {
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected void initInject() {
        mPresenter = new MainPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }


    @Override
    protected void initEventAndData() {
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rcView.setLayoutManager(llm);
        mPresenter.getSınav(MyApp.loggedUserId);

        mSwipeRefreshLayout.setOnRefreshListener(() -> mPresenter.getSınav(MyApp.loggedUserId));
    }


    @Override
    public void showError(int errorCode, String msg) {

    }

    @Override
    public void sınavCompleted(ArrayList<Sınav> sınavlar) {
        if (mSwipeRefreshLayout.isRefreshing()) mSwipeRefreshLayout.setRefreshing(false);
        Sınavlar sınav = new Sınavlar(sınavlar,mPresenter,this);
        rcView.setAdapter(sınav);
    }

    @Override
    public void sınavKayıt(MyHttpResponseNoBody response) {
        if (response.getResponseType() == 200)
            Toast.makeText(mActivity, "Başarılı", Toast.LENGTH_SHORT).show();
        else Toast.makeText(mActivity, "Sıkıntılı", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickData(String data) {
        Bundle bundle=new Bundle();
        bundle.putString("sınavid",data);
        ActivityUtil.startActivity(mActivity, SinavActivity.class,bundle);
    }



}
