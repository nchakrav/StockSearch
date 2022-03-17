package com.nikhilchakravartula.stocksearch.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

import android.app.SearchManager;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.nikhilchakravartula.stocksearch.R;
import com.nikhilchakravartula.stocksearch.constants.Constants;
import com.nikhilchakravartula.stocksearch.models.CompanyEarningModel;
import com.nikhilchakravartula.stocksearch.models.FavoriteStockModel;
import com.nikhilchakravartula.stocksearch.models.NewsModel;
import com.nikhilchakravartula.stocksearch.models.QuoteModel;
import com.nikhilchakravartula.stocksearch.models.RecommendationModel;
import com.nikhilchakravartula.stocksearch.models.SentimentModel;
import com.nikhilchakravartula.stocksearch.models.StockModel;
import com.nikhilchakravartula.stocksearch.models.StockOverviewModel;
import com.nikhilchakravartula.stocksearch.services.CompanyEarningService;
import com.nikhilchakravartula.stocksearch.services.NewsService;
import com.nikhilchakravartula.stocksearch.services.QuotesService;
import com.nikhilchakravartula.stocksearch.services.RecommendationService;
import com.nikhilchakravartula.stocksearch.services.SentimentService;
import com.nikhilchakravartula.stocksearch.services.StockOverviewService;

import java.io.Serializable;
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


        QuotesService.getQuotes(this, ticker, new QuotesService.QuotesListener() {
            @Override
            public void onResponse(QuoteModel quoteModel) {
                setQuoteModel(quoteModel);
                updateCount();
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

    private void bindViews()
    {

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