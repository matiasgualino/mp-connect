package com.mercadopago.mpconnect.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mromar on 9/27/16.
 */
public class AuthCodeIntent {

    @SerializedName("code")
    private String authorizationCode;

    @SerializedName("redirect_uri")
    private String redirectUri;

    @SerializedName("user_id_access_token")
    private String userIdentificationToken;

    public AuthCodeIntent(String authCode, String redirectUri, String userIdentificationToken) {
        this.authorizationCode = authCode;
        this.redirectUri = redirectUri;
        this.userIdentificationToken = userIdentificationToken;
    }
}
