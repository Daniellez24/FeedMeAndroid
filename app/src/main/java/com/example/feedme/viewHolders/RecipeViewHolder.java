package com.example.feedme.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedme.R;
import com.example.feedme.models.Recipe;

public class RecipeViewHolder extends RecyclerView.ViewHolder {
    TextView recipeTitle, recipeBody;
    ImageView profileImg, recipeImage;
    //todo complete the feedMe

    public RecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        recipeTitle = itemView.findViewById(R.id.feed_item_title_et);
        recipeBody = itemView.findViewById(R.id.feed_item_recipe_body_et);
        profileImg = itemView.findViewById(R.id.feed_item_profile_image_iv);
        recipeImage = itemView.findViewById(R.id.feed_item_recipe_image_iv);
    }

    public void bind(Recipe recipe){
        recipeBody.setText(recipe.getRecipeBody());
        recipeTitle.setText(recipe.getRecipeTitle());
        profileImg.setBackgroundResource(recipe.getProfilePic());
        recipeImage.setBackgroundResource(recipe.getRecipeImage());
    }
}