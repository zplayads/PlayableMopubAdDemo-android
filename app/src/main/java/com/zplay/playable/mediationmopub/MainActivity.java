package com.zplay.playable.mediationmopub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.mopub.common.MoPub;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;

/**
 * Description:
 * <p>
 * Created by lgd on 2018/4/12.
 */

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    static final String MOPUB_UNIT_ID_REWARDED_VIDEO = "ab638024c4b1408a9adf504c7a3fd066";
    static final String MOPUB_UNIT_ID_INTERSTITIAL = "d19dad039dd44f9da8f09665abcd422d";
    static final String MOPUB_UNIT_ID_BANNER = "5441d30a92a04ba583f75a40401b1632";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SdkConfiguration sdkConfiguration = new SdkConfiguration.Builder(MOPUB_UNIT_ID_REWARDED_VIDEO)
                .build();

        MoPub.initializeSdk(this, sdkConfiguration, initSdkListener());
    }

    private SdkInitializationListener initSdkListener() {
        return new SdkInitializationListener() {
            @Override
            public void onInitializationFinished() {
                Log.d(TAG, "onInitializationFinished: ");
            }
        };
    }

    public void showInterstitial(View view) {
        Intent i = new Intent(this, InterstitialActivity.class);
        startActivity(i);
    }

    public void showRewardedVideo(View view) {
        Intent i = new Intent(this, RewardedVideoActivity.class);
        startActivity(i);
    }

    public void showBanner(View view) {
        Intent i = new Intent(this, BannerActivity.class);
        startActivity(i);
    }
}
