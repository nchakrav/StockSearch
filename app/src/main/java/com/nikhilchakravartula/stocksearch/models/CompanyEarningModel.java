package com.nikhilchakravartula.stocksearch.models;


import com.google.gson.annotations.SerializedName;

public class CompanyEarningModel {

    private Double actual;
    private Double estimate;
    private Double surprise;
    private Double surprisePercent;
    private String period;
    private String symbol;

    public Double getActual() {
        return actual;
    }

    public void setActual(Double actual) {
        this.actual = actual;
    }

    public Double getEstimate() {
        return estimate;
    }

    public void setEstimate(Double estimate) {
        this.estimate = estimate;
    }

    public Double getSurprise() {
        return surprise;
    }

    public void setSurprise(Double surprise) {
        this.surprise = surprise;
    }

    public Double getSurprisePercent() {
        return surprisePercent;
    }

    public void setSurprisePercent(Double surprisePercent) {
        this.surprisePercent = surprisePercent;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

}