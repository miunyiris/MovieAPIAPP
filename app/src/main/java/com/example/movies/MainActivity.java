package com.example.movies;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

//import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movies.Network.Model.Movie.MovieResponse;
import com.example.movies.Network.MoviesService;
import com.example.movies.Network.RetrofitInstance;
import com.example.movies.Network.ListOfMovies.ResultsItem;
import com.example.movies.Network.ListOfMovies.PopularResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String BASE_IMAGE = "https://image.tmdb.org/t/p/original/";

    private RecyclerView recyclerView;
    private AdapterRecycler adapterRecycler;
    private MoviesService service = RetrofitInstance.Retrofit().create(MoviesService.class);

    private MoviesService popular = RetrofitInstance.Retrofit().create(MoviesService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList films = new ArrayList();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_movie);

        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this,numberOfColumns));

        obterDados(films);
    }

    public void obterDados(ArrayList lista){
        popular.fetchPopular("1b331259837a3ef33d777aea65e05723").enqueue(new Callback<PopularResponse>(){

                    @Override
                    public void onResponse(Call<PopularResponse> call, Response<PopularResponse> response) {
                        List<ResultsItem> list = response.body().getResults();

                        adapterRecycler = new AdapterRecycler(MainActivity.this, list);
                        recyclerView.setAdapter(adapterRecycler);

                    }

                    @Override
                    public void onFailure(Call<PopularResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "No Internet conection", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public  boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular:
               // newGame();
                return true;
            case R.id.favorites:
                int id = item.getItemId();
                if (id == R.id.favorites) {
                    Intent startSettingsActivity = new Intent(this, Favoritos.class);
                    startActivity(startSettingsActivity);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}