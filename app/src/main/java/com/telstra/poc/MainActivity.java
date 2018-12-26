package com.telstra.poc;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.telstra.poc.app.FeedApplication;
import com.telstra.poc.ui.main.FeedViewModel;
import com.telstra.poc.ui.main.MainFragment;
import com.telstra.poc.utility.ViewModelFactory;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Inject
    ViewModelFactory viewModelFactory;

    FeedViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ButterKnife.bind(this);
        ((FeedApplication) getApplication()).getAppComponent().doInjection(this);

        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(FeedViewModel.class);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }
}
