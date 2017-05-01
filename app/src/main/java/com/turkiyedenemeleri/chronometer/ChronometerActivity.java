package com.turkiyedenemeleri.chronometer;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.turkiyedenemeleri.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChronometerActivity extends AppCompatActivity {


    private static final long TIMER_LENGHT = 1800; // Ten seconds
    private long mTimeToGo;
    private CountDownTimer mCountDownTimer;
    private TimerState mState;
    private PrefUtils mPreferences;

    @BindView(R.id.main_timer)
    TextView mTimerText;

    @BindView(R.id.start)
    Button mStartButton;

    @BindView(R.id.stop)
    Button mStopButton;


    private enum TimerState {
        STOPPED,
        RUNNING
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometer);
        ButterKnife.bind(this);



        mPreferences = new PrefUtils(this);
        if (getIntent().getExtras() != null){
            String yeni = getIntent().getExtras().getString("yeni", null);
            if (yeni != null) {
                Log.e("TAG","11 kapatıyor");
                Intent startIntent = new Intent(ChronometerActivity.this, ForegroundService.class);
                startIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
                startService(startIntent);
            }
        }


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
            Log.e("TAG", "3 geri sayma/servis aç");
            Intent startIntent = new Intent(ChronometerActivity.this, ForegroundService.class);
            startIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
            startService(startIntent);
        }
    }

    private long getNow() {
        Calendar rightNow = Calendar.getInstance();
        return rightNow.getTimeInMillis() / 1000;
    }

    private void initTimer() {
        long startTime = mPreferences.getStartedTime();
        if (startTime > 0) {
            mTimeToGo = (TIMER_LENGHT - (getNow() - startTime));
            if (mTimeToGo <= 0) { // TIMER EXPIRED
                mTimeToGo = TIMER_LENGHT;
                mState = TimerState.STOPPED;
                onTimerFinish();
            } else {
                startTimer();
                mState = TimerState.RUNNING;
            }
        } else {
            mTimeToGo = TIMER_LENGHT;
            mState = TimerState.STOPPED;
        }
        updateTimeUi();
    }

    private void onTimerFinish() {
        Toast.makeText(this, "BİTTİ", Toast.LENGTH_SHORT).show();
        mPreferences.setStartedTime(0);
        mTimeToGo = TIMER_LENGHT;
        updateTimeUi();
    }

    private void updateTimeUi() {
        if (mState == TimerState.RUNNING) {
            mStartButton.setEnabled(false);
        } else {
            mStartButton.setEnabled(true);
        }
        mTimerText.setText(String.valueOf(mTimeToGo));
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
        Log.e("TAG", "2 geri say");
    }


    @OnClick(R.id.start)
    public void onButtonClicked() {
        if (mState == TimerState.STOPPED) {
            Log.e("TAG", "1");
            mPreferences.setStartedTime(getNow());
            startTimer();
            mState = TimerState.RUNNING;
        }
    }

    @OnClick(R.id.stop)
    public void onButtonClickedd() {
        Intent startIntent = new Intent(ChronometerActivity.this, ForegroundService.class);
        startIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
        startService(startIntent);

        mState = TimerState.STOPPED;
        onTimerFinish();
        updateTimeUi();

        mCountDownTimer.cancel();



    }
}