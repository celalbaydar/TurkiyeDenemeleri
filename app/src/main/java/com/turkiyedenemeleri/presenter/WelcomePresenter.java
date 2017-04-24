package com.turkiyedenemeleri.presenter;

import com.turkiyedenemeleri.base.RxPresenter;
import com.turkiyedenemeleri.model.MyHttpResponse;
import com.turkiyedenemeleri.model.User;
import com.turkiyedenemeleri.model.http.RetrofitHelper;
import com.turkiyedenemeleri.presenter.contract.WelcomeContract;
import com.turkiyedenemeleri.util.RxUtil;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by celal on 23/04/2017.
 */

public class WelcomePresenter extends RxPresenter<WelcomeContract.View> implements WelcomeContract.Presenter {

    private RetrofitHelper mRetrofitHelper;

    public WelcomePresenter() {
        this.mRetrofitHelper = RetrofitHelper.getInstance();
    }

    @Override
    public void addUser(String phoneNumber, String token) {
        Disposable rxSubscription=mRetrofitHelper.addUser(phoneNumber, token)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new DisposableObserver<MyHttpResponse<User>>() {
                    @Override
                    public void onNext(@NonNull MyHttpResponse<User> userMyHttpResponse) {
                        mView.chekUserResponse(userMyHttpResponse);
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
