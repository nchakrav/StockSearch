package com.nikhilchakravartula.stocksearch.sections.portfolio;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;

import com.nikhilchakravartula.stocksearch.R;
import com.nikhilchakravartula.stocksearch.models.PortfolioStockModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PortfolioSection extends Section {
    List<PortfolioStockModel> itemList = new ArrayList<>();

    public PortfolioSection() {
        // call constructor with layout resources for this Section header and items
        super(SectionParameters.builder()
                .itemResourceId(R.layout.section_item)
                .headerResourceId(R.layout.portfolio_section_header)
                .build());
    }


    public List<PortfolioStockModel> getItemList() {
        return itemList;
    }

    @Override
    public int getContentItemsTotal() {
        return itemList.size(); // number of items of this section
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        // return a custom instance of ViewHolder for the items of this section
        return new PortfolioItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        PortfolioItemViewHolder itemHolder = (PortfolioItemViewHolder) holder;
        // bind your view here
        itemHolder.companyTicker.setText(itemList.get(position).getTicker());
        itemHolder.stockPrice.setText(itemList.get(position).getStockPrice()+"");
        itemHolder.stockChange.setText(itemList.get(position).getStockChange()+"");
        itemHolder.stockDescription.setText(itemList.get(position).getNumStocksInvested()+"");
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        PortfolioHeaderViewHolder headerHolder = (PortfolioHeaderViewHolder) holder;
        headerHolder.headerTitle.setText("Portfolio");
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        // return an empty instance of ViewHolder for the headers of this section
        return new PortfolioHeaderViewHolder(view);
    }
}
