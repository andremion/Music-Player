package com.sample.andremion.musicplayer.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;

import com.sample.andremion.musicplayer.view.CoverView;

import java.util.ArrayList;
import java.util.List;

public class DiscViewTransition2 extends Transition {

    private static final String PROPNAME_RADIUS = DiscViewTransition2.class.getName() + ":radius";
    private static final String PROPNAME_TRACK_ALPHA = DiscViewTransition2.class.getName() + ":trackAlpha";

    private static final String[] sTransitionProperties = {
            PROPNAME_RADIUS,
            PROPNAME_TRACK_ALPHA
    };

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

    public DiscViewTransition2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void captureValues(TransitionValues values) {
        View view = values.view;
        if (!(view instanceof CoverView)) {
            return;
        }
        CoverView coverView = (CoverView) view;
        values.values.put(PROPNAME_RADIUS, coverView.getTransitionRadius());
        values.values.put(PROPNAME_TRACK_ALPHA, coverView.getTrackAlpha());
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
        final Float startRadius = (Float) startValues.values.get(PROPNAME_RADIUS);
        final Float endRadius = (Float) endValues.values.get(PROPNAME_RADIUS);
        final Integer startTrackAlpha = (Integer) startValues.values.get(PROPNAME_TRACK_ALPHA);
        final Integer endTrackAlpha = (Integer) endValues.values.get(PROPNAME_TRACK_ALPHA);
        if (startRadius == null || endRadius == null || startTrackAlpha == null || endTrackAlpha == null) {
            return null;
        }
        CoverView coverView = (CoverView) endValues.view;
        List<Animator> animatorList = new ArrayList<>();
        if (!startRadius.equals(endRadius)) {
            coverView.setTransitionRadius(startRadius);
            animatorList.add(ObjectAnimator.ofFloat(coverView, RADIUS_PROPERTY, startRadius, endRadius));
        }
        if (!startTrackAlpha.equals(endTrackAlpha)) {
            coverView.setTrackAlpha(startTrackAlpha);
            animatorList.add(ObjectAnimator.ofInt(coverView, TRACK_ALPHA_PROPERTY, startTrackAlpha, endTrackAlpha));
        }
        if (animatorList.isEmpty()) {
            return null;
        }
        AnimatorSet animator = new AnimatorSet();
        animator.playTogether(animatorList);
        return animator;
    }

}
