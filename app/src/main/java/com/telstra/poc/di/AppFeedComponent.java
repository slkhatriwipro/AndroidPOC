package com.telstra.poc.di;

import com.telstra.poc.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppFeedModule.class, UtilsFeedModule.class})
@Singleton
public interface AppFeedComponent {
    void doInjection(MainActivity loginActivity);

    /*@Component.Builder
    interface Builder {
        AppFeedComponent build();
        Builder appModule(AppFeedModule appModule);
    }*/
}
