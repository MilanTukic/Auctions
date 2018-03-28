package com.example.tukicmilan.auctions.milan.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.tukicmilan.auctions.milan.R;
import com.example.tukicmilan.auctions.milan.activities.ItemActivity;
import com.example.tukicmilan.auctions.milan.activities.RecyclerViewActivity;
import com.example.tukicmilan.auctions.milan.adapters.ItemsAdapter;
import com.example.tukicmilan.auctions.milan.adapters.RecyclerAdapter;
import com.example.tukicmilan.auctions.milan.data.HelperDatabaseRead;
import com.example.tukicmilan.auctions.milan.model.Item;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by TukicMilan on 1/23/2018.
 */

public class ItemsFragment extends  Fragment  implements View.OnLongClickListener, SwipeRefreshLayout.OnRefreshListener{
    Context context;
    private List<Item> iListR = new ArrayList<>();
    private RecyclerView recyclerView;
    private ItemsAdapter itemsAdapter;
    private MenuClickedListener listener;
    private Button mClearText;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                helperDatabaseRead = new HelperDatabaseRead();
                iListR = helperDatabaseRead.loadItemsFromDatabase(getActivity());
                swipeRefreshLayout.setRefreshing(false);
                Log.i("ref", "osvjezava se");
            }
        }, 3000);
    }

    public interface MenuClickedListener{
        void onClickedMenu(String clicked);
    }

    public void safeClickedMenu(String strClicked){
        listener.onClickedMenu(strClicked);
    }

    private final static String TAG = ItemsFragment.class.getSimpleName();
   // private ItemsInListViewAdapter mItemsInListViewAdapter;
   // private List<Item> items;
    private HelperDatabaseRead helperDatabaseRead;
    private EditText etSearch;
    private ImageView ivMenu;
    private Button button;

    public ItemsFragment() {
    }


    private void printLog(String s) {
        Log.d(TAG, s);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (MenuClickedListener) context;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void search(String filter){
        List<Item> itemFiltered = new ArrayList<>();
        /*
        Iterator<Item> i = items.iterator();
        Item item;
        while(i.hasNext()){
            item = i.next();
            if(item.getName().toLowerCase().contains(filter.toLowerCase())) {
                itemFiltered.add(item);
            }
            Log.i("!!!", "list size: "+itemFiltered.size());
        }
        mItemsInListViewAdapter.refresh(itemFiltered);
        treba itemsAdapter
        */
        for(Item item1: iListR){
            if(item1.getName().toLowerCase().contains(filter.toLowerCase()) || item1.getDescription().toLowerCase().contains(filter.toLowerCase()) ) {
                itemFiltered.add(item1);
            }
            Log.i("!!!", "list size: "+itemFiltered.size());
        }
        itemsAdapter.refresh(itemFiltered);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_items, container, false);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbarr);
        ((AppCompatActivity) getActivity()).getSupportActionBar();

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        helperDatabaseRead = new HelperDatabaseRead();
        iListR = helperDatabaseRead.loadItemsFromDatabase(getActivity());
        //final ListView listView = (ListView) rootView.findViewById(R.id.items_list_view);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.ryList);
        etSearch = rootView.findViewById(R.id.etSearchItems);


        mClearText = rootView.findViewById(R.id.clearText);

        mClearText.setVisibility(View.INVISIBLE);

        mClearText.setOnClickListener(onClickListener());

        ivMenu = rootView.findViewById(R.id.ivMenu);
        ivMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                safeClickedMenu("yes");
                Log.i("Menu clicked", "fragment onClicke menu icon");
            }
        });

        FloatingActionButton floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.recyclerActivity);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), RecyclerViewActivity.class));
            }
        });

        itemsAdapter = new ItemsAdapter(getActivity(), iListR);
        itemsAdapter.setOnClikListener(new ItemsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Item item, int position) {
                Intent intent = new Intent(getActivity(), ItemActivity.class);
                intent.putExtra("selectedItem", item);
                startActivity(intent);
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!etSearch.getText().toString().equals("")) { //if edittext include text
                    mClearText.setVisibility(View.VISIBLE);
                } else { //not include text
                    mClearText.setVisibility(View.GONE);
                    closeKeyboard();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                search(etSearch.getText().toString());
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemsAdapter);

        return rootView;
    }

    private View.OnClickListener onClickListener() {

        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                etSearch.setText(""); //clear edittext
                closeKeyboard();
            }
        };
    }

    private void closeKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        printLog("onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Override
    public boolean onLongClick(View view) {

        Log.i("onLong", "Kliknuo si");
        Toast.makeText(getActivity(), "Nestoooooo", Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    public void onStart() {
        printLog("onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        printLog("onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        printLog("onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        printLog("onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        printLog("onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        printLog("onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        printLog("onDetach");
        super.onDetach();
        listener = null;
    }
}
