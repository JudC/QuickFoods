package com.example.jcai.food.api.models;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jcai on 3/14/18.
 */

public class SearchResponse {

        @SerializedName("count")
        @Expose
        private int count;

        @SerializedName("recipes")
        @Expose
        private ArrayList<Recipes> recipes = null;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public ArrayList<Recipes> getRecipes() {
            return recipes;
        }

        public void setRecipes(ArrayList<Recipes> recipes) {
            this.recipes = recipes;
        }
}
