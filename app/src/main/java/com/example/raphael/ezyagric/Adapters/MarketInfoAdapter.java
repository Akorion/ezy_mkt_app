package com.example.raphael.ezyagric.Adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.raphael.ezyagric.Globals.Utils;
import com.example.raphael.ezyagric.R;
import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * Created by Raphael on 9/19/2017.
 */
public class MarketInfoAdapter extends RecyclerView.Adapter<MarketInfoAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<JsonObject> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView MARKET, COMMODITY, UNIT,WHOLESALEPRICE,RETAILPRICE;

        public MyViewHolder(View view) {
            super(view);
            MARKET = (TextView) view.findViewById(R.id.market);
            COMMODITY = (TextView) view.findViewById(R.id.commodity);
            UNIT = (TextView) view.findViewById(R.id.unit);
            WHOLESALEPRICE = (TextView) view.findViewById(R.id.wPrice);
            RETAILPRICE = (TextView) view.findViewById(R.id.rPrice);
        }
    }


    public MarketInfoAdapter(Context mContext, ArrayList<JsonObject> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.market_info_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        try {
            JsonObject ins = albumList.get(position);
            holder.MARKET.setText("Market: " + ins.get("market").getAsString());
            holder.COMMODITY.setText("Commodity: " + ins.get("commodity").getAsString());
            holder.UNIT.setText("Unit: " + ins.get("unit").getAsString());
            holder.WHOLESALEPRICE.setText("Whole sale Price: " + ins.get("wholesaleprice").getAsString());
            holder.RETAILPRICE.setText("Retail Price: " + ins.get("retailprice").getAsString());
//            Glide.with(mContext).load(Utils.IMAGE_URL + ins.get("image").getAsString()).into(holder.thumbnail);
//            Log.e("Path", Utils.IMAGE_URL + ins.get("image").getAsString());
        } catch (Exception e) {
            Log.e("CART", e.toString());
        }

    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_farmer, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Edited", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Deleted", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

}