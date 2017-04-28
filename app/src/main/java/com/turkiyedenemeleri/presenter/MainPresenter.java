package com.turkiyedenemeleri.presenter;

import com.turkiyedenemeleri.base.RxPresenter;
import com.turkiyedenemeleri.model.http.RetrofitHelper;
import com.turkiyedenemeleri.model.http.exceptions.ApiException;
import com.turkiyedenemeleri.presenter.contract.MainContract;
import com.turkiyedenemeleri.util.RxUtil;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by celal on 27/04/2017.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {
    private RetrofitHelper mRetrofitHelper;

    public MainPresenter() {
        this.mRetrofitHelper = RetrofitHelper.getInstance();
    }

    @Override
    public void getSınav(String token) {
        Disposable rxSubscription = mRetrofitHelper.getSınav(token)
                .compose(RxUtil.rxSchedulerHelper())
                .flatMap(userMyHttpResponse -> {
                    if (userMyHttpResponse.getResponseType() == 200)
                        return Observable.just(userMyHttpResponse.getData());
                    else {
                        mView.showError(userMyHttpResponse.getResponseType(), userMyHttpResponse.getResponseMessage());
                        return Observable.error(new ApiException(userMyHttpResponse.getResponseMessage()));
                    }
                })
                .subscribe(sınav -> {
                    mView.sınavCompleted(sınav);
                });
        addSubscribe(rxSubscription);
    }

    @Override
    public void addSınav(String token, String sınavid) {
        Disposable rxSubscription = mRetrofitHelper.addSınav(token,sınavid)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe(sınav -> {
                    mView.sınavKayıt(sınav);
                });
        addSubscribe(rxSubscription);
    }
}
