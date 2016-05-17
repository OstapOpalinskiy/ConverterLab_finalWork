package com.opalinskiy.ostap.converterlab.utils.connectUtils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opalinskiy.ostap.converterlab.constants.Constants;
import com.opalinskiy.ostap.converterlab.model.DataResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectHelper {
    private static ConnectHelper instance;

    private ConnectHelper (){}

    public static ConnectHelper getInstance(){
        if(instance == null){
            instance = new ConnectHelper();
        }
        return instance;
    }

    public Response getResponseSynchronous() throws IOException {
        return getCall().execute();
    }

    public void getResponseAsynchronous(Callback<DataResponse> callback) {
        getCall().enqueue(callback);
    }

    private Call<DataResponse> getCall() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DataResponse.class, new CustomDeserializer());
        final Gson gson = gsonBuilder.create();

        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Constants.DATA_SOURCE_KEY)
                .build();

        ConnectRetrofit connection = retrofit.create(ConnectRetrofit.class);
        Call<DataResponse> call = connection.connect();
        return call;
    }
}
