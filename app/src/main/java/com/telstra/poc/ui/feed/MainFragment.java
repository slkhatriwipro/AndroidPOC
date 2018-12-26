package com.telstra.poc.ui.feed;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.telstra.poc.MainActivity;
import com.telstra.poc.R;
import com.telstra.poc.models.FeedsModel;
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

    FeedViewModel mViewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.main_fragment, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFeeds_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFeeds_rv.setItemAnimator(new DefaultItemAnimator());

        mViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(FeedViewModel.class);

        mViewModel.feedApiResponse().observe(getActivity(), this::feedResponse);
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
            Gson mGson = new Gson();
            FeedsModel mFeedsModel = mGson.fromJson(response, FeedsModel.class);
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(mFeedsModel.getTitle());
            mFeeds_rv.setAdapter(new FeedsRecyclerViewAdaptar(getContext(), getFragmentManager(), mFeedsModel.getRows()));
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.errorString), Toast.LENGTH_SHORT).show();
        }
    }

}
