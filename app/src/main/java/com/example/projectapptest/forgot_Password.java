package com.example.projectapptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgot_Password extends AppCompatActivity {

    TextView textView1,textView2;
    EditText edForgotemail;
    Button btnGo;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);
        edForgotemail = findViewById(R.id.edEmail1);
        btnGo = findViewById(R.id.btnGoLink);
        textView1 =findViewById(R.id.txtview10);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar3);


    }
    public void btnGO(View view){
        String email = edForgotemail.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        fAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task){
                startActivity(new Intent(forgot_Password.this,LoginActivity.class));
                Toast.makeText(getApplicationContext(),"Reset Link is sent to email",Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(forgot_Password.this,"Account not found",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}