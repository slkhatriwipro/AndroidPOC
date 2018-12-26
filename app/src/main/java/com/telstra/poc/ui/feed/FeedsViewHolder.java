package com.telstra.poc.ui.feed;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.request.target.Target;
import com.telstra.poc.R;
import com.telstra.poc.di.GlideApp;
import com.telstra.poc.models.Row;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class FeedsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.feed_title_txt)
    AppCompatTextView mTitle;

    @BindView(R.id.feed_desc_txt)
    AppCompatTextView mDesc;

    @BindView(R.id.feed_item_image)
    ImageView mImage;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    private final Context context;
    private Row rowData;

    public FeedsViewHolder(final View itemView, Context context, FragmentManager supportFragmentManager) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = context;
    }

    public void bind(Row rowData) {
        this.rowData = rowData;
        mTitle.setText(rowData.getTitle());
        mDesc.setText(rowData.getDescription());

        GlideApp.with(context)
                .load(rowData.getImageHref())
                .transform(new RoundedCornersTransformation(10, 10))
                .override(300,300)
                .into(mImage);
    }
}