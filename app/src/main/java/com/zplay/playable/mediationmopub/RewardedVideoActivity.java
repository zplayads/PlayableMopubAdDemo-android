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

import static com.zplay.playable.mediationmopub.MainActivity.MOPUB_UNIT_ID_REWARDED_VIDEO;

public class RewardedVideoActivity extends Activity implements MoPubRewardedVideoListener {
    private static final String TAG = "RewardedVideoActivity";

    View mLoadingView;
    TextView mLogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewarded_video);
        mLoadingView = findViewById(R.id.progress_bar);
        mLogView = findViewById(R.id.log_text);

        MoPubRewardedVideos.setRewardedVideoListener(this);
    }

    public void requestAd(View view) {
        mLogView.setText("");
        mLoadingView.setVisibility(View.VISIBLE);
        MoPubRewardedVideos.loadRewardedVideo(MOPUB_UNIT_ID_REWARDED_VIDEO);
        addLog("start loading advertising.");
    }


    public void presentAd(View view) {
        MoPubRewardedVideos.showRewardedVideo(MOPUB_UNIT_ID_REWARDED_VIDEO);
    }


    void addLog(String msg) {
        mLogView.append("\n" + msg);
    }

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
}
