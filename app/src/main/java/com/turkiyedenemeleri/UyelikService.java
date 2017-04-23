package com.turkiyedenemeleri;

import com.turkiyedenemeleri.model.MyHttpResponse;
import com.turkiyedenemeleri.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by celal on 23/04/2017.
 */

public interface UyelikService {
    @FormUrlEncoded
    @POST("addUser.php")
    Call<MyHttpResponse<User>> addUser(@Field("telefon") String telefon, @Field("token") String token);
}
