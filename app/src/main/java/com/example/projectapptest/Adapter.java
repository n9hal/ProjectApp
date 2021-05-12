package com.example.projectapptest;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectapptest.model.Articles;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    List<Articles> articles;
    WebView newsView;

    public Adapter(Context context, List<Articles> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        newsView = view.findViewById(R.id.newsWeb);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Articles a = articles.get(position);
        holder.txtTitle.setText(a.getTitle());
        holder.txtSource.setText(a.getSource().getName());
        String imgUrl = a.getUrlToImage();
        final String newsUrl = a.getUrl();

        Picasso.with(context).load(imgUrl).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, webView.class);
                intent.putExtra("URL", newsUrl);
                context.startActivity(intent);
            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu =new PopupMenu(context,v);
                popupMenu.getMenuInflater().inflate(R.menu.popupmenu,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.newsCopy:
                                copyNews(newsUrl);
                                Toast.makeText(context,"News Copied",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.newsShare:
                                sActivity(newsUrl);
                                break;
                            default:
                                return false;
                        }
                        return true;
                    }
                });
                return false;
            }
        });
        holder.conMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu =new PopupMenu(context,v);
                popupMenu.getMenuInflater().inflate(R.menu.popupmenu,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.newsCopy:
                                copyNews(newsUrl);
                                Toast.makeText(context,"News Copied",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.newsShare:
                                sActivity(newsUrl);
                                break;
                            default:
                                return false;
                        }
                        return true;
                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle, txtSource;
        ImageView imageView,conMenu;
        CardView cardView;
        @SuppressLint("ResourceType")
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtSource = itemView.findViewById(R.id.txtSource);
            imageView = itemView.findViewById(R.id.imgCover);
            cardView = itemView.findViewById(R.id.cardView);
            conMenu =itemView.findViewById(R.id.itemcardmenu);

        }
    }
    public void sActivity(String newsUrl){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,newsUrl);
        context.startActivity(intent);
    }
    public void copyNews(String newsUrl){
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text",newsUrl);
        clipboardManager.setPrimaryClip(clipData);
    }

}

