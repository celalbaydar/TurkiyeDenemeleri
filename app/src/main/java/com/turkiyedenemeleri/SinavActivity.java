package com.turkiyedenemeleri;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;

import com.turkiyedenemeleri.app.Constants;
import com.turkiyedenemeleri.app.MyApp;
import com.turkiyedenemeleri.base.BaseActivity;
import com.turkiyedenemeleri.chronometer.ForegroundService;
import com.turkiyedenemeleri.chronometer.PrefUtils;
import com.turkiyedenemeleri.customviews.CanvasView;
import com.turkiyedenemeleri.customviews.TDTextView;
import com.turkiyedenemeleri.fragments.SınavBolumler;
import com.turkiyedenemeleri.model.SoruData;
import com.turkiyedenemeleri.presenter.SınavPresenter;
import com.turkiyedenemeleri.presenter.contract.SınavContact;
import com.turkiyedenemeleri.util.ActivityUtil;
import com.turkiyedenemeleri.util.DialogUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindView;

public class SinavActivity extends BaseActivity<SınavPresenter> implements SınavContact.View {
    public static HashMap<String, ArrayList<SoruData>> cevaplar = new HashMap<>();
    public static String lastURL = "";
    public static String lastBolum = "";
    public static int lastSoru = 0;

    private static final long TIMER_LENGHT = 5800; // Ten seconds
    private long mTimeToGo;
    private CountDownTimer mCountDownTimer;
    private TimerState mState;
    private PrefUtils mPreferences;
    String sınavid;
    CanvasView canvas;
    SınavBolumler newsFragment;
    @BindView(R.id.kalan)
    TDTextView kalan;
    Dialog karalama;
    TDTextView karala;

    private enum TimerState {
        STOPPED,
        RUNNING
    }

    @Override
    protected void setInitialValues() {
        karala = (TDTextView) findViewById(R.id.karala);
        sınavid = getIntent().getExtras().getString("sınavid");
        newsFragment = (SınavBolumler) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (newsFragment == null) {
            newsFragment = SınavBolumler.newInstance(sınavid);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), newsFragment, R.id.contentFrame);
        }

        MyApp.getRxBus().toObserverable()
                .subscribe(event -> {
                    if (event.getEventCode() == Constants.hidekarala) {
                        karala.setVisibility(View.INVISIBLE);
                    } else if (event.getEventCode() == Constants.showkarala) {
                        karala.setVisibility(View.VISIBLE);
                    }
                });


        mPreferences = new PrefUtils(this);
        if (getIntent().getExtras() != null) {
            String yeni = getIntent().getExtras().getString("yeni", null);
            if (yeni != null) {
                Intent startIntent = new Intent(this, ForegroundService.class);
                startIntent.putExtra("sınavid", sınavid);
                startIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
                startService(startIntent);
            }
        }
        if (mPreferences.getStartedTime(sınavid) == 0) {
            mPreferences.setStartedTime(sınavid, getNow());
            startTimer();
            mState = TimerState.RUNNING;
        } else {
            initTimer();
        }
    }

    @Override
    protected void setListeners() {
        DialogUtil du = new DialogUtil();
        karala.setOnClickListener(v -> {
            SoruData data = cevaplar.get(sınavid + "-" + lastBolum).get(lastSoru);
            if (data.getCanvas() == null) {
                canvas = new CanvasView(mContext);
                data.setCanvas(canvas);
            }
            canvas = data.getCanvas();
            karalama = du.addKaralaScreen(SinavActivity.this, lastURL, data.getCanvas());
            karalama.show();
            karalama.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (canvas.getParent() != null) {
                        ((ViewGroup) canvas.getParent()).removeView(canvas);
                    }
                }
            });
        });
    }

    @Override
    protected void initViews() {
        mPresenter = new SınavPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sinav;
    }

    @Override
    public void showError(int errorCode, String msg) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        initTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mState == TimerState.RUNNING) {
            mCountDownTimer.cancel();
            Intent startIntent = new Intent(this, ForegroundService.class);
            startIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
            startService(startIntent);
        }
    }

    private long getNow() {
        Calendar rightNow = Calendar.getInstance();
        return rightNow.getTimeInMillis() / 1000;
    }

    private void initTimer() {
        long startTime = mPreferences.getStartedTime(sınavid);
        if (startTime > 0) {
            mTimeToGo = (TIMER_LENGHT - (getNow() - startTime));
            if (mTimeToGo <= 0) { // TIMER EXPIRED
                //mTimeToGo = TIMER_LENGHT;
                mState = TimerState.STOPPED;
                onTimerFinish();
            } else  if (mState !=TimerState.RUNNING){
                startTimer();
                mState = TimerState.RUNNING;
            }
        } else {
            //mTimeToGo = TIMER_LENGHT;
            mState = TimerState.STOPPED;
        }
        updateTimeUi();
    }

    private void onTimerFinish() {
        //mPreferences.setStartedTime(sınavid, 0);
        updateTimeUi();
    }

    private void updateTimeUi() {
        if (mTimeToGo <= 0)
            kalan.setText("Süre Bitti");
        else
            kalan.setText(convertSecondToHour(mTimeToGo));
    }

    private String convertSecondToHour(long second) {
        short hours = (short) (second / 3600);
        short minutes = (short) ((second % 3600) / 60);
        short seconds = (short) (second % 60);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);

    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeToGo * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                mTimeToGo -= 1;
                updateTimeUi();
            }

            public void onFinish() {
                mState = TimerState.STOPPED;
                onTimerFinish();
                updateTimeUi();
            }
        }.start();
    }


    @Override
    public void onBackPressed() {
/*        if (karalama.isShowing()) {
            karalama.dismiss();
        } else*/
        super.onBackPressed();
    }


    public void btnUndo(View view) {
        canvas.undo();
    }

    public void btnDelete(View view) {
        canvas.setClickable(false);
        while (canvas.undo()) ;
        canvas.setClickable(true);

    }

    void back(View v) {
        karalama.cancel();
    }

    public void btnRed(View view) {
        canvas.setPaintStrokeColor(Color.RED);
    }

    public void btnOrange(View view) {
        canvas.setPaintStrokeColor(Color.parseColor("#FA0"));
    }

    public void btnGray(View view) {
        canvas.setPaintStrokeColor(Color.parseColor("#8b8b8b"));

    }

    public void btnBlack(View view) {

        canvas.setPaintStrokeColor(Color.BLACK);
    }

    public void btnGreen(View view) {
        canvas.setPaintStrokeColor(Color.GREEN);
    }

    public void btnBlue(View view) {
        canvas.setPaintStrokeColor(Color.BLUE);
    }

    public void btnThin(View view) {
        canvas.setPaintStrokeWidth(5.0f);
    }

    public void btnMedium(View view) {
        canvas.setPaintStrokeWidth(15.0f);
    }


}
