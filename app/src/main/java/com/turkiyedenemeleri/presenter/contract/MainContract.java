package com.turkiyedenemeleri.presenter.contract;

import com.turkiyedenemeleri.base.BasePresenter;
import com.turkiyedenemeleri.base.BaseView;
import com.turkiyedenemeleri.model.MyHttpResponseNoBody;
import com.turkiyedenemeleri.model.Sınav;

import java.util.ArrayList;

/**
 * Created by celal on 27/04/2017.
 */

public interface MainContract {
    interface View extends BaseView {
        void sınavCompleted(ArrayList<Sınav> sınavlar);
        void sınavKayıt(MyHttpResponseNoBody response);

    }

    interface Presenter extends BasePresenter<View> {
        void getSınav(String token);
        void addSınav(String token,String sınavid);

    }
}
