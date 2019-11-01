package com.playableads.mopubadapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mopub.common.LifecycleListener;
import com.mopub.common.MoPubReward;
import com.mopub.mobileads.CustomEventRewardedVideo;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubRewardedVideoManager;
import com.playableads.PlayLoadingListener;
import com.playableads.PlayPreloadingListener;
import com.playableads.PlayableAds;
import com.playableads.PlayableAdsSettings;

import java.util.Map;

import static com.playableads.mopubadapter.ZUtils.getAppId;
import static com.playableads.mopubadapter.ZUtils.getChanelId;
import static com.playableads.mopubadapter.ZUtils.getGDPRState;
import static com.playableads.mopubadapter.ZUtils.getUnitId;
import static com.playableads.mopubadapter.ZUtils.isAutoLoad;
import static com.playableads.mopubadapter.ZUtils.shouldRequest_READ_PHONE_STATE;

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
        PlayableAdsSettings.setGDPRConsent(getGDPRState(serverExtras));
        PlayableAdsSettings.enableAutoRequestPermissions(shouldRequest_READ_PHONE_STATE(serverExtras));

        final String appId = getAppId(serverExtras);

        mPa = PlayableAds.init(launcherActivity, appId);
        mPa.setChannelId(getChanelId(serverExtras));
        mPa.setAutoLoadAd(isAutoLoad(serverExtras));
        return true;
    }

    @Override
    protected void loadWithSdkInitialized(@NonNull Activity activity, @NonNull Map<String, Object> localExtras, @NonNull Map<String, String> serverExtras) throws Exception {
        adUnitId = getUnitId(serverExtras);
        mPa.requestPlayableAds(adUnitId, new PlayPreloadingListener() {
            @Override
            public void onLoadFinished() {
                MoPubRewardedVideoManager.onRewardedVideoLoadSuccess(ZPLAYAdsRewardedVideo.class, adUnitId);
            }

            @Override
            public void onLoadFailed(int i, String s) {
                Log.i(TAG, "onLoadFailed: " + s);
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
                MoPubRewardedVideoManager.onRewardedVideoClosed(ZPLAYAdsRewardedVideo.class, adUnitId);
            }

            @Override
            public void onAdsError(int i, String s) {
                Log.i(TAG, "onAdsError: " + s);
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
