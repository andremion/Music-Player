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
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.util.Property;
import android.view.ViewGroup;

import com.sample.andremion.musicplayer.R;
import com.sample.andremion.musicplayer.view.ProgressView;

public class ProgressViewTransition extends Transition {

    private static final String PROPNAME_MORPH = ProgressViewTransition.class.getName() + ":morph";
    private static final String[] sTransitionProperties = {PROPNAME_MORPH};
    private static final Property<ProgressView, Float> MORPH_PROPERTY =
            new Property<ProgressView, Float>(Float.class, "morph") {
                @Override
                public void set(ProgressView view, Float morph) {
                    view.setMorph(morph);
                }

                @Override
                public Float get(ProgressView view) {
                    return view.getMorph();
                }
            };

    private final int mMorph;

    public ProgressViewTransition(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
        int morph = a.getInt(R.styleable.ProgressView_morph, 0);
        a.recycle();
        mMorph = morph;
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
        if (transitionValues.view instanceof ProgressView) {
            transitionValues.values.put(PROPNAME_MORPH, value);
        }
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {

        if (endValues == null || !(endValues.view instanceof ProgressView)) {
            return null;
        }

        ProgressView progressView = (ProgressView) endValues.view;

        float startMorph = mMorph;
        float endMorph = 1 - startMorph;

        progressView.setMorph(startMorph);
        return ObjectAnimator.ofFloat(progressView, MORPH_PROPERTY, startMorph, endMorph);
    }

}
