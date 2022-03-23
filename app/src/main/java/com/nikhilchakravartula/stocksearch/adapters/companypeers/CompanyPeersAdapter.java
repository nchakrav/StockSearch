package com.nikhilchakravartula.stocksearch.adapters.companypeers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.nikhilchakravartula.stocksearch.R;
import com.nikhilchakravartula.stocksearch.activities.SearchResultsActivity;
import com.nikhilchakravartula.stocksearch.constants.Constants;
import com.nikhilchakravartula.stocksearch.models.FavoriteStockModel;
import com.nikhilchakravartula.stocksearch.models.PortfolioStockModel;
import com.nikhilchakravartula.stocksearch.storage.Storage;
import com.nikhilchakravartula.stocksearch.utils.OnCompanyPeerClickListener;

import java.util.ArrayList;
import java.util.List;

public class CompanyPeersAdapter extends  RecyclerView.Adapter<CompanyPeerViewHolder>
{

    int res;
    List<String> peers;
    Context context;
    Storage storage;
//    OnCompanyPeerClickListener onCompanyPeerClickListener

    public CompanyPeersAdapter(Context context, int res, List<String> peers)//,OnCompanyPeerClickListener onCompanyPeerClickListener)
    {
        this.res = res;
        this.context = context;
        this.peers = peers;
        this.storage = Storage.getStorage(context.getApplicationContext());
//        this.onCompanyPeerClickListener = onCompanyPeerClickListener;
    }
    @NonNull
    @Override
    public CompanyPeerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(res,null);
        TextView tv = view.findViewById(R.id.company_peer_tv);
        tv.setPaintFlags(tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        return new CompanyPeerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyPeerViewHolder holder, int position) {
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ticker = peers.get(holder.getAbsoluteAdapterPosition());
                Log.d("On Click",ticker);
//                PortfolioStockModel portfolioStockModel = new PortfolioStockModel();
//                FavoriteStockModel favoriteStockModel = new FavoriteStockModel();
//                boolean isFavorite = false;
//                List<PortfolioStockModel> portfolioStockModels =storage.getPortfolio();
//                List<FavoriteStockModel> favoriteStockModels = storage.getFavorites();
//                int pos = -1;
//                for(int i=0;i<portfolioStockModels.size();i++) {
//                    PortfolioStockModel portfolioStockModel1 = portfolioStockModels.get(i);
//                    if (portfolioStockModel1.getTicker().equalsIgnoreCase(ticker)) {
//                        portfolioStockModel = portfolioStockModel1;
//                    }
//                }
//
//                for(int i=0;i<favoriteStockModels.size();i++) {
//                    FavoriteStockModel favoriteStockModel1 = favoriteStockModels.get(i);
//                    if (favoriteStockModel1.getTicker().equalsIgnoreCase(ticker)) {
//                        favoriteStockModel = favoriteStockModel1;
//                        isFavorite = true;
//                        break;
//                    }
//                }
                Intent i = new Intent(context, SearchResultsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.QUERY,ticker.toUpperCase());
//                bundle.putString("favoriteStockModel",new Gson().toJson(favoriteStockModel));
//                bundle.putBoolean("isFavorite",isFavorite);
//                bundle.putString("portfolioStockModel",new Gson().toJson(portfolioStockModel));
                i.putExtras(bundle);
                //searchActivityLauncher.launch(i);
                context.startActivity(i);
            }
        });
        holder.tv.setText(peers.get(position));
        if(position==peers.size()-1)
            holder.comma.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return peers.size();
    }
}
