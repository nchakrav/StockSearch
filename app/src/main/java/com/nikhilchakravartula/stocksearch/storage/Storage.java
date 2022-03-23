package com.nikhilchakravartula.stocksearch.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.nikhilchakravartula.stocksearch.models.FavoriteStockModel;
import com.nikhilchakravartula.stocksearch.models.PortfolioStockModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Storage {
    public static Storage storage;
    Context context;
    SharedPreferences favoritesSharedPref;
    SharedPreferences portfolioSharedPref;
    SharedPreferences watchListSharedPref;
    public static final String FAVORITES_FILE_KEY = "com.nikhilchakravartula.stocksearch.storage.FAVORITES_FILE";
    public static final String PORTFOLIO_FILE_KEY = "com.nikhilchakravartula.stocksearch.storage.PORTFOLIO_FILE";
    public static final String FAVORITES_KEY = "com.nikhilchakravartula.stocksearch.storage.FAVORITES";
    public static final String PORTFOLIO_KEY = "com.nikhilchakravartula.stocksearch.storage.PORTFOLIO";
    public static final String WALLET_KEY = "com.nikhilchakravartula.stocksearch.storage.WALLET";
    public static final String WATCHLIST_FILE_KEY = "com.nikhilchakravartula.stocksearch.storage.WATCHLIST_FILE_KEY";
    public static final String WATCHLIST_KEY = "com.nikhilchakravartula.stocksearch.storage.WATCHLIST";
    private Storage(Context context)
    {
        this.context = context;
        favoritesSharedPref = context.getSharedPreferences(FAVORITES_FILE_KEY,Context.MODE_PRIVATE);
        portfolioSharedPref = context.getSharedPreferences(PORTFOLIO_FILE_KEY,Context.MODE_PRIVATE);
        watchListSharedPref = context.getSharedPreferences(WATCHLIST_FILE_KEY,Context.MODE_PRIVATE);
    }

    public static Storage getStorage(Context con)
    {
        if(storage == null)
        {
            storage = new Storage(con);
        }
        return storage;
    }


    public Double getMoneyInWallet()
    {
        if(!portfolioSharedPref.contains(WALLET_KEY))
        {
            SharedPreferences.Editor editor = portfolioSharedPref.edit();
            editor.putString(WALLET_KEY,"25000.00");
            editor.apply();
        }
        return Double.parseDouble(portfolioSharedPref.getString(WALLET_KEY,"25000"));
    }

    public void setFavorites(List<FavoriteStockModel> favoriteStockModels)
    {
        SharedPreferences.Editor editor = favoritesSharedPref.edit();
        editor.putString(FAVORITES_KEY,new Gson().toJson(favoriteStockModels));
        editor.apply();
    }

    public List<FavoriteStockModel> getFavorites()
    {
        String favoritesSharedPrefString = favoritesSharedPref.getString(FAVORITES_KEY,null);
        List<FavoriteStockModel> favoriteStockModels = new ArrayList<>();
        if(favoritesSharedPrefString!=null)
            favoriteStockModels =
                    new ArrayList<>(Arrays.asList(new Gson().fromJson(favoritesSharedPrefString,
                FavoriteStockModel[].class)));
        return favoriteStockModels;
    }

    public void setPortfolio(List<PortfolioStockModel> portfolioStockModels)
    {
        SharedPreferences.Editor editor = portfolioSharedPref.edit();
        editor.putString(PORTFOLIO_KEY,new Gson().toJson(portfolioStockModels));
        editor.apply();
    }

    public List<PortfolioStockModel> getPortfolio()
    {
        String portfolioSharedPrefString = portfolioSharedPref.getString(PORTFOLIO_KEY,null);
        List<PortfolioStockModel> portfolioStockModels = new ArrayList<>();
        if(portfolioSharedPrefString!=null)
            portfolioStockModels = new ArrayList<>(Arrays.asList(new Gson().fromJson(portfolioSharedPrefString, PortfolioStockModel[].class)));
        return portfolioStockModels;
    }

    public void addPortfolio(PortfolioStockModel portfolioStockModel)
    {
        List<PortfolioStockModel> portfolioStockModels = getPortfolio();
        portfolioStockModels.add(portfolioStockModel);
        setPortfolio(portfolioStockModels);
    }

    public void removePortfolio(PortfolioStockModel portfolioStockModel)
    {
        List<PortfolioStockModel> portfolioStockModels = getPortfolio();
        int pos = -1;
        for(int i=0;i<portfolioStockModels.size();i++)
        {
            if(portfolioStockModel.getTicker().equalsIgnoreCase(portfolioStockModels.get(i).getTicker()))
            {
                pos = i;
                break;
            }
        }

        if(pos!=-1) {
            portfolioStockModels.remove(pos);
            setPortfolio(portfolioStockModels);
        }
    }

    public void addFavorite(FavoriteStockModel favoriteStockModel)
    {
        List<FavoriteStockModel> favoriteStockModels = getFavorites();
        favoriteStockModels.add(favoriteStockModel);
        setFavorites(favoriteStockModels);
        Log.d("added and set fav",favoriteStockModel.getTicker());
    }

    public void removeFavorite(FavoriteStockModel favoriteStockModel)
    {
        List<FavoriteStockModel> favoriteStockModels = getFavorites();
        int pos = -1;
        for(int i=0;i<favoriteStockModels.size();i++)
        {
            if(favoriteStockModel.getTicker().equalsIgnoreCase(favoriteStockModels.get(i).getTicker()))
            {
                pos = i;
                break;
            }
        }

        if(pos!=-1)
        {
            favoriteStockModels.remove(pos);
            setFavorites(favoriteStockModels);
        }

    }

    public boolean hasFavorite(FavoriteStockModel favoriteStockModel)
    {
        List<FavoriteStockModel> favoriteStockModels = getFavorites();
        int pos = -1;
        for(int i=0;i<favoriteStockModels.size();i++)
        {
            if(favoriteStockModel.getTicker().equalsIgnoreCase(favoriteStockModels.get(i).getTicker()))
            {
                pos = i;
                break;
            }
        }
        return pos==-1?false:true;
    }

    public boolean hasPortfolio(PortfolioStockModel portfolioStockModel)
    {
        List<PortfolioStockModel> portfolioStockModels = getPortfolio();
        int pos = -1;
        for(int i=0;i<portfolioStockModels.size();i++)
        {
            if(portfolioStockModel.getTicker().equalsIgnoreCase(portfolioStockModels.get(i).getTicker()))
            {
                pos = i;
                break;
            }
        }
        return pos==-1?false:true;
    }


    public void setMoneyInWallet(Double moneyInWallet)
    {
            SharedPreferences.Editor editor = portfolioSharedPref.edit();
            editor.putString(WALLET_KEY,moneyInWallet.toString());
            editor.apply();
    }


    public List<String> getWatchList()
    {
        String watchListSharedPrefString = watchListSharedPref.getString(WATCHLIST_KEY,null);
        List<String> watchList = new ArrayList<>();
        if(watchListSharedPrefString!=null)
            watchList =
                    new ArrayList<>(Arrays.asList(new Gson().fromJson(watchListSharedPrefString,
                            String[].class)));
        return watchList;
    }

    public void addToWatchList(String tickerToWatch)
    {
        String watchListSharedPrefString =  watchListSharedPref.getString(WATCHLIST_KEY,null);

        List<String> watchList = new ArrayList<>();
        if(watchListSharedPrefString!=null)
            watchList = new ArrayList<>(Arrays.asList(new Gson().fromJson(watchListSharedPrefString,String[].class)));
        if(watchList.contains(tickerToWatch))
            return;
        SharedPreferences.Editor editor = watchListSharedPref.edit();
        watchList.add(tickerToWatch);
        editor.putString(WATCHLIST_KEY,new Gson().toJson(watchList));
        editor.apply();
    }


    public void removeFromWatchList(String tickerToWatch)
    {
        String watchListSharedPrefString =  watchListSharedPref.getString(WATCHLIST_KEY,null);

        List<String> watchList = new ArrayList<>();
        if(watchListSharedPrefString!=null)
            watchList = new ArrayList<>(Arrays.asList(new Gson().fromJson(watchListSharedPrefString,String[].class)));
        SharedPreferences.Editor editor = watchListSharedPref.edit();
        watchList.remove(tickerToWatch);
        editor.putString(WATCHLIST_KEY,new Gson().toJson(watchList));
        editor.apply();
    }

}
