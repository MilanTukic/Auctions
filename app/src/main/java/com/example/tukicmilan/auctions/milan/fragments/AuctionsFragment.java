package com.example.tukicmilan.auctions.milan.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tukicmilan.auctions.milan.R;
import com.example.tukicmilan.auctions.milan.activities.AuctionActivity;
import com.example.tukicmilan.auctions.milan.adapters.AuctionsAdapter;
import com.example.tukicmilan.auctions.milan.adapters.AuctionsInListViewAdapter;
import com.example.tukicmilan.auctions.milan.data.HelperDatabaseRead;
import com.example.tukicmilan.auctions.milan.model.Auction;
import com.example.tukicmilan.auctions.milan.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TukicMilan on 1/23/2018.
 */

public class AuctionsFragment extends  Fragment implements SwipeRefreshLayout.OnRefreshListener {
    Context context;
    private List<Auction> listAuction = new ArrayList<>();
    private RecyclerView recyclerView;
    private AuctionsAdapter auctionsAdapter;
    private final static String TAG = AuctionsFragment.class.getSimpleName();
    private MenuClickedListenerA listener;
    private ImageView ivMenu;
    private EditText etSearch;
    private Button mClearText;
    SwipeRefreshLayout swipeRefreshLayout;
    private HelperDatabaseRead helperDatabaseRead;



    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                helperDatabaseRead = new HelperDatabaseRead();
                listAuction = helperDatabaseRead.loadAuctionsFromDatabase(getActivity());
                swipeRefreshLayout.setRefreshing(false);
                Log.i("ref", "osvjezava se");
            }
        }, 4000);
    }


    public interface MenuClickedListenerA{
        void  onClickedMenuA(String clicked);
    }

    public void safeClickedMenu(String strClicked){
        listener.onClickedMenuA(strClicked);
    }

    public AuctionsFragment() {
    }

    private void printLog(String s) {
        Log.d(TAG, s);
    }

    @Override
    public void onAttach(Context context) {
        printLog("onAttach");
        super.onAttach(context);
        listener = (MenuClickedListenerA) context;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        printLog("onCreate");
        super.onCreate(savedInstanceState);
    }

    public void search(String filter) {
        List<Auction> auctionFiltered = new ArrayList<>();
        for(Auction auction1 : listAuction){
            if(auction1.getItem().getName().toLowerCase().contains(filter.toLowerCase())){
                auctionFiltered.add(auction1);
            }
        }
        auctionsAdapter.refresh(auctionFiltered);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        printLog("onCreateView");
        // TODO Auto-generated method stub
        View rootview = inflater.inflate(R.layout.fragment_auctions, container, false);
        //ListView listView = (ListView) rootview.findViewById(R.id.auctions_list_view);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbarr);
        ((AppCompatActivity) getActivity()).getSupportActionBar();

        swipeRefreshLayout = (SwipeRefreshLayout) rootview.findViewById(R.id.swipeRefreshLayoutA);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        helperDatabaseRead = new HelperDatabaseRead();
        listAuction = helperDatabaseRead.loadAuctionsFromDatabase(getActivity());
        recyclerView = (RecyclerView) rootview.findViewById(R.id.ryAuctions);
        etSearch = rootview.findViewById(R.id.etSearchItems);
        mClearText = rootview.findViewById(R.id.clearText);

        mClearText.setVisibility(View.INVISIBLE);


        mClearText.setOnClickListener(onClickListener());



        ivMenu = rootview.findViewById(R.id.ivMenu);
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                safeClickedMenu("yes");
            }
        });

        auctionsAdapter = new AuctionsAdapter(getActivity(), listAuction);
        auctionsAdapter.setOnClikListener(new AuctionsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Auction auction, int position) {
                Intent intent = new Intent(getActivity(), AuctionActivity.class);
                intent.putExtra("selectedAuction", auction);
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
/*
                if(s.length() != 0) {
                    mClearText.setVisibility(View.VISIBLE);
                } else {
                    mClearText.setVisibility(View.GONE);
                }
                */

            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(auctionsAdapter);
        /*
        mAuctionsInListViewAdapter = new AuctionsInListViewAdapter(this.getActivity(), auctions);

        listView.setAdapter(mAuctionsInListViewAdapter);
        listView.setEmptyView(rootview.findViewById(R.id.auctions_empty_state));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openSelectedAuction(auctions.get(position));
            }
        });

        // check if the list is empty and show empty state
        if (listView.getAdapter() == null || listView.getAdapter().isEmpty()) {
            ((TextView) rootview.findViewById(R.id.auctions_empty_state)).setText("NEMA AUKCIJA");
        }
        return rootview;
        */
        return rootview;
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
    /*
    public void clear(View view) {
        etSearch.setText("");
        mClearText.setVisibility(View.GONE);
    }
*/

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        printLog("onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
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
