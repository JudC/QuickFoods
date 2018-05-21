package com.example.jcai.food.events;

import com.example.jcai.food.api.models.SearchResponse;

import retrofit2.Response;

/**
 * Created by jcai on 3/29/18.
 */

public class SendSearchResponseEvent {
    private Response<SearchResponse> mResponse;

    public SendSearchResponseEvent(Response<SearchResponse> response) {
        mResponse = response;
    }

    public Response <SearchResponse> getResponse(){
        return mResponse;
    }
}
