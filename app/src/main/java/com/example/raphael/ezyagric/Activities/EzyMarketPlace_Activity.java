package com.example.raphael.ezyagric.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

import com.example.raphael.ezyagric.R;

public class EzyMarketPlace_Activity extends AppCompatActivity {
    private CardView Buy, Sell, MarketInfo, Reports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ezy_market_place_);


        Sell = (CardView) findViewById(R.id.card_view_soil);
        Buy = (CardView) findViewById(R.id.card_view_insure);
        MarketInfo = (CardView) findViewById(R.id.card_view_plant);
        Reports = (CardView) findViewById(R.id.card_view_inputs);

        MarketInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent l = new Intent(EzyMarketPlace_Activity.this, Market_Info.class);

                Intent l = new Intent(EzyMarketPlace_Activity.this, MarketsPricesActivity.class);
                startActivity(l);
            }
        });

        Sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent l = new Intent(EzyMarketPlace_Activity.this, Sell_Produce.class);
                startActivity(l);
            }
        });

        Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent l = new Intent(EzyMarketPlace_Activity.this, Buy_Produce.class);
                startActivity(l);
            }
        });

        Reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent l = new Intent(EzyMarketPlace_Activity.this, Reports.class);
                startActivity(l);
            }
        });
    }
}
