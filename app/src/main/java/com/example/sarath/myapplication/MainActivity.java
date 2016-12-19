package com.example.sarath.myapplication;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.sarath.customlistviewvolley.adapter.CustomListAdapter;
import com.sarath.customlistviewvolley.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String url= "http://api.androidhive.info/json/movies.json";
    private ProgressDialog progressDialog;
    private List<Movie> movieList = new ArrayList<Movie>();
    private ListView listView;
    private CustomListAdapter customListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);
        customListAdapter = new CustomListAdapter(this,movieList);
        listView.setAdapter(customListAdapter);

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        /*getActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#1b1b1b")));
*/
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        hidePDialog();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                Movie movie = new Movie();
                                movie.setTitle(object.getString("title"));
                                movie.setThumbnailUrl(object.getString("image"));
                                movie.setRating(((Number) object.get("rating"))
                                .doubleValue());
                                movie.setYear(object.getInt("releaseYear"));

                                JSONArray genreArray = object.getJSONArray("genre");
                                ArrayList<String> genre = new ArrayList<String>();
                                for (int j = 0; j < genreArray.length(); j++) {
                                    genre.add((String) genreArray.get(j));
                                }
                                movie.setGenre(genre);

                                movieList.add(movie);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        customListAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
              hidePDialog();
            }
    });
        AppController.getInstance().addToRequestQueue(movieReq);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (progressDialog!=null){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
