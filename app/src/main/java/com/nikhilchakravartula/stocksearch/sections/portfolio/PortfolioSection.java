package com.nikhilchakravartula.stocksearch.sections.portfolio;

import android.util.Log;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;

import com.nikhilchakravartula.stocksearch.activities.MainActivity;
import com.nikhilchakravartula.stocksearch.utils.Formatter;
import com.nikhilchakravartula.stocksearch.utils.OnDetailsClickListener;
import com.nikhilchakravartula.stocksearch.R;
import com.nikhilchakravartula.stocksearch.models.PortfolioStockModel;

import java.util.ArrayList;
import java.util.List;

public class PortfolioSection extends Section {
    List<PortfolioStockModel> itemList = new ArrayList<>();
    OnDetailsClickListener onDetailsClickListener;
    PortfolioHeaderViewHolder headerHolder;
    public PortfolioSection(OnDetailsClickListener onDetailsClickListener) {
        // call constructor with layout resources for this Section header and items
        super(SectionParameters.builder()
                .itemResourceId(R.layout.section_item)
                .headerResourceId(R.layout.portfolio_section_header)
                .build());
        this.onDetailsClickListener = onDetailsClickListener;
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
        Log.d("PortfolioSection","OnBindItemViewHolder at "+position);
        PortfolioItemViewHolder itemHolder = (PortfolioItemViewHolder) holder;
        // bind your view here
        String ticker = itemList.get(position).getTicker();
        itemHolder.companyTicker.setText(ticker);
        itemHolder.stockPrice.setText("$"+Formatter.format(itemList.get(position).getStockPrice()));
        Double stockChange = itemList.get(position).getStockChange();
        itemHolder.stockChange.setText("$"+Formatter.format(stockChange));
        itemHolder.stockDescription.setText(Formatter.format(itemList.get(position).getNumStocksInvested())+" shares");
        if(stockChange>0)
        {
            itemHolder.stockTrend.setImageResource(R.drawable.ic_twotone_trending_up_24);
        }
        else
        {
            itemHolder.stockTrend.setImageResource(R.drawable.ic_baseline_trending_down_24);
        }

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
        headerHolder = (PortfolioHeaderViewHolder) holder;
        headerHolder.headerTitle.setText("PORTFOLIO");
        headerHolder.netWorth.setText("$"+Formatter.format(MainActivity.moneyInWallet));
    }
    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        // return an empty instance of ViewHolder for the headers of this section
        return new PortfolioHeaderViewHolder(view);
    }

    public void add(PortfolioStockModel portfolioStockModel) {
        itemList.add(portfolioStockModel);
    }

    public void add(int pos, PortfolioStockModel portfolioStockModel) {
        itemList.add(pos,portfolioStockModel);
    }


    public int find(String ticker)
    {
        for(int i= 0;i<itemList.size();i++)
        {
            PortfolioStockModel stockModel = itemList.get(i);
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

    public void update(int pos, PortfolioStockModel portfolioStockModel) {
        itemList.set(pos,portfolioStockModel);
    }

    public void addAll(List<PortfolioStockModel> portfolio) {
        for(PortfolioStockModel portfolioStockModel:portfolio)
            add(portfolioStockModel);
    }

    public void removeAll() {
        itemList.clear();
    }
}
