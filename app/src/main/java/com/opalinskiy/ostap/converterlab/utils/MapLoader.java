package com.opalinskiy.ostap.converterlab.utils;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.opalinskiy.ostap.converterlab.constants.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class MapLoader extends AsyncTaskLoader<LatLng> {
    private int zoom;
    String address;
    String city;

    public MapLoader(Context context, String city, String address) {
        super(context);
        this.address = address;
        this.city = city;
    }

    @Override
    public LatLng loadInBackground() {
        Log.d(Constants.LOG_TAG, "do in background MapLoader");
        if (city == null || address == null) {
            return null;
        } else {
            setZoom(address);
            return parseCoordinates(getUrl(city, address));
        }
    }


    private void setZoom(String _address) {
        if (validateText(_address)) {
            zoom = 17;
        } else {
            zoom = 12;
        }
    }

    private String getUrl(String _city, String _address) {
        StringBuilder builder = new StringBuilder("http://maps.google.com/maps/api/geocode/json?address=Ukraine+");
        if (validateText(_address)) {
            try {
                builder.append(URLEncoder.encode(_city, "utf8") + "+" + URLEncoder.encode(_address, "utf8") + "&sensor=false");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return builder.toString();
        } else {
            try {
                builder.append(URLEncoder.encode(_city, "utf8") + "&sensor=false");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return builder.toString();
        }
    }


    private LatLng parseCoordinates(String uri) {
        String json = null;
        URL url = null;
        try {
            url = new URL(uri);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
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
                json = sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject;
        double lat = 0;
        double lng = 0;
        try {
            jsonObject = new JSONObject(json);

            lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");

            lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        LatLng latLng = new LatLng(lat, lng);
        return latLng;
    }

    public int getZoom() {
        return zoom;
    }

    public static boolean validateText(String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        } else {
            return true;
        }
    }
}