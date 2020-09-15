package com.example.contactsusingfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SelectAvatar extends AppCompatActivity {

    int imgId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_avatar);

        findViewById(R.id.redFemale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgId = R.drawable.avatar_f_2;
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", imgId);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
        findViewById(R.id.brownFemale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgId = R.drawable.avatar_f_1;
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", imgId);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
        findViewById(R.id.darkFemale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgId = R.drawable.avatar_f_3;
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", imgId);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
        findViewById(R.id.blondeMale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgId = R.drawable.avatar_m_3;
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", imgId);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
        findViewById(R.id.brownMale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgId = R.drawable.avatar_m_2;
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", imgId);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
        findViewById(R.id.darkMale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgId = R.drawable.avatar_m_1;
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", imgId);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}