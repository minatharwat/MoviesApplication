package com.example.user.moviesapplication.NetworkThreads;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.user.moviesapplication.MainActivity;
import com.example.user.moviesapplication.models.Example;
import com.example.user.moviesapplication.models.Result;
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
 * Created by User on 17/11/2016.
 */


public class Fetchmoviesdata extends AsyncTask<String, String, List<Result>> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ProgressDialog dialog=new ProgressDialog(mcontext);
        dialog.setMessage("Loading..");
        dialog.show();
    }

    //    private GridView gridv;
    private MainActivity mcontext;
    private List<Result> arrayofmovies;


    public List<Result> getArrayofmovies() {
        return arrayofmovies;
    }

    public Fetchmoviesdata(MainActivity mcontext) {
        this.mcontext = mcontext;
    }

    @Override
    protected List<Result> doInBackground(String... urls) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String moviesJsonstr = null;


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

                moviesJsonstr = null;
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


            moviesJsonstr = buffer.toString();
            Log.d("connection", "check 6");
            Gson gson = new GsonBuilder().create();
            Example p = gson.fromJson(moviesJsonstr, Example.class);
            arrayofmovies = p.getResults();

            Log.d("data+"+arrayofmovies,"d");

            return arrayofmovies;


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
    protected void onPostExecute(List<Result> s) {
        super.onPostExecute(s);

        mcontext.updatGridView(s);

    }

}

