package com.telstra.poc.app;

import android.app.Application;
import android.content.Context;

import com.telstra.poc.di.AppFeedComponent;
import com.telstra.poc.di.AppFeedModule;
import com.telstra.poc.di.DaggerAppFeedComponent;
import com.telstra.poc.di.UtilsFeedModule;

public class FeedApplication extends Application {
    AppFeedComponent appFeedComponent;
    Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        appFeedComponent = DaggerAppFeedComponent.builder()
                .appFeedModule(new AppFeedModule(mContext))
                .utilsFeedModule(new UtilsFeedModule())
                .build();
    }

    public AppFeedComponent getAppComponent() {
        return appFeedComponent;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }
}
