package com.opalinskiy.ostap.converterlab.model;

import com.opalinskiy.ostap.converterlab.constants.Constants;

import java.io.Serializable;

/**
 * Created by Evronot on 21.04.2016.
 */
public class Currency  implements Serializable{
    private String idCurrency;
    private String nameCurrency;
    private String ask;
    private String changeAsk;
    private String bid;
    private String changeBid;

    public Currency(){
        changeAsk = Constants.INCREASE_KEY;
        changeBid = Constants.INCREASE_KEY;
    }

    public String getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(String idCurrency) {
        this.idCurrency = idCurrency;
    }

    public String getNameCurrency() {
        return nameCurrency;
    }

    public void setNameCurrency(String nameCurrency) {
        this.nameCurrency = nameCurrency;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getBid() {
        return bid;
    }

    public String getChangeAsk() {
        return changeAsk;
    }

    public void setChangeAsk(String changeAsk) {
        this.changeAsk = changeAsk;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getChangeBid() {
        return changeBid;
    }

    public void setChangeBid(String changeBid) {
        this.changeBid = changeBid;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "idCurrency='" + idCurrency + '\'' +
                ", nameCurrency='" + nameCurrency + '\'' +
                ", ask='" + ask + '\'' +
                ", bid='" + bid + '\'' +
                '}';
    }
}
