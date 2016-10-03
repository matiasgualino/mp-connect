package com.mercadopago.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.mercadopago.mpconnect.MPConnect;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new MPConnect.StartActivityBuilder()
                .setActivity(this)
                .setAppId("3339632528347950")
                .setMerchantBaseUrl("http://mpconnect-wrapper.herokuapp.com/")
                .setMerchantGetCredentialsUri("checkout/get_credentials")
                .setUserIdentificationToken("123456789")
                .startConnectActivity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String accessToken;

        if (requestCode == MPConnect.CONNECT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                accessToken = data.getStringExtra("accessToken");
                Toast.makeText(MainActivity.this, "AccessToken: " + accessToken, Toast.LENGTH_SHORT).show();
                this.finish();
            } else {
                this.finish();
            }
        }
    }
}
