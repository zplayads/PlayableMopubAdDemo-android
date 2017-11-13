package com.zplay.playable.mediationmopub;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.mopub.common.MoPubReward;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubRewardedVideoListener;
import com.mopub.mobileads.MoPubRewardedVideos;

import java.util.Set;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MoPubRewardedVideos.initializeRewardedVideo(this);
        MoPubRewardedVideos.setRewardedVideoListener(new MoPubRewardedVideoListener() {
            @Override
            public void onRewardedVideoLoadSuccess(@NonNull String adUnitId) {
                Log.d(TAG, "onRewardedVideoLoadSuccess: " + adUnitId + " ");
            }

            @Override
            public void onRewardedVideoLoadFailure(@NonNull String adUnitId, @NonNull MoPubErrorCode errorCode) {
                Log.d(TAG, "onRewardedVideoLoadFailure: " + adUnitId + " " + errorCode);
            }

            @Override
            public void onRewardedVideoStarted(@NonNull String adUnitId) {
                Log.d(TAG, "onRewardedVideoStarted: " + adUnitId + " ");
            }

            @Override
            public void onRewardedVideoPlaybackError(@NonNull String adUnitId, @NonNull MoPubErrorCode errorCode) {
                Log.d(TAG, "onRewardedVideoPlaybackError: " + adUnitId + " ");
            }

            @Override
            public void onRewardedVideoClicked(@NonNull String adUnitId) {
                Log.d(TAG, "onRewardedVideoClicked: " + adUnitId + " ");
            }

            @Override
            public void onRewardedVideoClosed(@NonNull String adUnitId) {
                Log.d(TAG, "onRewardedVideoClosed: " + adUnitId + " ");
            }

            @Override
            public void onRewardedVideoCompleted(@NonNull Set<String> adUnitIds, @NonNull MoPubReward reward) {
                Log.d(TAG, "onRewardedVideoCompleted: ");
            }
        });
    }


    public void requestAd(View view) {
        MoPubRewardedVideos.loadRewardedVideo("06df97f8f3f14b64b92b7ff0392f8a5a");
    }


    public void presentAd(View view) {
        MoPubRewardedVideos.showRewardedVideo("06df97f8f3f14b64b92b7ff0392f8a5a");
    }

}
