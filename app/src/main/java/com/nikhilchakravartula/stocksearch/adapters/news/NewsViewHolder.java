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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.nikhilchakravartula.stocksearch.R;
import com.nikhilchakravartula.stocksearch.constants.Constants;
import com.squareup.picasso.Picasso;

import java.net.URLEncoder;

public class NewsViewHolder extends RecyclerView.ViewHolder {
    public ImageView img;
    public TextView src;
    public TextView timeAgo;
    public TextView title;
    public String newsUrl;
    public String imageUrl;

    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(v -> {
            if (newsUrl != null && newsUrl.length() > 0 && URLUtil.isValidUrl(newsUrl)) {
                Uri uri = Uri.parse(newsUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                itemView.getContext().startActivity(intent);
            }
        });
        itemView.setOnLongClickListener(v -> {
            Context context = v.getContext();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View shareDialogLayout = ((Activity) context).getLayoutInflater().inflate(R.layout.share_dialog, null);
            builder.setView(shareDialogLayout);
            AlertDialog dialog = builder.create();

            ImageView shareDialogImageView = shareDialogLayout.findViewById(R.id.news_img);
            if (imageUrl != null && imageUrl.length() > 0 && URLUtil.isValidUrl(imageUrl)) {
                Picasso.with(shareDialogImageView.getContext()).load(imageUrl)
                        .resize(420,300).into(shareDialogImageView);
            }

            TextView shareDialogNewsTitleTextView = shareDialogLayout.findViewById(R.id.news_title);
            shareDialogNewsTitleTextView.setText(title.getText().toString());

            ImageButton twitterShareButton = shareDialogLayout.findViewById(R.id.twitter_share_btn);
            twitterShareButton.setOnClickListener(v12 -> {
                String message = "Check out this Link: " + newsUrl;
                Intent intent = new Intent();
                intent.putExtra(Intent.EXTRA_TEXT, message);
                intent.setAction(Intent.ACTION_VIEW);
                try {
                    intent.setData(Uri.parse(String.format(Constants.TWITTER_URL, URLEncoder.encode(message, "UTF-8"))));
                }
                catch (Exception e)
                {

                }
                itemView.getContext().startActivity(intent);
            });

            ImageButton chromeViewButton = shareDialogLayout.findViewById(R.id.chrome_view_btn);
            chromeViewButton.setOnClickListener(v1 -> {
                if (newsUrl != null && newsUrl.length() > 0 && URLUtil.isValidUrl(newsUrl)) {
                    Uri uri = Uri.parse(newsUrl);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    itemView.getContext().startActivity(intent);
                }
            });


            ImageButton fbShareButton = shareDialogLayout.findViewById(R.id.fb_share_btn);
            fbShareButton.setOnClickListener(v12 -> {
                String message = newsUrl;
                Intent intent = new Intent();
                intent.putExtra(Intent.EXTRA_TEXT, message);
                intent.setAction(Intent.ACTION_VIEW);
                try {
                    intent.setData(Uri.parse(String.format(Constants.FACEBOOK_URL, URLEncoder.encode(message, "UTF-8"))));
                }
                catch (Exception e)
                {

                }
                itemView.getContext().startActivity(intent);
            });

            dialog.show();
            return true;
        });
    }
}
