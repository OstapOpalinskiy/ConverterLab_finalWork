package com.opalinskiy.ostap.converterlab.services;


import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opalinskiy.ostap.converterlab.R;
import com.opalinskiy.ostap.converterlab.constants.Constants;
import com.opalinskiy.ostap.converterlab.database.DbManager;
import com.opalinskiy.ostap.converterlab.model.DataResponse;
import com.opalinskiy.ostap.converterlab.model.Organisation;
import com.opalinskiy.ostap.converterlab.utils.connectUtils.ConnectRetrofit;
import com.opalinskiy.ostap.converterlab.utils.connectUtils.CustomDeserializer;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

        dbManager = new DbManager(getApplicationContext());
        dbManager.open();
        prepareNotification();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DataResponse.class, new CustomDeserializer());
        final Gson gson = gsonBuilder.create();

        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Constants.DATA_SOURCE_KEY)
                .build();

        ConnectRetrofit connection = retrofit.create(ConnectRetrofit.class);
        Call<DataResponse> call = connection.connect();
        Response<DataResponse> response = null;

        try {
            response = call.execute();
                    DataResponse data = response.body();

            organisations = data.getOrganizations();
            dbManager.setRatesVariationForList(organisations);
            dbManager.writeAllDataToDb(data);
            updateNotification(getString(R.string.db_updated));

        } catch (IOException e) {
            e.printStackTrace();
            updateNotification(getString(R.string.db_cant_update));
        }
    }

    private void prepareNotification() {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this)
                .setContentText("")
                .setContentTitle(getString(R.string.loading))
                .setSmallIcon(R.drawable.ic_link)
                .setOngoing(false);
    }

    private void updateNotification(String text) {
        builder.setContentText(text);
        notificationManager.notify(0, builder.build());
    }
}
