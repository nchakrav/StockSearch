package com.nikhilchakravartula.stocksearch.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.nikhilchakravartula.stocksearch.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoricalGraphFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoricalGraphFragment extends Fragment {

    public static final String TICKER_KEY = "ticker";
    public static final String COLOR_KEY = "color";

    private String ticker;
    private String color;

    public HistoricalGraphFragment() {
        super(R.layout.fragment_webview);
    }

    public static HistoricalGraphFragment newInstance(String ticker, String color) {
        HistoricalGraphFragment fragment = new HistoricalGraphFragment();
        Bundle args = new Bundle();
        args.putString(TICKER_KEY, ticker);
        args.putString(COLOR_KEY, color);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ticker = getArguments().getString(TICKER_KEY);
            color = getArguments().getString(COLOR_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_webview, container, false);
                WebView lineGraphWebView = (WebView) rootView.findViewById(R.id.webview);
        WebSettings lineGraphWebViewSettings = lineGraphWebView.getSettings();
        lineGraphWebViewSettings.setJavaScriptEnabled(true);
        lineGraphWebViewSettings.setDomStorageEnabled(true);
        lineGraphWebViewSettings.setLoadWithOverviewMode(true);
        lineGraphWebViewSettings.setUseWideViewPort(true);
        lineGraphWebViewSettings.setBuiltInZoomControls(true);
        lineGraphWebViewSettings.setDisplayZoomControls(false);
        lineGraphWebViewSettings.setSupportZoom(true);
        lineGraphWebViewSettings.setDefaultTextEncodingName("utf-8");

        String lineGraphWebViewContents = getString(R.string.stock_history_graph_html);
        lineGraphWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                String func_call = "loadHistoricalGraph(\"" + ticker + "\", \"" + color +"\")";
                lineGraphWebView.evaluateJavascript("javascript:" + func_call, null);

            }
        });
        lineGraphWebView.loadDataWithBaseURL("blarg://ignored", lineGraphWebViewContents, "text/html", "utf-8", "");

        return rootView;
    }
}