package com.telstra.poc.ui.feed;

import com.google.gson.JsonElement;
import com.telstra.poc.network.IRetrofitApi;

import io.reactivex.Observable;

public class FeedRepository {

    private IRetrofitApi retrofitApi;

    public FeedRepository(IRetrofitApi retrofitApi) {
        this.retrofitApi = retrofitApi;
    }

    //Calling Feed Api
    public Observable<JsonElement> fetchFeed() {
        return retrofitApi.feed();
    }

}
