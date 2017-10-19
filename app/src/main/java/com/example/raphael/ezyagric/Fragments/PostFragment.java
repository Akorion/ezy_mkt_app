package com.example.raphael.ezyagric.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent.*;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.raphael.ezyagric.Activities.Reports;
import com.example.raphael.ezyagric.Couchdb.CouchdbPost;
import com.example.raphael.ezyagric.Couchdb.CouchdbSellProduce;
import com.example.raphael.ezyagric.Globals.GlobalFunctions;
import com.example.raphael.ezyagric.Globals.Utils;
import com.example.raphael.ezyagric.R;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PostFragment extends Fragment {
    Spinner Districts, Description;
    private Button btn, btnActivity1;
    private ImageView imageview;
    public int GALLERY = 1, CAMERA = 2, RESULT_CANCELED;
    public int min = 1000;
    private ProgressDialog dialog = null;
    public LinearLayout Farmer_name, FARMERUNIQUEID;
    public EditText Crop, Variety, Quantity, Price, FarmerName, UNIQUEID;

    CouchdbPost couchdbPost;

    public PostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_post, container, false);
        btn = (Button) view.findViewById(R.id.btn);
        btnActivity1 = (Button) view.findViewById(R.id.uploadButton);

        //showing the picture dialog
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

        btnActivity1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    uploadImage();
                }
            }
        });

        imageview = (ImageView) view.findViewById(R.id.iv);

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Saving Data...");
        dialog.setCancelable(false);

        Crop = (EditText) view.findViewById(R.id.crop);
        Variety = (EditText) view.findViewById(R.id.variety);
        Quantity = (EditText) view.findViewById(R.id.quantity);
        Description = (Spinner) view.findViewById(R.id.describeSpinner);
        Districts = (Spinner) view.findViewById(R.id.districtSpinner);
        Price = (EditText) view.findViewById(R.id.price);

        String crop_text = getActivity().getIntent().getStringExtra("crop");
        String variety_text = getActivity().getIntent().getStringExtra("variety");
        String desc_text = getActivity().getIntent().getStringExtra("desc");
        String price_text = getActivity().getIntent().getStringExtra("price");
        String quantity_text = getActivity().getIntent().getStringExtra("quantity");

        Crop.setText(crop_text);
        Variety.setText(variety_text);
        Description.setPrompt("Produce status");
        Price.setText(price_text);
        Quantity.setText(quantity_text);


//        Farmer_name = (LinearLayout) view.findViewById(R.id.hide_name);

//        //value received from the farmer fragment is here
//        String value = getArguments().getString("YourKey");
//        Log.e("array", value.toString());
//        JsonElement element = new Gson().fromJson(value, JsonElement.class);
//        JsonObject jsonObj = element.getAsJsonObject();
//        Log.e("Vvvvv", jsonObj.get("first_name").getAsString().toString());
//
//        FarmerName.setText(jsonObj.get("first_name").getAsString());
//        UNIQUEID.setText(jsonObj.get("uniqueId").getAsString());


        return view;

    }

    private boolean isValid() {
        boolean result = true;
        try {
            if (Crop.getText().toString().isEmpty()) {
                Crop.setError("Add Crop");
                result = false;
            }
        } catch (NumberFormatException e) {
        }

        try {
            if (Variety.getText().toString().isEmpty()) {
                Variety.setError("Add Variety");
                result = false;
            }
        } catch (Exception e) {
        }

        try {
            if (Quantity.getText().toString().isEmpty()) {
                Quantity.setError("Add Quantity");
                result = false;
            } else if (Integer.parseInt(Quantity.getText().toString()) < min) {
                Quantity.setError("Minimum is 1000");
                Toast.makeText(getActivity(), "Quantity is Below Minimum", Toast.LENGTH_SHORT).show();
                result = false;
            }
        } catch (NumberFormatException e) {
        }

        try {
            if (Price.getText().toString().isEmpty()) {
                Price.setError("Add Price");
                result = false;
            }

        } catch (NumberFormatException e) {
        }
        return result;
    }


    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), contentURI);
                    imageview.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageview.setImageBitmap(thumbnail);
        }
    }

    public void uploadImage() {
        final JSONObject params = new JSONObject();
        String crop = Crop.getText().toString();
//        String first_name = FarmerName.getText().toString();
//        String unique_id = UNIQUEID.getText().toString();
        String variety = Variety.getText().toString();
        String quantity = Quantity.getText().toString();
        String description = Description.getSelectedItem().toString();
        String price = Price.getText().toString();
        String location = Districts.getSelectedItem().toString();
        final Bitmap image = ((BitmapDrawable) imageview.getDrawable()).getBitmap();

        dialog.show();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        try {
            params.put("image", encodedImage);
            params.put("crop", crop);
//            params.put("unique_id", unique_id);
            params.put("time", GlobalFunctions.getCurrentTime());
//            params.put("first_name", first_name);
            params.put("variety", variety);
            params.put("quantity", quantity);
            params.put("description", description);
            params.put("price", price);
            params.put("location", location);
            params.put("status", "new");
        } catch (JSONException e) {
            Log.e("JSONObject Here", e.toString());
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, Utils.urlUpload + "/buy_produce", params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.e("Message from server", jsonObject.toString());
                        dialog.dismiss();
                        try {
                            if (jsonObject.getInt("status") == 201 && !jsonObject.getJSONObject("result").isNull("id") && !jsonObject.getJSONObject("result").isNull("rev")) {
//                                    Toast.makeText(getActivity(), "Sent", Toast.LENGTH_LONG).show();
                                Log.e("PARAMS", params.toString());
                                params.put("_id", jsonObject.getJSONObject("result").getString("id"));
                                params.put("image", jsonObject.getString("photo_url"));
//                                jsonObject.put("Farmer", params);
                                couchdbPost = new CouchdbPost(getActivity());
                                if (CouchdbPost.addSells(params)) {
                                    Toast.makeText(getActivity(), "Saved", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(getActivity(), Reports.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Internet Connection Lost! ", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        Toast.makeText(getActivity(), "Order Successfully, We Shall Call You Within 24 Hours", Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(getActivity(), Reports.class);
//                        startActivity(i);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Internet Connection Lost! ", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);

    }

}

