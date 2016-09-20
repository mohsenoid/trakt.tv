package com.mirhoseini.trakttv.core.util;

/**
 * Created by Mohsen on 19/07/16.
 */

public class Constants {

    public static final String BASE_URL = "https://api.trakt.tv/";

    public static final String API_CONTENT_TYPE_JSON = "application/json";
    public static final String API_VERSION_2 = "2";
    public static final String API_KEY = "ad005b8c117cdeee58a1bdb7089ea31386cd489b21e14b19818c91511f12a086";
    public static final String API_EXTENDED_FULL_IMAGES = "full,images";

    public static final int PAGE_ROW_LIMIT = 10;

    public static final int NETWORK_CONNECTION_TIMEOUT = 30; // 30 sec
    public static final long CACHE_SIZE = 10 * 1024 * 1024; // 10 MB
    public static final int CACHE_MAX_AGE = 2; // 2 min
    public static final int CACHE_MAX_STALE = 7; // 7 day

    public static final long DELAY_BEFORE_SEARCH_STARTED = 1; // 2 sec
    public static final int RECYCLER_VIEW_ITEM_SPACE = 48;

}
