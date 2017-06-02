package com.turkiyedenemeleri.presenter.contract;

import com.turkiyedenemeleri.base.BasePresenter;
import com.turkiyedenemeleri.base.BaseView;
import com.turkiyedenemeleri.model.CevapAnahtarı;
import com.turkiyedenemeleri.model.MyHttpResponse;
import com.turkiyedenemeleri.model.SınavBolum;

import java.util.ArrayList;

/**
 * Created by celal on 27/04/2017.
 */

public interface SınavBolumContract {
    interface View extends BaseView {
        void bolumlerGeldi(ArrayList<SınavBolum> bolumler);
        void cevapKayitSonuc(MyHttpResponse<CevapAnahtarı> cevap);

    }

    interface Presenter extends BasePresenter<View> {
        void bolumGetir(String sınavid);
        void sınavKayit(String sınavid,String token,String cevap);

    }
}
