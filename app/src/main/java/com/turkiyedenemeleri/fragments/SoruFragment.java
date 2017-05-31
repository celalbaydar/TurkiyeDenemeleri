package com.turkiyedenemeleri.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.turkiyedenemeleri.R;
import com.turkiyedenemeleri.SinavActivity;
import com.turkiyedenemeleri.app.Constants;
import com.turkiyedenemeleri.app.MyApp;
import com.turkiyedenemeleri.base.BaseEvent;
import com.turkiyedenemeleri.base.BaseFragment;
import com.turkiyedenemeleri.model.SoruData;
import com.turkiyedenemeleri.presenter.MainPresenter;
import com.turkiyedenemeleri.util.PicassoUtil;

import java.util.ArrayList;

import static com.turkiyedenemeleri.R.id.C;


/**
 * A simple {@link Fragment} subclass.
 */
public class SoruFragment extends BaseFragment<MainPresenter> {

    private ImageView imageView;
    Button btn_A, btn_B, btn_C, btn_D, btn_E;
    View lastClicked;
    String bölüm;
    int number;
    String sınavid;

    public SoruFragment() {

    }

    public static SoruFragment newInstance(String sınavid, String bölüm, int number, SoruData cevap) {
        Bundle bundle = new Bundle();
        bundle.putInt("no", number);
        bundle.putString("sınavid", sınavid);
        bundle.putString("bölüm", bölüm);
        bundle.putString("cevap", cevap.getCevap());
        SoruFragment fm = new SoruFragment();
        fm.setArguments(bundle);
        return fm;
    }


    @Override
    public void showError(int errorCode, String msg) {

    }

    @Override
    protected void initInject() {
        mPresenter = new MainPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_soru;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        MyApp.getRxBus().send(new BaseEvent(Constants.showkarala, null));


        super.onViewCreated(view, savedInstanceState);
        number = getArguments().getInt("no") + 1;
        imageView = (ImageView) view.findViewById(R.id.soru);

        bölüm = getArguments().getString("bölüm");
        sınavid = getArguments().getString("sınavid");
        String cevap = getArguments().getString("cevap");

        String url = "soru/" + sınavid + "/" + bölüm + "/" + number;
        new PicassoUtil(getContext()).loadImageWithCache(url, imageView);

        btn_A = (Button) view.findViewById(R.id.A);
        btn_B = (Button) view.findViewById(R.id.B);
        btn_C = (Button) view.findViewById(R.id.C);
        btn_D = (Button) view.findViewById(R.id.D);
        btn_E = (Button) view.findViewById(R.id.E);

        btn_A.setOnClickListener(new ClickListener());
        btn_B.setOnClickListener(new ClickListener());
        btn_C.setOnClickListener(new ClickListener());
        btn_D.setOnClickListener(new ClickListener());
        btn_E.setOnClickListener(new ClickListener());


        if (cevap.equals("A"))
            changeBackground(btn_A, "A");
        else if (cevap.equals("B"))
            changeBackground(btn_B, "B");
        else if (cevap.equals("C"))
            changeBackground(btn_C, "C");
        else if (cevap.equals("D"))
            changeBackground(btn_D, "D");
        else if (cevap.equals("E"))
            changeBackground(btn_E, "E");

    }

    @Override
    protected void initEventAndData() {


    }

    private class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            String cevap = "";
            switch (view.getId()) {
                case R.id.A:
                    cevap = "A";
                    break;
                case R.id.B:
                    cevap = "B";
                    break;
                case C:
                    cevap = "C";
                    break;
                case R.id.D:
                    cevap = "D";
                    break;
                case R.id.E:
                    cevap = "E";
                    break;
            }
            changeBackground(view, cevap);
        }
    }

    private void changeBackground(View view, String cevap) {
        ArrayList<SoruData> list = SinavActivity.cevaplar.get(sınavid + "-" + bölüm);
        list.get(number-1).setCevap(cevap);
        //list.set(number - 1, cevap);

        if (lastClicked != null) {
            if (lastClicked.getId() == R.id.A)
                lastClicked.setBackground(getResources().getDrawable(R.drawable.button_left_shape));
            else if (lastClicked.getId() == R.id.E)
                lastClicked.setBackground(getResources().getDrawable(R.drawable.button_right_shape));
            else
                lastClicked.setBackground(getResources().getDrawable(R.drawable.button_background));
        }

        view.setBackgroundColor(getResources().getColor(R.color.item_green));
        lastClicked = view;
    }
}
