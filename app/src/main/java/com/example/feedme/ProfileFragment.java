package com.example.feedme;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ProfileFragment extends Fragment {


    private ImageView profileImg;
    private TextView profileTitle;
    private Button logoutBtn, saveBtn;
    private EditText userName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        profileImg = container.findViewById(R.id.profileFragment_user_img);
        profileTitle = container.findViewById(R.id.profileFragment_title_tv);
        userName = container.findViewById(R.id.profileFragment_userName_et);
        logoutBtn = container.findViewById(R.id.profileFragment_logout_btn);
        saveBtn = container.findViewById(R.id.profileFragment_save_btn);

        return inflater.inflate(R.layout.fragment_profile, container, false);

    }
}