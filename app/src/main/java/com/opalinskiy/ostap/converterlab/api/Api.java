package com.opalinskiy.ostap.converterlab.api;

import android.content.Context;

import com.opalinskiy.ostap.converterlab.utils.connectUtils.Connect;
import com.opalinskiy.ostap.converterlab.interfaces.ConnectCallback;
import com.opalinskiy.ostap.converterlab.model.DataResponse;

import java.io.IOException;


public class Api {
    public static void getDataResponse(ConnectCallback callback) {
        Connect.getInstance().getRequestWithParam(null, new DataResponse(), callback);
    }

    public static void getDataResponseSynchronous(Context context, ConnectCallback callback) {
        try {
            Connect.getInstance().getRequestSynchronous(context, new DataResponse(), callback);
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailure();
        }
    }
}
