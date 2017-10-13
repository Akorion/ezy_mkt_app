package com.example.raphael.ezyagric.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.raphael.ezyagric.Globals.Utils;
import com.example.raphael.ezyagric.R;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class SuppliesDetailsFragment extends Fragment {
    public TextView Crop,Variety, Quantity, Price,Description, DATEPOSTED,LOCATION;
    public ImageView imageView;


    public SuppliesDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_supplies_details, container, false);
        imageView = (ImageView) view.findViewById(R.id.thumbnail);


        Context context = getActivity().getApplicationContext();


        Crop = (TextView) view.findViewById(R.id.text1);
        Variety = (TextView) view.findViewById(R.id.text2);
        Quantity = (TextView) view.findViewById(R.id.text3);
        Price = (TextView) view.findViewById(R.id.text4);
        Description = (TextView) view.findViewById(R.id.text5);
        DATEPOSTED = (TextView) view.findViewById(R.id.text6);
        LOCATION = (TextView) view.findViewById(R.id.text7);

        //value received from the buy produce details fragment is here
        String value = getArguments().getString("YourKey");
        Log.e("array", value.toString());
        JsonElement element = new Gson().fromJson(value, JsonElement.class);
        JsonObject jsonObj = element.getAsJsonObject();
        Log.e("DATA", jsonObj.toString());

        Crop.setText("Crop: " + jsonObj.get("crop").getAsString());
        Variety.setText("Variety: " + jsonObj.get("variety").getAsString());
        Quantity.setText("Available Quantity: " + jsonObj.get("quantity").getAsString());
        Price.setText("Price: " + jsonObj.get("price").getAsString());
        Description.setText("Description: " + jsonObj.get("description").getAsString());
        DATEPOSTED.setText("Date Posted: " + jsonObj.get("time").getAsString());
        LOCATION.setText("Location: " + jsonObj.get("location").getAsString());

        Glide.with(context).load(Utils.IMAGE_URLS + jsonObj.get("image").getAsString()).into(imageView);

        return view;
    }
}

