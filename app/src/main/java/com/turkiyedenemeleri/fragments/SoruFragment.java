package com.turkiyedenemeleri.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.turkiyedenemeleri.R;
import com.turkiyedenemeleri.base.BaseFragment;
import com.turkiyedenemeleri.presenter.MainPresenter;
import com.turkiyedenemeleri.util.PicassoUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class SoruFragment extends BaseFragment<MainPresenter> {

    private ImageView imageView;

    public SoruFragment() {

    }

    public static SoruFragment newInstance(String sınavid,String bölüm,int number) {
        Bundle bundle = new Bundle();
        bundle.putInt("no",number);
        bundle.putString("sınavid",sınavid);
        bundle.putString("bölüm",bölüm);
        SoruFragment fm = new SoruFragment();
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
        return R.layout.fragment_soru;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int number=getArguments().getInt("no")+1;
        imageView = (ImageView) view.findViewById(R.id.soru);

        String bölüm=getArguments().getString("bölüm");
        String sınavid=getArguments().getString("sınavid");

        Log.e("deneme","soru/"+sınavid+"/"+bölüm+"/"+number);
        new PicassoUtil(getContext()).loadImageWithCache("soru/"+sınavid+"/"+bölüm+"/"+number,imageView);
    }

    @Override
    protected void initEventAndData() {



    }


}
