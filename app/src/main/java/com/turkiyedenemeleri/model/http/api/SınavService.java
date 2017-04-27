package com.turkiyedenemeleri.model.http.api;

import com.turkiyedenemeleri.model.MyHttpResponse;
import com.turkiyedenemeleri.model.http.Sınav;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by celal on 27/04/2017.
 */

public interface SınavService {
    @FormUrlEncoded
    @POST("getSınav.php")
    Observable<MyHttpResponse<ArrayList<Sınav>>> getSınav(@Field("token") String token);

}
