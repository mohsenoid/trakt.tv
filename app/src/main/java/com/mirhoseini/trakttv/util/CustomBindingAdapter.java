package com.mirhoseini.trakttv.util;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.mirhoseini.trakttv.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Mohsen on 19/07/16.
 */
public class CustomBindingAdapter {
    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String url) {
        Picasso.with(imageView.getContext())
                .load(url)
                .error(R.drawable.while_logo)
                .into(imageView);
    }
}
