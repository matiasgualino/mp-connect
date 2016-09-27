package com.mercadopago.mpconnect;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MPConnectActivity extends AppCompatActivity {

    private WebView mWebView;
    private String mAppId;
    private String mRedirectUri;
    //TODO mejorar

    private String mUrl = "https://www.mercadopago.com.ar?code=";
    //private static final String MP_API_BASE_URL = "https://api.mercadopago.com";
    //private static final String BASE_URL = "https://api.mercadopago.com/";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpconnect);

        mAppId = getIntent().getStringExtra("appId");
        mRedirectUri = getIntent().getStringExtra("redirectUri");

        mWebView = (WebView) findViewById(R.id.webViewLib);


        mWebView.setWebViewClient(new OAuthWebViewClient(this));
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();

        //FIX LOLLIPOP WEBVIEW CHROME HTTPS (UNSECURE MIXED CONTENT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            cookieManager.setAcceptThirdPartyCookies(mWebView, true);
        }

        mWebView.loadUrl("https://auth.mercadopago.com.ar/authorization?client_id=" + mAppId + "&response_type=code&platform_id=mp&redirect_uri=" + mRedirectUri);
        cookieManager.removeAllCookie();
        mWebView.clearCache(true);
    }

    private class OAuthWebViewClient extends WebViewClient {
        private Activity current;

        public OAuthWebViewClient(Activity current) {
            this.current = current;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains(mUrl)) {
                //TODO agarrar código
                Toast.makeText(MPConnectActivity.this, "Hola!", Toast.LENGTH_SHORT).show();
                getPrivateKey();
                current.finish();
                return true;
            }
            return false;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            //onFailure(current, String.valueOf(errorCode), description, null);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
//            isPageLoading = Boolean.TRUE;
//            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
//            isPageLoading = Boolean.FALSE;
//            progressBar.setVisibility(View.GONE);
        }

    }

    private void getPrivateKey() {
        /*Retrofit retrofitBuilder = new Retrofit.Builder()
                .client(HttpClientUtil.getClient(this))
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        MPService service = retrofitBuilder.create(MPService.class);

        Call<String> call = service.getPrivateKey(mPublicKey, mAuthoriztionCode);

        /*call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code() == 400) {
                    Log.e("Failure","Error 400, parameter invalid");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Failure","Service failure");
            }
        });
        */
    }
}
