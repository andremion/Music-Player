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

package com.sample.andremion.musicplayer.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.util.Property;
import android.view.ViewGroup;

import com.sample.andremion.musicplayer.R;
import com.sample.andremion.musicplayer.view.CoverView;

import java.util.ArrayList;
import java.util.List;

public class CoverViewTransition extends Transition {

    private static final String PROPNAME_RADIUS = CoverViewTransition.class.getName() + ":radius";
    private static final String PROPNAME_TRACK_ALPHA = CoverViewTransition.class.getName() + ":trackAlpha";
    private static final String[] sTransitionProperties = {PROPNAME_RADIUS, PROPNAME_TRACK_ALPHA};

    private static final Property<CoverView, Float> RADIUS_PROPERTY =
            new Property<CoverView, Float>(Float.class, "radius") {
                @Override
                public void set(CoverView view, Float radius) {
                    view.setTransitionRadius(radius);
                }

                @Override
                public Float get(CoverView view) {
                    return view.getTransitionRadius();
                }
            };
    private static final Property<CoverView, Integer> TRACK_ALPHA_PROPERTY =
            new Property<CoverView, Integer>(Integer.class, "trackAlpha") {
                @Override
                public void set(CoverView view, Integer alpha) {
                    view.setTrackAlpha(alpha);
                }

                @Override
                public Integer get(CoverView view) {
                    return view.getTrackAlpha();
                }
            };
    private final int mShape;

    public CoverViewTransition(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CoverView);
        int shape = a.getInt(R.styleable.CoverView_shape, CoverView.SHAPE_RECTANGLE);
        a.recycle();
        mShape = shape;
    }

    @Override
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        // Add fake value to force calling of createAnimator method
        captureValues(transitionValues, "start");
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        // Add fake value to force calling of createAnimator method
        captureValues(transitionValues, "end");
    }

    private void captureValues(TransitionValues transitionValues, Object value) {
        if (transitionValues.view instanceof CoverView) {
            transitionValues.values.put(PROPNAME_RADIUS, value);
            transitionValues.values.put(PROPNAME_TRACK_ALPHA, value);
        }
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {

        if (endValues == null || !(endValues.view instanceof CoverView)) {
            return null;
        }

        CoverView coverView = (CoverView) endValues.view;
        final float minRadius = coverView.getMinRadius();
        final float maxRadius = coverView.getMaxRadius();

        float startRadius, endRadius;
        int startTrackAlpha, endTrackAlpha;

        if (mShape == CoverView.SHAPE_RECTANGLE) {
            startRadius = maxRadius;
            endRadius = minRadius;
            startTrackAlpha = CoverView.TACK_ALPHA_TRANSPARENT;
            endTrackAlpha = CoverView.TACK_ALPHA_OPAQUE;
        } else {
            startRadius = minRadius;
            endRadius = maxRadius;
            startTrackAlpha = CoverView.TACK_ALPHA_OPAQUE;
            endTrackAlpha = CoverView.TACK_ALPHA_TRANSPARENT;
        }

        List<Animator> animatorList = new ArrayList<>();

        coverView.setTransitionRadius(startRadius);
        animatorList.add(ObjectAnimator.ofFloat(coverView, RADIUS_PROPERTY, startRadius, endRadius));

        coverView.setTrackAlpha(startTrackAlpha);
        animatorList.add(ObjectAnimator.ofInt(coverView, TRACK_ALPHA_PROPERTY, startTrackAlpha, endTrackAlpha));

        AnimatorSet animator = new AnimatorSet();
        animator.playTogether(animatorList);
        return animator;
    }

}
