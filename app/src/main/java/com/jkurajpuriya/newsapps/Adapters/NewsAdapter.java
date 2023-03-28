package com.jkurajpuriya.newsapps.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jkurajpuriya.newsapps.Activities.NewsActivity;
import com.jkurajpuriya.newsapps.Model.Articles;
import com.jkurajpuriya.newsapps.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private ArrayList<Articles> articlesArrayList;
    private Context context;
    Dialog dialog;

    public NewsAdapter(ArrayList<Articles> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        dialog=new Dialog(context);
        dialog.setContentView(R.layout.dialog_box);
        dialog.setCancelable(false);
        if (dialog.getWindow()!=null){
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        }
        dialog.show();
        Articles articles = articlesArrayList.get(position);
        holder.newsTitle.setText(articles.getTitle());
        holder.subTitle.setText(articles.getDescription());
        Picasso.get().load(articles.getUrlToImage()).into(holder.newsImage);
        dialog.dismiss();

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, NewsActivity.class);
            intent.putExtra("title",articles.getTitle());
            intent.putExtra("content",articles.getContent());
            intent.putExtra("desc",articles.getDescription());
            intent.putExtra("image",articles.getUrlToImage());
            intent.putExtra("url",articles.getUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView newsTitle, subTitle;
        private ImageView newsImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsTitle=itemView.findViewById(R.id.newsTitle);
            subTitle=itemView.findViewById(R.id.subTitle);
            newsImage=itemView.findViewById(R.id.newsImageView);
        }
    }
}
