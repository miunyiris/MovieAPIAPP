package com.example.movies.Views.MainView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movies.Views.SecondView.MovieDetailsActivity;
import com.example.movies.Network.ListOfMovies.ResultsItem;
import com.example.movies.R;

import java.util.List;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.Holder> {

    Context context;
    List<ResultsItem> list;

    public AdapterRecycler(Context context, List<ResultsItem> list ){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutidList = R.layout.movies;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutidList,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        String linkImage = "https://image.tmdb.org/t/p/original/" + list.get(i).getPosterPath();
        Glide.with(context).load(linkImage).into(holder.getImageView());

        holder.getImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("ID",list.get(i).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagenItem);
        }

        public ImageView getImageView() {
            return imageView;
        }
    }
}
