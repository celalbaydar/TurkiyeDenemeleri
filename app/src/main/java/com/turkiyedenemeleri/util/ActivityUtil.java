package com.turkiyedenemeleri.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
}
