package com.example.jcai.food.events;

/**
 * Created by jcai on 3/29/18.
 */

public class GetSearchResponseEvent {
  private String api_key;
  private String q;
  private String sort;
  private int page;
  private boolean isCalled = false;

  public GetSearchResponseEvent(String api_key, String q, String sort, int page, Boolean isCalled){
      this.api_key = api_key;
      this.q = q;
      this.sort = sort;
      this.page = page;
      this.isCalled = isCalled;
  }

  public String getQuery() {
      return q;
  }

  public String getSort() {
      return sort;
  }

  public int getPage() {
      return page;
  }

  public Boolean getIsCalled(){
      return isCalled;
  }
}
