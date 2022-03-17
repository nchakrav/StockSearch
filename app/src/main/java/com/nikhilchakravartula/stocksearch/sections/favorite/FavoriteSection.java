package com.nikhilchakravartula.stocksearch.sections.favorite;

import android.icu.number.CompactNotation;
import android.view.View;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;

import com.nikhilchakravartula.stocksearch.OnDetailsClickListener;
import com.nikhilchakravartula.stocksearch.R;
import com.nikhilchakravartula.stocksearch.activities.MainActivity;
import com.nikhilchakravartula.stocksearch.models.FavoriteStockModel;
import com.nikhilchakravartula.stocksearch.models.StockListModel;
import com.nikhilchakravartula.stocksearch.models.StockModel;

import java.util.ArrayList;
import java.util.Arrays;
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
        itemHolder.companyTicker.setText(itemList.get(position).getTicker());
        itemHolder.stockPrice.setText(itemList.get(position).getStockPrice()+"");
        itemHolder.stockChange.setText(itemList.get(position).getStockChange()+"");
        itemHolder.stockDescription.setText(itemList.get(position).getCompanyName()+"");
        itemHolder.detailArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDetailsClickListener.onClick(itemList.get(position).getTicker());
            }
        });

    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        FavoriteHeaderViewHolder headerHolder = (FavoriteHeaderViewHolder) holder;
        headerHolder.headerTitle.setText("Favorites");
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        // return an empty instance of ViewHolder for the headers of this section
        return new FavoriteHeaderViewHolder(view);
    }
}
