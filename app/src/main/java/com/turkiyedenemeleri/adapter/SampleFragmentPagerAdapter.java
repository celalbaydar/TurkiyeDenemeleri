package com.turkiyedenemeleri.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.turkiyedenemeleri.SinavActivity;
import com.turkiyedenemeleri.fragments.SoruFragment;
import com.turkiyedenemeleri.model.SoruData;

import java.util.ArrayList;

/**
 * Created by safakesberk on 29/04/2017.
 */

public class SampleFragmentPagerAdapter extends FragmentStatePagerAdapter {
    int PAGE_COUNT = 40;

    private Context context;
    private String sınavid;
    private String bölüm;
    ArrayList<SoruData> userCevap;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context, int soruSayısı, String sınavid, String bölüm) {
        super(fm);
        this.PAGE_COUNT = soruSayısı;
        this.sınavid = sınavid;
        this.bölüm = bölüm;
        userCevap= SinavActivity.cevaplar.get(sınavid+"-"+bölüm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return SoruFragment.newInstance(sınavid, bölüm, position,userCevap.get(position));
    }


}