package com.example.user.moviesapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.user.moviesapplication.Adapters.movieadapter;
import com.example.user.moviesapplication.NetworkThreads.Fetchmoviesdata;
import com.example.user.moviesapplication.StorageDatabase.MovieRealm;
import com.example.user.moviesapplication.models.Result;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmQuery;
import io.realm.RealmResults;


public class MainFragment extends Fragment {

    private NameListener nameListener;

    void Namelistener(NameListener nameListener) {
        this.nameListener = nameListener;
    }
    Result movie;
    private GridView movieGrid;
    private RecyclerView recyclerView;
    private Fetchmoviesdata defaultData;
    public Fetchmoviesdata topRatedData;
    public Fetchmoviesdata popularData;
    private double v;
    public static int flag = 0; // if flag =0 we are in default if flag = 1 we are in toprated else we are in poular
    private static final String topRated = "https://api.themoviedb.org/3/movie/top_rated?";
    private static final String apiKey = "api_key=b2b4514f6ae8ede688d60397f2552189";
    private static final String popularity = "http://api.themoviedb.org/3/movie/popular?";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.fragment_main, container, false);



        defaultData = new Fetchmoviesdata((MainActivity) getActivity());
        defaultData.execute(topRated + apiKey);


        movieGrid = (GridView) view.findViewById(R.id.gridmovie);


        movieGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (flag == 0) {
                     movie = defaultData.getArrayofmovies().get(i);
                    nameListener.set_the_name(movie);


                } else if (flag == 1) {

                     movie = topRatedData.getArrayofmovies().get(i);
                    nameListener.set_the_name(movie);
                } else if (flag == 2) {

                     movie = popularData.getArrayofmovies().get(i);
                    nameListener.set_the_name(movie);
                }
                Toast.makeText(getActivity(), "works", Toast.LENGTH_LONG).show();

            }
        });

        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_TopRated) {

            topRatedData = new Fetchmoviesdata((MainActivity) getActivity());
            topRatedData.execute(topRated + apiKey);
            flag = 1;

        } else if (id == R.id.action_popularity) {
            popularData = new Fetchmoviesdata((MainActivity) getActivity());
            popularData.execute(popularity + apiKey);
            flag = 2;

        } else if (id == R.id.action_Favourits) {
            flag=3;
            RealmQuery<MovieRealm> q = MainActivity.realm.where(MovieRealm.class);
            RealmResults<MovieRealm> result1 = q.findAll();
            List<Result> s=new ArrayList<>();
            for (int i = 0; i <result1.size() ; i++) {
             Result r=new Result();
                r.setOriginalTitle(result1.get(i).getTitle());
                r.setOverview(result1.get(i).getOverview());
                r.setPosterPath(result1.get(i).getImageURl());
                r.setReleaseDate(result1.get(i).getRelease_date());
                r.setVoteAverage(result1.get(i).getRate());
                s.add(r);
            }

            updateGridView(s);
            //MovieRealm user = (MovieRealm) Parcels.unwrap(getIntent().getParcelableExtra("user"));
        }
        return true;


    }


    public void updateGridView(List<Result> s) {
        if(s!=null) {

            movieadapter movieaadapter = new movieadapter(getActivity(), s);
            movieGrid.setAdapter(movieaadapter);
        }
        else
        {

            Toast.makeText(getActivity(), "NO Internet Connection", Toast.LENGTH_LONG).show();
        }
    }
}