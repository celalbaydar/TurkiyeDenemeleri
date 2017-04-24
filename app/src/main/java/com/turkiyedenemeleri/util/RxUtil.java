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

    /**
     * 统一线程处理
     *
     * @return
     */


    public static  <T> ObservableTransformer<T, T> rxSchedulerHelper() {
       return new ObservableTransformer<T,T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable <T> upstream) {
                return upstream.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
    /*
    static <T> Observable <T> sampleObservable() {
        return Observable.defer(new Callable<ObservableSource<T>>() {
            @Override public ObservableSource<T> call() throws Exception {
                return Observable.just(T);
            }
        });
    public static <T> Observable.Transformer<MyHttpResponse<T>, T> handleMyResult() {   //compose判断结果
        return new Observable.Transformer<MyHttpResponse<T>, T>() {
            @Override
            public Observable<T> call(Observable<MyHttpResponse<T>> httpResponseObservable) {
                return httpResponseObservable.flatMap(new Func1<MyHttpResponse<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(MyHttpResponse<T> tMyHttpResponse) {
                        if(tMyHttpResponse.getCode() == 200) {
                            return createData(tMyHttpResponse.getData());
                        } else {
                            return Observable.error(new ApiException("服务器返回error"));
                        }
                    }
                });
            }
        };
    }
*/


}
