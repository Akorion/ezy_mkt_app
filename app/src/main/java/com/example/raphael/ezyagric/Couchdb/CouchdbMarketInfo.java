package com.example.raphael.ezyagric.Couchdb;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.LiveQuery;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.example.raphael.ezyagric.Data.Farmer;
import com.example.raphael.ezyagric.Globals.GlobalFunctions;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.danlew.android.joda.DateUtils;
import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateMidnight;
import org.joda.time.LocalDate;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Raphael on 9/19/2017.
 */
public class CouchdbMarketInfo {
    private static final String TAG = "MARKET_INFORMATION";
    private static final String DOC_TYPE = "marketinfo";
    private static final String VIEW_NAME = "markets";

    private Context CONTEXT = null;
    private CouchdbManager couchdbManager;

    public CouchdbMarketInfo(Context c) {
        CONTEXT = c;
        couchdbManager = new CouchdbManager(c, DOC_TYPE, VIEW_NAME);
    }

    public List<JsonObject> allCrops(LocalDate start, LocalDate end) throws CouchbaseLiteException {
        ProgressDialog p = couchdbManager.showLoadingSpinner("Querying");
        QueryEnumerator res = couchdbManager.viewQuery().run();

        List<JsonObject> farmers = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");

        for (Iterator<QueryRow> it = res; it.hasNext(); ) {
            QueryRow row = it.next();

            try {
                JsonElement element = new Gson().fromJson(new Gson().toJson(row.getKey()), JsonElement.class);
                JsonObject jsonObj = element.getAsJsonObject();
                DateUtils date = new DateUtils();

                Date d = formatter.parse(jsonObj.get("date").getAsString());

                Log.e("break\n","______________");
//                Log.e("MARKET info", jsonObj.toString());
//                Log.e("Date check",d.toString());
//                Log.e("Start date", start.toDate().toString());
//                Log.e("End date", end.toDate().toString());

                if ((start.toDate().compareTo(d) * d.compareTo(end.toDate())) >= 0) {
                    farmers.add(jsonObj);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        p.dismiss();
        Log.e(TAG, farmers.toString());
        return farmers;

    }

    public void sync() {
        couchdbManager.startSync();
    }
}