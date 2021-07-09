package com.example.movies.Network;

import android.graphics.Movie;

import com.example.movies.Network.Model.MovieResponse;
import com.example.movies.Network.Model.PopularResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesService {

    @GET("{id}")
    Call<MovieResponse> fetchMovie(@Path("id") int id, @Query("api_key") String key);

    @GET("popular")
    Call<PopularResponse> fetchPopular(@Query("api_key") String key);

}
