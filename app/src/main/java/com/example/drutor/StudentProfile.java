package com.example.drutor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentProfile extends AppCompatActivity {
    FirebaseUser user;
    Dialog myDilaog;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef2 = database.getReference("Students");
    DatabaseReference myRef = database.getReference("Tutors");

    TextView name;
    EditText phone,id;
    Student s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);


        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        id=findViewById(R.id.id);

        user = FirebaseAuth.getInstance().getCurrentUser();
        name.setText(StudentsPage.Name);

        final Query query=myRef2.orderByChild("temail").equalTo(user.getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    s=d.getValue(Student.class);
                    if(s.getFullname().equals(StudentsPage.Name)){
                        phone.setText(s.getPhonenumer());
                        id.setText(s.getID());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
