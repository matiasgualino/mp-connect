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
     * Start the web view for log in to Mercado Pago account and get its access token.
     * If base url is null, the credentials uri have to be null.
     * If credentials uri is null, the base url have to be null.
     * If both base url and credentials uri are null, this component will use Mercado Pago base url and credentials uri by default.
     *
     * @param activity                  Reference to Android Context. Can not be null or empty.
     * @param appId                     The app identification of the merchant in Mercado Pago. Can not be null or empty.
     * @param merchantBaseUrl           The merchant base url where the component will ask the access token of the logged user. It can be null.
     * @param merchantGetCredentialsUri The merchant uri where the component will get the credentials. Can not be null or empty. It can be null.
     * @param userIdentificationToken   The user identification linked to the access token. It can be null.
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
         * Validate parameters and start connect activity
         **/
        public void startConnectActivity() {
            if (this.mActivity == null) throw new IllegalStateException("activity is null");
            if (isEmpty(this.mAppId)) throw new IllegalStateException("app id is null or empty");
            if (isBaseUrlEmptyAndUriNotEmpty() || isCredentialsUriEmptyAndUrlNotEmpty()) throw new IllegalStateException("base url and credentials uri can not be null or empty");

            MPConnect.startConnectActivity(this.mActivity, this.mAppId, this.mMerchantBaseUrl, this.mMerchantGetCredentialsUri, this.mUserIdentificationToken);
        }

        private boolean isBaseUrlEmptyAndUriNotEmpty(){
           return isEmpty(mMerchantBaseUrl) && !isEmpty(mMerchantGetCredentialsUri);
        }

        private boolean isCredentialsUriEmptyAndUrlNotEmpty(){
            return !isEmpty(mMerchantBaseUrl) && isEmpty(mMerchantGetCredentialsUri);
        }
    }
}
