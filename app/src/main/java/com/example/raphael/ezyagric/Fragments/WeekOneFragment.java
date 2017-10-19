package com.example.raphael.ezyagric.Fragments;

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

import com.example.raphael.ezyagric.Adapters.MarketInfoAdapter;
import com.example.raphael.ezyagric.Couchdb.CouchdbMarketInfo;
import com.example.raphael.ezyagric.Globals.ClickListener;
import com.example.raphael.ezyagric.Globals.RecyclerTouchListener;
import com.example.raphael.ezyagric.R;
import com.google.gson.JsonObject;


import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import java.util.ArrayList;


public class WeekOneFragment extends Fragment {
    public RelativeLayout _RecyclerViewLayout, _EmptyRecyclerViewLayout;
    private TextView farmer, title;

    RecyclerView recyclerView;
    MarketInfoAdapter adapter;
    ArrayList<JsonObject> ins = new ArrayList<>();

    CouchdbMarketInfo couchdbMarketInfo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_week_one, container, false);
        _RecyclerViewLayout = (RelativeLayout) view.findViewById(R.id.rv);
        _EmptyRecyclerViewLayout = (RelativeLayout) view.findViewById(R.id.empty_rv);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_insurance);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        couchdbMarketInfo = new CouchdbMarketInfo(getActivity());
        couchdbMarketInfo.sync();
        getInsurance();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
//                Intent l = new Intent(getActivity(), Farmer_detail.class);
//                String data = new Gson().toJson(ins.get(position));
//                l.putExtra("Details",data );
//                startActivity(l);
            }

            @Override
            public void onLongClick(View view, int position) {
//                Intent l = new Intent(getActivity(), Farmer_detail.class);
//                String data = new Gson().toJson(ins.get(position));
//                l.putExtra("Details",data );
//                startActivity(l);

            }
        }));

        return view;
    }

    public void getInsurance() {
        try {
            adapter = new MarketInfoAdapter(getActivity(), ins);
            recyclerView.setAdapter(adapter);
            ins.clear();
            final DateTime input = new DateTime();

//            final LocalDate startOfThisWeek = new LocalDate().dayOfWeek().withMinimumValue();
            final LocalDate startOfThisWeek = new LocalDate(input.withDayOfWeek(DateTimeConstants.MONDAY));
//            print(new LocalDate().dayOfWeek().withMinimumValue());
            final LocalDate endOfThisWeek = startOfThisWeek.plusDays(6);


            ins.addAll(couchdbMarketInfo.allCrops(startOfThisWeek, endOfThisWeek));
            Log.e("TAG",ins.toString());
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
