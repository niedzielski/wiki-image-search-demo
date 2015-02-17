package com.niedzielski.wikiimagesearch.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;

import com.niedzielski.wikiimagesearch.android.R;
import com.niedzielski.wikiimagesearch.android.activity.PageImageSearchFragment.PageImageSearchFragmentCallback;
import com.niedzielski.wikiimagesearch.android.dataclient.PageDataClient;
import com.niedzielski.wikiimagesearch.android.model.Page;
import com.niedzielski.wikiimagesearch.android.model.QueryResponse;

import java.util.Collections;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PageImageSearchActivity extends SingleFragmentActivity<PageImageSearchFragment>
        implements PageImageSearchFragmentCallback {
    private static final String TAG = PageImageSearchActivity.class.getName();

    private final Callback<QueryResponse> mQueryCallback = new Callback<QueryResponse>() {
        @Override
        public void success(QueryResponse queryResponse, Response response) {
            getFragment().setPageResults(queryResponse.hasQuery()
                    ? queryResponse.query().pages().values()
                    : Collections.<Page>emptyList());
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e(TAG, error.toString());
            // TODO: enhanced error handling.
        }
    };

    private PageDataClient mDataClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataClient();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_bar, menu);

        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                queryPages(newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        searchView.setQuery(getString(R.string.page_image_search_init_query), true);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onPageClick(Page page) {
        if (page.hasThumbnail()) {
            launchFullScreenImageActivity(page.thumbnail().source());
        }
    }

    @Override
    protected PageImageSearchFragment createFragment() {
        return PageImageSearchFragment.newInstance();
    }

    private void queryPages(String query) {
        int resultLimit = getResources().getInteger(R.integer.query_result_limit);
        mDataClient.queryPages(getResources().getDimensionPixelSize(R.dimen.thumbnail_size), query,
                resultLimit, query, resultLimit, mQueryCallback);
    }

    private void initDataClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(getString(R.string.page_service_uri))
                .build();

        mDataClient = restAdapter.create(PageDataClient.class);
    }

    private void launchFullScreenImageActivity(String imageUrl) {
        startActivity(PageFullScreenImageActivity.newIntent(this, imageUrl));
        animateNewActivityIn();
    }
}