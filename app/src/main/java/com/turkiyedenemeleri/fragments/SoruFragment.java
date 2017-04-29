package com.turkiyedenemeleri.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
        // Required empty public constructor

    }



    public static SoruFragment newInstance(int number) {
        Bundle bundle = new Bundle();
        bundle.putInt("no",number);
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
        new PicassoUtil(getContext()).loadImageWithCache("soru/1/matematik/"+number,imageView);
    }

    @Override
    protected void initEventAndData() {



    }


}
