package com.nikhilchakravartula.stocksearch.sections.portfolio;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.nikhilchakravartula.stocksearch.R;

import org.w3c.dom.Text;

public class PortfolioHeaderViewHolder extends RecyclerView.ViewHolder {
    public final TextView headerTitle;
    public final TextView netWorth;
    public PortfolioHeaderViewHolder(View itemView) {
        super(itemView);
        headerTitle = (TextView) itemView.findViewById(R.id.portfolio_section_header_title);
        netWorth = (TextView) itemView.findViewById(R.id.networth_value_textview);
    }
}
