package com.nikhilchakravartula.stocksearch.services;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.nikhilchakravartula.stocksearch.constants.Constants;
import com.nikhilchakravartula.stocksearch.models.SuggestionModel;
import com.nikhilchakravartula.stocksearch.network.VolleySingleton;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompanyPeersService {
    private static final String URL =
            Constants.BASE_URL + "companypeers?ticker=";
    public static interface CompanyPeersListener
    {
        void onResponse(List<String> companyPeers);
        void onError(String error);
    };


    public static void getCompanyPeers(Context context,
                                      String text,
                                      CompanyPeersListener companyPeersListener)
    {
        Gson gson= new Gson();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                URL + text,
                null,
                response -> {
                    List<String> companyPeers = Arrays.asList(gson.fromJson(response.toString(),
                            String[].class));
                    Log.d("Suggestion service",companyPeers.toString());
                    companyPeersListener.onResponse(companyPeers);
                },
                error -> {
                    Log.d("Suggestion Service","Error "+ error);
                });
        request.setShouldCache(false);
        VolleySingleton.newRequestQueue(context).add(
                request
        );
    }
}
