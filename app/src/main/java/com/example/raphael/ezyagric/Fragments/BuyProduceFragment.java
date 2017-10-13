package com.example.raphael.ezyagric.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.raphael.ezyagric.Adapters.BuyProduceAdapter;
import com.example.raphael.ezyagric.Couchdb.CouchdbBuyProduce;
import com.example.raphael.ezyagric.Globals.ClickListener;
import com.example.raphael.ezyagric.Globals.DividerItemDecoration;
import com.example.raphael.ezyagric.Globals.RecyclerTouchListener;
import com.example.raphael.ezyagric.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;


public class BuyProduceFragment extends Fragment {
    public RelativeLayout _RecyclerViewLayout, _EmptyRecyclerViewLayout;
    private TextView farmer, title;

    RecyclerView recyclerView;
    BuyProduceAdapter adapter;
    ArrayList<JsonObject> ins = new ArrayList<>();

    CouchdbBuyProduce couchdbBuyProduce;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buy_produce, container, false);
        _RecyclerViewLayout = (RelativeLayout) view.findViewById(R.id.rv);
        _EmptyRecyclerViewLayout = (RelativeLayout) view.findViewById(R.id.empty_rv);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_insurance);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        couchdbBuyProduce = new CouchdbBuyProduce(getActivity());
        couchdbBuyProduce.sync();
        getInsurance();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                String data = new Gson().toJson(ins.get(position));
                //Put the value to another fragment (to the BuyProduce_detailsFragment)
                BuyProduce_DetailsFragment ldf = new BuyProduce_DetailsFragment();
                Bundle args = new Bundle();
                args.putString("YourKey", data);
                ldf.setArguments(args);

                getFragmentManager().beginTransaction().replace(R.id.container, ldf)
                        .addToBackStack(null)
                        .commit();
            }

        }));

        return view;
    }

    public void getInsurance() {
        try {
            adapter = new BuyProduceAdapter(getActivity(), ins);
            recyclerView.setAdapter(adapter);
            ins.clear();
            ins.addAll(couchdbBuyProduce.allCrops());
            Log.e("TAG", ins.toString());
            adapter.notifyDataSetChanged();
            if (ins.isEmpty()) {
                _RecyclerViewLayout.setVisibility(View.GONE);
                _EmptyRecyclerViewLayout.setVisibility(View.VISIBLE);
            } else {
                _RecyclerViewLayout.setVisibility(View.VISIBLE);
                _EmptyRecyclerViewLayout.setVisibility(View.GONE);
            }

        } catch (Exception e) {
        }
    }
}
