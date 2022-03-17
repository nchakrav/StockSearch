package com.nikhilchakravartula.stocksearch.services;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.nikhilchakravartula.stocksearch.constants.Constants;
import com.nikhilchakravartula.stocksearch.models.CompanyEarningModel;
import com.nikhilchakravartula.stocksearch.models.SuggestionModel;
import com.nikhilchakravartula.stocksearch.network.VolleySingleton;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompanyEarningService {
    private static final String URL =
            Constants.BASE_URL + "companyearning?ticker=";
    public static interface CompanyEarningListener
    {
        void onResponse(List<CompanyEarningModel> companyEarningModels);
        void onError(String error);
    };


    public static void getCompanyEarnings(Context context,
                                      String text,
                                      CompanyEarningListener companyEarningListener)
    {
        Gson gson= new Gson();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                URL + text,
                null,
                response -> {
                    List<CompanyEarningModel> companyEarningModels = Arrays.asList(gson.fromJson(response.toString(),
                            CompanyEarningModel[].class));
                    Log.d("CompanyService",companyEarningModels.toString());
                    companyEarningListener.onResponse(companyEarningModels);
                },
                error -> {
                    Log.d("CompanyService","Error "+ error);
                });
        request.setShouldCache(false);
        VolleySingleton.newRequestQueue(context).add(
                request
        );
    }
}
