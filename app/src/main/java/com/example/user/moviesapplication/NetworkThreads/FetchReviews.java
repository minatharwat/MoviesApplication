package com.example.user.moviesapplication.NetworkThreads;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.user.moviesapplication.Adapters.ReviewRcycleAdapter;
import com.example.user.moviesapplication.R;
import com.example.user.moviesapplication.models.ResultReviews;
import com.example.user.moviesapplication.models.Reviews;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by User on 18/11/2016.
 */
public class FetchReviews extends AsyncTask<String, String, List<ResultReviews>> {

    private RecyclerView recyclerView2;
    private Activity scontext;
    private List<ResultReviews> arrayofreviews;


    public List<ResultReviews> getArrayofreviews() {
        return arrayofreviews;
    }

    public FetchReviews(Activity scontext) {
        this.scontext = scontext;
    }

    @Override
    protected List<ResultReviews> doInBackground(String... urls) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String reviewsJson = null;


        try {
            Log.d("connection", "check a");
            //urls[0]
            URL url = new URL(urls[0]);
            Log.d("connection", "check b");
            urlConnection = (HttpURLConnection) url.openConnection();
            Log.d("connection", "check c");
            urlConnection.setRequestMethod("GET");
            Log.d("connection", "check d");
            urlConnection.connect();
            Log.d("connection", "checked");
            InputStream inputStream = urlConnection.getInputStream();
            Log.d("connection", "check 1");

            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {

                reviewsJson = null;
            }
            Log.d("connection", "check 2");
            reader = new BufferedReader(new InputStreamReader(inputStream));
            Log.d("connection", "check 3");
            String line;
            Log.d("connection", "check 4");
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }
            Log.d("connection", "check 5");


            reviewsJson = buffer.toString();
            Log.d("connection", "check 6");
            Gson gson2 = new GsonBuilder().create();
            Reviews p = gson2.fromJson(reviewsJson, Reviews.class);
            arrayofreviews = p.getReviews();

            return arrayofreviews;


        } catch (IOException e) {
            Log.e("thefragment", "Error ", e);

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
        return null;


    }


    @Override
    protected void onPostExecute(List<ResultReviews> s) {
        super.onPostExecute(s);
        //gridv = (GridView) mcontext.findViewById(R.id.gridmovie);
        if(s!=null){

            recyclerView2=(RecyclerView)scontext.findViewById(R.id.ReviewRecyclerView);
            recyclerView2.setLayoutManager(new LinearLayoutManager(scontext));
            ReviewRcycleAdapter R_adapter=new ReviewRcycleAdapter(s);
            recyclerView2.setAdapter(R_adapter);
        }
        else
        {
            Toast.makeText(scontext, "No Internet Connection ", Toast.LENGTH_LONG).show();
        }

    }

}

