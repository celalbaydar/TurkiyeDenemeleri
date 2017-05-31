package com.turkiyedenemeleri.chronometer;

import android.support.v7.app.AppCompatActivity;

public class ChronometerActivity extends AppCompatActivity {

/*


    @BindView(R.id.main_timer)
    TextView mTimerText;

    @BindView(R.id.start)
    Button mStartButton;

    @BindView(R.id.stop)
    Button mStopButton;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometer);
        ButterKnife.bind(this);





    }

    @Override
    protected void onResume() {
        super.onResume();
        initTimer();


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



    }*/
}