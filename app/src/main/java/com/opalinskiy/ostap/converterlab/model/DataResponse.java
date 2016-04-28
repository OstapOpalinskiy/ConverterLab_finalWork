package com.opalinskiy.ostap.converterlab.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opalinskiy.ostap.converterlab.constants.Constants;
import com.opalinskiy.ostap.converterlab.interfaces.ModelResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataResponse implements ModelResponse {
    private String date;
    private ArrayList<Organisation> organisations;
    private HashMap<String, String> regions;
    private HashMap<String, String> cities;
    private HashMap<String, String> currencies;
    private HashMap<String, String> orgTypes;
    private JSONObject generalObject;

    @Override
    public void configure(Object object) throws JSONException, ParseException {
        generalObject = (JSONObject) object;
        // get date
        date = generalObject.getString(Constants.DATE_KEY);

        //get list of organisations
        JSONArray orgsArray = generalObject.getJSONArray(Constants.ORGANISATIONS_KEY);
        Gson gson = new Gson();
        organisations = gson.fromJson(orgsArray.toString(), new TypeToken<ArrayList<Organisation>>() {
        }.getType());

        //get map of regions
        JSONObject regionsObject = generalObject.getJSONObject(Constants.REGIONS_KEY);
        regions = gson.fromJson(regionsObject.toString(), new TypeToken<HashMap<String, String>>() {
        }.getType());

        // get map of currencies
        JSONObject currenciesObject = generalObject.getJSONObject(Constants.CURRENCIES_KEY);
        currencies = gson.fromJson(currenciesObject.toString(), new TypeToken<HashMap<String, String>>() {
        }.getType());

        // get map of cities
        JSONObject citiesObject = generalObject.getJSONObject(Constants.CITIES_KEY);
        cities = gson.fromJson(citiesObject.toString(), new TypeToken<HashMap<String, String>>() {
        }.getType());

        // get map of orgTypes
        JSONObject orgTypesObject = generalObject.getJSONObject(Constants.ORG_TYPES_MAP_KEY);
        orgTypes = gson.fromJson(orgTypesObject.toString(), new TypeToken<HashMap<String, String>>() {
        }.getType());

        //fill organisations
        for (int i = 0; i < organisations.size(); i++) {
            organisations.get(i).setRegion(regions.get(organisations.get(i).getRegionId()));
            organisations.get(i).setCity(cities.get(organisations.get(i).getCityId()));
            organisations.get(i).getCurrencies().setCurrencyList(fillCurrencies(i, gson));
            organisations.get(i).setDate(date);
        }
    }

    private List<Currency> fillCurrencies(int pos, Gson gson) {

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

    public Organisation getOrganisationById(String id) {
        Organisation result = null;
        for (int i = 0; i < organisations.size(); i++) {
            if (organisations.get(i).getId().equals(id)) {
                result = organisations.get(i);
                break;
            }
        }
        return result;
    }

    public ArrayList<Organisation> getOrganisations() {
        return organisations;
    }

    public String getDate() {
        return date;
    }

    public void setOrganisations(ArrayList<Organisation> organisations) {
        this.organisations = organisations;
    }

    public HashMap<String, String> getRegions() {
        return regions;
    }

    public void setRegions(HashMap<String, String> regions) {
        this.regions = regions;
    }

    public HashMap<String, String> getCities() {
        return cities;
    }

    public void setCities(HashMap<String, String> cities) {
        this.cities = cities;
    }

    public HashMap<String, String> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(HashMap<String, String> currencies) {
        this.currencies = currencies;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public HashMap<String, String> getOrgTypes() {
        return orgTypes;
    }

    public void setOrgTypes(HashMap<String, String> orgTypes) {
        this.orgTypes = orgTypes;
    }
}
