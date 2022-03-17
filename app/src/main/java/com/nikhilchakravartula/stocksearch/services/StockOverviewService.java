package com.nikhilchakravartula.stocksearch.services;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.nikhilchakravartula.stocksearch.constants.Constants;
import com.nikhilchakravartula.stocksearch.models.QuoteModel;
import com.nikhilchakravartula.stocksearch.models.StockOverviewModel;
import com.nikhilchakravartula.stocksearch.models.SuggestionModel;
import com.nikhilchakravartula.stocksearch.network.VolleySingleton;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StockOverviewService {
    private static final String URL =
            Constants.BASE_URL + "overview?ticker=";
    public static interface StockOverviewListener
    {
        void onResponse(StockOverviewModel stockOverviewModels);
        void onError(String error);
    };


    public static void getStockOverview(Context context,
                                 String text,
                                 StockOverviewListener stockOverviewListener)
    {
        Gson gson= new Gson();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                URL + text,
                null,
                response -> {
                    StockOverviewModel stockOverviewModel = gson.fromJson(response.toString(),
                            StockOverviewModel.class);
                    Log.d("Stock Overview service",stockOverviewModel.toString());
                    stockOverviewListener.onResponse(stockOverviewModel);
                },
                error -> {
                    Log.d("Stock Overview","Error "+ error);
                });
        request.setShouldCache(false);
        VolleySingleton.newRequestQueue(context).add(
                request
        );
    }
}
