package com.turkiyedenemeleri.model.http.api;

import com.turkiyedenemeleri.model.MyHttpResponse;
import com.turkiyedenemeleri.model.User;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by celal on 23/04/2017.
 */

public interface UyeService {

    @FormUrlEncoded
    @POST("addUser.php")
    Observable<MyHttpResponse<User>> addUser(@Field("telefon") String telefon, @Field("token") String token);
}
