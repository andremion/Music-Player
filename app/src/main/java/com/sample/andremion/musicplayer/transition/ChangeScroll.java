package com.sample.andremion.musicplayer.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class ChangeScroll extends Transition {

    private static final String LOG_TAG = ChangeScroll.class.getSimpleName();
    private static final String PROPNAME_SCROLL_X = "android:changeScroll:x";
    private static final String PROPNAME_SCROLL_Y = "android:changeScroll:y";

    public ChangeScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private static Animator mergeAnimators(Animator animator1, Animator animator2) {
        if (animator1 == null) {
            return animator2;
        } else if (animator2 == null) {
            return animator1;
        } else {
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(animator1, animator2);
            return animatorSet;
        }
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
        Log.d(LOG_TAG, "captureStartValues: " + transitionValues.view.toString() + ", scrollY: " + transitionValues.view.getScrollY());
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
        Log.d(LOG_TAG, "captureEndValues: " + transitionValues.view.toString() + ", scrollY: " + transitionValues.view.getScrollY());
    }

    private void captureValues(TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_SCROLL_X, transitionValues.view.getScrollX());
        transitionValues.values.put(PROPNAME_SCROLL_Y, transitionValues.view.getScrollY());
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues,
                                   TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        final View view = endValues.view;
        int startX = (Integer) startValues.values.get(PROPNAME_SCROLL_X);
        int endX = (Integer) endValues.values.get(PROPNAME_SCROLL_X);
        int startY = (Integer) startValues.values.get(PROPNAME_SCROLL_Y);
        int endY = (Integer) endValues.values.get(PROPNAME_SCROLL_Y);
        Animator scrollXAnimator = null;
        Animator scrollYAnimator = null;
        if (startX != endX) {
            view.setScrollX(startX);
            scrollXAnimator = ObjectAnimator.ofInt(view, "scrollX", startX, endX);
        }
        if (startY != endY) {
            view.setScrollY(startY);
            scrollYAnimator = ObjectAnimator.ofInt(view, "scrollY", startY, endY);
        }
        return mergeAnimators(scrollXAnimator, scrollYAnimator);
    }
}
