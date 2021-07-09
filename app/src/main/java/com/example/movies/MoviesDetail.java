package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movies.Network.Model.MovieResponse;
import com.example.movies.Network.MoviesService;
import com.example.movies.Network.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesDetail extends AppCompatActivity {
    int movieReceive;
    private MoviesService service = RetrofitInstance.Retrofit().create(MoviesService.class);
    private static final String apiKey=BuildConfig.MOVIE_KEY;
    //private SecondAdapter secondAdapter;
    private TextView tittleText;
    private ImageView posterPath;
    private TextView yearText;
    private TextView timeText;
    private TextView voteText;
    private TextView sinopseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.secondview);
        setContentView(R.layout.movie_detail);

        posterPath= findViewById(R.id.posterPath);
        tittleText= findViewById(R.id.titleText);
        yearText= findViewById(R.id.yearText);
        timeText= findViewById(R.id.timeText);
        voteText= findViewById(R.id.voteText);
        sinopseText= findViewById(R.id.sinopseText);


        //recyclerView2 = (RecyclerView) findViewById(R.id.recycler_movie);

        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            movieReceive = getIntent().getIntExtra("ID",0);
            moviesInfo(movieReceive);
        }



    }


    public void moviesInfo(int movieReceive)
    {
        service.fetchMovie(movieReceive,apiKey).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                MovieResponse  movie = response.body();

                String movieResponse = "https://image.tmdb.org/t/p/original/"+movie.getBackdropPath();
                Glide.with(MoviesDetail.this).load(movieResponse).into(posterPath);

                tittleText.setText(movie.getTitle());
                yearText.setText(movie.getReleaseDate());
                timeText.setText(movie.getRuntime()+"");
                String votos=movie.getVoteAverage()+"/10";
                voteText.setText(votos);
                sinopseText.setText(movie.getOverview());

                //secondAdapter= new SecondAdapter(SecondActivity.this,list);
                //recyclerView2.setAdapter(secondAdapter);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

}