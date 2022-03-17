package com.nikhilchakravartula.stocksearch.sections.favorite;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.nikhilchakravartula.stocksearch.R;

public class FavoriteHeaderViewHolder extends RecyclerView.ViewHolder {
    public final TextView headerTitle;
    public FavoriteHeaderViewHolder(View itemView) {
        super(itemView);
        headerTitle = (TextView) itemView.findViewById(R.id.fav_section_header_title);
    }
}
