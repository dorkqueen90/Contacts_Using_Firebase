package com.example.contactsusingfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button login, signup;
    EditText email, password;
    String emailstring, passwordstring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setTitle("Login");

        email = findViewById(R.id.emailLogin);
        password = findViewById(R.id.passwordLogin);
        login = findViewById(R.id.loginButton);
        signup = findViewById(R.id.signupButton);
        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailstring = email.getText().toString();
                passwordstring = password.getText().toString();
                if(isEmailValid() && isPasswordFilled()) {
                    mAuth.signInWithEmailAndPassword(emailstring, passwordstring)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(Login.this, ContactsList.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(Login.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(Login.this, "Please enter email and password.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private boolean isEmailValid() {
        return email.getText().toString().length() > 0 && email.getText().toString().contains("@") &&
                email.getText().toString().contains(".");
    }
    private boolean isPasswordFilled() {
        return password.getText().toString().length() > 0;
    }
}