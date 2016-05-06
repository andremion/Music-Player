package com.sample.andremion.musicplayer.activities;

import android.os.Bundle;
import android.transition.Transition;
import android.view.View;

import com.sample.andremion.musicplayer.R;
import com.sample.andremion.musicplayer.view.CoverView;
import com.sample.andremion.musicplayer.view.TransitionAdapter;

public class DetailActivity extends PlayerActivity {

    private CoverView mCoverView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_detail);

        mCoverView = (CoverView) findViewById(R.id.cover);
        mCoverView.setCallbacks(new CoverView.Callbacks() {
            @Override
            public void onStopAnimation() {
                supportFinishAfterTransition();
            }
        });

        getWindow().getSharedElementEnterTransition().addListener(new TransitionAdapter() {
            @Override
            public void onTransitionEnd(Transition transition) {
                play();
                mCoverView.start();
            }
        });
    }

    @Override
    public void onBackPressed() {
        onFabClick(null);
    }

    public void onFabClick(View view) {
        pause();
        mCoverView.stop();
    }

}
