package com.mercadopago.mpconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MPConnectActivity extends AppCompatActivity{

    private WebView mWebView;
    private String mAppId;
    private String mRedirectUri;
    //TODO mejorar
    private String mUrl = "http://www.mercadopago.com.ar?code=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpconnect);

        mAppId = getIntent().getStringExtra("appId");
        mRedirectUri = getIntent().getStringExtra("redirectUri");

        mWebView = (WebView) findViewById(R.id.webViewLib);
        mWebView.loadUrl("https://auth.mercadopago.com.ar/authorization?client_id="+ mAppId +"&response_type=code&platform_id=mp&redirect_uri=http%3A%2F%2F" + mRedirectUri );
        //mWebView.loadUrl("http://infobae.com");
        mWebView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                if (url.contains(mUrl)){
                    //TODO agarrar código
                    //getAuthorizationCode();
                    return true;
                }
                return false;
            }
        });
    }
}
