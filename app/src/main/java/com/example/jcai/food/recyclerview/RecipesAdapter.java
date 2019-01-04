package com.example.jcai.food.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.jcai.food.api.models.Recipes;
import com.example.jcai.test.R;

import java.net.URI;
import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.startActivity;

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
        public View detail;
        public TextView recipe_link;
        public Button recipe_link_button;


        public RecipesViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.recipe_title);
            image = itemView.findViewById(R.id.recipe_image);
            detail = itemView.findViewById(R.id.recipe_detail);
            recipe_link_button = itemView.findViewById(R.id.recipe_link_button);
            //  arrow = itemView.findViewById(R.id.arrow);
        }

        private void setExpandVisibility(Recipes recipes) {
            boolean expanded = recipes.isExpanded();

            detail.setVisibility(expanded? View.VISIBLE : View.GONE);
            Log.d("adapter", "" + detail.getVisibility());

            recipe_link_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse(recipes.getSourceUrl());
                    Intent i = new Intent(Intent.ACTION_VIEW, uri);
                    mContext.startActivity(i);
                }
            });
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
        boolean expanded = recipes.isExpanded();
        TextView titleText = holder.title;
        titleText.setText(recipes.getTitle());

        holder.setExpandVisibility(recipes);

        ImageView image = holder.image;
        glide.load(recipes.getImageUrl())
                .apply(new RequestOptions().override(800,400)
                .centerCrop())
                .into(image);

        holder.itemView.setOnClickListener(v -> {
            recipes.setExpanded(!expanded);
            notifyItemChanged(position);
            Log.d("adapter", "isclicked " + recipes.isExpanded());
        });


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
