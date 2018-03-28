package com.example.tukicmilan.auctions.milan.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tukicmilan.auctions.milan.R;
import com.example.tukicmilan.auctions.milan.model.Article;
import com.example.tukicmilan.auctions.milan.model.Item;

import java.util.List;

/**
 * Created by TukicMilan on 2/21/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private List<Article> articlesListRecycler;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView title, year, genre;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            year =  itemView.findViewById(R.id.year);
            genre = itemView.findViewById(R.id.genre);
        }
    }

    public RecyclerAdapter(List<Article> articlesListRecycler){
        this.articlesListRecycler = articlesListRecycler;
    }

    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_list_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.MyViewHolder holder, int position) {
        Article article = articlesListRecycler.get(position);
        holder.title.setText(article.getTitle());
        holder.genre.setText(article.getGenre());
        holder.year.setText(article.getYear());
    }

    @Override
    public int getItemCount() {
        return articlesListRecycler.size();
    }
}
