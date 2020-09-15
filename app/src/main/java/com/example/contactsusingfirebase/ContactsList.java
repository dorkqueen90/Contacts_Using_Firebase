package com.example.contactsusingfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ContactsList extends AppCompatActivity {
    Button newContact, logout;
    ListView listView;
    ArrayList<Contact> contacts = new ArrayList<>();
    ContactAdapter adapter;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);
        setTitle("Contacts");
        newContact = findViewById(R.id.newContactButton);
        logout = findViewById(R.id.logoutButton);
        listView = findViewById(R.id.listView);
        adapter = new ContactAdapter(ContactsList.this, R.layout.activity_contacts_layout, contacts);
        listView.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("users");
        FirebaseUser user = mAuth.getCurrentUser();
        myRef.child(user.getUid()).child("contacts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                contacts.clear();
                for(DataSnapshot child : snapshot.getChildren()){
                    Contact contact = child.getValue(Contact.class);
                    contacts.add(contact);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        newContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactsList.this, NewContact.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(ContactsList.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    class ContactAdapter extends ArrayAdapter<Contact>{

        public ContactAdapter(@NonNull Context context, int resource, @NonNull List<Contact> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.activity_contacts_layout, parent, false);
            }
            final Contact contact = getItem(position);

            ((TextView)convertView.findViewById(R.id.displayName)).setText(contact.name);
            ((TextView)convertView.findViewById(R.id.displayEmail)).setText(contact.email);
            ((TextView)convertView.findViewById(R.id.displayPhone)).setText(contact.phone);
            ((TextView)convertView.findViewById(R.id.displayDep)).setText(contact.dept);
            ((ImageView)convertView.findViewById(R.id.displayImage)).setImageResource(contact.imgId);

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    remove(contact);
                    //myRef.child(user.getUid()).removeValue();
                    //I couldn't figure out how to remove just one contact
                    adapter.notifyDataSetChanged();
                    return true;
                }
            });

            return convertView;
        }
    }
}