package com.telstra.poc.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.telstra.poc.MainActivity;
import com.telstra.poc.R;
import com.telstra.poc.app.FeedApplication;
import com.telstra.poc.network.ApiCallStatus;
import com.telstra.poc.network.RetrofitApiResponse;
import com.telstra.poc.utility.ViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment {

    @Inject
    ViewModelFactory viewModelFactory;

    @BindView(R.id.feeds_rv)
    RecyclerView mFeeds_rv;

    private FeedViewModel mViewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ButterKnife.bind(getActivity());
        ((FeedApplication) getActivity().getApplication()).getAppComponent().doInjection((MainActivity) getActivity());

        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(FeedViewModel.class);
        mViewModel.feedApiResponse().observe(this, this::feedResponse);
        mViewModel.callFeedApi();
    }

    private void feedResponse(RetrofitApiResponse retrofitApiResponse) {
        switch (retrofitApiResponse.apiCallStatus) {

            case ONSUCCESS:
                onSuccessResponse(retrofitApiResponse.response);
                break;

            case ONERROR:
                Toast.makeText(getActivity(), getResources().getString(R.string.errorString), Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    private void onSuccessResponse(JsonElement response) {
        if (!response.isJsonNull()) {
            Log.d("Feed Response =", response.toString());
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.errorString), Toast.LENGTH_SHORT).show();
        }
    }

}
