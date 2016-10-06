package com.mercadopago.mpconnect;

import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.mercadopago.mpconnect.model.AccessToken;
import com.mercadopago.mpconnect.test.FakeAPI;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.webKeys;

/**
 * Created by mromar on 10/4/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MPConnectActivityTest {

    @Rule
    public ActivityTestRule<ConnectActivity> mTestRule = new ActivityTestRule<>(ConnectActivity.class, true, false){
    @Override
    protected void afterActivityLaunched() {
        // Enable JS!
        onWebView().forceJavascriptEnabled();
    }
};
    private Intent validStartIntent;
    private FakeAPI mFakeAPI;
    private boolean mIntentsActive;

    @Before
    public void setValidStartIntent() {
        validStartIntent = new Intent();
        validStartIntent.putExtra("appId", "3339632528347950");
        validStartIntent.putExtra("merchantBaseUrl", "http://mpconnect-wrapper.herokuapp.com/");
        validStartIntent.putExtra("merchantGetCredentialsUri", "checkout/get_credentials");
        validStartIntent.putExtra("userIdentificationToken", "123456789");
    }

    @Before
    public void startFakeAPI() {
        mFakeAPI = new FakeAPI();
        mFakeAPI.start();
    }

    @Before
    public void initIntentsRecording() {
        Intents.init();
        mIntentsActive = true;
    }

    @After
    public void stopFakeAPI() {
        mFakeAPI.stop();
    }

    @After
    public void releaseIntents() {
        if (mIntentsActive) {
            mIntentsActive = false;
            Intents.release();
        }
    }

    @Test
    public void whenStartConnectWithAppIdBaseUrlUriAndUserIdTokenGetAccessToken(){
        AccessToken accessToken = getAccessToken();
        mFakeAPI.addResponseToQueue(accessToken, 200, "");

        ConnectActivity connectActivity = mTestRule.launchActivity(validStartIntent);

        onWebView().withElement(findElement(android.support.test.espresso.web.webdriver.Locator.ID, "user_id")).perform(webKeys("test_user_96694678@testuser.com"));
        onWebView().withElement(findElement(android.support.test.espresso.web.webdriver.Locator.ID, "passwordFieldBox")).perform(webKeys("test_user_96694678@testuser.com"));
        //onWebView().withElement(findElement(android.support.test.espresso.web.webdriver.Locator.ID, "signInButton")).perform(webClick());
    }

    private AccessToken getAccessToken(){
        AccessToken accessToken = new AccessToken();
        accessToken.setAccessToken("ACCESS-TOKEN-123456789");

        return accessToken;
    }
}
