package com.turkiyedenemeleri.util;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.turkiyedenemeleri.R;

/**
 * Created by celal on 25/04/2017.
 */

public class PicassoUtil {
    private final String BASE_URL="http://45.55.219.42/TurkiyeDenemeleri/userphotos/";
    Context context;

    public PicassoUtil(Context context) {
        this.context = context;
    }

    public void loadImageWithCache(String imageUrl, ImageView imageView){
        Log.d("deneme",BASE_URL+imageUrl);
        Picasso.with(context)
                .load(BASE_URL+imageUrl)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .placeholder(R.drawable.avatar_contact)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }
                    @Override
                    public void onError() {
                        Picasso.with(context)
                                .load(BASE_URL+imageUrl)
                                .placeholder(R.drawable.avatar_contact)
                                .error(R.drawable.avatar_contact)
                                .into(imageView, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                    }
                                    @Override
                                    public void onError() {
                                        Log.v("Picasso","Could not fetch image");
                                    }
                                });
                    }
                });
    }
}
