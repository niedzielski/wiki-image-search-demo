package com.niedzielski.wikiimagesearch.android.activity;

import android.content.Context;
import android.content.Intent;

public class PageFullScreenImageActivity extends SingleFragmentActivity<PageImageFragment> {
    public static Intent newIntent(Context context, String imageUrl) {
        return new Intent(context, PageFullScreenImageActivity.class)
                .putExtra(PageImageFragment.FRAGMENT_ARG_IMAGE_URL_KEY, imageUrl);
    }

    @Override
    protected PageImageFragment createFragment() {
        return PageImageFragment.newInstance(getIntent().getStringExtra(PageImageFragment.FRAGMENT_ARG_IMAGE_URL_KEY));
    }
}
