package com.niedzielski.wikiimagesearch.android.util;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageUtil {
    public static void load(String url, ImageView view) {
        Picasso.with(view.getContext())
                .load(url)
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .into(view);

    }

    private ImageUtil() {}
}