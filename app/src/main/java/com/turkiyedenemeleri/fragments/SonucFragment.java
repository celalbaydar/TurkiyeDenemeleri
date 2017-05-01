package com.turkiyedenemeleri.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.turkiyedenemeleri.R;
import com.turkiyedenemeleri.adapter.SampleFragmentPagerAdapter;
import com.turkiyedenemeleri.adapter.SoruNumaraAdapter;
import com.turkiyedenemeleri.base.BaseFragment;
import com.turkiyedenemeleri.customviews.TDTextView;
import com.turkiyedenemeleri.presenter.MainPresenter;

import butterknife.BindView;


public class SonucFragment extends BaseFragment<MainPresenter> {

    @BindView(R.id.rcView)
    RecyclerView rcView;
    private ViewPager viewPager;
    private TDTextView tdTextView;


    public SonucFragment() {
        // Required empty public constructor
    }



    public static SonucFragment newInstance(String sınavid,String bölüm) {
        Bundle bundle = new Bundle();
        bundle.putString("sınavid",sınavid);
        bundle.putString("bölüm",bölüm);
        SonucFragment fm = new SonucFragment();
        fm.setArguments(bundle);
        return fm;
    }


    @Override
    public void showError(int errorCode, String msg) {

    }

    @Override
    protected void initInject() {
        mPresenter=new MainPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sonuc;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        String sınavid=getArguments().getString("sınavid");
        String bölüm=getArguments().getString("bölüm");
        SampleFragmentPagerAdapter adapter = new SampleFragmentPagerAdapter(getFragmentManager(), getContext(),sınavid,bölüm);
        viewPager.setAdapter(adapter);

    }

    @Override
    protected void initEventAndData() {
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcView.setLayoutManager(llm);
        rcView.setAdapter(new SoruNumaraAdapter(40));
    }
}
