package com.nikhilchakravartula.stocksearch.adapters.companypeers;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nikhilchakravartula.stocksearch.R;

public class CompanyPeerViewHolder extends RecyclerView.ViewHolder
{

    TextView tv;
    TextView comma;
    public CompanyPeerViewHolder(@NonNull View itemView) {
        super(itemView);
        tv = (TextView) itemView.findViewById(R.id.company_peer_tv);
        comma = (TextView) itemView.findViewById(R.id.company_peer_tv_comma);
    }
}
