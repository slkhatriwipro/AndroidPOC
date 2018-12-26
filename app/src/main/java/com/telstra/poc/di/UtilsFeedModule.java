package com.telstra.poc.di;

import android.arch.lifecycle.ViewModelProvider;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.telstra.poc.network.IRetrofitApi;
import com.telstra.poc.ui.feed.FeedRepository;
import com.telstra.poc.utility.UrlUtility;
import com.telstra.poc.utility.ViewModelFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class UtilsFeedModule {
    @Provides
    @Singleton
    Gson getGsonSingleton() {
        GsonBuilder builder =
                new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return builder.setLenient().create();
    }

    @Provides
    @Singleton
    Retrofit getRetrofitSingleton(Gson gson, OkHttpClient okHttpClient) {

        return new Retrofit.Builder()
                .baseUrl(UrlUtility.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    @Singleton
    IRetrofitApi getRetrofitApi(Retrofit retrofit) {
        return retrofit.create(IRetrofitApi.class);
    }

    @Provides
    @Singleton
    OkHttpClient getRequestHeader() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .build();
            return chain.proceed(request);
        })
                .connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS);

        return httpClient.build();
    }

    @Provides
    @Singleton
    FeedRepository getFeedRepository(IRetrofitApi iRetrofitApi) {
        return new FeedRepository(iRetrofitApi);
    }

    @Provides
    @Singleton
    ViewModelProvider.Factory getViewModelFactory(FeedRepository feedRepository) {
        return new ViewModelFactory(feedRepository);
    }
}
