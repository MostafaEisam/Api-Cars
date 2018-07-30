package com.example.mostafaeisam.apicars.classes;

import com.example.mostafaeisam.apicars.responses.GetAuctionInfo;

import java.io.Serializable;
import java.util.Comparator;

public class Items  {
    private String image;
    private String makeAr;
    private String makeEn;
    private String modelEn;
    private int year;
    private int carID;
    private boolean isFavorite;
    private GetAuctionInfo AuctionInfo;
    private boolean isUpdated;

    public Items(String makeEn,GetAuctionInfo AuctionInfo,String image,String makeAr,int carID,boolean isUpdated) {
        this.makeEn = makeEn;
        this.AuctionInfo = AuctionInfo;
        this.image = image;
        this.makeAr = makeAr;
        this.carID = carID;
        this.isUpdated = isUpdated;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getModelEn() {
        return modelEn;
    }

    public void setModelEn(String modelEn) {
        this.modelEn = modelEn;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMakeAr() {
        return makeAr;
    }

    public void setMakeAr(String makeAr) {
        this.makeAr = makeAr;
    }

    public String getMakeEn() {
        return makeEn;
    }

    public void setMakeEn(String makeEn) {
        this.makeEn = makeEn;
    }

    public GetAuctionInfo getGetAuctionInfo() {
        return AuctionInfo;
    }

    public void setGetAuctionInfo(GetAuctionInfo AuctionInfo) {
        this.AuctionInfo = AuctionInfo;
    }


    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }
}