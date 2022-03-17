package com.nikhilchakravartula.stocksearch.models;

import com.google.gson.annotations.SerializedName;

public class SuggestionModel {

    public String toString()
    {
        return ticker+" | "+description;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplaySymbol() {
        return displaySymbol;
    }

    public void setDisplaySymbol(String displaySymbol) {
        this.displaySymbol = displaySymbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    @SerializedName("description")
    private String description;
    @SerializedName("displaySymbol")
    private String displaySymbol;
    @SerializedName("symbol")
    private String symbol;
    @SerializedName("type")
    private String type;
    @SerializedName("ticker")
    private String ticker;
}
