package com.nikhilchakravartula.stocksearch.models;

public class StockModel {

    public StockModel(String ticker, Double stockPrice, Double stockChange) {
        this.ticker = ticker;
        this.stockPrice = stockPrice;
        this.stockChange = stockChange;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Double getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(Double stockPrice) {
        this.stockPrice = stockPrice;
    }

    public Double getStockChange() {
        return stockChange;
    }

    public void setStockChange(Double stockChange) {
        this.stockChange = stockChange;
    }

    String ticker;
    Double stockPrice;
    Double stockChange;

}
