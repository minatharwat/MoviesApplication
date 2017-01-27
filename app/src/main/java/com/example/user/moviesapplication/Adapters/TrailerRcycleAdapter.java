package com.example.user.moviesapplication.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.moviesapplication.R;
import com.example.user.moviesapplication.models.ResultTrailers;

import java.util.List;

/**
 * Created by User on 22/11/2016.
 */
public class TrailerRcycleAdapter extends RecyclerView.Adapter<TrailerRcycleAdapter.Trailerviewholder>{


    List<ResultTrailers> trailers;
    private Activity context;
    public TrailerRcycleAdapter(List<ResultTrailers> trailers){

        this.trailers=trailers;

    }
    @Override
    public Trailerviewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row= LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item,parent,false);
        Trailerviewholder holder=new Trailerviewholder(row);
        return holder;

    }

    @Override
    public void onBindViewHolder(Trailerviewholder holder, int position) {
        ResultTrailers trailer=trailers.get(position);
        holder.movietrailer.setText(trailer.getName());

    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }
    class Trailerviewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView movietrailer;
        TextView key;
        public Trailerviewholder(View itemView) {
            super(itemView);
itemView.setOnClickListener(this);
            movietrailer=(TextView)itemView.findViewById(R.id.trailertext);


        }

        @Override
        public void onClick(View v) {
          int position=getLayoutPosition();
            final Intent intent;

            ResultTrailers trailer=trailers.get(position);
            Toast.makeText(v.getContext(),"click works"+position,Toast.LENGTH_SHORT).show();
            intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+trailer.getKey()));
            itemView.getContext().startActivity(intent);
        }
    }

}
