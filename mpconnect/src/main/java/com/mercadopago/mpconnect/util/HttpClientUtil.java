package com.mercadopago.mpconnect.util;

import android.content.Context;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by mromar on 9/27/16.
 */
public class HttpClientUtil {

    private static OkHttpClient client;
    private static OkHttpClient customClient;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public synchronized static okhttp3.OkHttpClient getClient(Context context) {

        if (customClientSet()) {
            return customClient;
        } else {
            if (client == null) {
                createClient(context);
            }
            return client;
        }
    }

    private static void createClient(Context context) {
        // Set log info
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

        // Set cache size
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(new File(context.getCacheDir().getPath() + "okhttp"), cacheSize);

        // Set client
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(cache)
                .addInterceptor(interceptor);

        client = okHttpClientBuilder.build();
    }

    public static void setCustomClient(okhttp3.OkHttpClient client) {
        customClient = client;
    }

    public static void removeCustomClient() {
        customClient = null;
    }

    private static boolean customClientSet() {
        return customClient != null;
    }
}
