package com.nikhilchakravartula.stocksearch.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.nikhilchakravartula.stocksearch.R;
import com.nikhilchakravartula.stocksearch.adapters.GraphPagerAdapter;
import com.nikhilchakravartula.stocksearch.constants.Constants;
import com.nikhilchakravartula.stocksearch.fragments.LineGraphFragment;
import com.nikhilchakravartula.stocksearch.models.CompanyEarningModel;
import com.nikhilchakravartula.stocksearch.models.NewsModel;
import com.nikhilchakravartula.stocksearch.models.QuoteModel;
import com.nikhilchakravartula.stocksearch.models.RecommendationModel;
import com.nikhilchakravartula.stocksearch.models.SentimentModel;
import com.nikhilchakravartula.stocksearch.models.StockOverviewModel;
import com.nikhilchakravartula.stocksearch.services.CompanyEarningService;
import com.nikhilchakravartula.stocksearch.services.NewsService;
import com.nikhilchakravartula.stocksearch.services.QuotesService;
import com.nikhilchakravartula.stocksearch.services.RecommendationService;
import com.nikhilchakravartula.stocksearch.services.SentimentService;
import com.nikhilchakravartula.stocksearch.services.StockOverviewService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchResultsActivity extends AppCompatActivity {

    private boolean isFavorite;
    private ConstraintLayout progressBar;
    private NestedScrollView scrollView;
    String ticker;
    int count = 0;

    List<CompanyEarningModel> companyEarningModels;
    List<NewsModel> newsModels;
    QuoteModel quoteModel;
    List<RecommendationModel> recommendationModels;
    Map<String,List<SentimentModel>> sentimentModels;
    StockOverviewModel stockOverviewModel;
    ArrayList<List<Double>> timestampPriceLastChartPrices;

    private GraphPagerAdapter pagerAdapter;
    private ViewPager2 viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorites,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                Intent data = new Intent();
                Bundle bundle = new Bundle();
                bundle.putBoolean("isFavorite",isFavorite);
                bundle.putString("ticker",stockOverviewModel.getTicker());
                bundle.putDouble("c",quoteModel.getC());
                bundle.putDouble("d",quoteModel.getD());
                bundle.putString("name",stockOverviewModel.getName());
                data.putExtras(bundle);
                setResult(RESULT_OK,data);
                finish();
                return true;
            case R.id.favorite:
                isFavorite = (isFavorite == true)?false:true;
                if(isFavorite) {
                    item.setIcon(R.drawable.ic_baseline_star_24);
                }
                else{
                    item.setIcon(R.drawable.ic_baseline_star_border_24);
                }
        }
        return super.onOptionsItemSelected(item);
    }

    private void init()
    {
        isFavorite = false;
        progressBar = findViewById(R.id.detail_progress_bar_layout);
        scrollView = findViewById(R.id.nested_scroll_view);
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        ticker = getIntent().getStringExtra(Constants.QUERY);
        setBackButton();
        setTitle();
        search();


    }
    private void setBackButton()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void setTitle() {
        getSupportActionBar().setTitle(ticker);
    }
    private void search()
    {
        count = 0;
        CompanyEarningService.getCompanyEarnings(this, ticker, new CompanyEarningService.CompanyEarningListener() {
            @Override
            public void onResponse(List<CompanyEarningModel> companyEarningModels) {
                setCompanyEarningModels(companyEarningModels);
                updateCount();
            }

            @Override
            public void onError(String error) {

            }
        });
        NewsService.getNews(this, ticker, new NewsService.NewsListener() {
            @Override
            public void onResponse(List<NewsModel> newsModels) {
                setNewsModels(newsModels);
                updateCount();
            }

            @Override
            public void onError(String error) {

            }
        });


        AppCompatActivity ctx = this;

        QuotesService.getQuotes(this, ticker, new QuotesService.QuotesListener() {
            @Override
            public void onResponse(QuoteModel quoteModel) {
                setQuoteModel(quoteModel);
                updateCount();


//                String timestamp = String.valueOf(quoteModel.getTimestamp());
//                LastChartPricesService.getLastChartPrices(ctx, ticker, timestamp, new LastChartPricesService.LastChartPricesListener() {
//                    @Override
//                    public void onResponse(ArrayList<List<Double>> lastChartPrices) {
//                        timestampPriceLastChartPrices = lastChartPrices;
//                        updateCount();
//                    }
//
//                    @Override
//                    public void onError(String error) {
//
//                    }
//                });
            }

            @Override
            public void onError(String error) {

            }
        });

        RecommendationService.getRecommendations(this, ticker, new RecommendationService.RecommendationListener() {
            @Override
            public void onResponse(List<RecommendationModel> recommendationModels) {
                setRecommendationModels(recommendationModels);
                updateCount();
            }

            @Override
            public void onError(String error) {

            }
        });

        SentimentService.getSentiments(this,ticker,new SentimentService.SentimentListener() {
            @Override
            public void onResponse(Map<String,List<SentimentModel>> sentimentModels) {
                setSentimentModels(sentimentModels);
                updateCount();
            }

            @Override
            public void onError(String error) {

            }
        });


        StockOverviewService.getStockOverview(this, ticker, new StockOverviewService.StockOverviewListener() {
            @Override
            public void onResponse(StockOverviewModel stockOverviewModel) {
                setStockOverviewModel(stockOverviewModel);
                updateCount();
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void bindViews() {

        //Summary
        ((TextView)findViewById(R.id.detail_ticker)).setText(ticker);
        ((TextView)findViewById(R.id.detail_company_name)).setText(stockOverviewModel.getName());
        ((TextView)findViewById(R.id.detail_stock_price)).setText(""+quoteModel.getC());
        ((TextView)findViewById(R.id.detail_change_value)).setText(quoteModel.getD()+"");

        //Portfolio
        ((TextView)findViewById(R.id.detail_shares_owned)).setText("You have "+5+" shares of "+stockOverviewModel.getTicker());


        //Stats
        //@Todo Check volume
        ((TextView)findViewById(R.id.detail_volume)).setText("Dummy volume");

        ((TextView)findViewById(R.id.detail_low_price)).setText(quoteModel.getL()+"");
        //@Todo
        ((TextView)findViewById(R.id.detail_bid_price)).setText("Dummy bid price");

        ((TextView)findViewById(R.id.detail_current_price)).setText(quoteModel.getC()+"");
        ((TextView)findViewById(R.id.detail_open_price)).setText(quoteModel.getO()+" ");
        ((TextView)findViewById(R.id.detail_high_price)).setText(quoteModel.getH()+" ");
        //@Todo
        ((TextView)findViewById(R.id.detail_mid_price)).setText("Dummy mid price");

        //About
        //@Todo
        ((TextView)findViewById(R.id.detail_toggle_about)).setText(stockOverviewModel.getWeburl());

        //News

        scrollView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        // Hicharts
//        HIChartView chartView = (HIChartView) findViewById(R.id.hc);
//
//        HIOptions options = new HIOptions();
//        HITitle title = new HITitle();
////        title.setText(ticker + " Hourly Price Variation");
//        title.setText("");
//        options.setTitle(title);
//
//        HILegend legend = new HILegend();
//        legend.setEnabled(false);
//        options.setLegend(legend);
//
//        HIChart chart = new HIChart();
//        chart.setBackgroundColor(HIColor.initWithHexValue("f7f7f7"));
//        chart.setType("stockChart");
//
//
//        HILine series = new HILine();
//        series.setData(timestampPriceLastChartPrices);
//        series.setName(ticker);
        String chart_color;
        if(quoteModel.getChange() < 0) {
            chart_color = "red";
        } else if (quoteModel.getChange() > 0) {
            chart_color = "green";
        } else {
            chart_color = "grey";
        }
//
//        series.setColor(HIColor.initWithName(chart_color));
//
//        HICredits credits = new HICredits();
//        credits.setEnabled(false);
//        options.setCredits(credits);
//
//        HIExporting exporting = new HIExporting();
//        exporting.setEnabled(false);
//        options.setExporting(exporting);
//
//        HILabel label = new HILabel();
//        label.setEnabled(false);
//        series.setLabel(label);
//
////        series.setKeys(keys);
//
//        series.setYAxisDescription("");
//
//        options.setSeries(new ArrayList<HISeries>(Collections.singletonList(series)));
//        options.setChart(chart);
//        chartView.setOptions(options);
        Bundle fragment_bundle = new Bundle();
        fragment_bundle.putString(LineGraphFragment.TICKER_KEY, ticker);
        fragment_bundle.putInt(LineGraphFragment.TIMESTAMP_KEY, quoteModel.getTimestamp());
        fragment_bundle.putString(LineGraphFragment.COLOR_KEY, chart_color);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new GraphPagerAdapter(this, fragment_bundle);
        viewPager.setAdapter(pagerAdapter);

        class ConfigStrategy implements TabLayoutMediator.TabConfigurationStrategy {

            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position == 0) tab.setIcon(R.drawable.ic_line);
                else tab.setIcon(R.drawable.ic_bar);
            }
        };

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                new ConfigStrategy()
        ).attach();

        // recommendation trends
        WebView recommendationTrendsWebView = (WebView) findViewById(R.id.recommendation_trends);
        WebSettings recommendationGraphSettings = recommendationTrendsWebView.getSettings();
        recommendationGraphSettings.setJavaScriptEnabled(true);
        recommendationGraphSettings.setDomStorageEnabled(true);
        recommendationGraphSettings.setLoadWithOverviewMode(true);
        recommendationGraphSettings.setUseWideViewPort(true);
        recommendationGraphSettings.setBuiltInZoomControls(true);
        recommendationGraphSettings.setDisplayZoomControls(false);
        recommendationGraphSettings.setSupportZoom(true);
        recommendationGraphSettings.setDefaultTextEncodingName("utf-8");

        String recommendationTrendsContent = getString(R.string.recommendation_trends_html);
        Log.d("cnt", recommendationTrendsContent);
        recommendationTrendsWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                String func_call = "loadTrends(\"" + ticker + "\")";
                recommendationTrendsWebView.evaluateJavascript("javascript:" + func_call, null);

            }
        });
        recommendationTrendsWebView.loadDataWithBaseURL("blarg://ignored", recommendationTrendsContent, "text/html", "utf-8", "");

        // eps
        WebView epsWebView = (WebView) findViewById(R.id.eps);
        WebSettings epsSettings = epsWebView.getSettings();
        epsSettings.setJavaScriptEnabled(true);
        epsSettings.setDomStorageEnabled(true);
        epsSettings.setLoadWithOverviewMode(true);
        epsSettings.setUseWideViewPort(true);
        epsSettings.setBuiltInZoomControls(true);
        epsSettings.setDisplayZoomControls(false);
        epsSettings.setSupportZoom(true);
        epsSettings.setDefaultTextEncodingName("utf-8");

        String epsContent = getString(R.string.eps_html);
        Log.d("cnt", epsContent);
        epsWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                String func_call = "loadEPS(\"" + ticker + "\")";
                epsWebView.evaluateJavascript("javascript:" + func_call, null);

            }
        });
        epsWebView.loadDataWithBaseURL("blarg://ignored", epsContent, "text/html", "utf-8", "");

    }
    private void updateCount()
    {
        count++;
        if(count==6)
        {
            bindViews();
        }
    }
    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public List<CompanyEarningModel> getCompanyEarningModels() {
        return companyEarningModels;
    }

    public List<NewsModel> getNewsModels() {
        return newsModels;
    }

    public void setCompanyEarningModels(List<CompanyEarningModel> companyEarningModels) {
        this.companyEarningModels = companyEarningModels;
    }

    public void setNewsModels(List<NewsModel> newsModels) {
        this.newsModels = newsModels;
    }

    public QuoteModel getQuoteModel() {
        return quoteModel;
    }

    public void setQuoteModel(QuoteModel quoteModel) {
        this.quoteModel = quoteModel;
    }

    public List<RecommendationModel> getRecommendationModels() {
        return recommendationModels;
    }

    public void setRecommendationModels(List<RecommendationModel> recommendationModels) {
        this.recommendationModels = recommendationModels;
    }

    public Map<String,List<SentimentModel>> getSentimentModels() {
        return sentimentModels;
    }

    public void setSentimentModels(Map<String,List<SentimentModel>> sentimentModels) {
        this.sentimentModels = sentimentModels;
    }

    public StockOverviewModel getStockOverviewModel() {
        return stockOverviewModel;
    }

    public void setStockOverviewModel(StockOverviewModel stockOverviewModel) {
        this.stockOverviewModel = stockOverviewModel;
    }

}