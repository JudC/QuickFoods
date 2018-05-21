package com.example.jcai.food.activity;

import com.example.jcai.food.api.models.SearchResponse;
import com.example.jcai.food.api.remote.F2FApiService;
import com.example.jcai.food.api.remote.RetrofitClient;

import retrofit2.Call;

/**
 * Created by jcai on 3/27/18.
 */

public final class SearchService {

    public Call<SearchResponse> getSearchResponse(String key, String q, String sort, int page){
        F2FApiService api = RetrofitClient.getApiService();
        return api.getSearchResponse(key, q, sort, page);
    }
}
