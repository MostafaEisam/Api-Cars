package com.example.mostafaeisam.apicars.responses;

import com.example.mostafaeisam.apicars.classes.Items;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetCarsListResponse {
    @SerializedName("Cars")
    private ArrayList<Items> carsList;
    @SerializedName("RefreshInterval")
    private int RefreshInterval;
    @SerializedName("Ticks")
    private String Ticks;
    @SerializedName("count")
    private String count;

    public ArrayList<Items> getCarsList() {
        return carsList;
    }

    public void setCarsList(ArrayList<Items> carsList) {
        this.carsList = carsList;
    }

    public int getRefreshInterval() {
        return RefreshInterval;
    }

    public void setRefreshInterval(int refreshInterval) {
        RefreshInterval = refreshInterval;
    }

    public String getTicks() {
        return Ticks;
    }

    public void setTicks(String ticks) {
        Ticks = ticks;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
