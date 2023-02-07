package com.example.feedme.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedme.R;
import com.example.feedme.models.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeViewHolder extends RecyclerView.ViewHolder {
    TextView recipeTitle, recipeBody;
    ImageView recipeImage;
//    List<Recipe> data;

    public RecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        recipeTitle = itemView.findViewById(R.id.feed_item_title_et);
        recipeBody = itemView.findViewById(R.id.feed_item_recipe_body_et);
        recipeImage = itemView.findViewById(R.id.feed_item_recipe_image_iv2);
    }

    public void bind(Recipe recipe){
        recipeBody.setText(recipe.getRecipeBody());
        recipeTitle.setText(recipe.getRecipeTitle());
        if(recipe.getRecipeImage() != "" && recipe.getRecipeImage().length() > 5){
            Picasso.get().load(recipe.getRecipeImage()).placeholder(R.drawable.cooking_icon).into(recipeImage);
        } else {
            recipeImage.setImageResource(R.drawable.cooking_icon);
        }
    }
}