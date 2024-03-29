package com.example.moviecatalogue.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.moviecatalogue.MovieDetailActivity;
import com.example.moviecatalogue.model.Movie;
import com.example.moviecatalogue.R;

import java.util.ArrayList;
import java.util.Collections;

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
        Collections.sort(listMovie);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_movie
                , viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.rvLayout.setVisibility(View.INVISIBLE);
        return viewHolder;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull final ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        AlphaAnimation alphaAnim = new AlphaAnimation(0.0f,1.0f);
        alphaAnim.setDuration(1500);
        alphaAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                holder.rvLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        holder.rvLayout.startAnimation(alphaAnim);
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
        Glide.with(context)
                .load(Movie.SMALL_IMG + movie.getPosterPath())
                .apply(new RequestOptions().centerCrop())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25,3)))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        if(movie.getPosterPath().isEmpty()){
                            viewHolder.rvLayout.setBackground(context.getResources().getDrawable(android.R.color.black));
                        }else{
                            viewHolder.rvLayout.setBackground(resource);
                        }
                    }
                });
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
        RelativeLayout rvLayout;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            rvLayout = itemView.findViewById(R.id.rv_layout);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvReleaseDate = itemView.findViewById(R.id.tv_item_date);
            tvOverview = itemView.findViewById(R.id.tv_item_overview);
            imgPoster = itemView.findViewById(R.id.img_item_poster);
        }
    }
}
