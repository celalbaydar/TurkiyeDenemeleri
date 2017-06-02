package com.turkiyedenemeleri.fragments;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.turkiyedenemeleri.R;
import com.turkiyedenemeleri.SinavActivity;
import com.turkiyedenemeleri.app.Constants;
import com.turkiyedenemeleri.app.MyApp;
import com.turkiyedenemeleri.base.BaseEvent;
import com.turkiyedenemeleri.base.BaseFragment;
import com.turkiyedenemeleri.model.CevapAnahtarı;
import com.turkiyedenemeleri.model.MyHttpResponse;
import com.turkiyedenemeleri.model.SoruData;
import com.turkiyedenemeleri.model.SınavBolum;
import com.turkiyedenemeleri.presenter.SınavBolumPresenter;
import com.turkiyedenemeleri.presenter.contract.SınavBolumContract;
import com.turkiyedenemeleri.util.ActivityUtil;
import com.turkiyedenemeleri.util.DialogUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.turkiyedenemeleri.SinavActivity.cevaplar;


public class SınavBolumler extends BaseFragment<SınavBolumPresenter> implements SınavBolumContract.View {
    int[] cvID = {R.id.cv1, R.id.cv2, R.id.cv3, R.id.cv4, R.id.cv5, R.id.cv6, R.id.cv7, R.id.cv8, R.id.cv9};
    int[] itemName = {R.id.item1, R.id.item2, R.id.item3, R.id.item4, R.id.item5, R.id.item6, R.id.item7, R.id.item8, R.id.item9};
    String sınavId;
    ArrayList<SınavBolum> bolumler;
    String bolumlerJson;

    public SınavBolumler() {

    }

    public static SınavBolumler newInstance(String sınavid) {
        SınavBolumler fragment = new SınavBolumler();
        Bundle bundle = new Bundle();
        bundle.putString("sınavid", sınavid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initInject() {
        mPresenter = new SınavBolumPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sorulari_indir;
    }


    @Override
    protected void initEventAndData() {
        Gson gson = new Gson();
        sınavId = getArguments().getString("sınavid");
        if (bolumlerJson == null)
            mPresenter.bolumGetir(sınavId);
        else {
            Type listType = new TypeToken<List<String>>() {
            }.getType();
            bolumler = gson.fromJson(bolumlerJson, listType);
            addBolumler();
        }
        MyApp.getRxBus().send(new BaseEvent(Constants.hidekarala, null));

        CardView cv = (CardView) mActivity.findViewById(R.id.bitir);
        cv.setOnClickListener(v -> {
            HashMap<String, ArrayList<String>> gsonHashMap = new HashMap<>();
            for (int i = 0; i < bolumler.size(); i++) {
                ArrayList<SoruData> temp = SinavActivity.cevaplar.get(sınavId + "-" + bolumler.get(i).getResimbase());
                ArrayList<String> gsonArrayList = new ArrayList<>();
                for (SoruData s : temp) {
                    gsonArrayList.add(s.getCevap());
                }
                gsonHashMap.put(bolumler.get(i).getResimbase(), gsonArrayList);
            }
            mPresenter.sınavKayit(sınavId, MyApp.loggedUserId, gson.toJson(gsonHashMap));
            Log.d("deneme", gson.toJson(gsonHashMap));
        });
    }


    @Override
    public void showError(int errorCode, String msg) {

    }

    @Override
    public void bolumlerGeldi(final ArrayList<SınavBolum> bolumler) {
        this.bolumler = bolumler;
        addBolumler();
        addBosCevap();
    }

    @Override
    public void cevapKayitSonuc(MyHttpResponse<CevapAnahtarı> cevap) {
        if (cevap.getResponseType() != 200) {
            DialogUtil.addErrorDialog(mActivity, "Hata", cevap.getResponseMessage()).show();
        } else {
            double matNet = netCalculate(Integer.valueOf(cevap.getData().getMatematikD()), Integer.valueOf(cevap.getData().getMatematikY()));
            double sosNet = netCalculate(Integer.valueOf(cevap.getData().getSosyalD()), Integer.valueOf(cevap.getData().getSosyalY()));
            double turkNet = netCalculate(Integer.valueOf(cevap.getData().getTurkceD()), Integer.valueOf(cevap.getData().getTurkceY()));
            double fenNet = netCalculate(Integer.valueOf(cevap.getData().getFenD()), Integer.valueOf(cevap.getData().getFenY()));

            SinavSonucuFragment sonucFragment = SinavSonucuFragment.newInstance(turkNet,sosNet,matNet,fenNet);
            sonucFragment.show(getFragmentManager(),"TAG");
        }
    }

    private double netCalculate(int t, int f) {
        return t - (f * 0.25);
    }

    private void addBolumler() {
        for (int i = 0; i < bolumler.size(); i++) {
            CardView cv = (CardView) mActivity.findViewById(cvID[i]);
            cv.setVisibility(View.VISIBLE);
            TextView text = (TextView) mActivity.findViewById(itemName[i]);
            text.setText(bolumler.get(i).getBölüm());
            final int finalI = i;
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityUtil.addToBackStackFragmentToActivity(getFragmentManager(), SonucFragment.newInstance(sınavId, bolumler.get(finalI).getResimbase(), bolumler.get(finalI).getSorusayısı()), R.id.contentFrame, bolumler.get(0).getResimbase());

                }
            });
        }
    }

    private void addBosCevap() {
        for (int i = 0; i < bolumler.size(); i++) {

            if (cevaplar.get(sınavId + "-" + bolumler.get(i).getResimbase()) != null)
                continue;

            ArrayList<SoruData> sınavSoruları = new ArrayList<>();
            for (int j = 0; j < bolumler.get(i).getSorusayısı(); j++) {
                sınavSoruları.add(new SoruData());
            }
            cevaplar.put(sınavId + "-" + bolumler.get(i).getResimbase(), sınavSoruları);
        }
    }
}
