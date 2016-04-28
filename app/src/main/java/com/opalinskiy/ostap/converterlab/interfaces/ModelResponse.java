package com.opalinskiy.ostap.converterlab.interfaces;

import org.json.JSONException;

import java.text.ParseException;

public interface ModelResponse {

    void configure(Object object) throws JSONException, ParseException;
}
