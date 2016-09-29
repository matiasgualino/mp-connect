package com.mercadopago.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import com.mercadopago.mpconnect.MPConnectActivity;

public class MainActivity extends AppCompatActivity {

    private String mAppId;
    private String mRedirectUri;
    private String mAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAppId = "3339632528347950";
        mRedirectUri = "https://www.mercadopago.com.ar";

        Intent intent = new Intent(this, MPConnectActivity.class);
        intent.putExtra("appId", mAppId);
        intent.putExtra("redirectUri", mRedirectUri);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MPConnectActivity.CONNECT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                mAccessToken = getIntent().getStringExtra("accessToken");
                Toast.makeText(MainActivity.this, "AccessToken" + mAccessToken, Toast.LENGTH_SHORT).show();
                this.finish();
            } else {
                this.finish();
            }
        }
    }
}
