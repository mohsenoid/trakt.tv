package com.mirhoseini.trakttv.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mirhoseini.trakttv.BR;
import com.mirhoseini.trakttv.R;
import com.mirhoseini.trakttv.view.fragment.PopularMoviesFragment;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.ButterKnife;
import tv.trakt.api.model.Movie;

/**
 * Created by Mohsen on 19/07/16.
 */

public class PopularMoviesRecyclerViewAdapter extends RecyclerView.Adapter<PopularMoviesRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Movie> movies;
    private final PopularMoviesFragment.OnListFragmentInteractionListener listener;

    public PopularMoviesRecyclerViewAdapter(Movie[] movies, PopularMoviesFragment.OnListFragmentInteractionListener listener) {
        this.movies = new ArrayList<>(Arrays.asList(movies));
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Movie movie = movies.get(position);

        holder.movie = movie;
        holder.getBinding().setVariable(BR.movie, movie);
        holder.getBinding().executePendingBindings();

        holder.view.setOnClickListener(v -> {
            if (null != listener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                listener.onListFragmentInteraction(holder.movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void addMoreMovies(Movie[] movies) {
        this.movies.addAll(new ArrayList<>(Arrays.asList(movies)));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View view;
        Movie movie;
        private ViewDataBinding binding;

        public ViewHolder(View view) {
            super(view);
            this.view = view;

            binding = DataBindingUtil.bind(view);

        }

        public ViewDataBinding getBinding() {
            return binding;
        }

    }
}
