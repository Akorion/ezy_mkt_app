package com.example.raphael.ezyagric.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.raphael.ezyagric.Activities.PostActivity;
import com.example.raphael.ezyagric.Globals.CircleTransform;
import com.example.raphael.ezyagric.Globals.Utils;
import com.example.raphael.ezyagric.R;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class BuyProduce_DetailsFragment extends Fragment {
    public TextView Crop,Variety, Quantity, Price,Description, DATEPOSTED,LOCATION;
    public Button FabInsure;
    public ImageView imageView;


    public BuyProduce_DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_buy_produce__details, container, false);
        FabInsure = (Button) view.findViewById(R.id.fab);
        imageView = (ImageView) view.findViewById(R.id.thumbnail);


        Context context = getActivity().getApplicationContext();
        FabInsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add the details of the crop and move with them

                Intent l = new Intent(getActivity(), PostActivity.class);
                l.putExtra("EXTRA_SESSION_ID", "Maize 2");
//                l.putExtra("crop", "General maize");
                startActivity(l);

//                var activity2 = new Intent (this, typeof(Activity2));
//                activity2.PutExtra ("MyData", "Data from Activity1");
//                StartActivity (activity2);
            }
        });

        Crop = (TextView) view.findViewById(R.id.crop);
        Variety = (TextView) view.findViewById(R.id.variety);
        Quantity = (TextView) view.findViewById(R.id.quantity);
        Price = (TextView) view.findViewById(R.id.price);
        Description = (TextView) view.findViewById(R.id.desc);
        DATEPOSTED = (TextView) view.findViewById(R.id.date);
        LOCATION = (TextView) view.findViewById(R.id.location);

        //value received from the buy produce details fragment is here
        String value = getArguments().getString("YourKey");
        Log.e("array", value.toString());
        JsonElement element = new Gson().fromJson(value, JsonElement.class);
        JsonObject jsonObj = element.getAsJsonObject();

        Crop.setText("Crop: " + jsonObj.get("crop").getAsString());
        Variety.setText("Variety: " + jsonObj.get("variety").getAsString());
        Quantity.setText("Quantity: " + jsonObj.get("quantity").getAsString()+" kg");
        Price.setText("Price: " + jsonObj.get("price").getAsString());
        Description.setText("Description: " + jsonObj.get("description").getAsString());
        DATEPOSTED.setText("Date Posted: " + jsonObj.get("time").getAsString());
        LOCATION.setText("Location: " + jsonObj.get("location").getAsString());

        Glide.with(context).load(Utils.IMAGE_URLS + jsonObj.get("photo_url").getAsString()).into(imageView);

        return view;
    }
}

