package com.nikhilchakravartula.stocksearch.services;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.nikhilchakravartula.stocksearch.constants.Constants;
import com.nikhilchakravartula.stocksearch.models.QuoteModel;
import com.nikhilchakravartula.stocksearch.models.SentimentModel;
import com.nikhilchakravartula.stocksearch.models.SuggestionModel;
import com.nikhilchakravartula.stocksearch.network.VolleySingleton;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SentimentService {
    private static final String URL =
            Constants.BASE_URL + "sentiment?ticker=";
    public static interface SentimentListener
    {
        void onResponse(Map<String,List<SentimentModel>> sentimentModels);
        void onError(String error);
    };


    public static void getSentiments(Context context,
                                 String text,
                                 SentimentListener sentimentListener)
    {
        Gson gson = new Gson();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                URL + text,
                null,
                response -> {
                    Map<String,List<SentimentModel>> sentimentModels = new HashMap<>();
                    try
                    {
                        JSONArray reddit =  response.getJSONArray("reddit");
                        JSONArray twitter =  response.getJSONArray("twitter");
                        if(reddit!=null ) {
                            sentimentModels.put("reddit",new ArrayList<SentimentModel>());
                            for (int i = 0; i < reddit.length(); i++) {
                                sentimentModels.get("reddit").add(gson.fromJson(reddit.getJSONObject(i).toString(), SentimentModel.class));
                            }
                        }
                        if(twitter!=null)
                        {
                            sentimentModels.put("twitter",new ArrayList<SentimentModel>());
                            for (int i = 0; i < twitter.length(); i++) {
                                sentimentModels.get("twitter").add(gson.fromJson(twitter.getJSONObject(i).toString(), SentimentModel.class));
                            }
                        }
                    }
                    catch(Exception e)
                    {

                    }
                    Log.d("Sentiment Service",sentimentModels.toString());
                    sentimentListener.onResponse(sentimentModels);
                },
                error -> {
                    Log.d("Sentiment Service","Error "+ error);
                });
        request.setShouldCache(false);
        VolleySingleton.newRequestQueue(context).add(
                request
        );
    }
}
