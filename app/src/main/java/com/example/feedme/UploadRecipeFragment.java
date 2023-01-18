package com.example.feedme;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class UploadRecipeFragment extends Fragment {
    private Button uploadButton,selectImageButton;
    private ImageView recipeImage;
    private EditText recipeTitle, recipeBody;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_upload_recipe, container, false);
        
        uploadButton = view.findViewById(R.id.uploadFragment_upload_btn);
        selectImageButton = view.findViewById(R.id.uploadFragment_img_select_btn);
        recipeImage =  view.findViewById(R.id.uploadFragment_recepie_img);
        recipeTitle =  view.findViewById(R.id.uploadFragment_title_et);
        recipeBody =  view.findViewById(R.id.uploadFragment_recepie_et);

        return view;
    }
}