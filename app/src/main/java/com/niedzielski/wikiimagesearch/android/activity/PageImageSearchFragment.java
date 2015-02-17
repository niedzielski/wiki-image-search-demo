package com.niedzielski.wikiimagesearch.android.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.niedzielski.wikiimagesearch.android.R;
import com.niedzielski.wikiimagesearch.android.model.Page;
import com.niedzielski.wikiimagesearch.android.util.ImageUtil;

import java.util.Collection;

import butterknife.InjectView;
import butterknife.OnItemClick;

public class PageImageSearchFragment extends DefaultFragment {
    public interface PageImageSearchFragmentCallback extends FragmentCallback {
        void onPageClick(Page page);
    }

    @InjectView(R.id.list)
    protected ListView mList;

    @OnItemClick(R.id.list)
    public void onItemClick(int position) {
        getCallback().onPageClick(mListAdapter.getItem(position));
    }

    private PageAdapter mListAdapter;

    public static PageImageSearchFragment newInstance() {
        return new PageImageSearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mListAdapter = new PageAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mList.setAdapter(mListAdapter);

        return view;
    }

    public void setPageResults(Collection<Page> pages) {
        if (getActivity() != null) {
            mListAdapter.clear();
            mListAdapter.addAll(pages);
            mListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initFromFragmentArgs() {}

    @Override
    protected PageImageSearchFragmentCallback getCallback() {
        return (PageImageSearchFragmentCallback) super.getCallback();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_image_search;
    }

    private static class PageAdapter extends ArrayAdapter<Page> {
        public PageAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = null;

            if (convertView == null) {
                imageView = new ImageView(parent.getContext());
            } else {
                imageView = (ImageView) convertView;
            }

            Page item = getItem(position);
            if (item.hasThumbnail()) {
                ImageUtil.load(item.thumbnail().source(), imageView);
            }

            return imageView;
        }
    }
}