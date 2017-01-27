package com.example.user.moviesapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.user.moviesapplication.models.Result;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static com.example.user.moviesapplication.MainFragment.flag;

public class MainActivity extends AppCompatActivity implements NameListener {


    public static Realm realm;
    Toolbar toolbar;
    MainFragment mainFragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public static boolean two_pane =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            mainFragment = new MainFragment();
            //make the activity the listener of the fragement
            mainFragment.Namelistener(this);
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.MainFragment, mainFragment, "MainFragment");
            fragmentTransaction.commit();

        } else {
            mainFragment = new MainFragment();
            //make the activity the listener of the fragement
            mainFragment.Namelistener(this);
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.MainFragment, mainFragment);
            fragmentTransaction.commit();

        }
        //check the two_pane
      if(null!=findViewById(R.id.DetailFragment)){

            two_pane=true;
        }


        realm = Realm.getInstance(
                new RealmConfiguration.Builder(this).name("theRealm.realm").build());









        //setHasOptionsMenu();

    }

    @Override
    public void set_the_name(Result movie) {

      //case 1 pane
       if(!two_pane) {

           if (flag == 0) {

               Intent intent = new Intent(this, DetailActivity.class);
               intent.putExtra("SelectedMovie", movie);
               startActivity(intent);

           } else if (flag == 1) {

               Intent intent = new Intent(this, DetailActivity.class);
               intent.putExtra("SelectedMovie", movie);
               startActivity(intent);
           } else if (flag == 2) {

               Intent intent = new Intent(this, DetailActivity.class);
               intent.putExtra("SelectedMovie", movie);
               startActivity(intent);
           }

       }else{
           //make the case 2pane
           DetailFragment detailFragment=new DetailFragment();
           Bundle Extras=new Bundle();
           Extras.putParcelable("SelectedMovie",movie);
           detailFragment.setArguments(Extras);
           getSupportFragmentManager().beginTransaction().replace(R.id.DetailFragment,detailFragment,"").commit();


       }


    }

    public void updatGridView(List<Result> s) {
        mainFragment.updateGridView(s);


    }



/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_TopRated) {
            new Fetchmoviesdata(this).execute("https://api.themoviedb.org/3/movie/top_rated?api_key=b2b4514f6ae8ede688d60397f2552189");
            return true;
        }
        else if (id == R.id.action_popularity) {
             new Fetchmoviesdata(this).execute("http://api.themoviedb.org/3/movie/popular?api_key=b2b4514f6ae8ede688d60397f2552189");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/

}
