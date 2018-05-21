package com.example.jcai.food.manager;

import android.content.Context;
import android.util.Log;

import com.example.jcai.food.activity.SearchService;
import com.example.jcai.food.api.models.SearchResponse;
import com.example.jcai.food.api.remote.F2FApiService;
import com.example.jcai.food.events.GetSearchResponseEvent;
import com.example.jcai.food.api.remote.RetrofitClient;
import com.example.jcai.food.events.SendSearchResponseEvent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jcai on 3/28/18.
 */

public class RecipesSearchManager {
    private Context mContext;
    private F2FApiService mApiService;

    public RecipesSearchManager(Context context) {
        mContext = context;
        mApiService = RetrofitClient.getApiService();
    }

    @Subscribe(sticky = true)
    public void onGetSearchResponseEvent(GetSearchResponseEvent getSearchResponseEvent) {
        SearchService service = new SearchService();
        Call<SearchResponse> getSearchResponse = service.getSearchResponse(
                RetrofitClient.api_key,
                getSearchResponseEvent.getQuery(),
                getSearchResponseEvent.getSort(),
                getSearchResponseEvent.getPage());

        getSearchResponse.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {

                if (response.isSuccessful()) {
                    Log.d("MainActivity", "Success");

                    EventBus.getDefault().post(new SendSearchResponseEvent(response));

                } else {
                    int statusCode = response.code();
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Log.d("MainActivity", "Error loading api");
            }
        });
    }

}

