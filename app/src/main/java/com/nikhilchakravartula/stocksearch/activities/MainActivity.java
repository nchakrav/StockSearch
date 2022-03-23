package com.nikhilchakravartula.stocksearch.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nikhilchakravartula.stocksearch.models.QuoteModel;
import com.nikhilchakravartula.stocksearch.services.QuotesService;
import com.nikhilchakravartula.stocksearch.storage.Storage;
import com.nikhilchakravartula.stocksearch.utils.OnDetailsClickListener;
import com.nikhilchakravartula.stocksearch.R;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.nikhilchakravartula.stocksearch.constants.Constants;
import com.nikhilchakravartula.stocksearch.models.FavoriteStockModel;
import com.nikhilchakravartula.stocksearch.models.PortfolioStockModel;
import com.nikhilchakravartula.stocksearch.models.SuggestionModel;
import com.nikhilchakravartula.stocksearch.sections.favorite.FavoriteSection;
import com.nikhilchakravartula.stocksearch.sections.portfolio.PortfolioSection;
import com.nikhilchakravartula.stocksearch.services.SuggestionsService;
import com.nikhilchakravartula.stocksearch.swipe.SwipeDragCallbackHelper;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;


public class MainActivity extends AppCompatActivity {

    //Views
    Context context;
    SearchView searchView;
    RecyclerView recyclerView;
    SearchView.SearchAutoComplete searchAutoCompleteView;

    //Adapters
    ArrayAdapter<SuggestionModel> suggestionsAdapter;
    SectionedRecyclerViewAdapter sectionAdapter;

    //Sections
    FavoriteSection favoriteSection;
    PortfolioSection portfolioSection;


    //For search
    ActivityResultLauncher<Intent> searchActivityLauncher;

    Storage storage ;

    //Live data
    HashMap<String,QuoteModel> liveQuotes;

    public static Double moneyInWallet = -1.00;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_StockSearch);
        super.onCreate(savedInstanceState);
        //For getting result from SearchResultsActivity. Need to do this before setting Content View
        initSearchResultActivity();
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        initSearch(menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        storage.setFavorites(favoriteSection.getItemList());
        storage.setPortfolio(portfolioSection.getItemList());

    }

    @Override
    protected void onResume() {
        super.onResume();
        portfolioSection.removeAll();
        favoriteSection.removeAll();
        portfolioSection.addAll(storage.getPortfolio());
        favoriteSection.addAll(storage.getFavorites());
        MainActivity.moneyInWallet = storage.getMoneyInWallet();
        sectionAdapter.notifyDataSetChanged();
    }

    private void initSections()
    {
        sectionAdapter = new SectionedRecyclerViewAdapter();
        portfolioSection = new PortfolioSection(new OnDetailsClickListener() {
            @Override
            public void onClick(String ticker) {
                searchView.setQuery(ticker,true);
            }
        });
        favoriteSection = new FavoriteSection(new OnDetailsClickListener() {
            @Override
            public void onClick(String ticker) {
                searchView.setQuery(ticker,true);
            }
        });
        sectionAdapter.addSection(portfolioSection);
        sectionAdapter.addSection(favoriteSection);
        recyclerView = (RecyclerView) findViewById(R.id.rcl_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(sectionAdapter);
    }
    private void initDragSwipe()
    {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeDragCallbackHelper(context,
                portfolioSection,
                favoriteSection,
                sectionAdapter,
                0,
                0));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
    private void initFooter()
    {
        TextView finnhub = (TextView) findViewById(R.id.finnhub);
        finnhub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.FINNHUB_URL));
                startActivity(intent);
            }
        });
    }

    private void initAutoRefresh()
    {
        // creating timer task, timer
        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                List<PortfolioStockModel> portfolioStockModels = portfolioSection.getItemList();
                List<FavoriteStockModel> favoriteStockModels = favoriteSection.getItemList();
                for(PortfolioStockModel portfolioStockModel:portfolioStockModels)
                {
                    QuotesService.getQuotes(context, portfolioStockModel.getTicker(), new QuotesService.QuotesListener() {
                        @Override
                        public void onResponse(QuoteModel quoteModel) {
                            Log.d("Changed",portfolioStockModel.getTicker());
                            portfolioStockModel.setStockChange(quoteModel.getD());
                            portfolioStockModel.setStockPrice(quoteModel.getC());
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
                }
                for(FavoriteStockModel favoriteStockModel:favoriteStockModels)
                {
                    QuotesService.getQuotes(context, favoriteStockModel.getTicker(), new QuotesService.QuotesListener() {
                        @Override
                        public void onResponse(QuoteModel quoteModel) {
                            Log.d("Changed",favoriteStockModel.getTicker());
                            favoriteStockModel.setStockChange(quoteModel.getD());
                            favoriteStockModel.setStockPrice(quoteModel.getC());
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
                }
                Log.d("Main","Resetting portfolio notifying ds changed");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(sectionAdapter!=null)
                            sectionAdapter.notifyDataSetChanged();
                    }
                });
            };
        };
        t.scheduleAtFixedRate(tt,0,15000);
    }
    void init() {
        context = this;
        storage = Storage.getStorage(getApplicationContext());
//        getSupportActionBar().setSubtitle("9 March 2022");
        suggestionsAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_dropdown_item_1line,
                new ArrayList<SuggestionModel>());
        TextView date = (TextView) findViewById(R.id.today_date);
        date.setText(new SimpleDateFormat("dd MMMM yyyy").format(new Date()));
        initSections();
        initDragSwipe();
        initFooter();
        initAutoRefresh();

    }

    void initSearchResultActivity()
    {
        searchActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Log.d(""+result.getResultCode(),""+Activity.RESULT_OK);
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            Bundle bundle = data.getExtras();
//                            updateFavoriteSection(bundle);
//                            updatePortFolioSection(bundle);
                        }
                    }
                });
    }


    private void initSearch(Menu menu) {

        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchAutoCompleteView =
                searchView.findViewById(androidx.appcompat.R.id.search_src_text);

        searchAutoCompleteView.setAdapter(suggestionsAdapter);


        searchAutoCompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString = ((SuggestionModel) adapterView.getItemAtPosition(itemIndex)).getTicker();
                searchAutoCompleteView.setText("" + queryString);
                Toast.makeText(MainActivity.this, "you clicked " + queryString, Toast.LENGTH_LONG).show();
                searchView.setQuery(queryString, true);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

//                PortfolioStockModel portfolioStockModel = new PortfolioStockModel();
//                FavoriteStockModel favoriteStockModel = new FavoriteStockModel();
//                boolean isFavorite = false;
//
//                int pos = portfolioSection.find(query);
//
//                if(pos!=-1)
//                {
//                    portfolioStockModel = portfolioSection.getItemList().get(pos);
//                }
//
//                pos = favoriteSection.find(query);
//                if(pos!=-1)
//                {
//                    favoriteStockModel = favoriteSection.getItemList().get(pos);
//                    isFavorite = true;
//                }

                Log.d("Action bar", "Clicked on search item");
                Intent i = new Intent(context, SearchResultsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.QUERY,query.toUpperCase());
//                bundle.putString("favoriteStockModel",new Gson().toJson(favoriteStockModel));
//                bundle.putBoolean("isFavorite",isFavorite);
//                bundle.putString("portfolioStockModel",new Gson().toJson(portfolioStockModel));
                i.putExtras(bundle);
                //searchActivityLauncher.launch(i);
                startActivity(i);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                SuggestionsService.getSuggestions(context,
                        newText,
                        new SuggestionsService.SuggestionListener() {
                            @Override
                            public void onResponse(List<SuggestionModel> suggestionModels) {
                                Log.d("Main activity suggestions", "" + suggestionModels);
                                suggestionsAdapter.clear();
                                suggestionsAdapter.addAll(suggestionModels);
                            }
                            @Override
                            public void onError(String error) {
                                Log.d("Error", "Suggestions failed");
                            }
                        });
                return true;
            }

        });
    }
    private void updateFavoriteSection(Bundle bundle)
    {
        boolean isFavorite = bundle.getBoolean("isFavorite");
        if(isFavorite) {
            FavoriteStockModel favoriteStockModel = new Gson().fromJson(bundle.getString("favoriteStockModel"),
                    (Type)FavoriteStockModel.class);
            if(favoriteSection.find(favoriteStockModel.getTicker())==-1) {
                favoriteSection.add(favoriteStockModel);
                sectionAdapter.notifyItemInserted(sectionAdapter.getItemCount() - 1);
            }
        }
        else
        {
            int offset = favoriteSection.findAndRemove(bundle.getString("ticker"));
            if(offset!=-1)
            {
                int pos = portfolioSection.getSectionItemsTotal()+1+offset;
                sectionAdapter.notifyItemRemoved(pos);
            }
        }
    }
    private void updatePortFolioSection(Bundle bundle) {
        Log.d("portfolio stock model",bundle.getString("portfolioStockModel"));
        PortfolioStockModel portfolioStockModel = new Gson().fromJson(bundle.getString("portfolioStockModel"),
        PortfolioStockModel.class);
        if(portfolioStockModel.getNumStocksInvested()==0.0)
        {
            int offset = portfolioSection.findAndRemove(portfolioStockModel.getTicker());
            Log.d("update Portfolio ",bundle.getString("ticker")+" , "+offset+" ");
            if(offset!=-1) {
                sectionAdapter.notifyItemRemoved(offset+1);
            }
        }
        else
        {
            int offset =portfolioSection.find(portfolioStockModel.getTicker());
            if(offset==-1) {
                portfolioSection.add(portfolioStockModel);
                Log.d("Notify after insert",portfolioSection.getSectionItemsTotal()+"");
                sectionAdapter.notifyItemInserted(portfolioSection.getSectionItemsTotal());
            }
            else
            {
                Log.d("Total",portfolioSection.getContentItemsTotal()+"");
                Log.d("Updating",offset+" with "+portfolioStockModel.getNumStocksInvested());
                Log.d("section adapter",sectionAdapter.getItemCount()+"");
                portfolioSection.update(offset,portfolioStockModel);
                sectionAdapter.notifyItemChanged(offset+1);
            }
        }
    }

}

