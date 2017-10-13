package com.example.raphael.ezyagric.Fragments;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.raphael.ezyagric.Activities.AgentsFarmersActivity;
import com.example.raphael.ezyagric.Couchdb.CouchdbFarmers;
import com.example.raphael.ezyagric.Globals.GlobalFunctions;
import com.example.raphael.ezyagric.Globals.Utils;
import com.example.raphael.ezyagric.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class AddFarmerFragment extends Fragment {
    Spinner Districts, Countries, Gender, Crops;
    private Button btn, btnActivity1;
    private ImageView imageview;
    public int GALLERY = 1, CAMERA = 2, RESULT_CANCELED;
    private ProgressDialog dialog = null;

    public EditText SubCounty, FirstName, LastName, FarmerOrganisation, Acreage,DateOfBirth, Parish, Village;
    public LinearLayout district_hide_show, sub_county_hide_show, parish_hide_show, village_hide_show;

    public DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;


    CouchdbFarmers couchdbFarmers;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_farmer, container, false);
        btn = (Button) view.findViewById(R.id.btn);
        btnActivity1 = (Button) view.findViewById(R.id.uploadButton);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        DateOfBirth = (EditText) view.findViewById(R.id.my_dob);

        DateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
            }
        });



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
                if(isValid()){
                uploadImage();
                }
            }
        });

        Countries = (Spinner)view.findViewById(R.id.countrySpinner);

        //hide and show fields when uganda is selected
        Countries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ERROR", Countries.getItemAtPosition(position).toString());
                if (Countries.getItemAtPosition(position).toString().equals("Uganda")) {
                    district_hide_show.setVisibility(View.VISIBLE);
                    sub_county_hide_show.setVisibility(View.VISIBLE);
                    parish_hide_show.setVisibility(View.VISIBLE);
                    village_hide_show.setVisibility(View.VISIBLE);
                } else {
                    district_hide_show.setVisibility(View.GONE);
                    sub_county_hide_show.setVisibility(View.GONE);
                    parish_hide_show.setVisibility(View.GONE);
                    village_hide_show.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });

        imageview = (ImageView) view.findViewById(R.id.imageView_pic);
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Saving Data...");
        dialog.setCancelable(false);
        FirstName = (EditText) view.findViewById(R.id.first_Name);
        district_hide_show = (LinearLayout) view.findViewById(R.id.layout_district);
        sub_county_hide_show = (LinearLayout) view.findViewById(R.id.layout_sub_county);
        parish_hide_show = (LinearLayout) view.findViewById(R.id.layout_parish);
        village_hide_show = (LinearLayout) view.findViewById(R.id.layout_village);
        FirstName = (EditText) view.findViewById(R.id.first_Name);
        LastName = (EditText) view.findViewById(R.id.last_Name);
        FarmerOrganisation = (EditText) view.findViewById(R.id.farmer_organisation);
        Acreage = (EditText) view.findViewById(R.id.acreage);
        SubCounty = (EditText) view.findViewById(R.id.sub_county);
        Parish = (EditText) view.findViewById(R.id.parish);
        Village = (EditText) view.findViewById(R.id.village);

        //  id for the upload button
        btnActivity1 = (Button) view.findViewById(R.id.uploadButton);

        //list of crops
        Crops = (Spinner) view.findViewById(R.id.cropSpinner);

        //lists of districts
        Districts = (Spinner) view.findViewById(R.id.districtSpinner);

        //getting gender
        Gender = (Spinner) view.findViewById(R.id.genderSpinner);
        setDateTimeField();
        return view;
    }

    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                DateOfBirth.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }
    private boolean isValid() {
        boolean result = true;

        try {
            if (FirstName.getText().toString().isEmpty()) {
                FirstName.setError("Add First Name");
                result = false;
            }

        } catch (NumberFormatException e) {}

        try {
            if (LastName.getText().toString().isEmpty()) {
                LastName.setError("Add Last Name");
                result = false;
            }
        } catch (Exception e) {}

        try {
            if (FarmerOrganisation.getText().toString().isEmpty()) {
                FarmerOrganisation.setError("Add Farmer Organisation");
                result = false;
            }
        } catch (NumberFormatException e) {}

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
        String first_name = FirstName.getText().toString();
        String last_name = LastName.getText().toString();
        String date_of_birth = DateOfBirth.getText().toString();
        String farmer_organisation = FarmerOrganisation.getText().toString();
        String country = Countries.getSelectedItem().toString();
        String crop = Crops.getSelectedItem().toString();
        String district = Districts.getSelectedItem().toString();
        String acreage = Acreage.getText().toString();
        String gender = Gender.getSelectedItem().toString();
        String village = Village.getText().toString();
        String parish = Parish.getText().toString();
        String sub_county = SubCounty.getText().toString();

        //generating the uniqueId
        String name = first_name.substring(0,3);
        String fName = name.toUpperCase();

        String dob = date_of_birth.replace("-", "");

        String name2 = last_name.substring(0,3);
        String lName = name2.toUpperCase();

        String name3 = district.substring(0,3);
        String distr = name3.toUpperCase();

        String name4 = village.substring(0,3);
        String villa = name4.toUpperCase();

        String uniqueId = fName + lName + dob + distr + villa;
        Log.e("MyId", uniqueId.toString());


        final Bitmap image = ((BitmapDrawable) imageview.getDrawable()).getBitmap();
        dialog.show();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        try {
            params.put("image", encodedImage);
            params.put("first_name", first_name);
            params.put("last_name", last_name);
            params.put("uniqueId", uniqueId);
            params.put("time", GlobalFunctions.getCurrentTime());
            params.put("date_of_birth", date_of_birth);
            params.put("farmer_organisation", farmer_organisation);
            params.put("crop", crop);
            params.put("country", country);
            params.put("district", district);
            params.put("sub_county", sub_county);
            params.put("parish", parish);
            params.put("village", village);
            params.put("gender", gender);
            params.put("acreage", acreage);
            params.put("status", "new");
        } catch (JSONException e) {
            Log.e("JSONObject Here", e.toString());
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, Utils.urlUpload + "/upload_KYF", params,
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
                                couchdbFarmers = new CouchdbFarmers(getActivity());
                                if (couchdbFarmers.addFarmer(params)) {
                                    Toast.makeText(getActivity(), "Saved", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(getActivity(), AgentsFarmersActivity.class);
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
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Internet Connection Lost! ", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);

    }

}

