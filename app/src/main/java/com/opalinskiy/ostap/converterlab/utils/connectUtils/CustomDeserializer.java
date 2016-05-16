package com.opalinskiy.ostap.converterlab.utils.connectUtils;


import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.opalinskiy.ostap.converterlab.constants.Constants;
import com.opalinskiy.ostap.converterlab.model.Currency;
import com.opalinskiy.ostap.converterlab.model.DataResponse;
import com.opalinskiy.ostap.converterlab.model.Organisation;


import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomDeserializer implements JsonDeserializer<DataResponse> {
    private DataResponse res;
    private ArrayList<Organisation> organisations;
    private HashMap<String, String> regions;
    private HashMap<String, String> cities;
    private HashMap<String, String> currencies;
    private HashMap<String, String> orgTypes;
    private Gson gson = new Gson();

    @Override
    public DataResponse deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        String stringJson = json.getAsJsonObject().toString();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(stringJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        init(json);
        fillOrganisations(jsonObject);
        return res;
    }

    private void fillOrganisations(JSONObject jsonObject) {
        for (int i = 0; i < organisations.size(); i++) {
            organisations.get(i).setRegion(regions.get(organisations.get(i).getRegionId()));
            organisations.get(i).setCity(cities.get(organisations.get(i).getCityId()));
            organisations.get(i).getCurrencies().setCurrencyList(fillCurrencies( jsonObject, i));
            organisations.get(i).setDate(res.getDate());
        }
    }

    private void init(JsonElement json) {
        res = gson.fromJson(json, DataResponse.class);
        organisations = res.getOrganizations();
        regions = res.getRegions();
        cities = res.getCities();
        currencies = res.getCurrencies();
        orgTypes = res.getOrgTypes();
    }


    private List<Currency> fillCurrencies(JSONObject generalObject, int pos) {

        JSONObject jsonCurrency = null;
        try {
            jsonCurrency = generalObject.getJSONArray(Constants.ORGANISATIONS_KEY).getJSONObject(pos)
                    .getJSONObject(Constants.CURRENCIES_KEY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<Currency> list = new ArrayList<>();

        for (String key : currencies.keySet()) {
            if (jsonCurrency.has(key)) {
                Currency currency = null;
                try {
                    currency = gson.fromJson(String.valueOf(jsonCurrency
                            .getJSONObject(key)), Currency.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                currency.setIdCurrency(key);
                currency.setNameCurrency(currencies.get(key));
                list.add(currency);
            }
        }
        return list;
    }
}