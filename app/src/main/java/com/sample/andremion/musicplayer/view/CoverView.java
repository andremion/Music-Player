/*
 * Copyright (c) 2016. André Mion
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sample.andremion.musicplayer.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Animatable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.sample.andremion.musicplayer.R;

public class CoverView extends ImageView implements Animatable {

    public static final int SHAPE_RECTANGLE = 0;
    public static final int SHAPE_CIRCLE = 1;
    public static final int TACK_ALPHA_TRANSPARENT = 0;
    public static final int TACK_ALPHA_OPAQUE = 255;

    private static final float TRACK_SIZE = 10;
    private static final float TRACK_WIDTH = 1;
    private static final int TRACK_COLOR = Color.parseColor("#56FFFFFF");

    private static final float FULL_ANGLE = 360;
    private static final float HALF_ANGLE = FULL_ANGLE / 2;
    private static final int DURATION = 3000;
    private static final float DURATION_PER_DEGREES = DURATION / FULL_ANGLE;

    private final ValueAnimator mStartAnimator;
    private final int mTrackAlpha;
    private final float mTrackSize;
    private final Paint mTrackPaint;
    private final Path mClipPath = new Path();
    private final Path mRectPath = new Path();
    private final Path mTrackPath = new Path();
    private ValueAnimator mEndAnimator;
    private float mRadius = 0;
    private int mShape = SHAPE_RECTANGLE;

    private Callbacks mCallbacks;

    public CoverView(Context context) {
        this(context, null, 0);
    }

    public CoverView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoverView(Context context, AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final float density = getResources().getDisplayMetrics().density;
        mTrackAlpha = Color.alpha(TRACK_COLOR);
        mTrackSize = TRACK_SIZE * density;
        mTrackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTrackPaint.setStyle(Paint.Style.STROKE);
        mTrackPaint.setStrokeWidth(TRACK_WIDTH * density);
        mTrackPaint.setColor(TRACK_COLOR);

        mStartAnimator = ObjectAnimator.ofFloat(this, View.ROTATION, 0, FULL_ANGLE);
        mStartAnimator.setInterpolator(new LinearInterpolator());
        mStartAnimator.setRepeatCount(Animation.INFINITE);
        mStartAnimator.setDuration(DURATION);
        mStartAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                float current = getRotation();
                float target = current > HALF_ANGLE ? FULL_ANGLE : 0; // Choose the shortest distance to 0 rotation
                float diff = target > 0 ? FULL_ANGLE - current : current;
                mEndAnimator = ObjectAnimator.ofFloat(CoverView.this, View.ROTATION, current, target);
                mEndAnimator.setInterpolator(new LinearInterpolator());
                mEndAnimator.setDuration((int) (DURATION_PER_DEGREES * diff));
                mEndAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        setRotation(0);
                        if (mCallbacks != null) {
                            mCallbacks.onStopAnimation();
                        }
                    }
                });
                mEndAnimator.start();
            }
        });

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CoverView);
        int shape = a.getInt(R.styleable.CoverView_shape, SHAPE_RECTANGLE);
        int alpha = a.getInt(R.styleable.CoverView_trackAlpha, TACK_ALPHA_TRANSPARENT);
        mShape = shape;
        mTrackPaint.setAlpha(alpha * mTrackAlpha / TACK_ALPHA_OPAQUE);
        a.recycle();
    }

    public void setShape(int shape) {
        if (shape != mShape) {
            mShape = shape;
            // Invalidate the radius value
            mRadius = 0;
        }
    }

    public float getTransitionRadius() {
        return mRadius;
    }

    public void setTransitionRadius(float radius) {
        if (radius != mRadius) {
            mRadius = radius;
            final int w = getWidth();
            final int h = getHeight();
            final float centerX = w / 2f;
            final float centerY = h / 2f;
            mClipPath.reset();
            mClipPath.addCircle(centerX, centerY, mRadius, Path.Direction.CW);
            invalidate();
        }
    }

    public int getTrackAlpha() {
        return mTrackPaint.getAlpha() * TACK_ALPHA_OPAQUE / mTrackAlpha;
    }

    public void setTrackAlpha(int alpha) {
        if (alpha != getTrackAlpha()) {
            mTrackPaint.setAlpha(alpha * mTrackAlpha / TACK_ALPHA_OPAQUE);
            invalidate();
        }
    }

    public float getMinRadius() {
        final int w = getMeasuredWidth();
        final int h = getMeasuredHeight();
        return Math.min(w, h) / 2f;
    }

    public float getMaxRadius() {
        final int w = getMeasuredWidth();
        final int h = getMeasuredHeight();
        return (float) Math.hypot(w / 2f, h / 2f);
    }

    public void setCallbacks(Callbacks callbacks) {
        mCallbacks = callbacks;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mRectPath.reset();
        mRectPath.addRect(0, 0, w, h, Path.Direction.CW);

        final float centerX = w / 2f;
        final float centerY = h / 2f;

        if (mRadius == 0) {
            if (SHAPE_CIRCLE == mShape) {
                mRadius = getMinRadius();
            } else {
                mRadius = getMaxRadius();
            }
        }

        mClipPath.reset();
        mClipPath.addCircle(centerX, centerY, mRadius, Path.Direction.CW);

        final int trackRadius = Math.min(w, h);
        final int trackCount = (int) (trackRadius / mTrackSize);
        mTrackPath.reset();
        for (int i = 1; i < trackCount - 2; i++) {
            mTrackPath.addCircle(centerX, centerY, trackRadius * (i / (float) trackCount), Path.Direction.CW);
        }
        if (!isInEditMode()) {
            mTrackPath.op(mRectPath, Path.Op.INTERSECT);
        }

    }

    @Override
    public void draw(Canvas canvas) {
        // Clip before any draw operation
        canvas.clipPath(mClipPath);
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mTrackPath, mTrackPaint);
    }

    @Override
    public void start() {
        mStartAnimator.start();
    }

    @Override
    public void stop() {
        if (isRunning()) {
            mStartAnimator.cancel();
        } else {
            if (mCallbacks != null) {
                mCallbacks.onStopAnimation();
            }
        }
    }

    @Override
    public boolean isRunning() {
        return mStartAnimator.isRunning()
                || (mEndAnimator != null && mEndAnimator.isRunning());
    }

    public interface Callbacks {
        void onStopAnimation();
    }

}
