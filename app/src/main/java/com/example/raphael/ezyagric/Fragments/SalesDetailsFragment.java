package com.example.raphael.ezyagric.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.raphael.ezyagric.Activities.PostActivity;
import com.example.raphael.ezyagric.Globals.Utils;
import com.example.raphael.ezyagric.R;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class SalesDetailsFragment extends Fragment {
    public TextView Crop, Variety, Quantity, Price, Description, DATEPOSTED, LOCATION;
    public ImageView imageView;

    public SalesDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sales_details, container, false);
        imageView = (ImageView) view.findViewById(R.id.thumbnail);


        Context context = getActivity().getApplicationContext();


        Crop = (TextView) view.findViewById(R.id.crop);
        Variety = (TextView) view.findViewById(R.id.variety);
        Quantity = (TextView) view.findViewById(R.id.quantity);
        Price = (TextView) view.findViewById(R.id.price);
        Description = (TextView) view.findViewById(R.id.desc);
        DATEPOSTED = (TextView) view.findViewById(R.id.date);
        LOCATION = (TextView) view.findViewById(R.id.location);

        //value received from the buy produce details fragment is here
        String value = getArguments().getString("Sales_key");
        Log.e("array", value.toString());
        JsonElement element = new Gson().fromJson(value, JsonElement.class);
        JsonObject jsonObj = element.getAsJsonObject();
        Log.e("DATA", jsonObj.toString());

        Crop.setText(jsonObj.get("crop").getAsString());
        Variety.setText(jsonObj.get("variety").getAsString());
        Quantity.setText(jsonObj.get("quantity").getAsString());
        Price.setText(jsonObj.get("price").getAsString());
        Description.setText(jsonObj.get("description").getAsString());
        DATEPOSTED.setText(jsonObj.get("time").getAsString());
        LOCATION.setText(jsonObj.get("location").getAsString());

        Glide.with(context).load(Utils.IMAGE_URLS + jsonObj.get("image").getAsString()).into(imageView);

        return view;
    }
}

