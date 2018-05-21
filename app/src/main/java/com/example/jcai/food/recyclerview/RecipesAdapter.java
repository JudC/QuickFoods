package com.example.jcai.food.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.jcai.food.api.models.Recipes;
import com.example.jcai.test.R;

import java.util.ArrayList;

/**
 * Created by jcai on 3/15/18.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {
    private ArrayList<Recipes> mRecipes;
    private Context mContext;
    private final RequestManager glide;

    public RecipesAdapter(Context mContext, ArrayList<Recipes> mRecipes, RequestManager glide){
        this.glide = glide;
        this.mContext = mContext;
        this.mRecipes = mRecipes;
    }

    public class RecipesViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;


        public RecipesViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.recipe_title);
            image = itemView.findViewById(R.id.recipe_image);
            //  arrow = itemView.findViewById(R.id.arrow);
        }
    }

    @Override
    public RecipesAdapter.RecipesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mCardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new RecipesViewHolder(mCardView);
    }

    @Override
    public void onBindViewHolder(RecipesAdapter.RecipesViewHolder holder, int position) {
        Recipes recipes = mRecipes.get(position);
        TextView titleText = holder.title;
        titleText.setText(recipes.getTitle());

        ImageView image = holder.image;
        glide.load(recipes.getImageUrl())
                .apply(new RequestOptions().override(800,400)
                .centerCrop())
                .into(image);
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public void updateData(ArrayList<Recipes> recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }
}
