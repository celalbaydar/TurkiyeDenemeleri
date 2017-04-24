package com.turkiyedenemeleri.presenter.contract;

import com.turkiyedenemeleri.base.BasePresenter;
import com.turkiyedenemeleri.base.BaseView;
import com.turkiyedenemeleri.model.MyHttpResponse;
import com.turkiyedenemeleri.model.User;

/**
 * Created by celal on 23/04/2017.
 */

public interface WelcomeContract {
    interface View extends BaseView {

        void chekUserResponse(MyHttpResponse<User> user);
    }

    interface Presenter extends BasePresenter<View> {

        void addUser(String phoneNumber,String token);
    }
}
