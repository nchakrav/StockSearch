package com.nikhilchakravartula.stocksearch.models;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QuoteModel {

    @SerializedName("c")
    private Double c;
    @SerializedName("d")
    private Double d;
    @SerializedName("dp")
    private Double dp;
    @SerializedName("h")
    private Double h;
    @SerializedName("l")
    private Double l;
    @SerializedName("o")
    private Double o;
    @SerializedName("pc")
    private Double pc;
    @SerializedName("t")
    private Integer t;
    @SerializedName("high")
    private Double high;
    @SerializedName("open")
    private Double open;
    @SerializedName("low")
    private Double low;
    @SerializedName("askPrice")
    private Double askPrice;
    @SerializedName("last")
    private Double last;
    @SerializedName("prevClose")
    private Double prevClose;
    @SerializedName("change")
    private Double change;
    @SerializedName("changePercentage")
    private Double changePercentage;
    @SerializedName("ticker")
    private String ticker;
    @SerializedName("timestamp")
    private Integer timestamp;
    @SerializedName("humantimestamp")
    private Date humantimestamp;
    @SerializedName("marketOpen")
    private Boolean marketOpen;

    public Double getC() {
        return c;
    }

    public void setC(Double c) {
        this.c = c;
    }

    public Double getD() {
        return d;
    }

    public void setD(Double d) {
        this.d = d;
    }

    public Double getDp() {
        return dp;
    }

    public void setDp(Double dp) {
        this.dp = dp;
    }

    public Double getH() {
        return h;
    }

    public void setH(Double h) {
        this.h = h;
    }

    public Double getL() {
        return l;
    }

    public void setL(Double l) {
        this.l = l;
    }

    public Double getO() {
        return o;
    }

    public void setO(Double o) {
        this.o = o;
    }

    public Double getPc() {
        return pc;
    }

    public void setPc(Double pc) {
        this.pc = pc;
    }

    public Integer getT() {
        return t;
    }

    public void setT(Integer t) {
        this.t = t;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(Double askPrice) {
        this.askPrice = askPrice;
    }

    public Double getLast() {
        return last;
    }

    public void setLast(Double last) {
        this.last = last;
    }

    public Double getPrevClose() {
        return prevClose;
    }

    public void setPrevClose(Double prevClose) {
        this.prevClose = prevClose;
    }

    public Double getChange() {
        return change;
    }

    public void setChange(Double change) {
        this.change = change;
    }

    public Double getChangePercentage() {
        return changePercentage;
    }

    public void setChangePercentage(Double changePercentage) {
        this.changePercentage = changePercentage;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getHumantimestamp() {
        return new SimpleDateFormat("MM-dd-yyyy").format(humantimestamp);
    }


    public Boolean getMarketOpen() {
        return marketOpen;
    }

    public void setMarketOpen(Boolean marketOpen) {
        this.marketOpen = marketOpen;
    }

}