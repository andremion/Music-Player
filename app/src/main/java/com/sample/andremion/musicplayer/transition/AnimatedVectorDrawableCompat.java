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
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

public class AnimatedVectorDrawableCompat {

    private static final Handler sHandler = new AnimatedVectorDrawableHandler();

    public static void addListener(AnimatedVectorDrawable drawable, Object callback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (callback instanceof Animatable2.AnimationCallback) {
                drawable.registerAnimationCallback((Animatable2.AnimationCallback) callback);
            } else {
                throw new IllegalArgumentException("Callback parameter must implement " + Animatable2.AnimationCallback.class.getName());
            }
        }
    }

    public static boolean removeListener(AnimatedVectorDrawable drawable, Object callback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (callback instanceof Animatable2.AnimationCallback) {
                return drawable.unregisterAnimationCallback((Animatable2.AnimationCallback) callback);
            } else {
                throw new IllegalArgumentException("Callback parameter must implement " + Animatable2.AnimationCallback.class.getName());
            }
        }
        return true;
    }

    public static void removeAllListeners(AnimatedVectorDrawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawable.clearAnimationCallbacks();
        }
    }

    public static void start(Animator animator, AnimatedVectorDrawable drawable) {
        Message msg = Message.obtain(sHandler, AnimatedVectorDrawableHandler.MSG_START, new Object[]{animator, drawable});
        sHandler.sendMessageDelayed(msg, animator.getStartDelay());
    }

    public static void stop(Animator animator, AnimatedVectorDrawable drawable) {
        Message msg = Message.obtain(sHandler, AnimatedVectorDrawableHandler.MSG_STOP, new Object[]{animator, drawable});
        sHandler.sendMessage(msg);
    }

    public static void cancel(AnimatedVectorDrawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawable.reset();
        }
        sHandler.removeMessages(AnimatedVectorDrawableHandler.MSG_START);
        sHandler.removeMessages(AnimatedVectorDrawableHandler.MSG_STOP);
    }

    private static class AnimatedVectorDrawableHandler extends Handler {

        private static final int MSG_START = 0;
        private static final int MSG_STOP = 1;

        @Override
        public void handleMessage(Message msg) {

            Object[] array = (Object[]) msg.obj;
            Animator animator = (Animator) array[0];
            AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) array[1];

            switch (msg.what) {

                case MSG_START:

                    drawable.start();
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                        for (Animator.AnimatorListener listener : animator.getListeners()) {
                            listener.onAnimationStart(animator);
                        }
                        Message msgStop = Message.obtain(sHandler, AnimatedVectorDrawableHandler.MSG_STOP, new Object[]{animator, drawable});
                        sendMessageDelayed(msgStop, animator.getDuration());
                    }

                    break;

                case MSG_STOP:

                    drawable.stop();
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                        for (Animator.AnimatorListener listener : animator.getListeners()) {
                            listener.onAnimationEnd(animator);
                        }
                    }

                    break;
            }
        }
    }
}
