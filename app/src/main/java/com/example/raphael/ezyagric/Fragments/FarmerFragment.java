package com.example.raphael.ezyagric.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.raphael.ezyagric.Activities.Farmer_detail;
import com.example.raphael.ezyagric.Activities.Sell_Produce;
import com.example.raphael.ezyagric.Adapters.FarmerAdapter;
import com.example.raphael.ezyagric.Couchdb.CouchdbFarmers;
import com.example.raphael.ezyagric.Globals.ClickListener;
import com.example.raphael.ezyagric.Globals.RecyclerTouchListener;
import com.example.raphael.ezyagric.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;


public class FarmerFragment extends Fragment {
    public RelativeLayout _RecyclerViewLayout, _EmptyRecyclerViewLayout;
    private TextView farmer, title;

    RecyclerView recyclerView;
    FarmerAdapter adapter;
    ArrayList<JsonObject> ins = new ArrayList<>();

    CouchdbFarmers couchdbFarmers;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_farmer, container, false);
        _RecyclerViewLayout = (RelativeLayout) view.findViewById(R.id.rv);
        _EmptyRecyclerViewLayout = (RelativeLayout) view.findViewById(R.id.empty_rv);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_insurance);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        couchdbFarmers = new CouchdbFarmers(getActivity());
        getInsurance();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String data = new Gson().toJson(ins.get(position));
//
                //Put the value to another fragment (to the sell_fragment)when agent is selling for the farmer
//                Sell_Fragment ldf = new Sell_Fragment ();
//                Bundle args = new Bundle();
//                args.putString("YourKey", data);
//                ldf.setArguments(args);
//
//                getFragmentManager().beginTransaction().replace(R.id.container, ldf)
//                        .addToBackStack(null)
//                        .commit();
            }

            @Override
            public void onLongClick(View view, int position) {


            }
        }));

        return view;
    }

    public void getInsurance() {
        try {
            adapter = new FarmerAdapter(getActivity(), ins);
            recyclerView.setAdapter(adapter);
            ins.clear();
            ins.addAll(couchdbFarmers.allCrops());
//            Log.e("TAG",ins.toString());
            adapter.notifyDataSetChanged();
            couchdbFarmers.sync();
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
