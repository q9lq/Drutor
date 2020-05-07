package com.example.drutor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DatesPage extends AppCompatActivity {
    TextView dates;
    ListView lv;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> listt;
    Dialog myDilaog;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef3 = database.getReference("Lessons");
    DatabaseReference myRef2 = database.getReference("Students");
    DatabaseReference myRef = database.getReference("Tutors");
    FirebaseUser user;
    Lesson l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dates_page);
        dates=findViewById(R.id.Dates);
        user = FirebaseAuth.getInstance().getCurrentUser();
        lv=findViewById(R.id.lv3);
        listt=new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,listt);

        dates.setText("Dates for : "+StudentProfile.Namee);

        Query query=myRef3.orderByChild("temail").equalTo(user.getEmail().toString());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    l=d.getValue(Lesson.class);
                 if(l.getS1()!=null&&l.getS2()!=null&&l.getS3()!=null)   {
                    if(l.getS1().equals(StudentProfile.Namee)||l.getS2().equals(StudentProfile.Namee)||l.getS3().equals(StudentProfile.Namee)){
                    listt.add(l.getDate());
                    lv.setAdapter(arrayAdapter);
                    arrayAdapter.notifyDataSetChanged(); }}
                 if(l.getS1()!=null&&l.getS2()!=null&&l.getS3()==null)   {
                        if(l.getS1().equals(StudentProfile.Namee)||l.getS2().equals(StudentProfile.Namee)){
                            listt.add(l.getDate());
                            lv.setAdapter(arrayAdapter);
                            arrayAdapter.notifyDataSetChanged(); }}
                 if(l.getS1()!=null&&l.getS2()==null&&l.getS3()==null)   {
                        if(l.getS1().equals(StudentProfile.Namee)){
                            listt.add(l.getDate());
                            lv.setAdapter(arrayAdapter);
                            arrayAdapter.notifyDataSetChanged(); }}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
