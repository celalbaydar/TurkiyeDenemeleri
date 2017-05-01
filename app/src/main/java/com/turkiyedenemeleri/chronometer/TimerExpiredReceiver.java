/*
 * Copyright (C) 2016 Federico Paolinelli.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.turkiyedenemeleri.chronometer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.turkiyedenemeleri.MainActivity;


public class TimerExpiredReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, i, 0);

        NotificationCompat.Builder b = new NotificationCompat.Builder(context);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        b.setSound(notification)
                .setContentTitle("Süre Bitti")
                .setContentText("Süre Bitti")
                .setAutoCancel(true)
                .setSmallIcon(android.R.drawable.ic_notification_clear_all)
                .setContentIntent(pIntent);


        Notification n =  b.build();
        b.build().flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, n);
        Log.e("TAG","7  Süre Bitti");


        Intent startIntent = new Intent(context, ForegroundService.class);
        startIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
        context.startService(startIntent);
        Log.e("TAG","8  Servisi İptal Et");

    }
}
