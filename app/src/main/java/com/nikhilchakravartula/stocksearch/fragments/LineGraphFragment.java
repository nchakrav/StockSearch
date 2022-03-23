package com.nikhilchakravartula.stocksearch.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nikhilchakravartula.stocksearch.R;
import com.nikhilchakravartula.stocksearch.adapters.GraphPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LineGraphFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LineGraphFragment extends Fragment {

    public static final String TICKER_KEY = "ticker";
    public static final String TIMESTAMP_KEY = "timestamp";
    public static final String COLOR_KEY = "color";

    private String ticker;
    private int timestamp;
    private String color;

    public LineGraphFragment() {
        super(R.layout.fragment_webview);
    }

    public static LineGraphFragment newInstance(String ticker, int timestamp, String color) {
        LineGraphFragment fragment = new LineGraphFragment();
        Bundle args = new Bundle();
        args.putString(TICKER_KEY, ticker);
        args.putInt(TIMESTAMP_KEY, timestamp);
        args.putString(COLOR_KEY, color);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ticker = getArguments().getString(TICKER_KEY);
            timestamp = getArguments().getInt(TIMESTAMP_KEY);
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

        String lineGraphWebViewContents = getString(R.string.stock_line_graph_html);
        lineGraphWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                String func_call = "loadLineGraph(\"" + ticker + "\", " + timestamp + ", \"" + color +"\")";
                lineGraphWebView.evaluateJavascript("javascript:" + func_call, null);

            }
        });
        lineGraphWebView.loadDataWithBaseURL("blarg://ignored", lineGraphWebViewContents, "text/html", "utf-8", "");

        return rootView;
    }

}