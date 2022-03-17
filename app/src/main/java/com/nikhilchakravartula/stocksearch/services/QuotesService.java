package com.nikhilchakravartula.stocksearch.services;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.nikhilchakravartula.stocksearch.constants.Constants;
import com.nikhilchakravartula.stocksearch.models.QuoteModel;
import com.nikhilchakravartula.stocksearch.models.SuggestionModel;
import com.nikhilchakravartula.stocksearch.network.VolleySingleton;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuotesService {
    private static final String URL =
            Constants.BASE_URL + "quotes?ticker=";
    public static interface QuotesListener
    {
        void onResponse(QuoteModel suggestionModels);
        void onError(String error);
    };


    public static void getQuotes(Context context,
                                      String text,
                                      QuotesListener quotesListener)
    {
        Gson gson= new Gson();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                URL + text,
                null,
                response -> {
                    List<QuoteModel> quoteModels = Arrays.asList(gson.fromJson(response.toString(),
                            QuoteModel[].class));
                    Log.d("Quotes service",quoteModels.toString());
                    quotesListener.onResponse(quoteModels.get(0));
                },
                error -> {
                    Log.d("Quotes service","Error "+ error);
                });
        request.setShouldCache(false);
        VolleySingleton.newRequestQueue(context).add(
                request
        );
    }
}
