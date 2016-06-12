package com.sample.andremion.musicplayer.view;

import android.content.Context;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.View;

public class InsetsPercentRelativeLayout extends PercentRelativeLayout {

    public InsetsPercentRelativeLayout(Context context) {
        this(context, null, 0);
    }

    public InsetsPercentRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InsetsPercentRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        ViewCompat.setOnApplyWindowInsetsListener(this, new android.support.v4.view.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                setWindowInsets(insets);
                return insets.consumeSystemWindowInsets();
            }
        });
    }

    private void setWindowInsets(WindowInsetsCompat insets) {
        // Now dispatch them to our children
        for (int i = 0, z = getChildCount(); i < z; i++) {
            final View child = getChildAt(i);
            insets = ViewCompat.dispatchApplyWindowInsets(child, insets);
            if (insets.isConsumed()) {
                break;
            }
        }
    }

}
