package com.example.projectapptest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {
    Button btnLogOut;
    FirebaseAuth fAuth;
    TextView txtName,tvEmail,tvPhone;
    ImageView iconmail,iconPhone,iconProfile;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_profile,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txtName =getActivity().findViewById(R.id.txtName);
        tvEmail =getActivity().findViewById(R.id.tvEmail);
        tvPhone =getActivity().findViewById(R.id.tvPhone);
        progressBar = getActivity().findViewById(R.id.progressBar4);
        iconmail = getActivity().findViewById(R.id.iconMail);
        iconPhone = getActivity().findViewById(R.id.iconPhone);
        iconProfile = getActivity().findViewById(R.id.iconProfile);
        btnLogOut = getActivity().findViewById(R.id.btnLogOut);

        progressBar.setVisibility(View.VISIBLE);
        iconmail.setVisibility(View.INVISIBLE);
        iconPhone.setVisibility(View.INVISIBLE);
        iconProfile.setVisibility(View.INVISIBLE);


        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();
                startActivity(new Intent(getActivity(),LoginActivity.class));
                getActivity().finish();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        fAuth = FirebaseAuth.getInstance();
        final FirebaseUser fUser = fAuth.getCurrentUser();
        String userID = fUser.getUid();
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        final DocumentReference docRef = fStore.collection("Users").document(userID);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult().exists()){
                    String name = task.getResult().getString("Name");
                    String email = task.getResult().getString("email");
                    String phone_no = task.getResult().getString("Phone_No");
                    txtName.setText(name);
                    tvEmail.setText(email);
                    tvPhone.setText(phone_no);
                    progressBar.setVisibility(View.INVISIBLE);
                    iconmail.setVisibility(View.VISIBLE);
                    iconPhone.setVisibility(View.VISIBLE);
                    iconProfile.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
