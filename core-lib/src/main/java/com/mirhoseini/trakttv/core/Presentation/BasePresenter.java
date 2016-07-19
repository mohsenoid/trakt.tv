package com.mirhoseini.trakttv.core.Presentation;

/**
 * Created by Mohsen on 19/07/16.
 */

public interface BasePresenter<T> {

    void setView(T view);

    void destroy();

}
