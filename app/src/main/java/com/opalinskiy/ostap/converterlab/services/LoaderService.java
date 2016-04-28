package com.opalinskiy.ostap.converterlab.services;


import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.opalinskiy.ostap.converterlab.R;
import com.opalinskiy.ostap.converterlab.api.Api;
import com.opalinskiy.ostap.converterlab.constants.Constants;
import com.opalinskiy.ostap.converterlab.utils.databaseUtils.DbManager;
import com.opalinskiy.ostap.converterlab.interfaces.ConnectCallback;
import com.opalinskiy.ostap.converterlab.model.DataResponse;
import com.opalinskiy.ostap.converterlab.model.Organisation;

import java.util.List;

public class LoaderService extends IntentService {
    private DbManager dbManager;
    private List<Organisation> organisations;
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;

    public LoaderService() {
        super("LoaderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(Constants.LOG_TAG, "handle intent service");
        dbManager = new DbManager(getApplicationContext());
        dbManager.open();
        prepareNotification();

        Api.getDataResponseSynchronous(getApplicationContext(), new ConnectCallback() {

            @Override
            public void onProgress(long percentage) {
                String message = "Progress:" + percentage + "%";
                updateNotification(message);
            }

            @Override
            public void onSuccess(Object object) {
                Log.d(Constants.LOG_TAG, "On success in service");
                DataResponse dataResponse = (DataResponse) object;
                organisations = dataResponse.getOrganisations();
                dbManager.setRatesVariationForList(organisations);
                dbManager.writeAllDataToDb(dataResponse);
                updateNotification("Loaded successfully");
            }

            @Override
            public void onFailure() {
                updateNotification("Cant load data");
                Log.d(Constants.LOG_TAG, "On failure in service");
            }
        });
    }

    private void prepareNotification() {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this)
                .setContentText("")
                .setContentTitle("Loading...")
                .setSmallIcon(R.drawable.ic_link)
                .setOngoing(true);
    }

    private void updateNotification(String text) {
        builder.setContentText(text);
        notificationManager.notify(0, builder.build());
    }
}
