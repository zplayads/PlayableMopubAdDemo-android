package com.playableads.mopubadapter;

import android.content.Context;
import android.util.Log;

import com.mopub.mobileads.CustomEventInterstitial;
import com.mopub.mobileads.MoPubErrorCode;
import com.playableads.PlayPreloadingListener;
import com.playableads.PlayableAdsSettings;
import com.playableads.PlayableInterstitial;
import com.playableads.SimplePlayLoadingListener;

import java.util.Map;

import static com.playableads.mopubadapter.ZUtils.getAppId;
import static com.playableads.mopubadapter.ZUtils.getGDPRState;
import static com.playableads.mopubadapter.ZUtils.getUnitId;
import static com.playableads.mopubadapter.ZUtils.isAutoLoad;
import static com.playableads.mopubadapter.ZUtils.shouldRequest_READ_PHONE_STATE;

/**
 * Description:
 * <p>
 * Created by lgd on 2018/4/12.
 */

public class ZPLAYAdsInterstitial extends CustomEventInterstitial {
    private static final String TAG = "ZPLAYAdsInterstitial";

    private PlayableInterstitial mPa;
    private String adUnitId;

    private CustomEventInterstitialListener mInterstitialListener;

    @Override
    protected void loadInterstitial(Context context, CustomEventInterstitialListener customEventInterstitialListener, Map<String, Object> localExtras, Map<String, String> serverExtras) {
        PlayableAdsSettings.setGDPRConsent(getGDPRState(serverExtras));
        PlayableAdsSettings.enableAutoRequestPermissions(shouldRequest_READ_PHONE_STATE(serverExtras));

        mInterstitialListener = customEventInterstitialListener;
        adUnitId = getUnitId(serverExtras);

        final String appId = getAppId(serverExtras);

        mPa = PlayableInterstitial.init(context, appId);
        mPa.setAutoload(isAutoLoad(serverExtras));
        mPa.requestPlayableAds(adUnitId, new PlayPreloadingListener() {
            @Override
            public void onLoadFinished() {
                if (mInterstitialListener != null) {
                    mInterstitialListener.onInterstitialLoaded();
                }
            }

            @Override
            public void onLoadFailed(int i, String s) {
                Log.d(TAG, "onLoadFailed: " + s);
                if (mInterstitialListener != null) {
                    mInterstitialListener.onInterstitialFailed(MoPubErrorCode.MRAID_LOAD_ERROR);
                }
            }
        });
    }

    @Override
    protected void showInterstitial() {
        mPa.presentPlayableAd(adUnitId, new SimplePlayLoadingListener() {

            @Override
            public void onLandingPageInstallBtnClicked() {
                if (mInterstitialListener != null) {
                    mInterstitialListener.onInterstitialClicked();
                }
            }

            @Override
            public void onVideoStart() {
                if (mInterstitialListener != null) {
                    mInterstitialListener.onInterstitialShown();
                }
            }

            @Override
            public void onAdClosed() {
                if (mInterstitialListener != null) {
                    mInterstitialListener.onInterstitialDismissed();
                }
            }

            @Override
            public void onAdsError(int i, String s) {
                Log.d(TAG, "onAdsError: " + s);
            }
        });
    }

    @Override
    protected void onInvalidate() {
    }
}
