package com.nikhilchakravartula.stocksearch.adapters.news;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import com.nikhilchakravartula.stocksearch.R;
import com.nikhilchakravartula.stocksearch.models.NewsModel;
import com.nikhilchakravartula.stocksearch.utils.Util;
import com.squareup.picasso.Picasso;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {
    private List<NewsModel> newsModels;

    public NewsAdapter(List<NewsModel> newsModels) {
        this.newsModels = newsModels;
    }

    public List<NewsModel> getNewsModels() {
        return newsModels;
    }



    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new TopNewsViewHolder(LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.top_news, parent, false));
        } else {
            return new RegularNewsViewHolder(LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.regular_news, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsModel newsModel = newsModels.get(position);
        if (position == 0) {
            String imageUrl = newsModel.getImage();
            if (imageUrl != null && imageUrl.length() > 0 && URLUtil.isValidUrl(imageUrl)) {
                Picasso.with(holder.img.getContext()).load(imageUrl)
                        .resize(380,250).into(holder.img);
            }

        }
        else {
            String imageUrl = newsModel.getImage();
            if (imageUrl != null && imageUrl.length() > 0 && URLUtil.isValidUrl(imageUrl)) {
                Picasso.with(holder.img.getContext()).load(imageUrl)
                        .resize(150,150).into(holder.img);
            }
        }

        holder.src.setText(newsModel.getSource());
        holder.title.setText(newsModel.getTitle());
        holder.timeAgo.setText(Util.getTimeAgo(newsModel, position));
        holder.newsUrl = newsModel.getUrl();
        holder.imageUrl = newsModel.getImage();
    }

    @Override
    public int getItemCount() {
        return newsModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }
}