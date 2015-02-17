package com.niedzielski.wikiimagesearch.android.dataclient;

import com.niedzielski.wikiimagesearch.android.model.QueryResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface PageDataClient {
    @GET("/?action=query&generator=prefixsearch&list=search&prop=pageimages&piprop=thumbnail&continue=&format=json")
    void queryPages(@Query("pithumbsize") int thumbSize,
                    @Query("gpssearch") String gpsSearch,
                    @Query("gpslimit") int gpsLimit,
                    @Query("srsearch") String srSearch,
                    @Query("pilimit") int piLimit,
                    Callback<QueryResponse> result);
}