package com.example.raphael.ezyagric.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.raphael.ezyagric.R;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;

public class Farmer_detail extends AppCompatActivity {
    TextView val;
    String here;
    JsonObject Obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_detail);

        val = (TextView) findViewById(R.id.title);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            here = extras.getString("Details");
            JsonElement element = new Gson().fromJson (here, JsonElement.class);
            JsonObject jsonObj = element.getAsJsonObject();
            Log.e("ARRAY", jsonObj.toString());
            val.setText(jsonObj.get("first_name").getAsString());
        } else {
            // handle case
        }
    }
}
