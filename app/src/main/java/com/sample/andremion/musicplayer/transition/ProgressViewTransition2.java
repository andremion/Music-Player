package com.sample.andremion.musicplayer.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;

import com.sample.andremion.musicplayer.view.ProgressView;

public class ProgressViewTransition2 extends Transition {

    private static final String PROPNAME_MORPH = ProgressViewTransition.class.getName() + ":morph";

    private static final String[] sTransitionProperties = {
            PROPNAME_MORPH
    };

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

    public ProgressViewTransition2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void captureValues(TransitionValues values) {
        View view = values.view;
        if (!(view instanceof ProgressView)) {
            return;
        }
        ProgressView progressView = (ProgressView) view;
        values.values.put(PROPNAME_MORPH, progressView.getMorph());
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        final Float startMorph = (Float) startValues.values.get(PROPNAME_MORPH);
        final Float endMorph = (Float) endValues.values.get(PROPNAME_MORPH);
        if (startMorph == null || endMorph == null) {
            return null;
        }
        if (startMorph.equals(endMorph)) {
            return null;
        }
        ProgressView progressView = (ProgressView) endValues.view;
        progressView.setMorph(startMorph);
        return ObjectAnimator.ofFloat(progressView, MORPH_PROPERTY, startMorph, endMorph);
    }

}
