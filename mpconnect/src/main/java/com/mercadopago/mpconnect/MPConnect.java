package com.mercadopago.mpconnect;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by mromar on 10/3/16.
 */
public class MPConnect {

    public static final int CONNECT_REQUEST_CODE = 0;

    private static void startConnectActivity(Activity activity, String appId, String merchantBaseUrl, String merchantUri, String userIdToken){

        Intent connectIntent = new Intent(activity, ConnectActivity.class);
        connectIntent.putExtra("appId", appId);

        connectIntent.putExtra("merchantBaseUrl", merchantBaseUrl);
        connectIntent.putExtra("merchantUri", merchantUri);
        connectIntent.putExtra("userIdToken", userIdToken);

        activity.startActivityForResult(connectIntent, CONNECT_REQUEST_CODE);
    }

    public static class StartActivityBuilder {
        private Activity mActivity;
        private String mAppId;
        private String mMerchantBaseUrl;
        private String mMerchantUri;
        private String mUserIdToken;

        public StartActivityBuilder setActivity(Activity activity){
            this.mActivity = activity;
            return this;
        }

        public StartActivityBuilder setAppId(String appId){
            this.mAppId = appId;
            return this;
        }

        public StartActivityBuilder setUserIdToken(String userIdToken){
            this.mUserIdToken = userIdToken;
            return this;
        }

        public StartActivityBuilder setMerchantUri(String merchantUri){
            this.mMerchantUri = merchantUri;
            return this;
        }

        public StartActivityBuilder setMerchantBaseUrl(String merchantBaseUrl){
            this.mMerchantBaseUrl = merchantBaseUrl;
            return this;
        }

        public void startConnectActivity(){
            if (this.mActivity == null) throw new IllegalStateException("activity is null");
            if (this.mAppId == null) throw new IllegalStateException("app id is null");
            if (this.mMerchantBaseUrl == null) throw new IllegalStateException("base url is null");
            if (this.mMerchantUri == null) throw new IllegalStateException("uri is null");


            MPConnect.startConnectActivity(this.mActivity, this.mAppId, this.mMerchantBaseUrl, this.mMerchantUri, this.mUserIdToken);
        }
    }
}
