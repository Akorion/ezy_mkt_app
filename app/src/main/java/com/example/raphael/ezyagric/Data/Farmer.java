package com.example.raphael.ezyagric.Data;

/**
 * Created by Raphael on 8/30/2017.
 */
public class Farmer {
    private String name,first_name, last_name,farmer_organisation,
            gender,crop, country,district, sub_county, parish, village, photo_url;
    private int numOfSongs,acreage;
    private int thumbnail;

    public Farmer() {
    }

    public Farmer(String name,String first_name,String last_name,String farmer_organisation,
                  String gender,String crop,String country, String sub_county,String photo_url,
                  String district, String parish, String village, int numOfSongs, int thumbnail) {
        this.name = name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.farmer_organisation = farmer_organisation;
        this.gender = gender;
        this.crop = crop;
        this.country = country;
        this.sub_county = sub_county;
        this.district = district;
        this.parish = parish;
        this.village = village;
        this.photo_url = photo_url;
        this.numOfSongs = numOfSongs;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //firstname
    public String getFirst_name() {
        return first_name;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    //lastName
    public String getLast_name() {
        return last_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    //farmerOrganisation
    public String getFarmer_organisation() {
        return farmer_organisation;
    }
    public void setFarmer_organisation(String farmer_organisation) {
        this.farmer_organisation = farmer_organisation;
    }

    //gender
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    //crop
    public String getCrop() {
        return crop;
    }
    public void setCrop(String crop) {
        this.crop = crop;
    }

    //acreage
    public int getAcreage() {
        return acreage;
    }
    public void setAcreage(int acreage) {
        this.acreage = acreage;
    }

    //country
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    //district
    public String getDistrict() {
        return district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }

    //subCounty
    public String getSub_county() {
        return sub_county;
    }
    public void setSub_county(String sub_county) {
        this.sub_county = sub_county;
    }

    //parish
    public String getParish() {
        return parish;
    }
    public void setParish(String parish) {
        this.parish = parish;
    }

    //village
    public String getVillage() {
        return village;
    }
    public void setVillage(String village) {
        this.village = village;
    }

    //photo_url
    public String getPhoto_url() {
        return photo_url;
    }
    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }


    public int getNumOfSongs() {
        return numOfSongs;
    }

    public void setNumOfSongs(int numOfSongs) {
        this.numOfSongs = numOfSongs;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}

