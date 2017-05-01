package com.turkiyedenemeleri.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.turkiyedenemeleri.fragments.SoruFragment;

/**
 * Created by safakesberk on 29/04/2017.
 */

public class SampleFragmentPagerAdapter extends FragmentStatePagerAdapter {
    int PAGE_COUNT = 40;

    private Context context;
    private String sınavid;
    private String bölüm;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context,String sınavid, String bölüm) {
        super(fm);
        this.sınavid=sınavid;
        this.bölüm=bölüm;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return SoruFragment.newInstance(sınavid, bölüm, position);
    }

}