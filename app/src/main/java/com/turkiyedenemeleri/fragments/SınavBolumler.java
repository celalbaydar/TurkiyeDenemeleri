package com.turkiyedenemeleri.fragments;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.turkiyedenemeleri.R;
import com.turkiyedenemeleri.base.BaseFragment;
import com.turkiyedenemeleri.model.SınavBolum;
import com.turkiyedenemeleri.presenter.SınavBolumPresenter;
import com.turkiyedenemeleri.presenter.contract.SınavBolumContract;
import com.turkiyedenemeleri.util.ActivityUtil;

import java.util.ArrayList;


public class SınavBolumler extends BaseFragment<SınavBolumPresenter> implements SınavBolumContract.View {
    int[] cvID = {R.id.cv1, R.id.cv2, R.id.cv3, R.id.cv4, R.id.cv5, R.id.cv6, R.id.cv7, R.id.cv8, R.id.cv9};
    int[] itemName = {R.id.item1, R.id.item2, R.id.item3, R.id.item4, R.id.item5, R.id.item6, R.id.item7, R.id.item8, R.id.item9};

    String sınavId;

    public SınavBolumler() {

    }

    public static SınavBolumler newInstance(String sınavid) {
        SınavBolumler fragment = new SınavBolumler();
        Bundle bundle = new Bundle();
        bundle.putString("sınavid", sınavid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initInject() {
        mPresenter = new SınavBolumPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sorulari_indir;
    }

    @Override
    protected void initEventAndData() {
        sınavId=getArguments().getString("sınavid");
        Log.e("deneme id",getArguments().getString("sınavid"));
        mPresenter.bolumGetir(sınavId);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void showError(int errorCode, String msg) {

    }

    @Override
    public void bolumlerGeldi(final ArrayList<SınavBolum> bolumler) {
        for (int i = 0; i < bolumler.size(); i++) {
            CardView cv = (CardView) mActivity.findViewById(cvID[i]);
            cv.setVisibility(View.VISIBLE);
            TextView text = (TextView) mActivity.findViewById(itemName[i]);
            text.setText(bolumler.get(i).getBölüm());
            final int finalI = i;
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityUtil.addToBackStackFragmentToActivity(getFragmentManager(),SonucFragment.newInstance(sınavId,bolumler.get(finalI).getResimbase()),R.id.contentFrame,bolumler.get(0).getResimbase());
                    Log.e("deneme", bolumler.get(finalI).getResimbase());

                }
            });
        }

    }
}
