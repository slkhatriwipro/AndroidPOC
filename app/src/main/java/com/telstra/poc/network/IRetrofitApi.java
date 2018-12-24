package com.telstra.poc.network;

import com.google.gson.JsonElement;
import com.telstra.poc.utility.UrlUtility;

import io.reactivex.Observable;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

public interface IRetrofitApi {
    @FormUrlEncoded
    @GET(UrlUtility.FEED_URL)
    Observable<JsonElement> feed();
}
