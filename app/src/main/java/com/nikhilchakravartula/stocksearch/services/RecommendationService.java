package com.nikhilchakravartula.stocksearch.services;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.nikhilchakravartula.stocksearch.constants.Constants;
import com.nikhilchakravartula.stocksearch.models.QuoteModel;
import com.nikhilchakravartula.stocksearch.models.RecommendationModel;
import com.nikhilchakravartula.stocksearch.models.SuggestionModel;
import com.nikhilchakravartula.stocksearch.network.VolleySingleton;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecommendationService {
    private static final String URL =
            Constants.BASE_URL + "recommendation?ticker=";
    public static interface RecommendationListener
    {
        void onResponse(List<RecommendationModel> suggestionModels);
        void onError(String error);
    };


    public static void getRecommendations(Context context,
                                 String text,
                                 RecommendationListener recommendationListener)
    {
        Gson gson= new Gson();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                URL + text,
                null,
                response -> {
                    List<RecommendationModel> recommendationModels = Arrays.asList(gson.fromJson(response.toString(),
                            RecommendationModel[].class));
                    Log.d("Recommendation service",recommendationModels.toString());
                    recommendationListener.onResponse(recommendationModels);
                },
                error -> {
                    Log.d("Recommendation service","Error "+ error);
                });
        request.setShouldCache(false);
        VolleySingleton.newRequestQueue(context).add(
                request
        );
    }
}
