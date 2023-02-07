package com.example.feedme;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.SystemClock;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.feedme.models.Model;
import com.example.feedme.models.Recipe;
import com.google.type.DateTime;

import java.sql.Time;
import java.time.Clock;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


public class UploadRecipeFragment extends Fragment {
    private Button uploadButton;
    private ImageButton galleryButton, cameraButton;
    private ImageView recipeImage;
    private EditText recipeTitle, recipeBody;

    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;
    Boolean isImageSelected = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if(result != null){
                    recipeImage.setImageBitmap(result);
                    isImageSelected = true;
                }
            }
        });

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if(result != null){
                    recipeImage.setImageURI(result);
                    isImageSelected = true;
                }
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_upload_recipe, container, false);
        
        uploadButton = view.findViewById(R.id.uploadFragment_upload_btn);
        galleryButton = view.findViewById(R.id.uploadFragment_gallery_btn);
        cameraButton = view.findViewById(R.id.uploadFragment_camera_btn);
        recipeImage =  view.findViewById(R.id.uploadFragment_recepie_img);
        recipeTitle =  view.findViewById(R.id.uploadFragment_title_et);
        recipeBody =  view.findViewById(R.id.uploadFragment_recepie_et);

        uploadButton.setOnClickListener((v) -> {
            String title = recipeTitle.getText().toString();
            String body = recipeBody.getText().toString();
            String userId = Model.instance().getCurrentUserId();
            Recipe recipe = new Recipe(userId, "", title, body);

            if(isImageSelected){
                recipeImage.setDrawingCacheEnabled(true);
                recipeImage.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable)recipeImage.getDrawable()).getBitmap();
                // used the UUID to generate unique key to identify the image path in the storage
                Model.instance().uploadImage(UUID.randomUUID().toString(), bitmap, url ->{
                    if(url != null){
                        recipe.setRecipeImage(url);
                    }
                    Model.instance().addRecipe(recipe, (unused) -> {
                        Model.instance().refreshRecipes();
                        Model.instance().refreshMyRecipesList();
                        Navigation.findNavController(v).popBackStack();
                    });
                });
            } else{ // save recipe without image (image not selected)
                Model.instance().addRecipe(recipe, (unused) -> {
                    Navigation.findNavController(v).popBackStack();
                });
            }

        });

        cameraButton.setOnClickListener((v) -> {
            cameraLauncher.launch(null);
        });

        galleryButton.setOnClickListener((v) -> {
            galleryLauncher.launch("image/*");
        });

        return view;
    }
}