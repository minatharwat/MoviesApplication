
package com.example.user.moviesapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Trailers {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<ResultTrailers> results = new ArrayList<ResultTrailers>();

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The results
     */
    public List<ResultTrailers> getTrailers() {
        return results;
    }

    /**
     * 
     * @param results
     *     The results
     */
    public void setResults(List<ResultTrailers> results) {
        this.results = results;
    }

}
