package com.sample.andremion.musicplayer.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.util.ArrayMap;

import java.util.ArrayList;

public class AnimatedVectorDrawableWrapper extends Animator {

    private final AnimatedVectorDrawable mAnimatedVectorDrawable;
    private final ArrayMap<AnimatorListener, AnimatorListenerWrapper> mListeners;
    private long mStartDelay;
    private long mDuration;

    public AnimatedVectorDrawableWrapper(AnimatedVectorDrawable animatedVectorDrawable) {
        mAnimatedVectorDrawable = animatedVectorDrawable;
        mListeners = new ArrayMap<>();
    }

    private AnimatorListenerWrapper createWrapper(Animator animator, Animator.AnimatorListener listener) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return new AnimatorListenerWrapperL();
        } else {
            return new AnimatorListenerWrapperM(animator, listener);
        }
    }

    @Override
    public ArrayList<AnimatorListener> getListeners() {
        return new ArrayList<>(mListeners.keySet());
    }

    @Override
    public void addListener(AnimatorListener listener) {
        AnimatorListenerWrapper wrapper = createWrapper(this, listener);
        if (!mListeners.containsKey(listener)) {
            mListeners.put(listener, wrapper);
            AnimatedVectorDrawableCompat.addListener(mAnimatedVectorDrawable, wrapper);
        }
    }

    @Override
    public void removeListener(AnimatorListener listener) {
        AnimatorListenerWrapper wrapper = mListeners.get(listener);
        if (wrapper != null) {
            mListeners.remove(listener);
            AnimatedVectorDrawableCompat.removeListener(mAnimatedVectorDrawable, wrapper);
        }
    }

    @Override
    public void removeAllListeners() {
        mListeners.clear();
        AnimatedVectorDrawableCompat.removeAllListeners(mAnimatedVectorDrawable);
    }

    @Override
    public long getStartDelay() {
        return mStartDelay;
    }

    @Override
    public void setStartDelay(long startDelay) {
        mStartDelay = startDelay;
    }

    @Override
    public long getDuration() {
        return mDuration;
    }

    @Override
    public Animator setDuration(long duration) {
        mDuration = duration;
        return this;
    }

    @Override
    public void setInterpolator(TimeInterpolator value) {
        // Nothing to do
    }

    @Override
    public boolean isRunning() {
        return mAnimatedVectorDrawable.isRunning();
    }

    @Override
    public void start() {
        AnimatedVectorDrawableCompat.start(this, mAnimatedVectorDrawable);
    }

    @Override
    public void end() {
        AnimatedVectorDrawableCompat.stop(this, mAnimatedVectorDrawable);
    }

    @Override
    public void cancel() {
        AnimatedVectorDrawableCompat.cancel(mAnimatedVectorDrawable);
    }

    private interface AnimatorListenerWrapper {
    }

    private static class AnimatorListenerWrapperL implements AnimatorListenerWrapper {

    }

    @TargetApi(Build.VERSION_CODES.M)
    private static class AnimatorListenerWrapperM extends Animatable2.AnimationCallback implements AnimatorListenerWrapper {

        private final Animator mAnimator;
        private final Animator.AnimatorListener mListener;

        public AnimatorListenerWrapperM(Animator animator, Animator.AnimatorListener listener) {
            mAnimator = animator;
            mListener = listener;
        }

        @Override
        public void onAnimationStart(Drawable drawable) {
            mListener.onAnimationStart(mAnimator);
        }

        @Override
        public void onAnimationEnd(Drawable drawable) {
            mListener.onAnimationEnd(mAnimator);
        }

    }

}
