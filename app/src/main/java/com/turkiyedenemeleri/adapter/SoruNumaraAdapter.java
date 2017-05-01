package com.turkiyedenemeleri.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.turkiyedenemeleri.R;
import com.turkiyedenemeleri.app.Constants;
import com.turkiyedenemeleri.app.MyApp;
import com.turkiyedenemeleri.base.BaseEvent;
import com.turkiyedenemeleri.util.RxBus;

/**
 * Created by celal on 29/04/2017.
 */

public class SoruNumaraAdapter extends RecyclerView.Adapter<GenericViewHolder> {
    int soruSayısı;
    RxBus adapterBus;

    public SoruNumaraAdapter(int soruSoyısı) {
        this.soruSayısı = soruSoyısı;

        adapterBus = MyApp.getRxBus();
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
            soruNumarası.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyApp.getRxBus().send(new BaseEvent(Constants.clickEventCode, position));
                }
            });
            if (position == 1)
                soruNumarası.setBackgroundColor(Color.parseColor("#ff5131"));

            Log.e("deneme", pos + "  " + position);
        }
    }
}
