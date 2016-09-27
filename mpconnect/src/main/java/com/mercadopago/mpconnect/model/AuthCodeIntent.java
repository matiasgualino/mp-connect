package com.mercadopago.mpconnect.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mromar on 9/27/16.
 */
public class AuthCodeIntent {

    @SerializedName("authorizationCode")
    public String mAuthorizationCode;

    public void setAuthorizationCode(String authorizationCode){
        this.mAuthorizationCode = authorizationCode;
    }
}
