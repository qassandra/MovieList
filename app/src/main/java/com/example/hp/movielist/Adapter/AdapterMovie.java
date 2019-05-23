package com.example.hp.movielist.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hp.movielist.DetailMovie;
import com.example.hp.movielist.R;
import com.example.hp.movielist.model.ModelMovies;

import java.util.ArrayList;

public class AdapterMovie extends RecyclerView.Adapter<AdapterMovie.ViewHolder> {

    ArrayList<ModelMovies> modelMovies;
    Context context;

    public AdapterMovie(Context context, ArrayList<ModelMovies> modelMovies){
        this.modelMovies = modelMovies;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ModelMovies movies = modelMovies.get(i);
        viewHolder.judul.setText(movies.getTitle());
        viewHolder.release.setText(movies.getRelease_date());
        Glide.with(context).load("https://image.tmdb.org/t/p/w154/"+movies.getPoster_path()).into(viewHolder.gambar);
    }

    @Override
    public int getItemCount() {
        return modelMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView judul,release;
        ImageView gambar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.judul);
            release = itemView.findViewById(R.id.release);
            gambar = itemView.findViewById(R.id.gambar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    ModelMovies model= modelMovies.get(pos);

                    Intent intent = new Intent(context, DetailMovie.class);
                    intent.putExtra("title",model.getTitle());
                    intent.putExtra("poster_path",model.getPoster_path());
                    intent.putExtra("release",model.getRelease_date());
                    intent.putExtra("overview",model.getOverview());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}