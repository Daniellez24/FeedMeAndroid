package com.example.feedme;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.feedme.databinding.FragmentEditRecipeBinding;
import com.example.feedme.models.Model;
import com.example.feedme.models.Recipe;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

public class EditRecipeFragment extends Fragment {

    FragmentEditRecipeBinding binding;
    Recipe recipe;

    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;
    Boolean isImageSelected = false;

    private Toast deleteToast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EditRecipeFragmentArgs args = EditRecipeFragmentArgs.fromBundle(getArguments());
        recipe = args.getRecipe();

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if(result != null){
                    binding.editRecipeFragmentRecipeImg.setImageBitmap(result);
                    isImageSelected = true;
                }
            }
        });

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if(result != null){
                    binding.editRecipeFragmentRecipeImg.setImageURI(result);
                    isImageSelected = true;
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentEditRecipeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // show current recipe data when entering the selected recipe
        Model.instance().getSelectedRecipeData(recipe.getRecipeId(), (recipe) -> {
            binding.editRecipeFragmentTitleEt.setText(recipe.getRecipeTitle());
            binding.editRecipeFragmentRecipeEt.setText(recipe.getRecipeBody());
            if(recipe.getRecipeImage() != ""){
                Picasso.get().load(recipe.getRecipeImage()).placeholder(R.drawable.cooking_icon).into(binding.editRecipeFragmentRecipeImg);
                isImageSelected = true;
            }
        });

        binding.editRecipeFragmentUpdateBtn.setOnClickListener((v) -> {
            binding.editRecipeFragmentTitleEt.setText(binding.editRecipeFragmentTitleEt.getText().toString());
            binding.editRecipeFragmentRecipeEt.setText(binding.editRecipeFragmentRecipeEt.getText().toString());
            String title = binding.editRecipeFragmentTitleEt.getText().toString();
            String body = binding.editRecipeFragmentRecipeEt.getText().toString();
            String userId = recipe.getUserId();
            String recipeId = recipe.getRecipeId();
            Recipe r = new Recipe(userId, "", title, body, recipeId);

            if(isImageSelected){
                binding.editRecipeFragmentRecipeImg.setDrawingCacheEnabled(true);
                binding.editRecipeFragmentRecipeImg.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable)binding.editRecipeFragmentRecipeImg.getDrawable()).getBitmap();

                Model.instance().uploadImage(r.getRecipeId(), bitmap, url -> {
                    if(url != null){
                        r.setRecipeImage(url);
                    }
                    Model.instance().editRecipe(r, (unused) -> {
                        Navigation.findNavController(v).popBackStack();
                        Model.instance().refreshRecipes();
                        Model.instance().refreshMyRecipesList();
                    });
                });
            } else {
                Model.instance().editRecipe(r, (unused) -> {
                    Navigation.findNavController(v).popBackStack();
                    Model.instance().refreshRecipes();
                    Model.instance().refreshMyRecipesList();
                });
            }
        });

        binding.editRecipeFragmentDeleteBtn.setOnClickListener((v) -> {
            Model.instance().deleteRecipe(recipe, (unused) -> {
                Navigation.findNavController(v).popBackStack();
                Model.instance().refreshRecipes();
                Model.instance().refreshMyRecipesList();

                Snackbar.make(view, "Recipe deleted successfully!", Snackbar.LENGTH_SHORT).show();
            });
        });

        binding.editRecipeFragmentCameraBtn.setOnClickListener((v) -> {
            cameraLauncher.launch(null);
        });

        binding.editRecipeFragmentGalleryBtn.setOnClickListener((v) -> {
            galleryLauncher.launch("image/*");
        });

        return view;
    }

}