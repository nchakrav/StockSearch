package com.nikhilchakravartula.stocksearch.sections.portfolio;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nikhilchakravartula.stocksearch.R;

public class PortfolioItemViewHolder extends RecyclerView.ViewHolder {
    public final TextView companyTicker;
    public final TextView stockDescription;
    public final TextView stockPrice;
    public final TextView stockChange;
    public final ImageView stockTrend;
    public PortfolioItemViewHolder(View itemView) {
        super(itemView);
        companyTicker = (TextView) itemView.findViewById(R.id.company_ticker);
        stockDescription = (TextView) itemView.findViewById(R.id.stock_desc);
        stockPrice = (TextView) itemView.findViewById(R.id.stock_price);
        stockChange = (TextView) itemView.findViewById(R.id.stock_change);
        stockTrend = (ImageView) itemView.findViewById(R.id.stock_trend);
    }
}
