package com.mirhoseini.trakttv.view.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mirhoseini.trakttv.BR;
import com.mirhoseini.trakttv.R;
import com.mirhoseini.trakttv.di.component.ApplicationComponent;

import javax.inject.Inject;

import tv.trakt.api.model.Movie;

/**
 * Created by Mohsen on 23/09/16.
 */

public class DetailsFragment extends BaseFragment {

    private static final String ARG_MOVIE = "movie";

    @Inject
    Context context;
    @Inject
    Resources resources;


    private Movie movie;
    private ViewDataBinding binding;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(Movie movie) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie = (Movie) getArguments().getSerializable(ARG_MOVIE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        binding = DataBindingUtil.bind(view);
        if (null != movie) {
            binding.setVariable(BR.movie, movie);
            binding.executePendingBindings();
        }

//        ActivityCompat.startPostponedEnterTransition(getActivity());

        return view;
    }

    @Override
    protected void injectDependencies(ApplicationComponent component) {
        component.inject(this);
    }

}
