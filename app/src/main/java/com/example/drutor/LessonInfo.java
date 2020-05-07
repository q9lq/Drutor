package com.example.drutor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LessonInfo extends AppCompatActivity {
    FirebaseUser user;
    Dialog myDilaog;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef3 = database.getReference("Lessons");
    DatabaseReference myRef2 = database.getReference("Students");
    DatabaseReference myRef = database.getReference("Tutors");

    TextView tv,date,time,std1,std2,std3;
    Button delete,back;
    Lesson l;
    Student s;
    String s1,s2,s3;
    Spinner spinner,spinner2;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    List<String> student;
    List<String> student2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_info);

        spinner=findViewById(R.id.spinner);
        student = new ArrayList<>();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String nname=parent.getItemAtPosition(position).toString();
                AlertDialog.Builder adb=new AlertDialog.Builder(LessonInfo.this);
                if(!parent.getItemAtPosition(position).toString().equals("Remove Student:")){
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete "+parent.getItemAtPosition(position).toString()+"from this lesson ?");
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final Query query=myRef3.orderByChild("temail").equalTo(user.getEmail());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot d:dataSnapshot.getChildren()){
                                    Lesson ll =d.getValue(Lesson.class);
                                    if(ll.getDate().equals(date.getText().toString())&&ll.getTime().equals(time.getText().toString())){

                                        if(ll.getS1().equals(nname)&&ll.getS2()==null&&ll.getS3()==null){
                                            Toast.makeText(LessonInfo.this, "This is the only Student" , Toast.LENGTH_SHORT).show();}

                                        if(ll.getS1().equals(nname)&&ll.getS2()!=null&&ll.getS3()==null){
                                            ll.setS1(ll.getS2());
                                            ll.setS2(null);
                                            myRef3.child(d.getKey()).setValue(ll);
                                            Toast.makeText(LessonInfo.this, "Student Removed." , Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                            Query queryy=myRef2.orderByChild("temail").equalTo(user.getEmail());
                                            queryy.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    for(DataSnapshot d:dataSnapshot.getChildren()){
                                                        s=d.getValue(Student.class);
                                                        if(nname.equals(s.getFullname())) {
                                                            String ln=s.getLessonsNum();
                                                            int i=Integer.parseInt(ln);
                                                            String num=String.valueOf(i-1);
                                                            s.setLessonsNum(num);
                                                            myRef2.child(d.getKey()).setValue(s);}

                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        }

                                        if(ll.getS2()!=null){
                                            if(ll.getS2().equals(nname)&&ll.getS1()!=null&&ll.getS3()==null){
                                            ll.setS2(null);
                                            Toast.makeText(LessonInfo.this, "Student Removed." , Toast.LENGTH_SHORT).show();
                                                myRef3.child(d.getKey()).setValue(ll);
                                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                                Query queryy=myRef2.orderByChild("temail").equalTo(user.getEmail());
                                                queryy.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        for(DataSnapshot d:dataSnapshot.getChildren()){
                                                            s=d.getValue(Student.class);
                                                            if(nname.equals(s.getFullname())) {
                                                                String ln=s.getLessonsNum();
                                                                int i=Integer.parseInt(ln);
                                                                String num=String.valueOf(i-1);
                                                                s.setLessonsNum(num);
                                                                myRef2.child(d.getKey()).setValue(s);}

                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                        }}

                                        if(ll.getS1().equals(nname)&&ll.getS2()!=null&&ll.getS3()!=null){
                                            ll.setS1(ll.getS2());
                                            ll.setS2(ll.getS3());
                                            ll.setS3(null);
                                            Toast.makeText(LessonInfo.this, "Student Removed." , Toast.LENGTH_SHORT).show();
                                            myRef3.child(d.getKey()).setValue(ll);
                                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                            Query queryy=myRef2.orderByChild("temail").equalTo(user.getEmail());
                                            queryy.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    for(DataSnapshot d:dataSnapshot.getChildren()){
                                                        s=d.getValue(Student.class);
                                                        if(nname.equals(s.getFullname())) {
                                                            String ln=s.getLessonsNum();
                                                            int i=Integer.parseInt(ln);
                                                            String num=String.valueOf(i-1);
                                                            s.setLessonsNum(num);
                                                            myRef2.child(d.getKey()).setValue(s);}

                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        }

                                        if(ll.getS2()!=null){
                                            if(ll.getS2().equals(nname)&&ll.getS1()!=null&&ll.getS3()!=null){
                                            ll.setS2(ll.getS3());
                                            ll.setS3(null);
                                            Toast.makeText(LessonInfo.this, "Student Removed." , Toast.LENGTH_SHORT).show();
                                                myRef3.child(d.getKey()).setValue(ll);
                                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                                Query queryy=myRef2.orderByChild("temail").equalTo(user.getEmail());
                                                queryy.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        for(DataSnapshot d:dataSnapshot.getChildren()){
                                                            s=d.getValue(Student.class);
                                                            if(nname.equals(s.getFullname())) {
                                                                String ln=s.getLessonsNum();
                                                                int i=Integer.parseInt(ln);
                                                                String num=String.valueOf(i-1);
                                                                s.setLessonsNum(num);
                                                                myRef2.child(d.getKey()).setValue(s);}

                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                        }}

                                        if(ll.getS3()!=null){
                                        if(ll.getS3().equals(nname)&&ll.getS1()!=null&&ll.getS2()!=null){
                                            ll.setS3(null);
                                        Toast.makeText(LessonInfo.this, "Student Removed." , Toast.LENGTH_SHORT).show();
                                            myRef3.child(d.getKey()).setValue(ll);
                                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                            Query queryy=myRef2.orderByChild("temail").equalTo(user.getEmail());
                                            queryy.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    for(DataSnapshot d:dataSnapshot.getChildren()){
                                                        s=d.getValue(Student.class);
                                                        if(nname.equals(s.getFullname())) {
                                                            String ln=s.getLessonsNum();
                                                            int i=Integer.parseInt(ln);
                                                            String num=String.valueOf(i-1);
                                                            s.setLessonsNum(num);
                                                            myRef2.child(d.getKey()).setValue(s);}

                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        }}


                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }});
                adb.show();
            }}

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ////////////////////////////////////////////////////////////////////////////////


        spinner2=findViewById(R.id.spinner2);
        student2 = new ArrayList<>();
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String nname2=parent.getItemAtPosition(position).toString();
                AlertDialog.Builder adb=new AlertDialog.Builder(LessonInfo.this);
                if(!parent.getItemAtPosition(position).toString().equals("Add Student:")) {
                    if (nname2.equals(std1.getText().toString()) || nname2.equals(std2.getText().toString()) || nname2.equals(std3.getText().toString())) {
                        Toast.makeText(LessonInfo.this, "This Student is Already Added.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                    adb.setTitle("Add?");
                    adb.setMessage("Are you sure you want to Add " + parent.getItemAtPosition(position).toString() + " to this lesson ?");
                    adb.setNegativeButton("Cancel", null);
                    adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            final Query query = myRef3.orderByChild("temail").equalTo(user.getEmail());
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                                        Lesson ll = d.getValue(Lesson.class);
                                        if (ll.getDate().equals(date.getText().toString()) && ll.getTime().equals(time.getText().toString())) {

                                            if (ll.getS1() != null && ll.getS2() != null && ll.getS3() != null) {
                                                Toast.makeText(LessonInfo.this, "You can't add more students!", Toast.LENGTH_SHORT).show();
                                            }

                                            if (ll.getS1() != null && ll.getS2() != null && ll.getS3() == null) {
                                                Toast.makeText(LessonInfo.this, "Student Added.", Toast.LENGTH_SHORT).show();
                                                ll.setS3(nname2);
                                                myRef3.child(d.getKey()).setValue(ll);
                                                Query queryy = myRef2.orderByChild("temail").equalTo(user.getEmail());
                                                queryy.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                                                            s = d.getValue(Student.class);
                                                            if (nname2.equals(s.getFullname())) {
                                                                String ln = s.getLessonsNum();
                                                                int i = Integer.parseInt(ln);
                                                                String num = String.valueOf(i + 1);
                                                                s.setLessonsNum(num);
                                                                myRef2.child(d.getKey()).setValue(s);
                                                            }

                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            }

                                            if (ll.getS1() != null && ll.getS2() == null && ll.getS3() == null) {
                                                Toast.makeText(LessonInfo.this, "Student Added.", Toast.LENGTH_SHORT).show();
                                                ll.setS2(nname2);
                                                myRef3.child(d.getKey()).setValue(ll);
                                                Query queryy = myRef2.orderByChild("temail").equalTo(user.getEmail());
                                                queryy.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                                                            s = d.getValue(Student.class);
                                                            if (nname2.equals(s.getFullname())) {
                                                                String ln = s.getLessonsNum();
                                                                int i = Integer.parseInt(ln);
                                                                String num = String.valueOf(i + 1);
                                                                s.setLessonsNum(num);
                                                                myRef2.child(d.getKey()).setValue(s);
                                                            }

                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            }


                                        }

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    });
                    adb.show();

                }
                }}

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        tv=findViewById(R.id.tv);
        date=findViewById(R.id.date);
        time=findViewById(R.id.time);
        std1=findViewById(R.id.Student1);
        std2=findViewById(R.id.Student2);
        std3=findViewById(R.id.Student3);
        delete=findViewById(R.id.delete);

        user = FirebaseAuth.getInstance().getCurrentUser();
        tv.setText(mode.DAATE);
        date.setText(mode.DAATE);

        student2.add("Add Student:");
        Query query=myRef2.orderByChild("temail").equalTo(user.getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    Student student;
                    student=d.getValue(Student.class);
                    student2.add(student.getFullname());
                    adapter2 = new ArrayAdapter(LessonInfo.this , android.R.layout.simple_spinner_item , student2);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter2);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        final Query query4=myRef3.orderByChild("temail").equalTo(user.getEmail());
        query4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    l=d.getValue(Lesson.class);
                    if(l.getDate().equals(mode.DAATE)&&l.getTime().equals(mode.TIIME)){
                        time.setText(l.getTime());
                        std1.setText(l.getS1());
                        std2.setText(l.getS2());
                        std3.setText(l.getS3());
                        student.add("Remove Student:");
                        if(l.getS1()!=null)student.add(l.getS1());
                        if(l.getS2()!=null)student.add(l.getS2());
                        if(l.getS3()!=null)student.add(l.getS3());
                        adapter = new ArrayAdapter(LessonInfo.this , android.R.layout.simple_spinner_item , student);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        std1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentsPage.Name=std1.getText().toString();
                startActivity(new Intent(getApplicationContext(),StudentProfile.class));
            }
        });

        std2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentsPage.Name=std2.getText().toString();
                startActivity(new Intent(getApplicationContext(),StudentProfile.class));
            }
        });

        std3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentsPage.Name=std3.getText().toString();
                startActivity(new Intent(getApplicationContext(),StudentProfile.class));
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb=new AlertDialog.Builder(LessonInfo.this);
                       adb.setTitle("Delete?");
                       adb.setMessage("Are you sure you want to delete this lesson ?");
                       adb.setNegativeButton("Cancel", null);
                       adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                           public void onClick(DialogInterface dialog, int which) {
                               final Query query=myRef3.orderByChild("temail").equalTo(user.getEmail());
                               query.addListenerForSingleValueEvent(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                       for(DataSnapshot d:dataSnapshot.getChildren()){
                                           Lesson ll =d.getValue(Lesson.class);
                                           if(ll.getDate().equals(date.getText().toString())&&ll.getTime().equals(time.getText().toString())){
                                               s1=ll.getS1();
                                               s2=ll.getS2();
                                               s3=ll.getS3();
                                               myRef3.child(d.getKey()).removeValue();
                                               startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                           }

                                       }
                                   }

                                   @Override
                                   public void onCancelled(@NonNull DatabaseError databaseError) {

                                   }
                               });

                               Query queryy=myRef2.orderByChild("temail").equalTo(user.getEmail());
                               queryy.addListenerForSingleValueEvent(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                       for(DataSnapshot d:dataSnapshot.getChildren()){
                                           s=d.getValue(Student.class);
                                           if(s.getFullname().equals(s1)||s.getFullname().equals(s2)||s.getFullname().equals(s3)) {
                                               String ln=s.getLessonsNum();
                                               int i=Integer.parseInt(ln);
                                               String num=String.valueOf(i-1);
                                               s.setLessonsNum(num);
                                               myRef2.child(d.getKey()).setValue(s);}

                                       }
                                   }

                                   @Override
                                   public void onCancelled(@NonNull DatabaseError databaseError) {

                                   }
                               });
                           }});
                       adb.show();


            }

        });

    }


}
