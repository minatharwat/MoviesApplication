package com.example.user.moviesapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.moviesapplication.NetworkThreads.FetchReviews;
import com.example.user.moviesapplication.NetworkThreads.FetchTrailers;
import com.example.user.moviesapplication.StorageDatabase.MovieRealm;
import com.example.user.moviesapplication.models.Result;
import com.example.user.moviesapplication.models.ResultTrailers;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import static com.example.user.moviesapplication.MainActivity.realm;


public class DetailFragment extends Fragment {

    Intent intent;
    Result Movie;
    ImageView poster;
    String key;
    ResultTrailers trailer;
    private Context mcontext;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private static final String urlMoviePartOne="https://api.themoviedb.org/3/movie/";
    private static final String video="/videos?";
    private static final String Review="/reviews?";
    private FetchTrailers trailerData;
    private FetchReviews  reviewData;
    private int id_movie;
    private static final String apiKey = "api_key=b2b4514f6ae8ede688d60397f2552189";
    private MainActivity scontext;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view_detailed;
        view_detailed  = inflater.inflate(R.layout.fragment_detail, container, false);

         poster= (ImageView) view_detailed.findViewById(R.id.poster);


        if(MainActivity.two_pane) {
            Bundle sendbundle = getArguments();
             Movie = sendbundle.getParcelable("SelectedMovie");
        }else{

            intent = getActivity().getIntent();
            Movie = intent.getParcelableExtra("SelectedMovie");

        }
        
        ////Todo

        Button button=(Button)view_detailed.findViewById(R.id.button) ;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


          try {

              realm.beginTransaction();
              MovieRealm mrealm = new MovieRealm();
              mrealm.setId(Movie.getId());
              mrealm.setImageURl(Movie.getPosterPath());
              mrealm.setRate(Movie.getVoteAverage());
              mrealm.setTitle(Movie.getOriginalTitle());
              mrealm.setOverview(Movie.getOverview());
              mrealm.setRelease_date(Movie.getReleaseDate());
              MovieRealm realmMovierealm = realm.copyToRealm(mrealm);
              realm.commitTransaction();


              Intent intent = new Intent(getActivity(), MainActivity.class);
              intent.putExtra("user", Parcels.wrap(Movie));
              startActivity(intent);
          }catch (Exception  o){

              Toast.makeText(getActivity(),"this movie is already exist",Toast.LENGTH_SHORT).show();
          }finally {
              //realm.commitTransaction();
              Toast.makeText(getActivity(),"this movie is already exist",Toast.LENGTH_SHORT).show();
          }

            }
        });







        ////// TODO: 25/11/2016  
        

        Picasso.with(mcontext).load("https://image.tmdb.org/t/p/w185/" + Movie.getPosterPath()).error(R.drawable.vi).into(poster);

        TextView name=(TextView)view_detailed.findViewById(R.id.name) ;
        name.setText(Movie.getOriginalTitle());


        TextView rate=(TextView)view_detailed.findViewById(R.id.rate) ;
        rate.setText( Movie.getVoteAverage()+"/"+10);

        TextView overview=(TextView)view_detailed.findViewById(R.id.overview) ;

        overview.setText(Movie.getOverview());



        TextView releaseDate=(TextView)view_detailed.findViewById(R.id.releaseDate) ;
        releaseDate.setText("Release Date  "+""+Movie.getReleaseDate());

       // Toast.makeText(getActivity(),"/"+Movie.getId()+"/", Toast.LENGTH_LONG).show();





        ////// TODO: 19/11/2016
        id_movie=Movie.getId();
        trailerData = new FetchTrailers(getActivity());
        trailerData.execute(urlMoviePartOne+id_movie+video+apiKey);
        reviewData=new FetchReviews(getActivity());
        reviewData.execute(urlMoviePartOne+id_movie+Review+apiKey);
        //reviewData = new FetchReviews(getActivity());
        //reviewData.execute(urlMoviePartOne+id_movie+Review+apiKey);
        ////// TODO: 19/11/2016

        recyclerView=(RecyclerView)view_detailed.findViewById(R.id.TrailerRecyclerView);
        recyclerView2=(RecyclerView)view_detailed.findViewById(R.id.ReviewRecyclerView);



        return view_detailed;

    }
}
