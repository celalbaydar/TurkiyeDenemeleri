package com.turkiyedenemeleri.util;

import android.content.Context;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by celal on 24/04/2017.
 */

public class DialogUtil {
    public static SweetAlertDialog addErrorDialog(Context context,String title,String content){
       return new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmText("Anladım!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                });
    }
}
