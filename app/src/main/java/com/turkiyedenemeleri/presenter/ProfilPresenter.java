package com.turkiyedenemeleri.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.turkiyedenemeleri.app.MyApp;
import com.turkiyedenemeleri.base.RxPresenter;
import com.turkiyedenemeleri.model.User;
import com.turkiyedenemeleri.model.http.RetrofitHelper;
import com.turkiyedenemeleri.model.http.exceptions.ApiException;
import com.turkiyedenemeleri.presenter.contract.ProfileContract;
import com.turkiyedenemeleri.util.RxUtil;
import com.turkiyedenemeleri.util.SharedPreferenceUtil;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.MultipartBody;

/**
 * Created by safakesberk on 26/04/2017.
 */

public class ProfilPresenter extends RxPresenter<ProfileContract.View> implements ProfileContract.Presenter {
    private RetrofitHelper mRetrofitHelper;

    public ProfilPresenter() {
        this.mRetrofitHelper = RetrofitHelper.getInstance();


    }

    @Override
    public void updateImage(String il, String token, String cinsiyet, String kullaniciAdi) {
        Disposable rxSubscription = mRetrofitHelper.updateProfile(il, token, cinsiyet, kullaniciAdi)
                .compose(RxUtil.rxSchedulerHelper())
                .flatMap(userMyHttpResponse -> {
                    if (userMyHttpResponse.getResponseType() == 200)
                        return Observable.just(userMyHttpResponse.getData());
                    else {
                        mView.showError(userMyHttpResponse.getResponseType(), userMyHttpResponse.getResponseMessage());
                        return Observable.error(new ApiException(userMyHttpResponse.getResponseMessage()));
                    }
                })
                .subscribe(user -> {
                    MyApp.loggedUser = user;
                    Gson gson = new Gson();
                    SharedPreferenceUtil.setLoggedUser(gson.toJson(user));
                    mView.profileUpdated();

                });
        addSubscribe(rxSubscription);
    }



    @Override
    public void updateImage(Map<String, String> options, MultipartBody.Part file) {
        Disposable rxSubscription = mRetrofitHelper.updateProfile(options,file)
                .compose(RxUtil.rxSchedulerHelper())
                .flatMap(userMyHttpResponse -> {
                    if (userMyHttpResponse.getResponseType() == 200)
                        return Observable.just(userMyHttpResponse.getData());
                    else {
                        mView.showError(userMyHttpResponse.getResponseType(), userMyHttpResponse.getResponseMessage());
                        return Observable.error(new ApiException(userMyHttpResponse.getResponseMessage()));
                    }
                })
                .subscribeWith(new DisposableObserver<User>() {
                    @Override
                    public void onNext(@NonNull User user) {
                        MyApp.loggedUser = user;
                        Gson gson = new Gson();
                        SharedPreferenceUtil.setLoggedUser(gson.toJson(user));
                        mView.profileUpdated();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error",e.getMessage().toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        addSubscribe(rxSubscription);
    }


}
