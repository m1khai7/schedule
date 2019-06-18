package com.example.misha.myapplication.data.network;

import android.util.Log;

import com.example.misha.myapplication.BuildConfig;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings("PMD")
public final class RetrofitClient {

    private static final int TIME_OUT = 15;

    private static volatile RetrofitClient instance;

    private final Retrofit retrofit;

    private final OkHttpClient okHttpClient;

    private final APIService requestInterface;

    private RetrofitClient() {

        okHttpClient = buildOkHttpClient();
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        requestInterface = retrofit.create(APIService.class);
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public APIService getRequestInterface() {
        return requestInterface;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    private OkHttpClient buildOkHttpClient() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        httpClientBuilder.connectionSpecs(
                Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT));
        if (BuildConfig.DEBUG) {
            httpClientBuilder.addInterceptor(provideHttpLoggingInterceptor());
        }
        return httpClientBuilder.build();
    }

    private HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor =
                new HttpLoggingInterceptor(message -> Log.d("HttpLogging", message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }


    private Gson provideGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .setLenient().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

}
