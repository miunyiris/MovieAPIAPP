package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.movies.Network.ListOfMovies.PopularResponse;
import com.example.movies.Network.ListOfMovies.ResultsItem;
import com.example.movies.Network.Model.Movie.MovieResponse;
import com.example.movies.Network.MoviesService;
import com.example.movies.Network.RetrofitInstance;
import com.example.movies.Network.TrailerVideos.TrailerResponse;
import com.example.movies.Network.TrailerVideos.TrailerResultsItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetails extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterRecyclerTrailers adapterRecycler;
    private ImageView imageView;
    private TextView textViewAno;
    private TextView textViewDescricao;
    private TextView textViewDuracao;
    private TextView textViewTitle;
    private TextView textViewVotos;
    Context context;
    private TextView textViewReviews;
    private int id;
    private MoviesService details = RetrofitInstance.Retrofit().create(MoviesService.class);
    private MoviesService trailer = RetrofitInstance.Retrofit().create(MoviesService.class);
    private ToggleButton Btn;
    private SharedPreferences shared;
    private String imageFav;
    private Button trailerBtn;
    private List<ResultsItem> favItemList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_trailers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//adicionar botao back

        textViewTitle = (TextView) findViewById(R.id.textView_title);
        imageView = findViewById(R.id.imagenDetail);
        textViewAno = (TextView) findViewById(R.id.textView_Ano);
        textViewDescricao = (TextView) findViewById(R.id.textView_Descricao);
        textViewDuracao = (TextView) findViewById(R.id.textView_Duration);
        textViewVotos = (TextView) findViewById(R.id.textView_votos);

        textViewReviews = (TextView) findViewById(R.id.textView_review);

        Btn = findViewById(R.id.tb_favorites);

        DataBaseHelper db= new DataBaseHelper(this);


        shared = getSharedPreferences("favoritos", MODE_PRIVATE);

        trailerBtn = findViewById(R.id.button);

        Bundle extra = this.getIntent().getExtras();
        if(extra != null){
            id = extra.getInt("ID");
        }

        details.fetchMovieItem(id, "1b331259837a3ef33d777aea65e05723").enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                MovieResponse movieResponse = response.body();

                Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Btn.getText()=="MAKE AS FAVORITE")
                        {
                            db.addFavorite(movieResponse);
                        }
                        else db.removeFavorite(movieResponse);
                    }
                });

                String linkImage = "https://image.tmdb.org/t/p/original/" + movieResponse.getPosterPath();
                Glide.with(MovieDetails.this).load(linkImage).into(imageView);

                imageFav = linkImage;

                textViewTitle.setText(movieResponse.getTitle());
                String ano = movieResponse.getReleaseDate();
                textViewAno.setText(ano.substring(0, 4));//Primeiros 4 carateres da string( o ano)
                textViewDuracao.setText(movieResponse.getRuntime() + " min");
                textViewDescricao.setText(movieResponse.getOverview());
                textViewVotos.setText((int) movieResponse.getVoteAverage() + "/10");


            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MovieDetails.this, ":(", Toast.LENGTH_SHORT).show();
            }
        });

        Btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @SuppressLint("ResourceAsColor")
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked){
                   SharedPreferences.Editor editor = shared.edit();
                   editor.putString("id", imageFav);
                   editor.commit();
               }

                else buttonView.setBackgroundColor(R.color.black);
            }
        });

        mostrarDados();

    }

    private void mostrarDados() {

        trailer.fetchTrailer(id, "1b331259837a3ef33d777aea65e05723").enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {

                List<TrailerResultsItem> trailerResponse = response.body().getResults();


                String YOUTUBE_APP_BASE = "vnd.youtube://";
                String YOUTUBE_BASE_URL = "http://www.youtube.com/watch?v=";


                String key = trailerResponse.get(0).getKey();


                trailerBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_APP_BASE + key));

                        startActivity(appIntent);
                    }
                });

                adapterRecycler = new AdapterRecyclerTrailers(MovieDetails.this, trailerResponse);
                recyclerView.setAdapter(adapterRecycler);
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                Toast.makeText(MovieDetails.this, ":(", Toast.LENGTH_SHORT).show();
            }
        });
    }


}