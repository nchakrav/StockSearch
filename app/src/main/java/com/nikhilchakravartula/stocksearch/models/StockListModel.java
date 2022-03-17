package com.nikhilchakravartula.stocksearch.models;


import java.util.ArrayList;
import java.util.List;

public class StockListModel {
    List<StockModel> portfolio;

    StockListModel()
    {
        portfolio  = new ArrayList<>();
    }
    void add(StockModel stockModel)
    {
        portfolio.add(stockModel);
    }
    void remove(StockModel stockModel)
    {
        for(int i=0;i<portfolio.size();i++)
        {
            if(portfolio.get(i).getTicker().equals(stockModel.getTicker()))
            {
                portfolio.remove(i);
                break;
            }
        }
    }

    int size()
    {
        return portfolio.size();
    }

    boolean hasItems()
    {
        return (size()!=0);
    }

    StockModel get(int i)
    {
        return portfolio.get(i);
    }
}
