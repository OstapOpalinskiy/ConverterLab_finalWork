package com.opalinskiy.ostap.converterlab.model;


import java.util.ArrayList;
import java.util.HashMap;

public class DataResponse {
    private String date;
    private ArrayList<Organisation> organizations;
    private HashMap<String, String> regions;
    private HashMap<String, String> cities;
    private HashMap<String, String> currencies;
    private HashMap<String, String> orgTypes;

    public String getDate() {
        return date;
    }

    public ArrayList<Organisation> getOrganizations() {
        return organizations;
    }

    public HashMap<String, String> getRegions() {
        return regions;
    }

    public HashMap<String, String> getCities() {
        return cities;
    }

    public HashMap<String, String> getCurrencies() {
        return currencies;
    }

    public HashMap<String, String> getOrgTypes() {
        return orgTypes;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setOrganizations(ArrayList<Organisation> organizations) {
        this.organizations = organizations;
    }

    public void setRegions(HashMap<String, String> regions) {
        this.regions = regions;
    }

    public void setCities(HashMap<String, String> cities) {
        this.cities = cities;
    }

    public void setCurrencies(HashMap<String, String> currencies) {
        this.currencies = currencies;
    }

    public void setOrgTypes(HashMap<String, String> orgTypes) {
        this.orgTypes = orgTypes;
    }
}
