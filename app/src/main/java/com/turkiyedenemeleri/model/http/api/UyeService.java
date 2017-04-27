package com.turkiyedenemeleri.model.http.api;

import com.turkiyedenemeleri.model.MyHttpResponse;
import com.turkiyedenemeleri.model.User;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by celal on 23/04/2017.
 */

public interface UyeService {

    @FormUrlEncoded
    @POST("addUser.php")
    Observable<MyHttpResponse<User>> addUser(@Field("telefon") String telefon, @Field("token") String token);

    @FormUrlEncoded
    @POST("imageUpload.php")
    Observable<MyHttpResponse<User>> updateProfile(@Field("il") String il,
                                                   @Field("token") String token,
                                                   @Field("cinsiyet") String cinsiyet,
                                                   @Field("kullaniciadi") String KullaniciAdi);

    @Multipart
    @POST("imageUpload.php")
    Observable<MyHttpResponse<User>> updateProfile(@PartMap Map<String, String> userInfos,
                                                   @Part MultipartBody.Part file
    );

}
