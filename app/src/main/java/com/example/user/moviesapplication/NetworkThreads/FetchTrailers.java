package com.example.user.moviesapplication.NetworkThreads;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.user.moviesapplication.Adapters.TrailerRcycleAdapter;
import com.example.user.moviesapplication.R;
import com.example.user.moviesapplication.models.ResultTrailers;
import com.example.user.moviesapplication.models.Trailers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class FetchTrailers extends AsyncTask<String, String, List<ResultTrailers>> {

    private RecyclerView recyclerView;

    private Activity mcontext;
    private List<ResultTrailers> arrayofTrailers;

    public List<ResultTrailers> getArrayoftrailers() {
        return arrayofTrailers;
    }

    public FetchTrailers(Activity mcontext) {
        this.mcontext = mcontext;
    }

    @Override
    protected List<ResultTrailers> doInBackground(String... urls) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String trailersJson = null;


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

                trailersJson = null;
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


            trailersJson = buffer.toString();
            Log.d("connection", "check 6");
            Gson gson1 = new GsonBuilder().create();
            Trailers pT = gson1.fromJson(trailersJson, Trailers.class);
            arrayofTrailers = pT.getTrailers();

            return arrayofTrailers;



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
    protected void onPostExecute(List<ResultTrailers> s) {
        super.onPostExecute(s);
if(s!=null) {
    //Toast.makeText(mcontext, "onpost trailer is data  " , Toast.LENGTH_LONG).show();


    recyclerView=(RecyclerView) mcontext.findViewById(R.id.TrailerRecyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(mcontext));
    TrailerRcycleAdapter T_adapter=new TrailerRcycleAdapter(s);
    recyclerView.setAdapter(T_adapter);


}
        else
{
    Toast.makeText(mcontext, "No Internet Connection to get the trailers " , Toast.LENGTH_LONG).show();

}
        /*
        gridv = (GridView) mcontext.findViewById(R.id.gridmovie);
        Toast.makeText(mcontext, "onpost", Toast.LENGTH_LONG).show();

        movieadapter movieaadapter = new movieadapter(mcontext, s);
        gridv.setAdapter(movieaadapter);*/


    }

}


