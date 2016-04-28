package com.opalinskiy.ostap.converterlab.utils.connectUtils;


import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.opalinskiy.ostap.converterlab.constants.Constants;
import com.opalinskiy.ostap.converterlab.interfaces.ConnectCallback;
import com.opalinskiy.ostap.converterlab.interfaces.ModelResponse;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;

public class Connect {
    private static Connect instance;
    private AsyncHttpClient client;

    private Connect() {
    }

    public static Connect getInstance() {
        if (instance == null) {
            instance = new Connect();
            instance.client = new AsyncHttpClient();
        }

        return instance;
    }

    public void getRequestWithParam(RequestParams requestParams, final ModelResponse modelResponse, final ConnectCallback callback) {
        client.get(Constants.DATA_SOURCE_KEY, requestParams, new JsonHttpResponseHandler() {

                    @Override
                    public void onProgress(int bytesWritten, int totalSize) {
                        super.onProgress(bytesWritten, totalSize);
                        long progressPercentage = (long) ((bytesWritten * 10) / totalSize);
                        callback.onProgress(progressPercentage);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.d(Constants.LOG_TAG, "success");
                        parseData(response, modelResponse, callback);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        Log.d(Constants.LOG_TAG, "successArray");
                        parseData(response, modelResponse, callback);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.d(Constants.LOG_TAG, "failure");
                        callback.onFailure();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.d(Constants.LOG_TAG, "failureArray");
                    }
                }
        );
    }

    private void parseData(Object jsonObject, ModelResponse modelObject, ConnectCallback callback) {
        if (null != modelObject) {
            try {
                modelObject.configure(jsonObject);
                callback.onSuccess(modelObject);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void getRequestSynchronous(Context context, ModelResponse modelResponse, ConnectCallback callback) throws IOException {
        URL url = new URL(Constants.DATA_SOURCE_KEY);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setConnectTimeout(10000);
        conn.setRequestMethod("GET");

        int respCode = conn.getResponseCode();
        if (respCode == 200) {
            final BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            try {
               parseData(new JSONObject(sb.toString()), modelResponse, callback);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
