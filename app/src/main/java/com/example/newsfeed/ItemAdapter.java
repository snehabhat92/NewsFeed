package com.example.newsfeed;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.NewsHolder> {

    private List<NewsFeed.Articles> articles;
    private Context context;

    public ItemAdapter(List<NewsFeed.Articles> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }
    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        NewsFeed.Articles article = articles.get(position);
        holder.textTitle.setText(article.getTitle());
        holder.textDescription.setText(article.getDescription());
        if (!TextUtils.isEmpty(article.getAuthor())) {
            holder.textAuthor.setText("By " + article.getAuthor());
        }
        Glide.with(context).load(article.getUrlToImage()).into(holder.imageView);
        holder.textDate.setText(convertDate(article.getPublishedAt()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("url", article.getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (articles != null) ? articles.size() : 0;
    }

    class NewsHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textTitle;
        private TextView textDescription;
        private TextView textAuthor;
        private TextView textDate;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textTitle = itemView.findViewById(R.id.txt_title);
            textDescription = itemView.findViewById(R.id.txt_description);
            textAuthor = itemView.findViewById(R.id.txt_author);
            textDate = itemView.findViewById(R.id.txt_published_date);

        }
    }

    private String convertDate(String dateString) {
        String formattedDate = "";
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(dateString);
            return new SimpleDateFormat("dd-MM-yyyy").format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

}
