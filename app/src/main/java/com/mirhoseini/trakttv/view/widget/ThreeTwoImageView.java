package com.mirhoseini.trakttv.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Mohsen on 24/09/2016.
 */

public class ThreeTwoImageView extends ImageView {
    public ThreeTwoImageView(Context context) {
        super(context);
    }

    public ThreeTwoImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThreeTwoImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int ThreeTwoHeightMeasure = MeasureSpec.getSize(widthMeasureSpec) * 2 / 3;
        int ThreeTwoHeightMeasureSpec = MeasureSpec.makeMeasureSpec(ThreeTwoHeightMeasure, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, ThreeTwoHeightMeasureSpec);
    }
}
