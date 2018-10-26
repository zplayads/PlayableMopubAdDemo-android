package com.zplay.playable.mediationmopub;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.mopub.mobileads.CustomEventInterstitial;
import com.mopub.mobileads.MoPubErrorCode;
import com.playableads.PlayPreloadingListener;
import com.playableads.PlayableAds;
import com.playableads.SimplePlayLoadingListener;

import java.util.Map;

/**
 * Description:
 * <p>
 * Created by lgd on 2018/4/12.
 */

public class MPZPLAYAdsInterstitial extends CustomEventInterstitial {
    private static final String TAG = "MPZPLAYAdsInterstitial";

    private PlayableAds mPa;
    private String adUnitId;

    private CustomEventInterstitialListener mInterstitialListener;

    @Override
    protected void loadInterstitial(Context context, CustomEventInterstitialListener customEventInterstitialListener, Map<String, Object> localExtras, Map<String, String> serverExtras) {
        mInterstitialListener = customEventInterstitialListener;
        adUnitId = serverExtras.get("AdUnitId");
        String appId = serverExtras.get("APPID");
        mPa = PlayableAds.init(context, appId);
        mPa.setAutoLoadAd(false);
        Log.d(TAG, "loadInterstitial: " + !TextUtils.isEmpty(appId));
        mPa.requestPlayableAds(adUnitId, new PlayPreloadingListener() {
            @Override
            public void onLoadFinished() {
                if (mInterstitialListener != null) {
                    mInterstitialListener.onInterstitialLoaded();
                } else {
                    Log.d(TAG, "onLoadFailed: listener is null");
                }
            }

            @Override
            public void onLoadFailed(int i, String s) {
                Log.d(TAG, "onLoadFailed: " + s);
                if (mInterstitialListener != null) {
                    mInterstitialListener.onInterstitialFailed(MoPubErrorCode.MRAID_LOAD_ERROR);
                } else {
                    Log.d(TAG, "onLoadFailed: listener is null");
                }
            }
        });
    }

    @Override
    protected void showInterstitial() {
        mPa.presentPlayableAD(adUnitId, new SimplePlayLoadingListener() {

            @Override
            public void onLandingPageInstallBtnClicked() {
                if (mInterstitialListener != null) {
                    mInterstitialListener.onInterstitialClicked();
                } else {
                    Log.d(TAG, "onLoadFailed: listener is null");
                }
            }

            @Override
            public void onVideoStart() {
                if (mInterstitialListener != null) {
                    mInterstitialListener.onInterstitialShown();
                } else {
                    Log.d(TAG, "onLoadFailed: listener is null");
                }
            }

            @Override
            public void onAdClosed() {
                if (mInterstitialListener != null) {
                    mInterstitialListener.onInterstitialDismissed();
                } else {
                    Log.d(TAG, "onLoadFailed: listener is null");
                }
            }

            @Override
            public void onAdsError(int i, String s) {
                Log.d(TAG, "presentPlayableAD onAdsError: " + s);
            }
        });
    }

    @Override
    protected void onInvalidate() {
    }
}
