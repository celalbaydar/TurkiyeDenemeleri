package com.turkiyedenemeleri;

import android.util.Log;

import com.turkiyedenemeleri.data.remote.ServiceGenerator;
import com.turkiyedenemeleri.model.MyHttpResponse;
import com.turkiyedenemeleri.model.User;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;

import retrofit2.Call;

/**
 * Created by celal on 23/04/2017.
 */

public class RetrofitHelper {

    public void addUser(String telefon,String token){
        UyelikService uyelıkService = ServiceGenerator.createService(UyelikService.class);
        Call<MyHttpResponse<User>> call = uyelıkService.addUser(telefon,token);
        call.enqueue(new Callback<MyHttpResponse<User>>() {
            @Override
            public void success(Result<MyHttpResponse<User>> result) {
                Log.d("deneme", String.valueOf(result.data.getResponseType()));
                Log.d("deneme", result.data.getData().getTelefon());
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });
    }
}
