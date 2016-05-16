package com.opalinskiy.ostap.converterlab.utils.connectUtils;




import com.opalinskiy.ostap.converterlab.model.DataResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ConnectRetrofit {
    @GET("ru/public/currency-cash.json")
    Call<DataResponse> connect();
}
