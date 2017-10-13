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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.raphael.ezyagric.Data.Farmer;
import com.example.raphael.ezyagric.Globals.CircleTransform;
import com.example.raphael.ezyagric.Globals.Utils;
import com.example.raphael.ezyagric.R;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Raphael on 8/30/2017.
 */

public class FarmerAdapter extends RecyclerView.Adapter<FarmerAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<JsonObject> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView FirstName, LastName;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            FirstName = (TextView) view.findViewById(R.id.first_Name);
            LastName = (TextView) view.findViewById(R.id.last_Name);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }


    public FarmerAdapter(Context mContext, ArrayList<JsonObject> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_farmer_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        try {
            JsonObject ins = albumList.get(position);
            Log.e("DETAILS", ins.toString());
            holder.FirstName.setText(ins.get("first_name").getAsString()+" "+ins.get("last_name").getAsString());
            holder.LastName.setText(ins.get("number_of_farmers").getAsString()+" farmers\n"+ins.get("contact").getAsString());

            Log.e("Path",Utils.IMAGE_URL + ins.get("image").getAsString());
            Glide.with(mContext).load(Utils.IMAGE_URL + ins.get("image").getAsString())
                    .thumbnail(0.5f)
                    .crossFade()
                    .transform(new CircleTransform(mContext))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.thumbnail);

        } catch (Exception e) {
            Log.e("CART", e.toString());
        }
//        holder.overflow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showPopupMenu(holder.overflow);
//            }
//      });
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