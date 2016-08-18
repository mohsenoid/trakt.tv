package com.mirhoseini.trakttv.view.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.mirhoseini.trakttv.TraktApplication;
import com.mirhoseini.trakttv.di.component.ApplicationComponent;

/**
 * Created by Mohsen on 19/07/16.
 */

public abstract class BaseFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        injectDependencies(TraktApplication.getComponent());

        // can be used for general purpose in all Fragments of Application
    }

    protected abstract void injectDependencies(ApplicationComponent component);

    protected abstract void initBindings();
}