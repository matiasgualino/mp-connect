package com.mercadopago.mpconnect.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mromar on 9/28/16.
 */
public class AccessToken {

    @SerializedName("access_token")
    public String mAccessToken;

    @SerializedName("public_key")
    public String mPublicKey;

    @SerializedName("refresh_token")
    public String mRefreshToken;

    @SerializedName("live_mode")
    public Boolean mLiveMode;

    @SerializedName("user_id")
    public Long mUserId;

    @SerializedName("token_type")
    public String mTokenType;

    @SerializedName("expires_in")
    public Long mExpiresIn;

    @SerializedName("scope")
    public String mScope;

    public String getAccessToken(){
        return this.mAccessToken;
    }

    public String getPublicKey(){
        return this.mPublicKey;
    }

    public String getmRefreshToken() {
        return this.mRefreshToken;
    }


}
