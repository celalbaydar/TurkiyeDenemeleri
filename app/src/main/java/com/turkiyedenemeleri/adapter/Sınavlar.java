package com.turkiyedenemeleri.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.turkiyedenemeleri.R;
import com.turkiyedenemeleri.app.MyApp;
import com.turkiyedenemeleri.customviews.star.LikeButtonView;
import com.turkiyedenemeleri.model.Sınav;
import com.turkiyedenemeleri.presenter.MainPresenter;

import java.util.ArrayList;


public class Sınavlar extends RecyclerView.Adapter<GenericViewHolder> {
    MainPresenter mPresenter;
    private ArrayList<Sınav> sınavlar;
    private final int SINAV_BASVURU = 0;
    private final int SINAVA_BASLA = 1;
    private String[] aylar = {"OCAK", "ŞUBAT", "MART", "NİSAN", "MAYIS", "HAZİRAN", "TEMMUZ", "AĞUSTOS", "EYLÜL", "EKİM", "KASIM", "ARALIK"};

    public Sınavlar(ArrayList<Sınav> sınavlar, MainPresenter mPresenter) {
        this.sınavlar = sınavlar;
        this.mPresenter = mPresenter;
    }


    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == SINAV_BASVURU) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_remaining_date, parent, false);
            return new SınavBasvuru(itemView);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_sinava_basla, parent, false);
            return new SınavBasla(itemView);

        }
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        holder.setDataOnView(position);
    }


    @Override
    public int getItemCount() {
        for (int i = 0; i < sınavlar.size(); i++) {
            if (sınavlar.get(i).getId() == null && Integer.parseInt(sınavlar.get(i).getKalansure().split(":")[0]) < 0) {
                sınavlar.remove(i);
                i--;
            }
        }
        return sınavlar.size();
    }

    @Override
    public int getItemViewType(int position) {
        String[] sınavBastarih = sınavlar.get(position).getKalansure().split(":");
        int baslamaSaat = Integer.parseInt(sınavBastarih[0]);
        Log.d("deneme",baslamaSaat+"");
        if (baslamaSaat < 0)
            return SINAVA_BASLA;
        else
            return SINAV_BASVURU;
    }

    public class SınavBasvuru extends GenericViewHolder {
        TextView tv_gun, tv_ay, tv_sınav_adi, tv_bas_saat, tv_bit_saat, tv_kisi;
        LikeButtonView like;

        public SınavBasvuru(View view) {
            super(view);
            tv_gun = (TextView) view.findViewById(R.id.tv_gun);
            tv_ay = (TextView) view.findViewById(R.id.tv_ay);
            tv_sınav_adi = (TextView) view.findViewById(R.id.tv_sınav_adi);
            tv_bas_saat = (TextView) view.findViewById(R.id.tv_bas_saat);
            tv_bit_saat = (TextView) view.findViewById(R.id.tv_bit_saat);
            tv_kisi = (TextView) view.findViewById(R.id.tv_kisi);
            like = (LikeButtonView) view.findViewById(R.id.like);
        }

        @Override
        public void setDataOnView(int position) {

            String[] sınavBastarih = sınavlar.get(position).getBaslama().split("[-:\\s]");
            String[] sınavBittarih = sınavlar.get(position).getBitis().split("[-:\\s]");
            tv_gun.setText(sınavBastarih[2]);
            tv_bas_saat.setText(": " + sınavBastarih[3] + ":" + sınavBastarih[4]);
            tv_bit_saat.setText(": " + sınavBittarih[3] + ":" + sınavBittarih[4]);
            tv_sınav_adi.setText(sınavlar.get(position).getAciklama());
            tv_kisi.setText(sınavlar.get(position).getKayıtlıkullanıcı() + "kişi kaydoldu");
            tv_ay.setText(aylar[Integer.parseInt(sınavBastarih[1]) - 1]);
            if (sınavlar.get(position).getId() != null) {
                like.onClick(null);
                like.setClickable(false);
            }
            like.setOnTouchListener((v, event) -> {
                Log.d("denem","tıklandı2");
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (like.isClickable()) {
                        like.onClick(null);
                        Log.d("denem","tıklandı");
                        mPresenter.addSınav(MyApp.loggedUserId, sınavlar.get(getAdapterPosition()).getSınavid());
                    }
                }
                return false;
            });
        }
    }

    public class SınavBasla extends GenericViewHolder {
        TextView tv_kisi, tv_sınav_adi, saat;
        Button basla;

        public SınavBasla(View view) {
            super(view);
            tv_sınav_adi = (TextView) view.findViewById(R.id.tv_sınav_adi);
            saat = (TextView) view.findViewById(R.id.saat);
            tv_kisi = (TextView) view.findViewById(R.id.tv_kisi);
            basla = (Button) view.findViewById(R.id.like);
        }

        @Override
        public void setDataOnView(int position) {
            String[] bitTarih = sınavlar.get(position).getBitis().split("[-:\\s]");
            tv_kisi.setText(sınavlar.get(position).getKayıtlıkullanıcı() + "kişi kaydoldu");
            saat.setText(bitTarih[3] + ":" + bitTarih[4]);
            tv_sınav_adi.setText(sınavlar.get(position).getAciklama());

        }
    }
}
