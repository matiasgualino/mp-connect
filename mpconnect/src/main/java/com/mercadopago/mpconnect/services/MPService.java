package com.mercadopago.mpconnect.services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by mromar on 9/26/16.
 */
public interface MPService {

    @POST("/v1/card_tokens")
    Call<String> getPrivateKey(@Query("public_key") String publicKey, @Body String authorizationCode);
}
