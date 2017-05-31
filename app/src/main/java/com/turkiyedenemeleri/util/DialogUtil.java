package com.turkiyedenemeleri.util;

import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.turkiyedenemeleri.R;
import com.turkiyedenemeleri.customviews.CanvasView;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by celal on 24/04/2017.
 */

public class DialogUtil {
    public static CanvasView lastAdded = null;


    public static SweetAlertDialog addErrorDialog(Context context, String title, String content) {
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

    public Dialog addKaralaScreen(Context context, String url, CanvasView canvas) {
        Dialog karalama;
        RelativeLayout relativeLayout;
        ImageView i;
        karalama = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        karalama.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        karalama.setContentView(R.layout.tahta);
        karalama.setTitle("Karalama Tahtası");
        relativeLayout = (RelativeLayout) karalama.findViewById(R.id.rlShareContent);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        relativeLayout.addView(canvas, params);
        relativeLayout.bringToFront();
        i = (ImageView) karalama.findViewById(R.id.ivQuestion);

        new PicassoUtil(context).loadImageWithCache(url, i);
        return karalama;
    }
}
