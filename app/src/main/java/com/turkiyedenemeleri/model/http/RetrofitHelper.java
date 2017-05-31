package com.turkiyedenemeleri.model.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.turkiyedenemeleri.app.Constants;
import com.turkiyedenemeleri.model.CevapAnahtarı;
import com.turkiyedenemeleri.model.MyHttpResponse;
import com.turkiyedenemeleri.model.MyHttpResponseNoBody;
import com.turkiyedenemeleri.model.Sınav;
import com.turkiyedenemeleri.model.SınavBolum;
import com.turkiyedenemeleri.model.User;
import com.turkiyedenemeleri.model.http.api.SınavService;
import com.turkiyedenemeleri.model.http.api.UyeService;
import com.turkiyedenemeleri.util.SystemUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by celal on 23/04/2017.
 */

public class RetrofitHelper {
    private static final String BASE_URL = "http://45.55.219.42/TurkiyeDenemeleri/";
    private static RetrofitHelper retrofitHelper;


    private static OkHttpClient okHttpClient = null;
    private static UyeService uyeService = null;
    private static SınavService sınavService = null;

    private void init() {
        initOkHttp();
        uyeService = getApiService(UyeService.class);
        sınavService = getApiService(SınavService.class);

    }

    public static RetrofitHelper getInstance() {
        if (retrofitHelper == null) { //
            synchronized (RetrofitHelper.class) {
                if (retrofitHelper == null) {
                    retrofitHelper = new RetrofitHelper();
                }
            }
        }
        return retrofitHelper;
    }


    private RetrofitHelper() {
        init();
    }


    private <T> T getApiService(Class<T> clz) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(clz);
    }

    private static void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // http://www.jianshu.com/p/93153b34310e
        File cacheFile = new File(Constants.PATH_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!SystemUtil.isNetworkConnected()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (SystemUtil.isNetworkConnected()) {
                    int maxAge = 0;
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };

        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        okHttpClient = builder.build();
    }

    public Observable<MyHttpResponse<User>> addUser(String phoneNumber, String token) {
        return uyeService.addUser(phoneNumber, token);
    }

    public Observable<MyHttpResponse<User>> updateProfile(String il, String token, String cinsiyet, String kullaniciAdi) {
        return uyeService.updateProfile(il, token, cinsiyet, kullaniciAdi);
    }

    public Observable<MyHttpResponse<User>> updateProfile(Map<String, String> options, MultipartBody.Part file) {
        return uyeService.updateProfile(options,file);
    }

    public Observable<MyHttpResponseNoBody> addSınav(String token, String sınavid) {
        return sınavService.sınavKayıt(token, sınavid);
    }
    public  Observable<MyHttpResponse<ArrayList<Sınav>>> getSınav(String token) {
        return sınavService.getSınav(token);
    }
    public  Observable<ArrayList<SınavBolum>> getSınavBölüm(String token) {
        return sınavService.getSınavBolum(token);
    }
    public  Observable<MyHttpResponse<CevapAnahtarı>> sinavKayit(String sinavId, String token, String cevap) {
        return sınavService.sinavCevapKayit(sinavId,token,cevap);
    }


}
