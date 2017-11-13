package com.zplay.playable.mediationmopub;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mopub.common.LifecycleListener;
import com.mopub.mobileads.CustomEventRewardedVideo;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubRewardedVideoManager;
import com.playableads.PlayPreloadingListener;
import com.playableads.PlayableAds;
import com.playableads.SimplePlayLoadingListener;

import java.util.Map;

/**
 * Description: mopub聚合playableAds的适配器
 * <p>
 * Created by lgd on 2017/11/8.
 */

public class PlayableAdsRewardedVideo extends CustomEventRewardedVideo{
    private PlayableAds mPa;
    private String adUnitId;

    @Override
    protected boolean hasVideoAvailable() {
        return mPa != null && mPa.canPresentAd(adUnitId);
    }

    @Override
    protected void showVideo() {
        mPa.presentPlayableAD(adUnitId, new SimplePlayLoadingListener() {
            @Override
            public void onAdsError(int i, String s) {
                MoPubRewardedVideoManager.onRewardedVideoPlaybackError(PlayableAdsRewardedVideo.class, adUnitId, MoPubErrorCode.VIDEO_PLAYBACK_ERROR);
            }

            @Override
            public void playableAdsIncentive() {
                MoPubRewardedVideoManager.onRewardedVideoClosed(PlayableAdsRewardedVideo.class, adUnitId);
            }
        });
    }

    @Nullable
    @Override
    protected LifecycleListener getLifecycleListener() {
        return null;
    }

    @Override
    protected boolean checkAndInitializeSdk(@NonNull Activity launcherActivity, @NonNull Map<String, Object> localExtras, @NonNull Map<String, String> serverExtras) throws Exception {
        String appId = serverExtras.get("APPID");
        mPa = PlayableAds.init(launcherActivity, appId);
        return true;
    }

    @Override
    protected void loadWithSdkInitialized(@NonNull Activity activity, @NonNull Map<String, Object> localExtras, @NonNull Map<String, String> serverExtras) throws Exception {
        adUnitId = serverExtras.get("AdUnitId");
        mPa.requestPlayableAds(adUnitId, new PlayPreloadingListener() {
            @Override
            public void onLoadFinished() {
                MoPubRewardedVideoManager.onRewardedVideoLoadSuccess(PlayableAdsRewardedVideo.class, adUnitId);
            }

            @Override
            public void onLoadFailed(int i, String s) {
                MoPubRewardedVideoManager.onRewardedVideoLoadFailure(PlayableAdsRewardedVideo.class, adUnitId, MoPubErrorCode.MRAID_LOAD_ERROR);
            }
        });
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
