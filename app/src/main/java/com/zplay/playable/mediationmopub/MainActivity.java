package com.zplay.playable.mediationmopub;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED   ){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE}, 0);
        }
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
