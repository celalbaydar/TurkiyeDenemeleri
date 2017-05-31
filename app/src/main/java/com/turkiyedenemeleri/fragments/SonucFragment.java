package com.turkiyedenemeleri.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.turkiyedenemeleri.R;
import com.turkiyedenemeleri.SinavActivity;
import com.turkiyedenemeleri.adapter.SampleFragmentPagerAdapter;
import com.turkiyedenemeleri.adapter.SoruNumaraAdapter;
import com.turkiyedenemeleri.app.Constants;
import com.turkiyedenemeleri.app.MyApp;
import com.turkiyedenemeleri.base.BaseEvent;
import com.turkiyedenemeleri.base.BaseFragment;
import com.turkiyedenemeleri.customviews.TDTextView;
import com.turkiyedenemeleri.presenter.MainPresenter;

import rx.functions.Action1;


public class SonucFragment extends BaseFragment<MainPresenter> {

    RecyclerView rcView;
    private ViewPager viewPager;
    private TDTextView tdTextView;
    private SoruNumaraAdapter snAdapter;

    public SonucFragment() {
        // Required empty public constructor
    }


    public static SonucFragment newInstance(String sınavid, String bölüm, int soruSayısı) {
        Bundle bundle = new Bundle();
        bundle.putInt("sorusayısı", soruSayısı);
        bundle.putString("sınavid", sınavid);
        bundle.putString("bölüm", bölüm);
        SonucFragment fm = new SonucFragment();
        fm.setArguments(bundle);
        return fm;
    }


    @Override
    public void showError(int errorCode, String msg) {

    }

    @Override
    protected void initInject() {
        mPresenter = new MainPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sonuc;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        rcView = (RecyclerView) view.findViewById(R.id.rcView);
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    protected void initEventAndData() {
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);

        rcView.setLayoutManager(llm);
        int sorusayısı = getArguments().getInt("sorusayısı");

        snAdapter = new SoruNumaraAdapter(sorusayısı);
        snAdapter.setLastClicked(0);
        rcView.setAdapter(snAdapter);

        String sınavid = getArguments().getString("sınavid");
        String bölüm = getArguments().getString("bölüm");
        SinavActivity.lastURL = "soru/" + sınavid + "/" + bölüm + "/" + (1);
        SinavActivity.lastBolum = bölüm;
        SinavActivity.lastSoru = 0;

        SampleFragmentPagerAdapter adapter = new SampleFragmentPagerAdapter(getFragmentManager(), getContext(), sorusayısı, sınavid, bölüm);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                rcView.scrollToPosition(position);
                snAdapter.setLastClicked(position);
                snAdapter.notifyDataSetChanged();
                String url = "soru/" + sınavid + "/" + bölüm + "/" + (position + 1);

                SinavActivity.lastURL = url;
                SinavActivity.lastBolum = bölüm;
                SinavActivity.lastSoru = position ;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        MyApp.getRxBus().toObserverable()
                .subscribe(new Action1<BaseEvent>() {
                    @Override
                    public void call(BaseEvent event) {
                        if (event.getEventCode() == Constants.clickEventCode) {
                            viewPager.setCurrentItem(((int) event.getData()), false);
                        }
                    }
                });

    }


}
