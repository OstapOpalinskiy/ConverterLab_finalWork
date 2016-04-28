package com.opalinskiy.ostap.converterlab.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.opalinskiy.ostap.converterlab.constants.Constants;
import com.opalinskiy.ostap.converterlab.services.LoaderService;

/**
 * Created by Evronot on 25.04.2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent serviceIntent = new Intent(context, LoaderService.class);
        Intent alarm = new Intent(context, AlarmReceiver.class);
        boolean isRunning = (PendingIntent.getBroadcast(context, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);

        if(isRunning == false) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarm, 0);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime(),Constants.THIRTY_MINUTES, pendingIntent);
        }
        Log.d(Constants.LOG_TAG, "ALARM WORKS!!!");
        context.startService(serviceIntent);
    }
}
