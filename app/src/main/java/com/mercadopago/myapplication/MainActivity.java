package com.mercadopago.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.mercadopago.mpconnect.MPConnectActivity;

public class MainActivity extends AppCompatActivity {

    private String appId;
    private String redirectUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appId = "1440117086771094";
        redirectUri = "www.mercadopago.com.ar";

        Intent intent = new Intent(this, MPConnectActivity.class);
        intent.putExtra("appId", appId);
        intent.putExtra("redirectUri", redirectUri);
        startActivity(intent);
    }
}
