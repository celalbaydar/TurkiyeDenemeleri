package com.turkiyedenemeleri.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.turkiyedenemeleri.R;
import com.turkiyedenemeleri.model.http.Sınav;

import java.util.ArrayList;


public class Sınavlar extends RecyclerView.Adapter<Sınavlar.ViewHolder>{

    private ArrayList<Sınav> sınavlar;
    public Sınavlar(ArrayList<Sınav> sınavlar) {
        this.sınavlar=sınavlar;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_remaining_date, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_ad.setText(sınavlar.get(position).getAciklama());
        holder.tarih.setText(sınavlar.get(position).getBaslama());
        holder.gün.setText(sınavlar.get(position).getBitis());
        holder.kalangun.setText(sınavlar.get(position).getKayıtlıkullanıcı());

    }

    @Override
    public int getItemCount() {
        return sınavlar.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_ad,tarih,gün,baslama,bitis,kalangun;
        public ViewHolder(View view) {
            super(view);
            tv_ad= (TextView) view.findViewById(R.id.tv_ad);
            tarih= (TextView) view.findViewById(R.id.tv_tarih);
            gün= (TextView) view.findViewById(R.id.tv_gun_icon);
            kalangun= (TextView) view.findViewById(R.id.tv_kalan_gun);

        }
    }
}
