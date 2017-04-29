package com.turkiyedenemeleri.presenter;

import com.turkiyedenemeleri.base.RxPresenter;
import com.turkiyedenemeleri.model.http.RetrofitHelper;
import com.turkiyedenemeleri.presenter.contract.SınavBolumContract;
import com.turkiyedenemeleri.util.RxUtil;

import io.reactivex.disposables.Disposable;

/**
 * Created by celal on 27/04/2017.
 */

public class SınavBolumPresenter extends RxPresenter<SınavBolumContract.View> implements SınavBolumContract.Presenter {
    private RetrofitHelper mRetrofitHelper;

    public SınavBolumPresenter() {
        this.mRetrofitHelper = RetrofitHelper.getInstance();
    }

    @Override
    public void bolumGetir(String sınavid) {
        Disposable rxSubscription = mRetrofitHelper.getSınavBölüm(sınavid)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe(sınavBolum -> mView.bolumlerGeldi(sınavBolum));
        addSubscribe(rxSubscription);
    }
}
