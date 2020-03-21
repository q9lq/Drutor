package com.example.drutor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.List;

public class StudentsPage extends AppCompatActivity {
    public static String Name;
    FirebaseUser user;
    Button add;
    ListView lv;
    ArrayAdapter<String> arrayAdapter;
    ArrayList <String> list;
    Dialog myDilaog;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef2 = database.getReference("Students");
    DatabaseReference myRef = database.getReference("Tutors");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_page);

        user = FirebaseAuth.getInstance().getCurrentUser();


        add=findViewById(R.id.add);
        lv=findViewById(R.id.lv);
        list=new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,list);

        Query query=myRef2.orderByChild("temail").equalTo(user.getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    Student student;
                    student=d.getValue(Student.class);
                    list.add(student.getFullname());
                    lv.setAdapter(arrayAdapter);
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        myDilaog=new Dialog(this);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtclose;
                final EditText fullname,phonenum,id;
                Button addstud;

                myDilaog.setContentView(R.layout.custompopup);
                fullname=(EditText) myDilaog.findViewById(R.id.fullname);
                phonenum=(EditText) myDilaog.findViewById(R.id.phonenum);
                id=(EditText) myDilaog.findViewById(R.id.id);
                txtclose=(TextView) myDilaog.findViewById(R.id.txtclose);
                addstud=(Button) myDilaog.findViewById(R.id.addstud);

                addstud.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Student s = new Student(fullname.getText().toString(),phonenum.getText().toString(),id.getText().toString(),user.getEmail().toString());
                        myRef2.push().setValue(s);
                        myDilaog.dismiss();
                        String name=fullname.getText().toString();
                        list.add(s.getFullname());
                        lv.setAdapter(arrayAdapter);
                        arrayAdapter.notifyDataSetChanged();
                    }
                });

                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDilaog.dismiss();
                    }
                });
                myDilaog.show();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Name=list.get(position).toString();
                startActivity(new Intent(getApplicationContext(),StudentProfile.class));
            }
        });


    }
}
