package com.turkiyedenemeleri.fragments;

import com.turkiyedenemeleri.R;
import com.turkiyedenemeleri.base.BaseFragment;
import com.turkiyedenemeleri.presenter.MainPresenter;


public class SonucFragment extends BaseFragment<MainPresenter> {


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
    protected void initEventAndData() {

    }
}
