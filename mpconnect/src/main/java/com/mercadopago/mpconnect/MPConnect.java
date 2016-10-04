package com.mercadopago.mpconnect;

import android.app.Activity;
import android.content.Intent;

import static android.text.TextUtils.isEmpty;

/**
 * Created by mromar on 10/3/16.
 */
public class MPConnect {

    public static final int CONNECT_REQUEST_CODE = 0;

    /**
     * Start the ConnectActivity
     *
     * @param activity                  Reference to Android Context. Cannot be {@code null or empty}.
     * @param appId                     The app identification of the merchant in Mercado Pago. {@code null or empty}.
     * @param merchantBaseUrl           The merchant base url for where ask the access token of the user. {@code null or empty}.
     * @param merchantGetCredentialsUri The merchant uri where get the credentials. {@code null or empty}.
     * @param userIdentificationToken   The user identification asociate with an access token. Can be {@code null or empty}.
     */
    private static void startConnectActivity(Activity activity, String appId, String merchantBaseUrl, String merchantGetCredentialsUri, String userIdentificationToken) {

        Intent connectIntent = new Intent(activity, ConnectActivity.class);
        connectIntent.putExtra("appId", appId);
        connectIntent.putExtra("merchantBaseUrl", merchantBaseUrl);
        connectIntent.putExtra("merchantGetCredentialsUri", merchantGetCredentialsUri);
        connectIntent.putExtra("userIdentificationToken", userIdentificationToken);

        activity.startActivityForResult(connectIntent, CONNECT_REQUEST_CODE);
    }

    public static class StartActivityBuilder {
        private Activity mActivity;
        private String mAppId;
        private String mMerchantBaseUrl;
        private String mMerchantGetCredentialsUri;
        private String mUserIdentificationToken;

        public StartActivityBuilder setActivity(Activity activity) {
            this.mActivity = activity;
            return this;
        }

        public StartActivityBuilder setAppId(String appId) {
            this.mAppId = appId;
            return this;
        }

        public StartActivityBuilder setUserIdentificationToken(String userIdentificationToken) {
            this.mUserIdentificationToken = userIdentificationToken;
            return this;
        }

        public StartActivityBuilder setMerchantGetCredentialsUri(String merchantGetCredentialsUri) {
            this.mMerchantGetCredentialsUri = merchantGetCredentialsUri;
            return this;
        }

        public StartActivityBuilder setMerchantBaseUrl(String merchantBaseUrl) {
            this.mMerchantBaseUrl = merchantBaseUrl;
            return this;
        }

        /**
         * Validate the parameters and start connect activity
         **/
        public void startConnectActivity() {
            if (this.mActivity == null) throw new IllegalStateException("activity is null");
            if (isEmpty(this.mAppId)) throw new IllegalStateException("app id is null or empty");
            if (isEmpty(this.mMerchantBaseUrl))
                throw new IllegalStateException("base url is null or empty");
            if (isEmpty(this.mMerchantGetCredentialsUri))
                throw new IllegalStateException("uri is null or empty");

            MPConnect.startConnectActivity(this.mActivity, this.mAppId, this.mMerchantBaseUrl, this.mMerchantGetCredentialsUri, this.mUserIdentificationToken);
        }
    }
}
