package com.nikhilchakravartula.stocksearch.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nikhilchakravartula.stocksearch.OnDetailsClickListener;
import com.nikhilchakravartula.stocksearch.R;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.nikhilchakravartula.stocksearch.constants.Constants;
import com.nikhilchakravartula.stocksearch.models.FavoriteStockModel;
import com.nikhilchakravartula.stocksearch.models.StockListModel;
import com.nikhilchakravartula.stocksearch.models.StockModel;
import com.nikhilchakravartula.stocksearch.models.SuggestionModel;
import com.nikhilchakravartula.stocksearch.sections.favorite.FavoriteSection;
import com.nikhilchakravartula.stocksearch.sections.portfolio.PortfolioSection;
import com.nikhilchakravartula.stocksearch.services.SuggestionsService;
import com.nikhilchakravartula.stocksearch.swipe.SwipeDragCallbackHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


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

    //Stock lists
    StockListModel portfolioStocks;
    StockListModel favoriteStocks;

    //For search
    ActivityResultLauncher<Intent> searchActivityLauncher;

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

    void init() {
        context = this;
        getSupportActionBar().setSubtitle("9 March 2022");
        suggestionsAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_dropdown_item_1line,
                new ArrayList<SuggestionModel>());
        sectionAdapter = new SectionedRecyclerViewAdapter();
        portfolioSection = new PortfolioSection();
        favoriteSection = new FavoriteSection(new OnDetailsClickListener() {
            @Override
            public void onClick(String ticker) {
                Intent i = new Intent(context, SearchResultsActivity.class);
                i.putExtra(Constants.QUERY,ticker.toUpperCase());
                //startActivity(i);
                searchActivityLauncher.launch(i);
            }
        });


        sectionAdapter.addSection(portfolioSection);
        sectionAdapter.addSection(favoriteSection);

        recyclerView = (RecyclerView) findViewById(R.id.rcl_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(sectionAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeDragCallbackHelper(context,
                portfolioSection,
                favoriteSection,
                sectionAdapter,
                0,
                0));
        itemTouchHelper.attachToRecyclerView(recyclerView);
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
                            boolean isFavorite = bundle.getBoolean("isFavorite");
                            if(isFavorite) {
                                favoriteSection.getItemList().add(new FavoriteStockModel(bundle.getString("ticker"),
                                        bundle.getDouble("c"),
                                        bundle.getDouble("d"),
                                        bundle.getString("name")));
                                sectionAdapter.notifyItemInserted(sectionAdapter.getItemCount() - 1);
                            }
                            else
                            {

                            }
                            Log.d("Ticker in main",isFavorite+"");
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
                Log.d("Action bar", "Clicked on search item");
                Intent i = new Intent(context, SearchResultsActivity.class);
                i.putExtra(Constants.QUERY,query.toUpperCase());
//                startActivity(i);
                searchActivityLauncher.launch(i);
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
}

