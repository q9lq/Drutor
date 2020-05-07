package com.example.drutor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Profile extends AppCompatActivity {


    EditText fullName,phoneN,ID,password,price;
    FirebaseUser user;
    Button save;
    Tutor tutor;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Tutors");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fullName=findViewById(R.id.et1);
        phoneN=findViewById(R.id.et2);
        ID=findViewById(R.id.et3);
        password=findViewById(R.id.et4);
        save=findViewById(R.id.save);
        price=findViewById(R.id.et5);
        user = FirebaseAuth.getInstance().getCurrentUser();
        Login.EMAIL = user.getEmail();


        Query query=myRef.orderByChild("email").equalTo(Login.EMAIL);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    tutor=d.getValue(Tutor.class);
                    fullName.setText(tutor.getFullname());
                    phoneN.setText(tutor.getPhonenumber());
                    ID.setText(tutor.getID());
                    password.setText(tutor.getPassword());
                    price.setText(tutor.getPrice());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));

                tutor.setFullname(fullName.getText().toString());
                tutor.setPhonenumber(phoneN.getText().toString());
                tutor.setID(ID.getText().toString());
                tutor.setPrice(price.getText().toString());

                Query query=myRef.orderByChild("email").equalTo(Login.EMAIL);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot d:dataSnapshot.getChildren()){
                            myRef.child(d.getKey()).setValue(tutor);
                        }
                        Toast.makeText(Profile.this, "Info Updated.",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                }
        });

    }//onCreate




}
