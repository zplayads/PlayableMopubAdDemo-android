package com.zplay.playable.mediationmopub;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.mopub.common.MoPubReward;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubRewardedVideoListener;
import com.mopub.mobileads.MoPubRewardedVideos;

import java.util.Set;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    private static final String MOPUB_UNIT_ID = "ab638024c4b1408a9adf504c7a3fd066";

    View mLoadingView;
    TextView mLogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingView = findViewById(R.id.progress_bar);
        mLogView = findViewById(R.id.log_text);

        MoPubRewardedVideos.initializeRewardedVideo(this);
        MoPubRewardedVideos.setRewardedVideoListener(new MoPubRewardedVideoListener() {
            @Override
            public void onRewardedVideoLoadSuccess(@NonNull String adUnitId) {
                mLoadingView.setVisibility(View.GONE);
                addLog("onRewardedVideoLoadSuccess: " + adUnitId + " ");
            }

            @Override
            public void onRewardedVideoLoadFailure(@NonNull String adUnitId, @NonNull MoPubErrorCode errorCode) {
                mLoadingView.setVisibility(View.GONE);
                addLog("onRewardedVideoLoadFailure: " + adUnitId + " " + errorCode);
            }

            @Override
            public void onRewardedVideoStarted(@NonNull String adUnitId) {
                addLog("onRewardedVideoStarted: " + adUnitId + " ");
            }

            @Override
            public void onRewardedVideoPlaybackError(@NonNull String adUnitId, @NonNull MoPubErrorCode errorCode) {
                addLog("onRewardedVideoPlaybackError: " + adUnitId + " ");
            }

            @Override
            public void onRewardedVideoClicked(@NonNull String adUnitId) {
                addLog("onRewardedVideoClicked: " + adUnitId + " ");
            }

            @Override
            public void onRewardedVideoClosed(@NonNull String adUnitId) {
                addLog("onRewardedVideoClosed: " + adUnitId + " ");
            }

            @Override
            public void onRewardedVideoCompleted(@NonNull Set<String> adUnitIds, @NonNull MoPubReward reward) {
                addLog("onRewardedVideoCompleted: ");
            }
        });
    }


    public void requestAd(View view) {
        mLogView.setText("");
        mLoadingView.setVisibility(View.VISIBLE);
        MoPubRewardedVideos.loadRewardedVideo(MOPUB_UNIT_ID);
        addLog("start loading advertising.");
    }


    public void presentAd(View view) {
        MoPubRewardedVideos.showRewardedVideo(MOPUB_UNIT_ID);
    }


    void addLog(String msg) {
        mLogView.append("\n" + msg);
    }
}
