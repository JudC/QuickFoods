package com.example.jcai.food.api.remote;

import com.example.jcai.food.api.models.SearchResponse;
import com.example.jcai.food.api.models.Recipe;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jcai on 3/14/18.
 */

public interface F2FApiService {
    @GET("search")
    Call<SearchResponse> getSearchResponse(
            @Query("key") String key,
            @Query("q") String q,
            @Query("sort") String sort,
            @Query("page") int page
    );

    @GET("get")
    Call<Recipe> getRecipe(
            @Query("key") String key,
            @Query("rId") String Id
    );
}

