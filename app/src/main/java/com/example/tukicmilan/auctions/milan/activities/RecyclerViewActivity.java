package com.example.tukicmilan.auctions.milan.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.tukicmilan.auctions.milan.R;
import com.example.tukicmilan.auctions.milan.adapters.RecyclerAdapter;
import com.example.tukicmilan.auctions.milan.model.Article;
import com.example.tukicmilan.auctions.milan.model.Item;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private List<Article> articleListRecycler = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.items_toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerList);
        prepareItemData();

        recyclerAdapter = new RecyclerAdapter(articleListRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void prepareItemData(){

        Article article = new Article("Audi", "Polovan", "ewgewg");
        articleListRecycler.add(article);

         article = new Article("Bmw", "Nov, 10000e", "juy");
        articleListRecycler.add(article);
        articleListRecycler.add(new Article("Volvo", "Nov", "kako god"));
    }
}
