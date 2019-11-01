package com.playableads.mopubadapter;

import android.text.TextUtils;

import com.playableads.entity.BannerSize;
import com.playableads.entity.GDPRStatus;

import java.util.Map;

import static com.playableads.entity.BannerSize.BANNER_320x50;
import static com.playableads.entity.BannerSize.BANNER_728x90;
import static com.playableads.entity.BannerSize.SMART_BANNER;
import static com.playableads.entity.GDPRStatus.NON_PERSONALIZED;
import static com.playableads.entity.GDPRStatus.PERSONALIZED;

/**
 * Description:
 * <p>
 * Created by lgd on 2019-10-30.
 */
class ZUtils {
    private static final String APP_ID = "appId";
    private static final String UNIT_ID = "unitId";
    private static final String BANNER_SIZE = "bannerSize";
    private static final String CHANNEL_ID = "channelId";
    private static final String GDPR_STATE = "gdprState";
    private static final String AUTO_LOAD = "autoLoad";
    private static final String REQUEST_READ_PHONE_STATE = "requestReadPhoneState";

    static String getAppId(Map<String, String> extras) {
        if (extras == null) {
            return "";
        }
        return extras.get(APP_ID);
    }

    static String getUnitId(Map<String, String> extras) {
        if (extras == null) {
            return "";
        }
        return extras.get(UNIT_ID);
    }

    static BannerSize getBannerSize(Map<String, String> extras) {
        if (extras == null) {
            return BANNER_320x50;
        }
        String bannerSize = extras.get(BANNER_SIZE);

        if (TextUtils.equals(bannerSize, "SMART_BANNER")) {
            return SMART_BANNER;
        } else if (TextUtils.equals(bannerSize, "BANNER_728x90")) {
            return BANNER_728x90;
        } else {
            return BANNER_320x50;
        }
    }

    static boolean shouldRequest_READ_PHONE_STATE(Map<String, String> extras) {
        if (extras == null) {
            return false;
        }

        return Boolean.valueOf(extras.get(REQUEST_READ_PHONE_STATE));
    }

    static String getChanelId(Map<String, String> extras) {
        if (extras == null) {
            return "";
        }
        final String channelId = extras.get(CHANNEL_ID);
        return TextUtils.isEmpty(channelId) ? "" : channelId;
    }

    static GDPRStatus getGDPRState(Map<String, String> extras) {
        if (extras == null) {
            return PERSONALIZED;
        }

        String gdprState = extras.get(GDPR_STATE);
        if (TextUtils.equals(gdprState, "NON_PERSONALIZED")) {
            return NON_PERSONALIZED;
        } else {
            return PERSONALIZED;
        }
    }

    static boolean isAutoLoad(Map<String, String> extras) {
        if (extras == null) {
            return false;
        }

        return Boolean.valueOf(extras.get(AUTO_LOAD));
    }

    static BannerSize getBannerSize(int width, int height) {
        // Use the smallest AdSize that will properly contain the adView
        if (height <= 50) {
            return BANNER_320x50;
        } else if (height <= 90) {
            return BANNER_728x90;
        } else {
            return BANNER_320x50;
        }
    }
}
