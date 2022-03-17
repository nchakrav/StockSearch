package com.nikhilchakravartula.stocksearch.models;

import com.google.gson.annotations.SerializedName;

public class StockOverviewModel {

    @SerializedName("country")
    private String country;
    @SerializedName("currency")
    private String currency;
    @SerializedName("exchange")
    private String exchange;
    @SerializedName("name")
    private String name;
    @SerializedName("ticker")
    private String ticker;
    @SerializedName("ipo")
    private String ipo;
    @SerializedName("marketCapitalization")
    private Double marketCapitalization;
    @SerializedName("shareOutstanding")
    private Double shareOutstanding;
    @SerializedName("logo")
    private String logo;
    @SerializedName("phone")
    private String phone;
    @SerializedName("weburl")
    private String weburl;
    @SerializedName("finnhubIndustry")
    private String finnhubIndustry;
    @SerializedName("description")
    private String description;
    @SerializedName("exchangeCode")
    private String exchangeCode;
    @SerializedName("startDate")
    private String startDate;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getIpo() {
        return ipo;
    }

    public void setIpo(String ipo) {
        this.ipo = ipo;
    }

    public Double getMarketCapitalization() {
        return marketCapitalization;
    }

    public void setMarketCapitalization(Double marketCapitalization) {
        this.marketCapitalization = marketCapitalization;
    }

    public Double getShareOutstanding() {
        return shareOutstanding;
    }

    public void setShareOutstanding(Double shareOutstanding) {
        this.shareOutstanding = shareOutstanding;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public String getFinnhubIndustry() {
        return finnhubIndustry;
    }

    public void setFinnhubIndustry(String finnhubIndustry) {
        this.finnhubIndustry = finnhubIndustry;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

}