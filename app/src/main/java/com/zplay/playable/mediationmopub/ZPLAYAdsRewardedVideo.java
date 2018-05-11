package com.zplay.playable.mediationmopub;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.mopub.common.LifecycleListener;
import com.mopub.common.MoPubReward;
import com.mopub.mobileads.CustomEventRewardedVideo;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubRewardedVideoManager;
import com.playableads.PlayLoadingListener;
import com.playableads.PlayPreloadingListener;
import com.playableads.PlayableAds;

import java.util.Map;

/**
 * Description: mopub聚合playableAds的适配器
 * <p>
 * Created by lgd on 2017/11/8.
 */

public class ZPLAYAdsRewardedVideo extends CustomEventRewardedVideo {
    private static final String TAG = "ZPLAYAdsRewardedVideo";
    private PlayableAds mPa;
    private String adUnitId;

    @Override
    protected boolean checkAndInitializeSdk(@NonNull Activity launcherActivity, @NonNull Map<String, Object> localExtras, @NonNull Map<String, String> serverExtras) throws Exception {
        String appId = serverExtras.get("APPID");
        mPa = PlayableAds.init(launcherActivity, appId);
        mPa.setAutoLoadAd(false);
        Log.d(TAG, "PlayableAds init: " + !TextUtils.isEmpty(appId));
        return true;
    }

    @Override
    protected void loadWithSdkInitialized(@NonNull Activity activity, @NonNull Map<String, Object> localExtras, @NonNull Map<String, String> serverExtras) throws Exception {
        adUnitId = serverExtras.get("AdUnitId");
        Log.d(TAG, "loadWithSdkInitialized");
        mPa.requestPlayableAds(adUnitId, new PlayPreloadingListener() {
            @Override
            public void onLoadFinished() {
                MoPubRewardedVideoManager.onRewardedVideoLoadSuccess(ZPLAYAdsRewardedVideo.class, adUnitId);
            }

            @Override
            public void onLoadFailed(int i, String s) {
                Log.d(TAG, "onLoadFailed: " + s);
                MoPubRewardedVideoManager.onRewardedVideoLoadFailure(ZPLAYAdsRewardedVideo.class, adUnitId, MoPubErrorCode.MRAID_LOAD_ERROR);
            }
        });
    }

    @Override
    protected boolean hasVideoAvailable() {
        return mPa != null && mPa.canPresentAd(adUnitId);
    }

    @Override
    protected void showVideo() {

        mPa.presentPlayableAD(adUnitId, new PlayLoadingListener() {
            @Override
            public void onVideoStart() {
                Log.d(TAG, "onVideoStart: ");
                MoPubRewardedVideoManager.onRewardedVideoStarted(ZPLAYAdsRewardedVideo.class, adUnitId);
            }

            @Override
            public void onVideoFinished() {
            }

            @Override
            public void playableAdsIncentive() {
                MoPubRewardedVideoManager.onRewardedVideoCompleted(ZPLAYAdsRewardedVideo.class, adUnitId, MoPubReward.success("ZPLAYAds", 1));
            }

            @Override
            public void onLandingPageInstallBtnClicked() {
                MoPubRewardedVideoManager.onRewardedVideoClicked(ZPLAYAdsRewardedVideo.class, adUnitId);
            }

            @Override
            public void onAdClosed() {
                Log.d(TAG, "onAdClosed: ");
                MoPubRewardedVideoManager.onRewardedVideoClosed(ZPLAYAdsRewardedVideo.class, adUnitId);
            }

            @Override
            public void onAdsError(int i, String s) {
                Log.d(TAG, "onAdsError: ");
                MoPubRewardedVideoManager.onRewardedVideoPlaybackError(ZPLAYAdsRewardedVideo.class, adUnitId, MoPubErrorCode.VIDEO_PLAYBACK_ERROR);
            }
        });
    }

    @Nullable
    @Override
    protected LifecycleListener getLifecycleListener() {
        return null;
    }

    @NonNull
    @Override
    protected String getAdNetworkId() {
        return adUnitId;
    }

    @Override
    protected void onInvalidate() {
    }
}
