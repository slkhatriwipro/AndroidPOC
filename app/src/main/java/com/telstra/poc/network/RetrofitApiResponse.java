package com.telstra.poc.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.JsonElement;

public class RetrofitApiResponse {
    public final ApiCallStatus apiCallStatus;

    @Nullable
    public final JsonElement response;

    @Nullable
    public final Throwable error;

    private RetrofitApiResponse(ApiCallStatus apiCallStatus, @Nullable JsonElement response, @Nullable Throwable error) {
        this.apiCallStatus = apiCallStatus;
        this.response = response;
        this.error = error;
    }

    public static RetrofitApiResponse onSuccess(@NonNull JsonElement response) {
        return new RetrofitApiResponse(ApiCallStatus.ONSUCCESS, response, null);
    }

    public static RetrofitApiResponse onError(@NonNull Throwable error) {
        return new RetrofitApiResponse(ApiCallStatus.ONERROR, null, error);
    }
}
