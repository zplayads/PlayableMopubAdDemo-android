package com.playableads.mopubadapter;

import android.content.Context;
import android.util.Log;

import com.mopub.common.DataKeys;
import com.mopub.mobileads.CustomEventBanner;
import com.mopub.mobileads.MoPubErrorCode;
import com.playableads.AtmosplayAdsBanner;
import com.playableads.BannerListener;
import com.playableads.PlayableAdsSettings;
import com.playableads.presenter.widget.AtmosBannerView;

import java.util.Map;

import static com.mopub.mobileads.MoPubErrorCode.NETWORK_INVALID_STATE;
import static com.mopub.mobileads.MoPubErrorCode.NETWORK_TIMEOUT;
import static com.mopub.mobileads.MoPubErrorCode.NO_FILL;
import static com.mopub.mobileads.MoPubErrorCode.UNSPECIFIED;
import static com.playableads.constants.StatusCode.NETWORK_ERROR;
import static com.playableads.constants.StatusCode.PRELOAD_NO_AD;
import static com.playableads.constants.StatusCode.TIMEOUT_ERROR;
import static com.playableads.mopubadapter.ZUtils.getAppId;
import static com.playableads.mopubadapter.ZUtils.getBannerSize;
import static com.playableads.mopubadapter.ZUtils.getChanelId;
import static com.playableads.mopubadapter.ZUtils.getGDPRState;
import static com.playableads.mopubadapter.ZUtils.getUnitId;
import static com.playableads.mopubadapter.ZUtils.shouldRequest_READ_PHONE_STATE;

/**
 * Description:
 * <p>
 * Created by lgd on 2019-10-30.
 */
public class ZPLAYAdsBanner extends CustomEventBanner {
    private static final String TAG = "ZPLAYAdsBanner";

    @Override
    protected void loadBanner(Context context, final CustomEventBannerListener customEventBannerListener, Map<String, Object> localExtras, Map<String, String> serverExtras) {
        PlayableAdsSettings.setGDPRConsent(getGDPRState(serverExtras));
        PlayableAdsSettings.enableAutoRequestPermissions(shouldRequest_READ_PHONE_STATE(serverExtras));

        final String appId = getAppId(serverExtras);
        final String unitId = getUnitId(serverExtras);
        final String chanelId = getChanelId(serverExtras);

        int width;
        int height;
        if (localExtrasAreValid(localExtras)) {
            width = (Integer) localExtras.get(DataKeys.AD_WIDTH);
            height = (Integer) localExtras.get(DataKeys.AD_HEIGHT);
            Log.i(TAG, "Banner Load size width : " + width + ", height : " + height);
        } else {
            Log.i(TAG, "Banner Load Failed,local Extras Are not Valid");
            if (customEventBannerListener != null) {
                customEventBannerListener.onBannerFailed(MoPubErrorCode.NETWORK_NO_FILL);
            }
            return;
        }

        AtmosplayAdsBanner banner = new AtmosplayAdsBanner(context, appId, unitId);
        banner.setBannerSize(getBannerSize(width, height));
        banner.setChannelId(chanelId);
        banner.setBannerListener(new BannerListener() {

            @Override
            public void onBannerPrepared(AtmosBannerView atmosBannerView) {
                customEventBannerListener.onBannerLoaded(atmosBannerView);
            }

            @Override
            public void onBannerPreparedFailed(int i, String s) {
                MoPubErrorCode mpec = UNSPECIFIED;
                if (i == PRELOAD_NO_AD.code) {
                    mpec = NO_FILL;
                } else if (i == NETWORK_ERROR.code) {
                    mpec = NETWORK_INVALID_STATE;
                } else if (i == TIMEOUT_ERROR.code) {
                    mpec = NETWORK_TIMEOUT;
                }
                customEventBannerListener.onBannerFailed(mpec);
            }

            @Override
            public void onBannerClicked() {
                customEventBannerListener.onBannerClicked();
            }
        });
        banner.loadAd();
    }


    @Override
    protected void onInvalidate() {
    }

    private boolean localExtrasAreValid(final Map<String, Object> localExtras) {
        if (localExtras == null) {
            return false;
        }
        return localExtras.get(DataKeys.AD_WIDTH) instanceof Integer
                && localExtras.get(DataKeys.AD_HEIGHT) instanceof Integer;
    }

}
