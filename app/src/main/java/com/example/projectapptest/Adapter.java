package com.example.projectapptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectapptest.model.Articles;
import com.example.projectapptest.model.Source;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    List<Articles> articles;

    public Adapter(Context context, List<Articles> articles){
        this.context= context;
        this.articles= articles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Articles a= articles.get(position);
        holder.txtTitle.setText(a.getTitle());
        holder.txtSource.setText(a.getSource().getName());

        String imgUrl = a.getUrlToImage();
        Picasso.with(context).load(imgUrl).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle,txtSource;
        ImageView imageView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtSource=itemView.findViewById(R.id.txtSource);
            imageView = itemView.findViewById(R.id.imgCover);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
