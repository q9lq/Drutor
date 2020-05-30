package com.example.drutor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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



public class StudentProfile extends AppCompatActivity {
    FirebaseUser user;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef2 = database.getReference("Students");
    DatabaseReference myRef = database.getReference("Tutors");

    public static String Namee;
    TextView name;
    EditText phone,id,LessonsNumber,PayedLessonsNumber;
    TextView RemainingPayment;
    Student s;
    Button delete,save,rdates;
    Tutor t;
    int g,x,y;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        rdates=findViewById(R.id.rdates);
        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        LessonsNumber=findViewById(R.id.LessonsNum);
        PayedLessonsNumber=findViewById(R.id.PayedLessonsNum);
        RemainingPayment=findViewById(R.id.RemainingPayment);
        id=findViewById(R.id.id);
        save=findViewById(R.id.save);
        delete=findViewById(R.id.delete);
        user = FirebaseAuth.getInstance().getCurrentUser();
        name.setText(StudentsPage.Name);


        rdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Namee=name.getText().toString();
                startActivity(new Intent(getApplicationContext(),DatesPage.class));
            }
        });


        final Query query=myRef2.orderByChild("temail").equalTo(user.getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    s=d.getValue(Student.class);
                    if(s.getFullname().equals(StudentsPage.Name)){
                        phone.setText(s.getPhonenumer());
                        id.setText(s.getID());
                        LessonsNumber.setText(s.getLessonsNum());
                        x=Integer.parseInt(s.getLessonsNum());
                        PayedLessonsNumber.setText(s.getPayedNum());
                        y=Integer.parseInt(s.getPayedNum());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query=myRef2.orderByChild("temail").equalTo(user.getEmail());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot d:dataSnapshot.getChildren()){
                            s=d.getValue(Student.class);
                            if(name.getText().toString().equals(s.getFullname())){
                            s.setFullname(name.getText().toString());
                            s.setID(id.getText().toString());
                            s.setTEmail(user.getEmail());
                            s.setPhonenumer(phone.getText().toString());
                            s.setLessonsNum(LessonsNumber.getText().toString());
                            s.setPayedNum(PayedLessonsNumber.getText().toString());
                            myRef2.child(d.getKey()).setValue(s);}
                        }
                        Toast.makeText(StudentProfile.this, "Info Updated.",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        });
        /////////////////////////////////////////////////////////////////////
        Query query1=myRef.orderByChild("email").equalTo(user.getEmail().toString());
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    t=d.getValue(Tutor.class);
                    g=Integer.parseInt(t.getPrice());
                    String num=String.valueOf((x-y)*g);
                    RemainingPayment.setText("Remaining Paiment: "+num);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /////////////////////////////////////////////////////////////////////
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb=new AlertDialog.Builder(StudentProfile.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete "+name.getText().toString()+"?");
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Query queryy=myRef2.orderByChild("temail").equalTo(user.getEmail());
                        queryy.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot d:dataSnapshot.getChildren()){
                                    s=d.getValue(Student.class);
                                    if(s.getFullname().equals(name.getText().toString())) {
                                        myRef2.child(d.getKey()).removeValue();
                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    }
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
