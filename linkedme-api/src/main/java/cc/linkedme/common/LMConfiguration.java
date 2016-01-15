package cc.linkedme.common;

/**
 * Created by qipo on 15/8/28.
 */
public class LMConfiguration {

    /**
     * those static final variables of LinkedMe about install, open, close, custom url
     */

    private static final String KEY_APP_KEY = "app_key";
    private static final String KEY_APP_ID = "app_id";

    private static final String KEY_APP_VERSION = "app_version";
    private static final String KEY_CLICK_ID= "click_id";
    private static final String KEY_DEBUG = "debug";
    private static final String KEY_DEVICE_FINGERPRINT_ID = "device_fingerprint_id";
    private static final String KEY_IS_REFERRABLE = "is_referrable";
    private static final String KEY_OS = "os";
    private static final String KEY_OS_VERSION = "os_version";
    private static final String KEY_OS_RELEASE = "os_release";
    private static final String KEY_URI_SCHEME = "uri_scheme";
    private static final String KEY_SDK = "sdk";
    private static final String KEY_TYPE = "type";
    private static final String KEY_DATA = "data";
    private static final String KEY_BRAND = "brand";
    private static final String KEY_HARDWARE_ID = "hardware_id";
    private static final String KEY_IS_HARDWARE_ID_REAl = "is_hardware_id_real";
    private static final String KEY_MODEL = "model";
    private static final String KEY_SCREEN_HEIGHT = "screen_height";
    private static final String KEY_SCREEN_WIDTH = "screen_width";
    private static final String KEY_CARRIER = "carrier";
    private static final String KEY_UPDATE = "update";
    private static final String KEY_TAGS = "tags";
    private static final String KEY_CHANNEL = "channel";
    private static final String KEY_FEATURE = "feature";
    private static final String KEY_STATE = "state";
    private static final String KEY_STAGE = "stage";
    private static final String KEY_ALIAS = "alias";
    private static final String KEY_SESSION_ID = "session_id";
    private static final String KEY_IDENTITY_ID = "identity_id";
    private static final String KEY_IDENTITY = "identity";
    private static final String KEY_LINK_CLICK_ID = "link_click_id";
    private static final String KEY_LINK_CLICK_IDENTIFIER = "link_click_identifier";
    private static final String KEY_SESSION_PARAMS = "session_params";
    private static final String KEY_INSTALL_PARAMS = "install_params";
    private static final String KEY_USER_URL = "user_url";
    private static final String TAG = "LMConfiguration";
    private static final String SHARED_PREF_FILE = "deep_share_preference";
    private static final String KEY_LINK = "link";
    private static final String KEY_BROWSER_FINGERPRINT_ID = "browser_fingerprint_id";
    private static final String KEY_URL = "url";
    private static final String KEY_CAMPAGIN = "compagin";
    private static final String KEY_DEEPLINK_PATH = "deeplink_path";

    //Android
    private static final String KEY_BLUETOOTH = "bluetooth";;
    private static final String KEY_BLUETOOTH_VERSION = "bluetooth_version";
    private static final String KEY_HAS_NFC = "has_nfc";
    private static final String KEY_HAS_TELEPHONE = "has_telephone";
    private static final String KEY_OS_REALASE = "os_release";
    private static final String KEY_SCREEN_DPI = "screen_dpi";
    private static final String KEY_WIFI = "wifi";
    private static final String KEY_GOOGLE_ADVERTISING_ID = "google_advertising_id";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_LAT_VAL = "lat_val";

    //Branch
    private static final String KEY_AD_TRACKING_ENABLED = "ad_tracking_enabled";
    private static final String KEY_IOS_BUNDLE_ID = "ios_bundle_id";
    private static final String KEY_RETRYNUMBER = "retryNumber";
    private static final String KEY_IOS_TEAM_ID = "ios_team_id";
    private static final String KEY_LINK_IDENTIFIER = "link_identifier";
    private static final String KEY_SPOTLIGHT_IDENTIFIER = "spotlight_identifier";
    private static final String KEY_UNIVERSAL_LINK_URL = "universal_link_url";
    private static final String KEY_IS_FIRST_SESSION = "is_first_session";
    private static final String KEY_CLICKED_LINKEDME_LINK = "clicked_linkedme_link";
    private static final String KEY_LINKEDME_KEY = "linkedme_key";
    private static final String KEY_BRANCH_KEY = "branch_key";

    public static String getKeyBranchKey() {
        return KEY_BRANCH_KEY;
    }

    private static final String KEY_SOURCE = "source";

    //server domain
//    private static final String KEY_TEST_DOMAIN = "http://127.0.0.1:8888/l/";
//    private static final String KEY_TEST_DOMAIN = "http://192.168.1.129:8888/l/";
//    private static final String KEY_TEST_DOMAIN = "http://192.168.10.101:8888/l/";
//    private static final String KEY_TEST_DOMAIN = "http://123.57.140.107/l/";
//    private static final String KEY_TEST_DOMAIN = "http://192.168.1.107:8888/l/";
//    private static final String KEY_TEST_DOMAIN = "http://182.92.108.111/l/";
//    private static final String KEY_TEST_DOMAIN = "http://192.168.11.177:8888/l/";
    private static final String KEY_TEST_DOMAIN = "http://lkme.cc/l/";



//
//        private static final String KEY_TEST_DOMAIN = "http://192.168.81.39:8888/l/";

    private static final String KEY_DOMAIN = "http://lmm.so/l/";
    private static final String KEY_LINK_ERROR = "http://error";
    private static final String KEY_SHORT_URL = "://clickId=";
    private static final String KEY_CLICK_ID_URL = "clickId=";
    private static final String KEY_LINK_CLICK_ID_URL = "link_click_id=";

    /**
     * construction function
     */

    public LMConfiguration() {
    }

    public static String getKeyLinkedmeKey() {
        return KEY_LINKEDME_KEY;
    }

    public static String getKeySource() {
        return KEY_SOURCE;
    }

    public static String getKeyIosBundleId() {
        return KEY_IOS_BUNDLE_ID;
    }

    public static String getKeyRetrynumber() {
        return KEY_RETRYNUMBER;
    }

    public static String getKeyIosTeamId() {
        return KEY_IOS_TEAM_ID;
    }

    public static String getKeyLinkIdentifier() {
        return KEY_LINK_IDENTIFIER;
    }

    public static String getKeySpotlightIdentifier() {
        return KEY_SPOTLIGHT_IDENTIFIER;
    }

    public static String getKeyUniversalLinkUrl() {
        return KEY_UNIVERSAL_LINK_URL;
    }

    public static String getKeyIsFirstSession() {
        return KEY_IS_FIRST_SESSION;
    }

    public static String getKeyClickedLinkedmeLink() {
        return KEY_CLICKED_LINKEDME_LINK;
    }

    public static String getKeyLinkClickIdUrl() {
        return KEY_LINK_CLICK_ID_URL;
    }

    public static String getKeyLatVal() {
        return KEY_LAT_VAL;
    }

    /**
     * get function
     */

    public static String getKeyAdTrackingEnabled() {
        return KEY_AD_TRACKING_ENABLED;
    }

    public static String getKeyOsRelease() {
        return KEY_OS_RELEASE;
    }

    public static String getKeyDeeplinkPath() {
        return KEY_DEEPLINK_PATH;
    }

    public static String getKeyCampagin() {
        return KEY_CAMPAGIN;
    }

    public static String getKeyClickIdUrl() {
        return KEY_CLICK_ID_URL;
    }

    public static String getKeyShortUrl() {
        return KEY_SHORT_URL;
    }

    public static String getKeyState() {
        return KEY_STATE;
    }

    public static String getKeyHasNfc() {
        return KEY_HAS_NFC;
    }

    public static String getKeyDuration() {
        return KEY_DURATION;
    }

    public static String getKeyBluetooth() {
        return KEY_BLUETOOTH;
    }

    public static String getKeyBluetoothVersion() {
        return KEY_BLUETOOTH_VERSION;
    }

    public static String getKeyHasTelephone() {
        return KEY_HAS_TELEPHONE;
    }

    public static String getKeyOsRealase() {
        return KEY_OS_REALASE;
    }

    public static String getKeyScreenDpi() {
        return KEY_SCREEN_DPI;
    }

    public static String getKeyWifi() {
        return KEY_WIFI;
    }

    public static String getKeyGoogleAdvertisingId() {
        return KEY_GOOGLE_ADVERTISING_ID;
    }

    public static String getKeyLinkError() {
        return KEY_LINK_ERROR;
    }

    public static String getKeyTestDomain() {
        return KEY_TEST_DOMAIN;
    }

    public static String getKeyDomain() {
        return KEY_DOMAIN;
    }

    public static String getKeyUrl() {
        return KEY_URL;
    }

    public static String getKeyBrowserFingerprintId() {
        return KEY_BROWSER_FINGERPRINT_ID;
    }

    public static String getKeyLink() {
        return KEY_LINK;
    }

    public static String getKeyTags() {
        return KEY_TAGS;
    }

    public static String getKeyChannel() {
        return KEY_CHANNEL;
    }

    public static String getKeyFeature() {
        return KEY_FEATURE;
    }

    public static String getKeyStage() {
        return KEY_STAGE;
    }

    public static String getKeyAlias() {
        return KEY_ALIAS;
    }

    public static String getKeyCarrier() {
        return KEY_CARRIER;
    }

    public static String getKeyUpdate() {
        return KEY_UPDATE;
    }

    public static String getKeyBrand() {
        return KEY_BRAND;
    }

    public static String getKeyHardwareId() {
        return KEY_HARDWARE_ID;
    }

    public static String getKeyIsHardwareIDReal() {
        return KEY_IS_HARDWARE_ID_REAl;
    }

    public static String getKeyModel() {
        return KEY_MODEL;
    }

    public static String getKeyScreenHeight() {
        return KEY_SCREEN_HEIGHT;
    }

    public static String getKeyScreenWidth() {
        return KEY_SCREEN_WIDTH;
    }

    public static String getKeySdk() {
        return KEY_SDK;
    }

    public static String getKeyType() {
        return KEY_TYPE;
    }

    public static String getKeyData() {
        return KEY_DATA;
    }

    public static String getKeyClickId() {
        return KEY_CLICK_ID;
    }

    public static String getKeyAppKey() {
        return KEY_APP_KEY;
    }

    public static String getKeyAppId() {
        return KEY_APP_ID;
    }

    public static String getKeyAppVersion() {
        return KEY_APP_VERSION;
    }

    public static String getKeyDebug() {
        return KEY_DEBUG;
    }

    public static String getKeyDeviceFingerprintId() {
        return KEY_DEVICE_FINGERPRINT_ID;
    }

    public static String getKeyIsReferrable() {
        return KEY_IS_REFERRABLE;
    }

    public static String getKeyOs() {
        return KEY_OS;
    }

    public static String getKeyOsVersion() {
        return KEY_OS_VERSION;
    }

    public static String getKeyUriScheme() {
        return KEY_URI_SCHEME;
    }

    public static String getKeySessionId() {
        return KEY_SESSION_ID;
    }

    public static String getKeyIdentityId() {
        return KEY_IDENTITY_ID;
    }

    public static String getKeyIdentity() {
        return KEY_IDENTITY;
    }

    public static String getKeyLinkClickId() {
        return KEY_LINK_CLICK_ID;
    }

    public static String getKeyLinkClickIdentifier() {
        return KEY_LINK_CLICK_IDENTIFIER;
    }

    public static String getKeySessionParams() {
        return KEY_SESSION_PARAMS;
    }

    public static String getKeyInstallParams() {
        return KEY_INSTALL_PARAMS;
    }

    public static String getKeyUserUrl() {
        return KEY_USER_URL;
    }

    public static String getTAG() {
        return TAG;
    }

    public static String getSharedPrefFile() {
        return SHARED_PREF_FILE;
    }
}
