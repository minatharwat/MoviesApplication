package com.example.user.moviesapplication.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.moviesapplication.R;
import com.example.user.moviesapplication.models.ResultReviews;

import java.util.List;

/**
 * Created by User on 20/11/2016.
 */
public class ReviewRcycleAdapter extends RecyclerView.Adapter<ReviewRcycleAdapter.RecycleViewHolder> {
    List<ResultReviews> reviews;
    public ReviewRcycleAdapter(List<ResultReviews> reviews){

        this.reviews=reviews;

    }
    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item,parent,false);
        RecycleViewHolder holder=new RecycleViewHolder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecycleViewHolder holder, int position) {
        ResultReviews Review=reviews.get(position);
        holder.moviereview_author.setText("Author Name :"+Review.getAuthor());
        holder.content.setText(Review.getContent());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class RecycleViewHolder extends RecyclerView.ViewHolder{
TextView moviereview_author;
        TextView content;

        public RecycleViewHolder(View itemView) {
            super(itemView);
            moviereview_author=(TextView)itemView.findViewById(R.id.Review_textView);
            content=(TextView)itemView.findViewById(R.id.content);
        }


    }




}
