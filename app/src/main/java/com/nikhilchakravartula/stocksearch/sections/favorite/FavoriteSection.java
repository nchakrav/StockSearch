package com.nikhilchakravartula.stocksearch.sections.favorite;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;

import com.nikhilchakravartula.stocksearch.models.PortfolioStockModel;
import com.nikhilchakravartula.stocksearch.utils.Formatter;
import com.nikhilchakravartula.stocksearch.utils.OnDetailsClickListener;
import com.nikhilchakravartula.stocksearch.R;
import com.nikhilchakravartula.stocksearch.models.FavoriteStockModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteSection extends Section {
    List<FavoriteStockModel> itemList = new ArrayList<>();
    OnDetailsClickListener onDetailsClickListener;
    public FavoriteSection(OnDetailsClickListener onDetailsClickListener) {
        // call constructor with layout resources for this Section header and items
        super(SectionParameters.builder()
                .itemResourceId(R.layout.section_item)
                .headerResourceId(R.layout.favorite_section_header)
                .build());
        this.onDetailsClickListener = onDetailsClickListener;
    }


    public List<FavoriteStockModel> getItemList()
    {
        return itemList;
    }
    @Override
    public int getContentItemsTotal() {
        return itemList.size(); // number of items of this section
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        // return a custom instance of ViewHolder for the items of this section
        return new FavoriteItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        FavoriteItemViewHolder itemHolder = (FavoriteItemViewHolder) holder;
        // bind your view here
        String ticker = itemList.get(position).getTicker();
        itemHolder.companyTicker.setText(ticker);
        itemHolder.stockPrice.setText("$"+Formatter.format(itemList.get(position).getStockPrice()));
        Double stockChange = itemList.get(position).getStockChange();
        itemHolder.stockChange.setText("$"+ Formatter.format(stockChange));
        itemHolder.stockDescription.setText(itemList.get(position).getCompanyName()+"");
        itemHolder.detailArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDetailsClickListener.onClick(ticker);
            }
        });

        if(stockChange>0)
        {
            itemHolder.stockTrend.setImageResource(R.drawable.ic_twotone_trending_up_24);
        }
        else
        {
            itemHolder.stockTrend.setImageResource(R.drawable.ic_baseline_trending_down_24);
        }


    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        FavoriteHeaderViewHolder headerHolder = (FavoriteHeaderViewHolder) holder;
        headerHolder.headerTitle.setText("FAVORITES");
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        // return an empty instance of ViewHolder for the headers of this section
        return new FavoriteHeaderViewHolder(view);
    }

    public void add(FavoriteStockModel favoriteStockModel) {
        itemList.add(favoriteStockModel);
    }

    public int find(String ticker)
    {
        for(int i= 0;i<itemList.size();i++)
        {
            FavoriteStockModel stockModel = itemList.get(i);
            if(stockModel.getTicker().equalsIgnoreCase(ticker))
            {
                return  i;
            }
        }
        return -1;
    }


    public int findAndRemove(String ticker)
    {
        int pos = find(ticker);
        if(pos!=-1) {
            itemList.remove(pos);
        }
        return pos;
    }


    public void addAll(List<FavoriteStockModel> favoriteStockModels) {
        for(FavoriteStockModel favoriteStockModel:favoriteStockModels)
            add(favoriteStockModel);
    }

    public void removeAll() {
        itemList.clear();
    }
}
