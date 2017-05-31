package com.turkiyedenemeleri.util;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by codeest on 2016/8/3.
 */
public class RxUtil {


    public static  <T> ObservableTransformer<T, T> rxSchedulerHelper() {
       return new ObservableTransformer<T,T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable <T> upstream) {
                return upstream.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


}
