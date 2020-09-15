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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.HashSet;

public class SignUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText firstName, lastName, email, password, password2;
    Button signup, cancel;
    String emailstring, passwordstring;
    final String TAG = "demo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up");
        firstName = findViewById(R.id.firstNameSignUp);
        lastName = findViewById(R.id.lastNameSignUp);
        email = findViewById(R.id.emailSignUp);
        password = findViewById(R.id.passwordSignUp);
        password2 = findViewById(R.id.confirmPasswordSignUp);
        signup = findViewById(R.id.signUpButtonSignUpPage);
        cancel = findViewById(R.id.cancelButton);

        mAuth = FirebaseAuth.getInstance();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("users");

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEverythingFilled() && doPasswordsMatch()){
                    emailstring = email.getText().toString();
                    passwordstring = password.getText().toString();
                    final String fname = firstName.getText().toString();
                    final String lname = lastName.getText().toString();
                    mAuth.createUserWithEmailAndPassword(emailstring, passwordstring)
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        HashMap<String, Object> data = new HashMap<>();
                                        data.put("firstname", fname);
                                        data.put("lastname", lname);
                                        data.put("email", emailstring);
                                        myRef.child(user.getUid()).setValue(data)
                                                .addOnCompleteListener(SignUp.this, new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(SignUp.this, "User has been created.",
                                                        Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(SignUp.this, ContactsList.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });

                                    } else {
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(SignUp.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else{
                    Toast.makeText(SignUp.this, "Please fill blanks and make sure password matches",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private boolean isFirstNameFilled() {
        return firstName.getText().toString().length() > 0;
    }
    private boolean isLastNameFilled() {
        return lastName.getText().toString().length() > 0;
    }
    private boolean isEmailValid() {
        return email.getText().toString().length() > 0 && email.getText().toString().contains("@") &&
                email.getText().toString().contains(".");
    }
    private boolean isPasswordFilled() {
        return password.getText().toString().length() > 0;
    }
    private boolean isPassword2Filled() {
        return password2.getText().toString().length() > 0;
    }
    private boolean doPasswordsMatch() {
        return password.getText().toString().equals(password2.getText().toString());
    }
    private boolean isEverythingFilled() {
        return isFirstNameFilled() && isLastNameFilled() && isEmailValid() && isPasswordFilled() && isPassword2Filled();
    }
}