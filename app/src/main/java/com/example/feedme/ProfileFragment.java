package com.example.feedme;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.feedme.models.Model;


public class ProfileFragment extends Fragment {


    private ImageView profileImg;
    private TextView profileTitle;
    private Button logoutBtn, saveBtn;
    private EditText userName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImg = view.findViewById(R.id.profileFragment_user_img);
        profileTitle = view.findViewById(R.id.profileFragment_title_tv);
        userName = view.findViewById(R.id.profileFragment_userName_et);
        logoutBtn = view.findViewById(R.id.profileFragment_logout_btn);
        saveBtn = view.findViewById(R.id.profileFragment_save_btn);

        logoutBtn.setOnClickListener((v -> {
            Model.instance().signoutUser((x) -> startActivity(new Intent(getActivity(), LoginActivity.class)));
        }));

        saveBtn.setOnClickListener((v -> {
            Model.instance().editUser(userName.getText().toString(), profileImg.getTransitionName(), (unused) ->{
                Navigation.findNavController(v).popBackStack();
            });
        }));

        return view;
    }

}