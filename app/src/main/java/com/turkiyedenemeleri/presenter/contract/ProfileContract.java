package com.turkiyedenemeleri.presenter.contract;

import com.turkiyedenemeleri.base.BasePresenter;
import com.turkiyedenemeleri.base.BaseView;

import java.util.Map;

import okhttp3.MultipartBody;

/**
 * Created by safakesberk on 26/04/2017.
 */

public interface ProfileContract {
    interface View extends BaseView {
        void profileUpdated();
    }

    interface Presenter extends BasePresenter<View> {
        void updateImage(String il, String token, String cinsiyet, String kullaniciAdi);

        void updateImage(Map<String, String> options, MultipartBody.Part file);

    }
}
