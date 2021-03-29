package com.example.projectapptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.proto.TargetGlobal;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity {
    EditText edName,edCrEmail,edCrPassword,edRePassword,edPhone;
    Button btnSignUp;
    TextView txtLogin,txthead1,txtHead2,txtAccount;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        edName = findViewById(R.id.edName);
        edCrEmail = findViewById(R.id.edCrEmail);
        edCrPassword = findViewById(R.id.edCrPassword);
        edRePassword = findViewById(R.id.edRePassword);
        progressBar = findViewById(R.id.progressBar2);
        txthead1 = findViewById(R.id.txtHead1);
        txtHead2 = findViewById(R.id.txtHead2);
        txtLogin=findViewById(R.id.txtLogIn);
        edPhone= findViewById(R.id.edPhoneNo);
        txtAccount=findViewById(R.id.txtAccount);

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
    }
    public void CreateAccount(View view){
        final String name= edName.getText().toString();
        final String email = edCrEmail.getText().toString().trim();
        String password = edCrPassword.getText().toString().trim();
        String password2= edRePassword.getText().toString().trim();
        final String phoneNo = edPhone.getText().toString();

        if (TextUtils.isEmpty(email)) {
            edCrEmail.setError("Email Required!");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            edCrPassword.setError("Password Required!");
            return;
        }
        if (password.length() < 6) {
            edCrPassword.setError("Password should be 6 character or longer");
        }
        if (!password2.equals(password)){
            Toast.makeText(CreateAccountActivity.this,"Password didn't match",Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CreateAccountActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                    final String userID= mAuth.getCurrentUser().getUid();
                    DocumentReference  docRef= fstore.collection("Users").document(userID);
                    Map<String,Object> Users = new HashMap<>();
                    Users.put("Name",name);
                    Users.put("email",email);
                    Users.put("Phone_No",phoneNo);
                    docRef.set(Users).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG","OnSuccess : User Profile is created for " + userID);
                        }
                    });

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                else {
                    Toast.makeText(CreateAccountActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void LogIn(View view){
        startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
        finish();
    }
}