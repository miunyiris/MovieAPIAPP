package com.example.movies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movies.Network.ListOfMovies.ResultsItem;
import com.example.movies.Network.TrailerVideos.TrailerResultsItem;

import java.util.List;

public class AdapterRecyclerTrailers extends RecyclerView.Adapter<AdapterRecyclerTrailers.Holder> {

    Context context;
    List<TrailerResultsItem> list;


    public AdapterRecyclerTrailers(Context context, List<TrailerResultsItem> list ){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutidList = R.layout.trailer;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutidList,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {

        String YOUTUBE_APP_BASE = "vnd.youtube://" + list.get(i).getKey();;
        String YOUTUBE_BASE_URL = "http://www.youtube.com/watch?v=";

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_APP_BASE));
                //Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_BASE_URL + key));
                context.startActivity(appIntent);
                //startActivity(webIntent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class Holder extends RecyclerView.ViewHolder {
        public Button button;

        public Holder(@NonNull View itemView) {
            super(itemView);
            button = (Button) itemView.findViewById(R.id.buttonTrailer);
        }

        public Button getButtonView() {
            return button;
        }
    }
}



