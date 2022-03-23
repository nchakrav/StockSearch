package com.nikhilchakravartula.stocksearch.models;

public class FavoriteStockModel extends StockModel{


    String companyName;

    public FavoriteStockModel()
    {
        this("",0.0,0.0,"");
    }
    public FavoriteStockModel(String ticker,
                               Double stockPrice,
                               Double stockChange,
                               String companyName) {
        super(ticker, stockPrice, stockChange);
        this.companyName = companyName;
    }
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }



}
