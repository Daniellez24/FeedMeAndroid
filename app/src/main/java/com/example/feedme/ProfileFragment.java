package com.example.feedme;

import android.content.Intent;
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

import com.google.firebase.auth.FirebaseAuth;


public class ProfileFragment extends Fragment {


    private ImageView profileImg;
    private TextView profileTitle;
    private Button logoutBtn, saveBtn;
    private EditText userName;

    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImg = view.findViewById(R.id.profileFragment_user_img);
        profileTitle = view.findViewById(R.id.profileFragment_title_tv);
        userName = view.findViewById(R.id.profileFragment_userName_et);
        logoutBtn = view.findViewById(R.id.profileFragment_logout_btn);
        saveBtn = view.findViewById(R.id.profileFragment_save_btn);

        // TODO: get firbase out to the model!!
        mAuth = FirebaseAuth.getInstance();

        logoutBtn.setOnClickListener((v -> {
            mAuth.signOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }));

        return view;
    }

}