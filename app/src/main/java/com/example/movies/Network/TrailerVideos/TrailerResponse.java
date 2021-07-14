package com.example.movies.Network.TrailerVideos;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TrailerResponse{

    @SerializedName("id")
    private int id;

    @SerializedName("results")
    private List<TrailerResultsItem> results;

    public int getId(){
        return id;
    }

    public List<TrailerResultsItem> getResults(){
        return results;
    }
}
