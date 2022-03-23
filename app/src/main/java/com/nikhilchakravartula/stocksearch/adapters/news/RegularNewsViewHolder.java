package com.nikhilchakravartula.stocksearch.adapters.news;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nikhilchakravartula.stocksearch.R;
import com.nikhilchakravartula.stocksearch.constants.Constants;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.net.URLEncoder;


public class RegularNewsViewHolder extends NewsViewHolder {

    public RegularNewsViewHolder(@NonNull View itemView) {
        super(itemView);
        img = itemView.findViewById(R.id.regularnews_imageview);
        src = itemView.findViewById(R.id.regularnews_source_textview);
        timeAgo = itemView.findViewById(R.id.regularnews_timeago_textview);
        title = itemView.findViewById(R.id.regularnews_description_textview);
    }
}