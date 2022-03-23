package com.nikhilchakravartula.stocksearch.models;

import java.util.Objects;

public class StockModel {

    public StockModel()
    {}
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockModel that = (StockModel) o;
        return ticker.equalsIgnoreCase(that.ticker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticker);
    }
}
