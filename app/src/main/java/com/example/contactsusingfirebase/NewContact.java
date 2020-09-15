package com.example.contactsusingfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewContact extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    final int IMG_KEY = 1;
    Button submit;
    EditText name, email, phone;
    RadioGroup selected;
    ImageView image;
    int imgId = R.drawable.select_avatar;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMG_KEY){
            if(resultCode == Activity.RESULT_OK){
                imgId = data.getIntExtra("result", IMG_KEY);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        image.setImageResource(imgId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        submit = findViewById(R.id.submitButton);
        image = findViewById(R.id.avatarImage);
        name = findViewById(R.id.editName);
        email = findViewById(R.id.editEmail);
        phone = findViewById(R.id.editPhone);
        selected = findViewById(R.id.radioGroup);
        image.setImageResource(imgId);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("users");

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewContact.this, SelectAvatar.class);
                startActivityForResult(intent, IMG_KEY);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dept = "";
                if(isNameFilled() && isEmailValid() && isPhoneFilled()){
                    if(selected.getCheckedRadioButtonId() == R.id.sisButton){
                        dept = "SIS";
                    } else if(selected.getCheckedRadioButtonId() == R.id.csButton){
                        dept = "CS";
                    } else if(selected.getCheckedRadioButtonId() == R.id.bioButton){
                        dept = "BIO";
                    }
                    String stringname = name.getText().toString();
                    String stringemail = email.getText().toString();
                    String stringphone = phone.getText().toString();
                    Contact contact = new Contact(stringname, stringemail, stringphone, dept, imgId);
                    FirebaseUser user = mAuth.getCurrentUser();
                    //String key = myRef.child(user.getUid()).push().getKey();

                    //I couldn't figure out how to add a new child under the active contact without
                    //overwriting the current contact
                    myRef.child(user.getUid()).child("contacts").child("contact").setValue(contact).addOnCompleteListener(NewContact.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Log.d("demo", "Success!");
                                finish();
                            } else{
                                Log.d("demo", "Not so successful...");
                                task.getException().printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }
    private boolean isNameFilled() {
        return name.getText().toString().length() > 0;
    }
    private boolean isEmailValid() {
        return email.getText().toString().length() > 0 && email.getText().toString().contains("@") &&
                email.getText().toString().contains(".");
    }
    private boolean isPhoneFilled() {
        return phone.getText().toString().length() > 0;
    }
}