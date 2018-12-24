package com.telstra.poc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.telstra.poc.app.FeedApplication;
import com.telstra.poc.ui.main.MainFragment;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        /*ButterKnife.bind(this);
        ((FeedApplication) getApplication()).getAppComponent().doInjection(this);*/

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }
}
