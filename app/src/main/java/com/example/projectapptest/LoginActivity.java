package com.example.projectapptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText edEmail,edPassword;
    Button btnLogin;
    TextView txtForgotLink,txtCreateNew,txthead1,txtHead2;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtCreateNew = findViewById(R.id.txtCreateNew);
        txthead1 = findViewById(R.id.txtHead1);
        txtHead2 = findViewById(R.id.txtHead2);
        txtForgotLink=findViewById(R.id.txtForgotLink);
        progressBar= findViewById(R.id.progressBar);
        mAuth= FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

    }
    public void LogIn(final View view){
            String email = edEmail.getText().toString().trim();
            String password = edPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                edEmail.setError("Email Required!");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                edPassword.setError("Password Required!");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "You have entered an invalid email or password", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            });
    }
    public void CreateAccount(View view){
        startActivity(new Intent(LoginActivity.this,CreateAccountActivity.class));
    }

    public void forgotPass(View view){
        startActivity(new Intent(LoginActivity.this,forgot_Password.class));
    }
}