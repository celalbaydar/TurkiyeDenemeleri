package com.turkiyedenemeleri.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.turkiyedenemeleri.R;
import com.turkiyedenemeleri.adapter.SampleFragmentPagerAdapter;
import com.turkiyedenemeleri.base.BaseFragment;
import com.turkiyedenemeleri.customviews.TDTextView;
import com.turkiyedenemeleri.presenter.MainPresenter;


public class SonucFragment extends BaseFragment<MainPresenter> {

    private ViewPager viewPager;
    private TDTextView tdTextView;


    public SonucFragment() {
        // Required empty public constructor
    }



    public static SonucFragment newInstance() {
        return new SonucFragment();
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
        SampleFragmentPagerAdapter adapter = new SampleFragmentPagerAdapter(getFragmentManager(), getContext());
        viewPager.setAdapter(adapter);

        tdTextView = (TDTextView) view.findViewById(R.id.tvRandom);
        tdTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(5,false);
            }
        });
    }

    @Override
    protected void initEventAndData() {



    }


}
