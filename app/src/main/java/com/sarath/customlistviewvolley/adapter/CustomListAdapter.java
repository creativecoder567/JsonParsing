package com.sarath.customlistviewvolley.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.sarath.myapplication.AppController;
import com.example.sarath.myapplication.R;
import com.sarath.customlistviewvolley.model.Movie;

import java.util.List;

/**
 * Created by sarath on 19/12/16.
 */

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater layoutInflater;
    private List<Movie> movieItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity,List<Movie> movieItems) {
        this.activity = activity;
        this.movieItems = movieItems;
    }

    @Override
    public int getCount() {
        return movieItems.size();
    }

    @Override
    public Object getItem(int location) {
        return movieItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (layoutInflater ==null)
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = layoutInflater.inflate(R.layout.list_row,null);
        if (imageLoader==null)
            imageLoader= AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);
        TextView genre = (TextView) convertView.findViewById(R.id.genre);
        TextView year = (TextView) convertView.findViewById(R.id.releaseYear);

        Movie m = movieItems.get(position);

        thumbNail.setImageUrl(m.getThumbnailUrl(),imageLoader);

        title.setText(m.getTitle());

        rating.setText("Rating: " +String.valueOf(m.getRating()));

        String genreString="";
        for (String str:m.getGenre()){
            genreString+=str+", ";
        }
        genreString = genreString.length()>0?genreString.substring(0,genreString.length()-2):genreString;
        genre.setText(genreString);

        year.setText(String.valueOf(m.getYear()));
        return convertView;
    }
}
