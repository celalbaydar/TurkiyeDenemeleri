/*
 * Copyright (c) 2016. Truiton (http://www.truiton.com/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 * Mohit Gupt (https://github.com/mohitgupt)
 *
 */

package com.turkiyedenemeleri.chronometer;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.turkiyedenemeleri.MainActivity;
import com.turkiyedenemeleri.R;
import com.turkiyedenemeleri.app.Constants;

public class ForegroundService extends Service {
    private static final String LOG_TAG = "ForegroundService";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {

            PrefUtils mPreferences = new PrefUtils(this);
            long wakeUpTime = (mPreferences.getStartedTime(intent.getStringExtra("sınavid")) + 1800) * 1000 ;
            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent timerExpire = new Intent(this, TimerExpiredReceiver.class);
            PendingIntent sender = PendingIntent.getBroadcast(this, 0, timerExpire, PendingIntent.FLAG_CANCEL_CURRENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                am.setAlarmClock(new AlarmManager.AlarmClockInfo(wakeUpTime, sender), sender);
            } else {
                am.set(AlarmManager.RTC_WAKEUP, wakeUpTime, sender);
            }
            Log.e("TAG","4 bitince servisi durduracak, broadcast");


            Intent notificationIntent = new Intent(this, MainActivity.class);
            notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            notificationIntent.putExtra("yeni","yeni");
            PendingIntent pendingIntent =
                    PendingIntent.getActivity(this, 0, notificationIntent, 0);


            Log.e("TAG","5 tıklarsam ekranı aç");


            Bitmap icon = BitmapFactory.decodeResource(getResources(),
                    R.mipmap.ic_launcher);

            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle("Sınav Devam Ediyor")
                    .setTicker("Sınav Devam Ediyor")
                    .setContentText("Sınav Devam Ediyor")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
                    .setContentIntent(pendingIntent)
                    .setOngoing(true).build();

            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, notification);
            Log.e("TAG","6  Devam ediyor");


        } else if (intent.getAction().equals(
                Constants.ACTION.STOPFOREGROUND_ACTION)) {
            Log.i(LOG_TAG, "Received Stop Foreground Intent");

            Intent cancelIntent = new Intent(this, TimerExpiredReceiver.class);
            PendingIntent sender = PendingIntent.getBroadcast(this, 0, cancelIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            am.cancel(sender);
            Log.e("TAG","9  Receiver İpta");


            stopForeground(true);
            stopSelf();

            Log.e("TAG","10 Notif İptal");
        }
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "In onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Used only in case of bound services.
        return null;
    }
}
