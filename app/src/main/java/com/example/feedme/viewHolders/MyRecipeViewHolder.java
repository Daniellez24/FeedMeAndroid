package com.example.feedme.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedme.R;
import com.example.feedme.adapters.MyRecipesRecyclerAdapter;
import com.example.feedme.models.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

// holds the view of one row
public class MyRecipeViewHolder extends RecyclerView.ViewHolder {

    TextView title;
    TextView body;
    ImageView recipeImage;
    List<Recipe> data;

    public MyRecipeViewHolder(@NonNull View itemView, List<Recipe> data) {
        super(itemView);
        this.data = data;
        title = itemView.findViewById(R.id.myRecipes_title_tv);
        body = itemView.findViewById(R.id.myRecipes_body_tv);
        recipeImage = itemView.findViewById(R.id.myRecipes_recipe_image);

    }

    public void bind(Recipe recipe, int pos){
        title.setText(recipe.getRecipeTitle());
        body.setText(recipe.getRecipeBody());
        if(recipe.getRecipeImage() != null && recipe.getRecipeImage().length() > 5){
            Picasso.get().load(recipe.getRecipeImage()).placeholder(R.drawable.cooking_icon).into(recipeImage);
        } else {
            recipeImage.setImageResource(R.drawable.cooking_icon);
        }
    }
}
