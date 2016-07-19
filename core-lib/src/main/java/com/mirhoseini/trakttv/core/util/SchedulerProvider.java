package com.mirhoseini.trakttv.core.util;

import rx.Scheduler;

/**
 * Created by Mohsen on 19/07/16.
 */

public interface SchedulerProvider {

    Scheduler mainThread();

    Scheduler backgroundThread();

}
