package com.nikhilchakravartula.stocksearch.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nikhilchakravartula.stocksearch.R;
import com.nikhilchakravartula.stocksearch.adapters.companypeers.CompanyPeersAdapter;
import com.nikhilchakravartula.stocksearch.adapters.news.NewsAdapter;
import com.nikhilchakravartula.stocksearch.constants.Constants;
import com.nikhilchakravartula.stocksearch.models.CompanyEarningModel;
import com.nikhilchakravartula.stocksearch.models.FavoriteStockModel;
import com.nikhilchakravartula.stocksearch.models.NewsModel;
import com.nikhilchakravartula.stocksearch.models.PortfolioStockModel;
import com.nikhilchakravartula.stocksearch.models.QuoteModel;
import com.nikhilchakravartula.stocksearch.models.RecommendationModel;
import com.nikhilchakravartula.stocksearch.models.SentimentModel;
import com.nikhilchakravartula.stocksearch.models.StockOverviewModel;
import com.nikhilchakravartula.stocksearch.services.CompanyEarningService;
import com.nikhilchakravartula.stocksearch.services.CompanyPeersService;
import com.nikhilchakravartula.stocksearch.services.NewsService;
import com.nikhilchakravartula.stocksearch.services.QuotesService;
import com.nikhilchakravartula.stocksearch.services.RecommendationService;
import com.nikhilchakravartula.stocksearch.services.SentimentService;
import com.nikhilchakravartula.stocksearch.services.SentimentSummary;
import com.nikhilchakravartula.stocksearch.services.StockOverviewService;
import com.nikhilchakravartula.stocksearch.storage.Storage;
import com.nikhilchakravartula.stocksearch.trade.SuccessDialog;
import com.nikhilchakravartula.stocksearch.trade.TradeDialog;
import com.nikhilchakravartula.stocksearch.utils.Formatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveAction;

public class SearchResultsActivity extends AppCompatActivity {

    private boolean isFavorite;
    private ConstraintLayout progressBar;
    private NestedScrollView scrollView;
    String ticker;
    int count = 0;
    Button tradeBtn;

    MenuItem favoriteView;
    List<CompanyEarningModel> companyEarningModels;
    List<NewsModel> newsModels;
    QuoteModel quoteModel;
    List<RecommendationModel> recommendationModels;
    Map<String,SentimentSummary> sentimentSummaryMap;
    List<String> companyPeers;
    StockOverviewModel stockOverviewModel;

    FavoriteStockModel favoriteStockModel;
    PortfolioStockModel portfolioStockModel;

    Bundle bundleFromMain;
    Storage storage;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorites,menu);
        favoriteView = (MenuItem)menu.findItem(R.id.favorite);
        setFavorite(isFavorite);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                Intent data = new Intent();
                Bundle bundle = new Bundle();

                if(isFavorite)
                {
                    favoriteStockModel.setTicker(stockOverviewModel.getTicker());
                    favoriteStockModel.setStockChange(quoteModel.getD());
                    favoriteStockModel.setCompanyName(stockOverviewModel.getName());
                    favoriteStockModel.setStockPrice(quoteModel.getC());

                    bundle.putString("favoriteStockModel",new Gson().toJson(favoriteStockModel));
                }
                bundle.putBoolean("isFavorite",isFavorite);


                portfolioStockModel.setStockChange(quoteModel.getD());
                portfolioStockModel.setTicker(stockOverviewModel.getTicker());
                portfolioStockModel.setStockPrice(quoteModel.getC());

                bundle.putString("portfolioStockModel",new Gson().toJson(portfolioStockModel));

                data.putExtras(bundle);
                setResult(RESULT_OK,data);



                if(isFavorite)
                {
                    if(!storage.hasFavorite(favoriteStockModel))
                        storage.addFavorite(favoriteStockModel);
                }
                else
                {
                    storage.removeFavorite(favoriteStockModel);
                }
                storage.removePortfolio(portfolioStockModel);
                if(portfolioStockModel.getNumStocksInvested()!=0.0)
                {
                    storage.addPortfolio(portfolioStockModel);
                }
                storage.setMoneyInWallet(MainActivity.moneyInWallet);

                finish();
                return true;
            case R.id.favorite:
                setFavorite( (isFavorite == true)?false:true );

        }
        return super.onOptionsItemSelected(item);
    }

    private void init()
    {
        context = this;
        progressBar = findViewById(R.id.detail_progress_bar_layout);
        scrollView = findViewById(R.id.nested_scroll_view);
        storage = Storage.getStorage(getApplicationContext());
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        bundleFromMain = getIntent().getExtras();
        ticker = bundleFromMain.getString(Constants.QUERY);

        List<FavoriteStockModel> favoriteStockModels = storage.getFavorites();
        List<PortfolioStockModel> portfolioStockModels = storage.getPortfolio();
        this.favoriteStockModel = new FavoriteStockModel();
        this.portfolioStockModel = new PortfolioStockModel();

        for(int i=0;i<portfolioStockModels.size();i++) {
            PortfolioStockModel portfolioStockModel1 = portfolioStockModels.get(i);
            if (portfolioStockModel1.getTicker().equalsIgnoreCase(ticker)) {
                portfolioStockModel = portfolioStockModel1;
            }
        }
        isFavorite = false;
        for(int i=0;i<favoriteStockModels.size();i++) {
            FavoriteStockModel favoriteStockModel1 = favoriteStockModels.get(i);
            if (favoriteStockModel1.getTicker().equalsIgnoreCase(ticker)) {
                favoriteStockModel = favoriteStockModel1;
                isFavorite = true;
                break;
            }
        }

        setFavorite(isFavorite);
//        portfolioStockModel = new Gson().fromJson(bundleFromMain.getString("portfolioStockModel"), PortfolioStockModel.class);


        tradeBtn = findViewById(R.id.detail_trade_button);

        tradeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TradeDialog tradeDialog = new TradeDialog(
                        stockOverviewModel.getTicker(),
                        portfolioStockModel.getNumStocksInvested(),
                        quoteModel.getC(),
                        new TradeDialog.OnActionListener() {
                            @Override
                            public void onBuy(Double numSharesBought) {
                                portfolioStockModel.setNumStocksInvested(portfolioStockModel.getNumStocksInvested()+ numSharesBought);
                                portfolioStockModel.setCost(portfolioStockModel.getCost()+numSharesBought* quoteModel.getC());
                                MainActivity.moneyInWallet-=numSharesBought*quoteModel.getC();
                                Log.d(""+numSharesBought,"total bought");
                                SuccessDialog successDialog = new SuccessDialog(ticker,
                                        numSharesBought,
                                        "bought");
                                successDialog.show(getSupportFragmentManager(),"Buy Succeeded");
                                updatePortfolioView();

                            }

                            @Override
                            public void onSell(Double numSharesSold) {
                                Double avgCost = portfolioStockModel.getCost()/portfolioStockModel.getNumStocksInvested();
                                portfolioStockModel.setNumStocksInvested(portfolioStockModel.getNumStocksInvested()- numSharesSold);
                                portfolioStockModel.setCost(portfolioStockModel.getCost()-numSharesSold* avgCost);
                                MainActivity.moneyInWallet+=numSharesSold * avgCost;
                                SuccessDialog successDialog = new SuccessDialog(ticker,
                                        numSharesSold,
                                        "sold");
                                successDialog.show(getSupportFragmentManager(),"Sell Succeeded");
                                updatePortfolioView();
                                Log.d(""+numSharesSold,"sold");

                            }
                        });
                tradeDialog.show(getSupportFragmentManager(),"Trade");
            }
        });

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
                finish();
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
                finish();
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
                finish();
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
                finish();
            }
        });

        SentimentService.getSentiments(this,ticker,new SentimentService.SentimentListener() {
            @Override
            public void onResponse(Map<String, SentimentSummary> sentimentSummaryMap) {
                setSentimentSummaryMap(sentimentSummaryMap);
                updateCount();
            }

            @Override
            public void onError(String error) {
                finish();
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
                finish();
            }
        });

        CompanyPeersService.getCompanyPeers(this, ticker, new CompanyPeersService.CompanyPeersListener() {
            @Override
            public void onResponse(List<String> companyPeersList) {
                companyPeers = companyPeersList;
                updateCount();
            }

            @Override
            public void onError(String error) {
                finish();
            }
        });
    }

    private void updatePortfolioView()
    {

        portfolioStockModel.setStockPrice(quoteModel.getC());
        portfolioStockModel.setStockChange(quoteModel.getD());


//        if(portfolioStockModel.getNumStocksInvested()==0.0)
//        {
//            ((LinearLayoutCompat)findViewById(R.id.no_shares_view)).setVisibility(View.VISIBLE);
//            ((ConstraintLayout)findViewById(R.id.detail_portfolio_view)).setVisibility(View.GONE);
//        }
//        else
//        {
//
//            ((LinearLayoutCompat)findViewById(R.id.no_shares_view)).setVisibility(View.GONE);
//            ((ConstraintLayout)findViewById(R.id.detail_portfolio_view)).setVisibility(View.VISIBLE);
//        }
        //Portfolio
        ((TextView)findViewById(R.id.detail_shares_owned_value)).setText(Formatter.format(portfolioStockModel.getNumStocksInvested()));

        if(portfolioStockModel.getNumStocksInvested()!=0.0)
            ((TextView)findViewById(R.id.detail_avg_cost_value)).setText("$"+Formatter.format(portfolioStockModel.getCost()/portfolioStockModel.getNumStocksInvested()));
        else
            ((TextView)findViewById(R.id.detail_avg_cost_value)).setText("$"+Formatter.format(0.0));

        ((TextView)findViewById(R.id.detail_total_cost_value)).setText("$"+Formatter.format(portfolioStockModel.getCost()));
        Double change = 0.0;
        Double marketValue = 0.0;
        if(portfolioStockModel.getNumStocksInvested()!=0.0)
        {
            change = portfolioStockModel.getStockPrice()-portfolioStockModel.getCost()/portfolioStockModel.getNumStocksInvested();
            marketValue = portfolioStockModel.getStockPrice()*portfolioStockModel.getNumStocksInvested();
            Log.d("Change",""+portfolioStockModel.getStockPrice()+","+portfolioStockModel.getCost()+","+portfolioStockModel.getNumStocksInvested()+":"+change);

        }

        TextView changedTv = ((TextView)findViewById(R.id.detail_portfolio_change_value));
        TextView marketValueTv = ((TextView)findViewById(R.id.detail_market_value_value));

        changedTv.setText("$"+Formatter.format(change));
        marketValueTv.setText("$"+Formatter.format(marketValue));
        if(change>0)
        {
            changedTv.setTextColor(ContextCompat.getColor(context,R.color.green));
            marketValueTv.setTextColor(ContextCompat.getColor(context,R.color.green));
        }
        else if(change<0)
        {
            changedTv.setTextColor(ContextCompat.getColor(context,R.color.red));
            marketValueTv.setTextColor(ContextCompat.getColor(context,R.color.red));
        }
        else
        {
            changedTv.setTextColor(ContextCompat.getColor(context,R.color.black));
            marketValueTv.setTextColor(ContextCompat.getColor(context,R.color.black));
        }

    }

    private void updateSummaryView()
    {

        //Summary
        ((TextView)findViewById(R.id.detail_ticker)).setText(ticker);
        ((TextView)findViewById(R.id.detail_company_name)).setText(stockOverviewModel.getName());
        ((TextView)findViewById(R.id.detail_stock_price)).setText("$"+Formatter.format(quoteModel.getC()));
        ((TextView)findViewById(R.id.detail_change_value)).setText("$"+Formatter.format(quoteModel.getD()));
    }
    private void updateStatsView()
    {

        ((TextView)findViewById(R.id.detail_low_price_value)).setText("$"+Formatter.format(quoteModel.getLow()));
        ((TextView)findViewById(R.id.detail_open_price_value)).setText("$"+Formatter.format(quoteModel.getOpen()));
        ((TextView)findViewById(R.id.detail_high_price_value)).setText("$"+Formatter.format(quoteModel.getHigh()));
        ((TextView)findViewById(R.id.detail_prev_close_value)).setText("$"+Formatter.format(quoteModel.getPrevClose()));

    }
    private void updateAboutView()
    {
        //About
        //@Todo
        ((TextView)findViewById(R.id.ipo_start_date)).setText(stockOverviewModel.getStartDate());
        ((TextView)findViewById(R.id.industry)).setText(stockOverviewModel.getFinnhubIndustry());
        ((TextView)findViewById(R.id.webpage)).setText(stockOverviewModel.getWeburl());
        RecyclerView recyclerView =((RecyclerView)findViewById(R.id.company_peers));
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(new CompanyPeersAdapter(
                this,
                R.layout.company_peer,
                companyPeers
        ));
    }

    private void updateNewsView()
    {
        RecyclerView newsRcl = ((RecyclerView)findViewById(R.id.news_recycler_view));
        newsRcl.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        newsRcl.setAdapter(new NewsAdapter(newsModels));
    }

    private void updateSentimentsView()
    {
        SentimentSummary sentimentSummary = sentimentSummaryMap.getOrDefault("reddit",new SentimentSummary());
        ((TextView) findViewById(R.id.reddit_negative_mentions)).setText(""+sentimentSummary.getNegativeMentions());
        ((TextView) findViewById(R.id.reddit_positive_mentions)).setText(""+sentimentSummary.getPositiveMentions());
        ((TextView) findViewById(R.id.reddit_total_mentions)).setText(""+sentimentSummary.getMentions());
        sentimentSummary = sentimentSummaryMap.getOrDefault("twitter",new SentimentSummary());
        ((TextView) findViewById(R.id.twitter_negative_mentions)).setText(""+sentimentSummary.getNegativeMentions());
        ((TextView) findViewById(R.id.twitter_positive_mentions)).setText(""+sentimentSummary.getPositiveMentions());
        ((TextView) findViewById(R.id.twitter_total_mentions)).setText(""+sentimentSummary.getMentions());

    }
    private void bindViews()
    {

        updatePortfolioView();
        updateSummaryView();
        updateStatsView();
        updateSentimentsView();
        updateAboutView();
        updateNewsView();
        scrollView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);



    }
    private void updateCount()
    {
        count++;
        if(count==7)
        {
            bindViews();
        }
    }
    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {

        isFavorite = favorite;
        if(favoriteView == null)
            return;
        if(isFavorite) {
            favoriteView.setIcon(R.drawable.ic_baseline_star_24);
        }
        else{
            favoriteView.setIcon(R.drawable.ic_baseline_star_border_24);
        }
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

    public Map<String,SentimentSummary> getSentimentModels() {
        return sentimentSummaryMap;
    }

    public void setSentimentSummaryMap(Map<String,SentimentSummary> sentimentSummaryMap) {
        this.sentimentSummaryMap = sentimentSummaryMap;
    }

    public StockOverviewModel getStockOverviewModel() {
        return stockOverviewModel;
    }

    public void setStockOverviewModel(StockOverviewModel stockOverviewModel) {
        this.stockOverviewModel = stockOverviewModel;
    }

}