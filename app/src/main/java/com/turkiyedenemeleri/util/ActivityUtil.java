package com.turkiyedenemeleri.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.turkiyedenemeleri.R;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by celal on 25/04/2017.
 */

public class ActivityUtil {
    public static void startActivity(Context context,Class clas){
        context.startActivity(new Intent(context,clas));
    }
    public static void startActivity(Context context, Class clas, Bundle bundle){
        Intent intent=new Intent(context,clas);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    public static void startActivity(Activity context, Class clas, int flags){
        Intent intent=new Intent(context,clas);
        intent.setFlags(flags);
        context.startActivity(intent);
        context.overridePendingTransition(0, 0);

    }
    public static void startActivity(Context context,Class clas,int flags, Bundle bundle){
        Intent intent=new Intent(context,clas);
        intent.setFlags(flags);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                               @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left);
        transaction.add(frameId, fragment);
        transaction.commit();
    }
    public static void addToBackStackFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId,String tag) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.exit_to_right,R.anim.enter_to_left);
        transaction.replace(frameId, fragment);
        transaction.addToBackStack(tag);
        transaction.commit();
    }
    public static void replaceToBackStackFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                                         @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.exit_to_right,R.anim.enter_to_left);
        transaction.replace(frameId, fragment);
        transaction.commit();
    }
}
