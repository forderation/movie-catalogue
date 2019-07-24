package com.example.submission3.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.submission3.MovieDetailActivity;
import com.example.submission3.PlainOldJavaObject.Movie;
import com.example.submission3.R;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Movie> listMovie = new ArrayList<>();

    public MovieAdapter(Context context) {
        this.context = context;
    }

    private ArrayList<Movie> getListMovie() {
        return listMovie;
    }

    public void setListMovie(ArrayList<Movie> itemList) {
        listMovie.clear();
        listMovie.addAll(itemList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_movie
                , viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Movie movie = getListMovie().get(i);
        viewHolder.tvTitle.setText(movie.getOriginalTitle());
        viewHolder.tvReleaseDate.setText(movie.getReleaseDate());
        viewHolder.tvOverview.setText(movie.getOverview());

        Glide.with(context)
                .load(Movie.PATH_IMG + movie.getPosterPath())
                .apply(new RequestOptions().fitCenter())
                .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(25,3)))
                .into(viewHolder.imgPoster);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.TAG_DETAIL_MOVIE, movie);
                Pair<View, String> p1 = Pair.create((View) viewHolder.imgPoster, "poster");
                Pair<View, String> p2 = Pair.create((View) viewHolder.tvTitle, "title");
                Pair<View, String> p3 = Pair.create((View) viewHolder.tvReleaseDate, "release_date");
                Pair<View, String> p4 = Pair.create((View) viewHolder.tvOverview, "overview");
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) context, p1, p2, p3, p4);
                context.startActivity(intent,options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvReleaseDate, tvOverview;
        ImageView imgPoster;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvReleaseDate = itemView.findViewById(R.id.tv_item_date);
            tvOverview = itemView.findViewById(R.id.tv_item_overview);
            imgPoster = itemView.findViewById(R.id.img_item_poster);
        }
    }
}
