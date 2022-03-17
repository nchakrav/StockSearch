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

public class SuggestionsService {
    private static final String URL =
            Constants.BASE_URL + "autocomplete?query=";
    public static interface SuggestionListener
    {
        void onResponse(List<SuggestionModel> suggestionModels);
        void onError(String error);
    };


    public static void getSuggestions(Context context,
                                      String text,
                                      SuggestionListener suggestionListener)
    {
        Gson gson= new Gson();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                URL + text,
                null,
                response -> {
                    List<SuggestionModel> suggestionModels = Arrays.asList(gson.fromJson(response.toString(),
                            SuggestionModel[].class));
                    Log.d("Suggestion service",suggestionModels.toString());
                    suggestionListener.onResponse(suggestionModels);
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
