package com.example.hp.movielist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndJSONObjectRequestListener;
import com.example.hp.movielist.Adapter.AdapterMovie;
import com.example.hp.movielist.model.ModelMovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv_movie;
    RecyclerView.Adapter rvAdapter;
ArrayList <ModelMovies> movieModelArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv_movie = findViewById(R.id.rv_movies);
        rv_movie.setHasFixedSize(true);
        RecyclerView.LayoutManager lm_movie = new LinearLayoutManager(this);
        rv_movie.setLayoutManager(lm_movie);
        rv_movie.setAdapter(rvAdapter);
        AndroidNetworking.initialize(this);

        AndroidNetworking.get("https://api.themoviedb.org/3/discover/movie?api_key=666a2970fcc09551a95e628f24baf8dd&language=en-US")
                .setPriority(Priority.LOW)
                .build()
                .getAsOkHttpResponseAndJSONObject(new OkHttpResponseAndJSONObjectRequestListener() {
                    @Override
                    public void onResponse(Response okHttpResponse, JSONObject response) {

                        Log.e("tag", "onResponse: "+response );
                        try {
                            JSONObject json = new JSONObject(response.toString());
                            JSONArray results = json.getJSONArray("results");
                            for (int i = 0; i< results.length(); i++){
                                JSONObject jsonObject = results.getJSONObject(i);
                                String title = jsonObject.getString("title");
                                String poster_path = jsonObject.getString("poster_path");
                                String release_date = jsonObject.getString("release_date");
                                String overview = jsonObject.getString("overview");

                                ModelMovies movieModel = new ModelMovies();
                                movieModel.setOverview(overview);
                                movieModel.setTitle(title);
                                movieModel.setPoster_path(poster_path);
                                movieModel.setRelease_date(release_date);
                                movieModelArrayList.add(movieModel);
                                // masukin datanya ke recycler view.
                                rvAdapter = new AdapterMovie(getApplicationContext(),movieModelArrayList);
                                rvAdapter.notifyDataSetChanged();
                                rv_movie.setAdapter(rvAdapter);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }
}
