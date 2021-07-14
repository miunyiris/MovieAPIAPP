package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.movies.Network.ListOfMovies.PopularResponse;
import com.example.movies.Network.ListOfMovies.ResultsItem;
import com.example.movies.Network.MoviesService;
import com.example.movies.Network.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Favoritos extends AppCompatActivity {

    private ImageView imageView;
    private RecyclerView recyclerView;
    private AdapterRecycler adapterRecycler;

    private MoviesService favoritos = RetrofitInstance.Retrofit().create(MoviesService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        imageView = findViewById(R.id.imagenItem);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_fovoritos);

        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this,numberOfColumns));

        loadFavorites();


        favoritos.fetchPopular("1b331259837a3ef33d777aea65e05723").enqueue(new Callback<PopularResponse>(){

            @Override
            public void onResponse(Call<PopularResponse> call, Response<PopularResponse> response) {
                List<ResultsItem> list = response.body().getResults();


                adapterRecycler = new AdapterRecycler(Favoritos.this, list);
                recyclerView.setAdapter(adapterRecycler);

            }

            @Override
            public void onFailure(Call<PopularResponse> call, Throwable t) {
                Toast.makeText(Favoritos.this, "No Internet conection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFavorites() {
        SharedPreferences preferences = getSharedPreferences("favoritos", Context.MODE_PRIVATE);

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
                int id = item.getItemId();
                if (id == R.id.popular) {
                    Intent startSettingsActivity = new Intent(this, MainActivity.class);
                    startActivity(startSettingsActivity);
                }
                return true;
            case R.id.favorites:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}