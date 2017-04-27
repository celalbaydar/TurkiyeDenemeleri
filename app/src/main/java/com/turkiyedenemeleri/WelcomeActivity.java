package com.turkiyedenemeleri;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.method.LinkMovementMethod;
import android.util.Log;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.google.gson.Gson;
import com.turkiyedenemeleri.app.MyApp;
import com.turkiyedenemeleri.base.BaseActivity;
import com.turkiyedenemeleri.customviews.TDTextView;
import com.turkiyedenemeleri.model.User;
import com.turkiyedenemeleri.presenter.WelcomePresenter;
import com.turkiyedenemeleri.presenter.contract.WelcomeContract;
import com.turkiyedenemeleri.util.ActivityUtil;
import com.turkiyedenemeleri.util.DialogUtil;
import com.turkiyedenemeleri.util.SharedPreferenceUtil;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import butterknife.BindView;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class WelcomeActivity extends BaseActivity<WelcomePresenter> implements WelcomeContract.View {
    @BindView(R.id.TDtv_link)
    TDTextView link;

    @BindView(R.id.auth_button)
    DigitsAuthButton digitsButton;
    private static final String TWITTER_KEY = "nIhyru1O7xMYNo41wwja2Jxwo";
    private static final String TWITTER_SECRET = "1paz0cu4JKx4f7BObdJ0b8Wrj7gmz4CTziuXlOxpcafPKd1znG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Digits.Builder digitsBuilder = new Digits.Builder();
        Fabric.with(this, new TwitterCore(authConfig), digitsBuilder.build());
        if (!Digits.isDigitsUser() || SharedPreferenceUtil.getLoggedUser() == null) {
            Digits.logout();
            //startActivity(new Intent(this, WelcomeActivity.class));
        } else if (!SharedPreferenceUtil.isUserFillProfil()) {
            Gson gson = new Gson();
            MyApp.loggedUser = gson.fromJson(SharedPreferenceUtil.getLoggedUser(), User.class);
            Log.e("TAG",SharedPreferenceUtil.getLoggedUser());
            int flags = Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK;
            ActivityUtil.startActivity(this, ProfilActivity.class, flags);
        } else {
            int flags = Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK;
            ActivityUtil.startActivity(this, MainActivity.class, flags);
        }
        //Digits.logout();

    }

    @Override
    protected void setInitialValues() {
        link.setMovementMethod(LinkMovementMethod.getInstance());

        digitsButton.setBackgroundResource(R.drawable.btn_accept);
        digitsButton.setText(R.string.accaptandcontinue);

        mPresenter = new WelcomePresenter();
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        digitsButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                //phone deleted +9
                //new RetrofitHelper().addUser(phoneNumber.substring(2), MyApp.loggedUserId);
                mPresenter.addUser(phoneNumber.substring(2), MyApp.loggedUserId);
            }

            @Override
            public void failure(DigitsException exception) {
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        WelcomeActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);

    }

    @OnClick(R.id.regisbutton)
    public void regisButtonClick() {
        WelcomeActivityPermissionsDispatcher.showSmsWithCheck(WelcomeActivity.this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }


    @NeedsPermission(Manifest.permission.RECEIVE_SMS)
    void showSms() {
        digitsButton.performClick();
    }

    @OnShowRationale(Manifest.permission.RECEIVE_SMS)
    void showRationaleForSms(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.permission_sms_rationale)
                .setPositiveButton(R.string.button_allow, (dialog, button) -> request.proceed())
                .setNegativeButton(R.string.button_deny, (dialog, button) -> request.cancel())
                .show();
    }

    @OnPermissionDenied(Manifest.permission.RECEIVE_SMS)
    void receiveSmsDenied() {
        digitsButton.performClick();
    }

    @Override
    public void showError(int errorCode, String msg) {
        DialogUtil.addErrorDialog(this, "Oppppsss", msg).show();
    }

    @Override
    public void chekUserResponse(User user) {
        MyApp.loggedUser = user;

        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        SharedPreferenceUtil.setLoggedUser(userJson);

        int flags = Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK;
        ActivityUtil.startActivity(this, ProfilActivity.class, flags);
    }


}
