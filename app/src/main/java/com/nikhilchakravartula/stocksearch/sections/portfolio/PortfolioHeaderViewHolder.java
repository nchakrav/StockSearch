package com.nikhilchakravartula.stocksearch.sections.portfolio;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.nikhilchakravartula.stocksearch.R;

public class PortfolioHeaderViewHolder extends RecyclerView.ViewHolder {
    public final TextView headerTitle;
    public PortfolioHeaderViewHolder(View itemView) {
        super(itemView);
        headerTitle = (TextView) itemView.findViewById(R.id.portfolio_section_header_title);
    }
}
