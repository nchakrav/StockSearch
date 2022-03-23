package com.nikhilchakravartula.stocksearch.models;

import java.util.Objects;

public class PortfolioStockModel extends StockModel{

    Double numStocksInvested;
    Double cost;

    public PortfolioStockModel()
    {
        this("",0.0,0.0,0.0,0.0);
    }
    public PortfolioStockModel(PortfolioStockModel obj)
    {
        this(obj.getTicker(),
                obj.getStockPrice(),
                obj.getStockChange(),
                obj.getNumStocksInvested(),
                obj.getCost());
    }
    public PortfolioStockModel(String ticker,
                               Double stockPrice,
                               Double stockChange,
                               Double numStocksInvested,
                               Double cost) {
        super(ticker, stockPrice, stockChange);
        this.numStocksInvested = numStocksInvested;
        this.cost = cost;
    }
    public Double getNumStocksInvested() {
        return numStocksInvested;
    }

    public void setNumStocksInvested(Double numStocksInvested) {
        this.numStocksInvested = numStocksInvested;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
