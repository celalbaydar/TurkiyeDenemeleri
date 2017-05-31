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

/**
 * Created by celal on 29/04/2017.
 */

public class SoruNumaraAdapter extends RecyclerView.Adapter<GenericViewHolder> {
    int soruSayısı;
    int lastClicked = -1;
    TextView lasClickedTextView;


    public void setLastClicked(int lastClicked) {
        this.lastClicked = lastClicked;
        if (lasClickedTextView != null)
            lasClickedTextView.setBackgroundColor(Color.parseColor("#d50000"));
        lasClickedTextView = null;
    }

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
        }

        @Override
        public void setDataOnView(final int position) {
            setIsRecyclable(false);
            int pos = getAdapterPosition();
            soruNumarası.setText((pos + 1) + "");
            if (lastClicked == pos) {
                soruNumarası.setBackgroundColor(Color.parseColor("#ff5131"));
            }
            soruNumarası.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lasClickedTextView = soruNumarası;
                    lastClicked = pos;

                    soruNumarası.setBackgroundColor(Color.parseColor("#ff5131"));
                    MyApp.getRxBus().send(new BaseEvent(Constants.clickEventCode, position));
                }
            });


            Log.e("deneme", pos + "  " + position);
        }
    }
}
