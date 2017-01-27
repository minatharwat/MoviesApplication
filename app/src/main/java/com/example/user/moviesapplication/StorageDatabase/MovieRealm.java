package com.example.user.moviesapplication.StorageDatabase;

import org.parceler.Parcel;

import io.realm.MovieRealmRealmProxy;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by User on 25/11/2016.
 */
@Parcel(implementations = {MovieRealmRealmProxy.class},
        value = Parcel.Serialization.BEAN,
        analyze = { MovieRealm.class })
public class MovieRealm extends RealmObject{
    @PrimaryKey
    private int id;

    private String overview;
    private Double rate;
    private String release_date;
    private String imageURl;
    private String Title;
//setters and getters
    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setImageURl(String imageURl) {
        this.imageURl = imageURl;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getOverview() {
        return overview;
    }

    public Double getRate() {
        return rate;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getImageURl() {
        return imageURl;
    }

    public String getTitle() {
        return Title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {

        return id;
    }
}
