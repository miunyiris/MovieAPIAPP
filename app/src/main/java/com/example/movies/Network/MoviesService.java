package com.example.movies.Network;

import com.example.movies.Network.ListOfMovies.PopularResponse;
import com.example.movies.Network.Model.Movie.MovieResponse;
import com.example.movies.Network.ListOfMovies.PopularResponse;
import com.example.movies.Network.ListOfMovies.ResultsItem;
import com.example.movies.Network.TrailerVideos.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesService {

    //https://api.themoviedb.org/3/movie/550?api_key;

    @GET("{id}")
    Call<MovieResponse> fetchMovieItem(@Path("id") int id, @Query("api_key") String key);

    @GET("popular")
    Call<PopularResponse> fetchPopular(@Query("api_key") String key);

    @GET("{id}/videos")
    Call<TrailerResponse> fetchTrailer(@Path("id") int id, @Query("api_key") String key);

    /*@GET("{id}/videos")
    Call<TrailerResponse> fetchTrailer( @Query("api_key") String key);*/
}
