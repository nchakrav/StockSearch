package com.nikhilchakravartula.stocksearch.services;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nikhilchakravartula.stocksearch.constants.Constants;
import com.nikhilchakravartula.stocksearch.models.QuoteModel;
import com.nikhilchakravartula.stocksearch.network.VolleySingleton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LastChartPricesService {
    public static interface LastChartPricesListener
    {
        void onResponse(ArrayList<List<Double>> lastChartPrices);
        void onError(String error);
    };


    public static void getLastChartPrices(Context context,
                                      String text,
                                      String timestamp,
                                         LastChartPricesListener lastChartPricesListener)
    {
        Gson gson= new Gson();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                Constants.BASE_URL + "lastChartPrices?ticker=" + text + "&timestamp=" + timestamp,
                null,
                response -> {
                    ArrayList<List<Double>> lastChartPrices = gson.fromJson(response.toString(), new TypeToken<ArrayList<List<Double>>>(){}.getType());
                    Log.d("LastChartPrices service",lastChartPrices.toString());
                    lastChartPricesListener.onResponse(lastChartPrices);
                },
                error -> {
                    Log.d("LastChartPrices service","Error "+ error);
                });
        request.setShouldCache(false);
        VolleySingleton.newRequestQueue(context).add(
                request
        );
    }
}
