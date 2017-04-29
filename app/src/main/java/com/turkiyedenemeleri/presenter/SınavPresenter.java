package com.turkiyedenemeleri.presenter;

import com.turkiyedenemeleri.base.RxPresenter;
import com.turkiyedenemeleri.model.http.RetrofitHelper;
import com.turkiyedenemeleri.presenter.contract.SınavContact;

/**
 * Created by celal on 27/04/2017.
 */

public class SınavPresenter extends RxPresenter<SınavContact.View> implements SınavContact.Presenter {
    private RetrofitHelper mRetrofitHelper;

    public SınavPresenter() {
        this.mRetrofitHelper = RetrofitHelper.getInstance();
    }


}
