package com.opalinskiy.ostap.converterlab.model;


import com.opalinskiy.ostap.converterlab.constants.Constants;

import java.io.Serializable;

public class Organisation implements Serializable {

    private String id;
    private int oldId;
    private int orgType;
    private boolean branch;
    private String title;
    private String regionId;
    private String region;
    private String cityId;
    private String city;
    private String phone;
    private String address;
    private String link;
    private Currencies currencies;
    private String date;

    public Organisation() {
        currencies = new Currencies();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getOldId() {
        return oldId;
    }

    public void setOldId(Integer oldId) {
        this.oldId = oldId;
    }

    public Integer getOrgType() {
        return orgType;
    }

    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }

    public Boolean getBranch() {
        return branch;
    }

    public void setBranch(Boolean branch) {
        this.branch = branch;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Currencies getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Currencies currencies) {
        this.currencies = currencies;
    }

    public void setOldId(int oldId) {
        this.oldId = oldId;
    }

    public void setOrgType(int orgType) {
        this.orgType = orgType;
    }

    public boolean isBranch() {
        return branch;
    }

    public void setBranch(boolean branch) {
        this.branch = branch;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Organisation{" +
                "id='" + id + '\'' +
                ", oldId=" + oldId +
                ", orgType=" + orgType +
                ", branch=" + branch +
                ", title='" + title + '\'' +
                ", regionId='" + regionId + '\'' +
                ", region='" + region + '\'' +
                ", cityId='" + cityId + '\'' +
                ", city='" + city + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", link='" + link + '\'' +
                ", currencies=" + currencies +
                '}';
    }
}