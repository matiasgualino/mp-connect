package com.mercadopago.mpconnect;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mercadopago.mpconnect.model.AccessToken;
import com.mercadopago.mpconnect.model.AuthCodeIntent;
import com.mercadopago.mpconnect.services.AccessTokenService;
import com.mercadopago.mpconnect.util.HttpClientUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectActivity extends AppCompatActivity {

    //Control
    private WebView mWebView;

    //Local Ver Connect Mercado Pago
    private static final String mUrl = "https://www.mercadopago.com.ar/?code=";
    private static final String mRedirectUri = "https://www.mercadopago.com.ar";
    private AccessToken mAccessToken;

    //Parameters
    private String mAppId;
    private String mMerchantBaseUrl;
    private String mMerchantGetCredentialsUri;
    private String mUserIdentificationToken;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpconnect);

        getActivityParameters();
        initializeWebView();

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains(mUrl)) {
                    String authCode = getAuthCodeFromUrl(url);
                    getPrivateKey(authCode);
                    return true;
                }
                return false;
            }
        });

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();

        //Fix Lollipop webview Chrome HTTPS (unsecure mixed content)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            cookieManager.setAcceptThirdPartyCookies(mWebView, true);
        }

        mWebView.loadUrl("https://auth.mercadopago.com.ar/authorization?client_id=" + mAppId + "&response_type=code&platform_id=mp&redirect_uri=" + mRedirectUri);
        cookieManager.removeAllCookie();
        mWebView.clearCache(true);
    }

    private void getActivityParameters() {
        mAppId = getIntent().getStringExtra("appId");
        mMerchantBaseUrl = getIntent().getStringExtra("merchantBaseUrl");
        mMerchantGetCredentialsUri = getIntent().getStringExtra("merchantGetCredentialsUri");
        mUserIdentificationToken = getIntent().getStringExtra("userIdentificationToken");
    }

    private void initializeWebView() {
        mWebView = (WebView) findViewById(R.id.webViewLib);
    }

    private String getAuthCodeFromUrl(String url) {
        return url.substring(url.lastIndexOf("=") + 1);
    }

    private void getPrivateKey(String authCode) {
        AuthCodeIntent authCodeIntent = new AuthCodeIntent(authCode, mRedirectUri, mUserIdentificationToken);

        Retrofit retrofitBuilder = new Retrofit.Builder()
                .client(HttpClientUtil.getClient(this))
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mMerchantBaseUrl)
                .build();

        AccessTokenService service = retrofitBuilder.create(AccessTokenService.class);

        Call<AccessToken> call = service.getAccessToken(mMerchantGetCredentialsUri, authCodeIntent);
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.code() >= 200 && response.code() <= 300) {
                    mAccessToken = response.body();
                    finishWithResult();
                } else {
                    finishWithCancelResult();
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Log.e("Failure", "Service failure");
                finishWithCancelResult();
            }
        });
    }

    private void finishWithResult() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("accessToken", mAccessToken.getAccessToken());
        this.setResult(RESULT_OK, resultIntent);
        this.finish();
    }

    private void finishWithCancelResult() {
        this.setResult(RESULT_CANCELED);
        this.finish();
    }
}
