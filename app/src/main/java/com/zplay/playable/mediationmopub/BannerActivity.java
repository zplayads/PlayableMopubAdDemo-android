package com.zplay.playable.mediationmopub;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;

import static com.zplay.playable.mediationmopub.MainActivity.MOPUB_UNIT_ID_BANNER;

/**
 * Description:
 * <p>
 * Created by lgd on 2019-10-30.
 */
public class BannerActivity extends Activity implements MoPubView.BannerAdListener {
    private static final String TAG = "BannerActivity";

    private MoPubView moPubView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        moPubView = findViewById(R.id.moPubView);
        moPubView.setAdUnitId(MOPUB_UNIT_ID_BANNER);
        moPubView.setBannerAdListener(this);
        moPubView.setAutorefreshEnabled(true);
    }

    public void loadAd(View view) {
        moPubView.loadAd();
    }

    public void show(View view) {
    }

    @Override
    public void onBannerLoaded(MoPubView banner) {
        Log.d(TAG, "onBannerLoaded: ");
    }

    @Override
    public void onBannerFailed(MoPubView banner, MoPubErrorCode errorCode) {
        Log.d(TAG, "onBannerFailed: " + banner + ", errorCode: " + errorCode);

    }

    @Override
    public void onBannerClicked(MoPubView banner) {
        Log.d(TAG, "onBannerClicked: " + banner);
    }

    @Override
    public void onBannerExpanded(MoPubView banner) {
        Log.d(TAG, "onBannerExpanded: " + banner);
    }

    @Override
    public void onBannerCollapsed(MoPubView banner) {
        Log.d(TAG, "onBannerCollapsed: " + banner);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        moPubView.destroy();
    }
}
