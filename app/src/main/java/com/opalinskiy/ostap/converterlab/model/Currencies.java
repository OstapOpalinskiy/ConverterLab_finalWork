package com.opalinskiy.ostap.converterlab.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Currencies implements Serializable{
    public Currencies(){
        currencyList = new ArrayList();
    }
    private List<Currency> currencyList;

    public List<Currency> getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(List<Currency> currencyList) {
        this.currencyList = currencyList;
    }

    @Override
    public String toString() {
        return "Currencies{" +
                "currencyList=" + currencyList +
                '}';
    }
}
