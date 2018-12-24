package com.telstra.poc.ui.main;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.telstra.poc.network.RetrofitApiResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FeedViewModel extends ViewModel {
    private FeedRepository feedRepository;
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    private final MutableLiveData<RetrofitApiResponse> apiResponseMutableLiveData = new MutableLiveData<>();


    public FeedViewModel() {

    }

    public FeedViewModel(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    public MutableLiveData<RetrofitApiResponse> feedApiResponse() {
        return apiResponseMutableLiveData;
    }

    public void callFeedApi() {

        mDisposable.add(feedRepository.fetchFeed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> apiResponseMutableLiveData.setValue(RetrofitApiResponse.onSuccess(result)),
                        throwable -> apiResponseMutableLiveData.setValue(RetrofitApiResponse.onError(throwable))
                ));

    }

    @Override
    protected void onCleared() {
        mDisposable.clear();
    }
}
