package com.example.movies;

import android.graphics.Movie;
import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView.LayoutManager;
import android.widget.ImageView;

//import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.Network.Model.PopularResponse;
import com.example.movies.Network.Model.ResultsItem;
import com.example.movies.Network.MoviesService;
import com.example.movies.Network.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String BASE_IMAGE = "https://image.tmdb.org/t/p/original/";
    private static final String apiKey=BuildConfig.MOVIE_KEY;

    private ImageView imageView;

    private RecyclerView recyclerView;
    private AdapterRecycler adapterRecycler;
    private MoviesService service = RetrofitInstance.Retrofit().create(MoviesService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList films = new ArrayList();


        imageView = findViewById(R.id.imagenItem);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_movie);

        //LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this,numberOfColumns));

        obterDados(films);
    }

    public void obterDados(ArrayList lista) {

        service.fetchPopular(apiKey).enqueue(new Callback<PopularResponse>() {
            @Override
            public void onResponse(Call<PopularResponse> call, Response<PopularResponse> response) {
                List<ResultsItem> list = response.body().getResults();

                adapterRecycler = new AdapterRecycler(MainActivity.this,list);

                recyclerView.setAdapter(adapterRecycler);


            }

            @Override
            public void onFailure(Call<PopularResponse> call, Throwable t) {

            }
        });
        }




}