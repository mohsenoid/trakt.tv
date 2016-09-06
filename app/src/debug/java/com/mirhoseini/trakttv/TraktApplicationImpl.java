package com.mirhoseini.trakttv;

import timber.log.Timber;

/**
 * Created by Mohsen on 19/07/16.
 */

public class TraktApplicationImpl extends TraktApplication {

    @Override
    public void initApplication() {

        //initialize Timber to log in debug version
        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                //adding line number to logs
                return super.createStackElementTag(element) + ":" + element.getLineNumber();
            }
        });

    }
}
