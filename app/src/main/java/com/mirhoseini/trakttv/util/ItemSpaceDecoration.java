package com.mirhoseini.trakttv.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Mohsen on 20/07/16.
 */
public class ItemSpaceDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public ItemSpaceDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        // Add top margin only for the first item to avoid double space at the beginning of the list
        if (parent.getChildLayoutPosition(view) == 0)
            outRect.top = space;
    }
}
