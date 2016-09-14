package com.mirhoseini.trakttv.core.viewmodel;

import rx.Observable;

/**
 * Created by Mohsen on 18/08/16.
 */
public interface BaseViewModel {

    Observable<Boolean> isLoadingObservable();

}
