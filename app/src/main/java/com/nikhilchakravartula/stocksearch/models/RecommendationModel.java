package com.nikhilchakravartula.stocksearch.models;

import com.google.gson.annotations.SerializedName;

public class RecommendationModel {

    @SerializedName("symbol")
    private String symbol;
    @SerializedName("buy")
    private Integer buy;
    @SerializedName("hold")
    private Integer hold;
    @SerializedName("period")
    private String period;
    @SerializedName("sell")
    private Integer sell;
    @SerializedName("strongBuy")
    private Integer strongBuy;
    @SerializedName("strongSell")
    private Integer strongSell;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getBuy() {
        return buy;
    }

    public void setBuy(Integer buy) {
        this.buy = buy;
    }

    public Integer getHold() {
        return hold;
    }

    public void setHold(Integer hold) {
        this.hold = hold;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Integer getSell() {
        return sell;
    }

    public void setSell(Integer sell) {
        this.sell = sell;
    }

    public Integer getStrongBuy() {
        return strongBuy;
    }

    public void setStrongBuy(Integer strongBuy) {
        this.strongBuy = strongBuy;
    }

    public Integer getStrongSell() {
        return strongSell;
    }

    public void setStrongSell(Integer strongSell) {
        this.strongSell = strongSell;
    }

}