package com.cihatpala.week1project.models;

import com.google.gson.annotations.SerializedName;

public class CurrencyModel {

    @SerializedName("coin")
    public String currency;
    @SerializedName("name")
    public String currencyName;
    @SerializedName("price")
    public double currencyPrice;

    @Override
    public String toString() {
        return "CurrencyModel{" +
                "currency='" + currency + '\'' +
                ", currencyName='" + currencyName + '\'' +
                ", currencyPrice=" + currencyPrice +
                '}';
    }
}
