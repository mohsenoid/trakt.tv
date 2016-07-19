package com.mirhoseini.trakttv.core.view;

/**
 * Created by Mohsen on 19/07/16.
 */

public interface BaseView {

    void showProgress();

    void hideProgress();

    void showMessage(String message);

    void updateProgress(String newMessage);

    void showOfflineMessage();

    void showConnectionError();

    void showRetryMessage();

}
