package com.example.raphael.ezyagric.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raphael.ezyagric.Adapters.FarmerAdapter;
import com.example.raphael.ezyagric.Adapters.PostProduceAdapter;
import com.example.raphael.ezyagric.Adapters.SellProduceAdapter;
import com.example.raphael.ezyagric.Couchdb.CouchdbFarmers;
import com.example.raphael.ezyagric.Couchdb.CouchdbPost;
import com.example.raphael.ezyagric.Couchdb.CouchdbSellProduce;
import com.example.raphael.ezyagric.Globals.ClickListener;
import com.example.raphael.ezyagric.Globals.RecyclerTouchListener;
import com.example.raphael.ezyagric.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class TwoFragment extends Fragment{

    public RelativeLayout _RecyclerViewLayout, _EmptyRecyclerViewLayout;
    private TextView farmer, title;

    RecyclerView recyclerView;
//    PostProduceAdapter adapter;
    FarmerAdapter adapter;
    ArrayList<JsonObject> ins = new ArrayList<>();

    CouchdbFarmers couchdbPost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        _RecyclerViewLayout = (RelativeLayout) view.findViewById(R.id.rv);
        _EmptyRecyclerViewLayout = (RelativeLayout) view.findViewById(R.id.empty_rv);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_insurance);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        couchdbPost = new CouchdbFarmers(getActivity());
        couchdbPost.sync();
        getInsurance();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String data = new Gson().toJson(ins.get(position));
                TwoFragment ldf = new TwoFragment();
                Bundle args = new Bundle();
                args.putString("YourKey", data);
                ldf.setArguments(args);

                getFragmentManager().beginTransaction().replace(R.id.container, ldf)
                        .addToBackStack(null)
                        .commit();

                Toast.makeText(getActivity(),"agent clicked",Toast.LENGTH_SHORT).show();


            }

//            @Override
//            public void onLongClick(View view, int position) {
//                Intent l = new Intent(getActivity(), Farmer_detail.class);
//                String data = new Gson().toJson(ins.get(position));
//                l.putExtra("Details",data );
//                startActivity(l);

//            }
        }));

        return view;
    }

    public void getInsurance() {
        try {
            adapter = new FarmerAdapter(getActivity(), ins);
            recyclerView.setAdapter(adapter);
            ins.clear();
            ins.addAll(couchdbPost.allCrops());
//            Log.e("TAG",ins.toString());
            adapter.notifyDataSetChanged();
            if (ins.isEmpty()) {
                _RecyclerViewLayout.setVisibility(View.GONE);
                _EmptyRecyclerViewLayout.setVisibility(View.VISIBLE);
            } else {
                _RecyclerViewLayout.setVisibility(View.VISIBLE);
                _EmptyRecyclerViewLayout.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            System.out.print("Adapter error "+e.getMessage());
        }
    }
}
