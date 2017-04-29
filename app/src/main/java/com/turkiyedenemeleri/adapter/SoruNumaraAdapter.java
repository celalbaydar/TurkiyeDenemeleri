package com.turkiyedenemeleri.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.turkiyedenemeleri.R;

/**
 * Created by celal on 29/04/2017.
 */

public class SoruNumaraAdapter extends RecyclerView.Adapter<GenericViewHolder> {
    int soruSayısı;

    public SoruNumaraAdapter(int soruSoyısı) {
        this.soruSayısı = soruSoyısı;
    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_soru_numarasi, parent, false);
        return new SoruNumarası(itemView);
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        holder.setDataOnView(position);

    }

    @Override
    public int getItemCount() {
        return soruSayısı;
    }


    public class SoruNumarası extends GenericViewHolder {
        TextView soruNumarası;

        public SoruNumarası(View view) {
            super(view);
            soruNumarası = (TextView) view.findViewById(R.id.text1);
            setIsRecyclable(false);
        }

        @Override
        public void setDataOnView(final int position) {
            int pos = getAdapterPosition();
            soruNumarası.setText((pos + 1) + "");
            if (position == 1)
                soruNumarası.setBackgroundColor(Color.parseColor("#ff5131"));

            Log.e("deneme",pos +"  "+position);
        }
    }
}
