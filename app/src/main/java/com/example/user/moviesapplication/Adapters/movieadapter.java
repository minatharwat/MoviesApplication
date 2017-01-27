package com.example.user.moviesapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.user.moviesapplication.R;
import com.example.user.moviesapplication.models.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by User on 17/11/2016.
 */
public class movieadapter extends BaseAdapter {
    ImageView posterpath;

    private LayoutInflater inflater;
    private Context mcontext;//context
    private List<Result> results;

    public movieadapter(Context mcontext, List<Result
            > results) {
        this.mcontext = mcontext;
        this.results = results;
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Result getItem(int i) {
        return results.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View converView, ViewGroup viewGroup) {
        inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (converView == null) {

            converView = inflater.inflate(R.layout.grid_item, null);
        }


        posterpath = (ImageView) converView.findViewById(R.id.poster);

        Picasso.with(mcontext).load("https://image.tmdb.org/t/p/w185/" + getItem(position).getPosterPath()).into(posterpath);

        return converView;
    }
}