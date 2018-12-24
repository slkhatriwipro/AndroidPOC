package com.telstra.poc.utility;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.telstra.poc.ui.main.FeedRepository;
import com.telstra.poc.ui.main.FeedViewModel;

import javax.inject.Inject;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private FeedRepository feedRepository;

    @Inject
    public ViewModelFactory(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FeedViewModel.class)) {
            return (T) new FeedViewModel(feedRepository);
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
