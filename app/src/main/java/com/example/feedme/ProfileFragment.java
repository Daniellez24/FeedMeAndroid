package com.example.feedme;

import android.content.Intent;
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

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.feedme.models.Model;
import com.example.feedme.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {

    private ImageView profileImg;
    private TextView profileTitle;
    private Button logoutBtn, saveBtn;
    private EditText userName;
    private ImageButton galleryButton, cameraButton;

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
                    profileImg.setImageBitmap(result);
                    isImageSelected = true;
                }
            }
        });

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if(result != null){
                    profileImg.setImageURI(result);
                    isImageSelected = true;
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImg = view.findViewById(R.id.profileFragment_user_img);
        profileTitle = view.findViewById(R.id.profileFragment_title_tv);
        userName = view.findViewById(R.id.profileFragment_userName_et);
        logoutBtn = view.findViewById(R.id.profileFragment_logout_btn);
        saveBtn = view.findViewById(R.id.profileFragment_save_btn);
        cameraButton = view.findViewById(R.id.profileFragment_camera_Ib);
        galleryButton = view.findViewById(R.id.profileFragment_gallery_Ib);

        // show the current user data when entering the profile
        Model.instance().getUserProfileData((user) -> {
            userName.setText(user.getName());
            if(user.getImage() != ""){
                Picasso.get().load(user.getImage()).placeholder(R.drawable.profile).into(profileImg);
                isImageSelected = true;
            }
        });

        logoutBtn.setOnClickListener((v -> {
            Model.instance().signoutUser((x) -> startActivity(new Intent(getActivity(), LoginActivity.class)));
        }));

        saveBtn.setOnClickListener((v -> {
            userName.setText(userName.getText().toString());
            String name = userName.getText().toString();
            String id = Model.instance().getCurrentUserId();
            User user = new User(id, name, "", new ArrayList());

            if(isImageSelected){
                profileImg.setDrawingCacheEnabled(true);
                profileImg.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable)profileImg.getDrawable()).getBitmap();

                Model.instance().uploadImage(Model.instance().getCurrentUserId(), bitmap, url -> {
                    if(url != null){
                        user.setImage(url);
                    }
                    Model.instance().editUser(user.name, user.image, (unused) ->{
                        Navigation.findNavController(v).popBackStack();
                    });
                });
            } else { // save user edited data without image
                Model.instance().editUser(userName.getText().toString(), user.image, (unused) ->{
                    Navigation.findNavController(v).popBackStack();
                });
            }


        }));

        cameraButton.setOnClickListener((v) -> {
            cameraLauncher.launch(null);
        });

        galleryButton.setOnClickListener((v) -> {
            galleryLauncher.launch("image/*");
        });

        return view;
    }

}