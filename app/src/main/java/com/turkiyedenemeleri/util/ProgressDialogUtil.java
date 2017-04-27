package com.turkiyedenemeleri.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by celal on 27/04/2017.
 */

public class ProgressDialogUtil {
   Context context;
   public ProgressDialogUtil(Context context){
        this.context=context;
    }
    public ProgressDialog create(String message){
        ProgressDialog dialog = ProgressDialog.show(context, "",
                message, true);
        dialog.setCancelable(false);
        return dialog;
    }
}
