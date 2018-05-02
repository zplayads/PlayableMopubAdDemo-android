package com.zplay.playable.mediationmopub;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;

/**
 * Description:
 * <p>
 * Created by lgd on 2018/4/12.
 */

public class InterstitialActivity extends Activity implements MoPubInterstitial.InterstitialAdListener {
    private static final String TAG = "InterstitialActivity";
    private static final String MOPUB_UNIT_ID_INTERSTITIAL = "d19dad039dd44f9da8f09665abcd422d";

    View mLoadingView;
    TextView mLogView;

    private MoPubInterstitial mInterstitial;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstiail);
        mLoadingView = findViewById(R.id.ai_progress_bar);
        mLogView = (TextView) findViewById(R.id.ai_log_text);

        mInterstitial = new MoPubInterstitial(this, MOPUB_UNIT_ID_INTERSTITIAL);
        mInterstitial.setInterstitialAdListener(this);
        mInterstitial.load();
    }

    public void requestAd(View view) {
        mLogView.setText("");
        mLoadingView.setVisibility(View.VISIBLE);
        mInterstitial.load();
        addLog("start loading advertising.");
    }

    public void presentAd(View view) {
        if (mInterstitial.isReady()) {
            mInterstitial.show();
        } else {
            addLog("interstitial ad not ready");
        }
    }

    @Override
    protected void onDestroy() {
        mInterstitial.destroy();
        super.onDestroy();
    }

    @Override
    public void onInterstitialLoaded(MoPubInterstitial interstitial) {
        mLoadingView.setVisibility(View.GONE);
        addLog("onInterstitialLoaded: ");
    }

    @Override
    public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {
        mLoadingView.setVisibility(View.GONE);
        addLog("onInterstitialFailed: " + errorCode);
    }

    @Override
    public void onInterstitialShown(MoPubInterstitial interstitial) {
        addLog("onInterstitialShown: ");
    }

    @Override
    public void onInterstitialClicked(MoPubInterstitial interstitial) {
        addLog("onInterstitialClicked: ");
    }

    @Override
    public void onInterstitialDismissed(MoPubInterstitial interstitial) {
        addLog("onInterstitialDismissed: ");
    }

    void addLog(String msg) {
        Log.d(TAG, msg);
        mLogView.append("\n" + msg);
    }
}
