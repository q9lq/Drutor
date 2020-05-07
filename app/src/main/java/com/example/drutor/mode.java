package com.example.drutor;


import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;

import java.util.Calendar;
import java.util.Stack;

public class mode<currentDate> extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef3 = database.getReference("Lessons");
    DatabaseReference myRef2 = database.getReference("Students");
    DatabaseReference myRef = database.getReference("Tutors");

    final int SEND_SMS_PERMISSIONS_REQUEST_CODE = 0 ;
    public static String DAATE;
    public static String TIIME;
    public static TextView tvdate;
    public static TextView tvtime;
    private TextView textViewTime;
    private TimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;
    FirebaseUser user;
    Dialog myDilaog;
    String date;
    public static ListView lv1;
    Student s;
    Lesson lesson;
    String h1,h2,h3;
    String ph1,ph2,ph3,msg;
    Stack<Lesson> ls=new Stack<Lesson>();

    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this,permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);

        if(!checkPermission(Manifest.permission.SEND_SMS)){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSIONS_REQUEST_CODE);
        }

        lv1=findViewById(R.id.lv1);
        final ArrayList <Lesson> list1;
        list1=new ArrayList<Lesson>();
        final LessonListAdapter arrayAdapter1 = new LessonListAdapter(this, R.layout.adapter_view_layout ,list1);

        user = FirebaseAuth.getInstance().getCurrentUser();
        Button add;
        add=findViewById(R.id.add);
        myDilaog=new Dialog(this);

        Query query1=myRef3.orderByChild("temail").equalTo(user.getEmail());
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    Lesson lsn,l;
                    lsn=d.getValue(Lesson.class);
                    ls.push(l = new Lesson(lsn.getDate(),lsn.getTime()));

                }
                while(!ls.isEmpty()){
                    list1.add(ls.pop());
                    lv1.setAdapter(arrayAdapter1);
                    arrayAdapter1.notifyDataSetChanged();}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            int c=0;Stack <String> st = new Stack<String>();
            Stack <String> stemp = new Stack<String>();

            Calendar calendar = Calendar.getInstance();
            ////////////////////////////////////////////////////////

            ////////////////////////////////////////////////////////
            private void PickDate() {
                final java.util.Calendar calendar = java.util.Calendar.getInstance();
                datePickerDialog = new DatePickerDialog( mode.this, new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int dayOfMonth) {
                        date = dayOfMonth+"/"+(month+1)+"/"+year;
                        tvdate.setText(date);
                    }
                },calendar.get( java.util.Calendar.YEAR),calendar.get( java.util.Calendar.MONTH ),calendar.get( java.util.Calendar.DAY_OF_MONTH ) );
                datePickerDialog.show();

            }
            ///////////////////////////////////////////////////////
            private void PickTime(){
                timePickerDialog = new TimePickerDialog(mode.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = hourOfDay + ":"+minute;
                        tvtime.setText(time);
                    }
                },calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true );

                timePickerDialog.show();
            }
            @Override
            public void onClick(View v) {



                TextView txtclose;
                final ListView lv;
                Button addstud;


                myDilaog.setContentView(R.layout.customaddlesson);

                lv=myDilaog.findViewById(R.id.listViewCustom);
                final ArrayAdapter<String> arrayAdapter;
                final ArrayList <String> list;
                list=new ArrayList<String>();
                arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,list);

                 tvdate=(TextView) myDilaog.findViewById(R.id.tvdate);
                 tvtime=(TextView) myDilaog.findViewById(R.id.tvtime);

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


                c=0;
                while(!stemp.isEmpty()){
                    stemp.pop();
                }

                while(!st.isEmpty()){
                    st.pop();
                }

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if(view.isEnabled()){
                            if(c<3){
                            view.setEnabled(false);
                            st.push(list.get(position).toString());
                            c=c+1;
                            Toast.makeText(mode.this, c+" Added",Toast.LENGTH_SHORT).show();}

                            else{
                                Toast.makeText(mode.this, "Can't add more!",Toast.LENGTH_SHORT).show();

                            }
                        }
                        else {
                            view.setEnabled(true);
                            c=c-1;
                            while(!st.peek().equals(list.get(position).toString())){
                                stemp.push(st.pop());
                            }
                            st.pop();
                            while(!stemp.isEmpty()){
                                st.push(stemp.pop());
                            }
                        }
                    }
                });


                ////////////////////
                Button addless=(Button) myDilaog.findViewById(R.id.addless);
                    addless.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                         if(c>0 && tvdate.getText().toString().isEmpty()==false && tvtime.getText().toString().isEmpty()==false){
                             if(c==1){
                                 h1=st.pop(); h2=null; h3=null;
                                  lesson = new Lesson(tvtime.getText().toString(),tvdate.getText().toString(),user.getEmail().toString(),h1,h2,h3);
                             }
                             if(c==2){
                                 h1=st.pop(); h2=st.pop(); h3=null;
                                  lesson = new Lesson(tvtime.getText().toString(),tvdate.getText().toString(),user.getEmail().toString(),h1,h2,h3);
                             }
                             if(c==3){
                                 h1=st.pop(); h2=st.pop(); h3=st.pop();
                                  lesson = new Lesson(tvtime.getText().toString(),tvdate.getText().toString(),user.getEmail().toString(),h1,h2,h3);
                             }
                             myRef3.push().setValue(lesson);
                             msg=", you have a lesson in: "+lesson.getDate()+" at: "+lesson.getTime();

                             Lesson oh;
                            list1.add(oh = new Lesson(tvdate.getText().toString(),tvtime.getText().toString()));
                            lv1.setAdapter(arrayAdapter1);
                            arrayAdapter1.notifyDataSetChanged();

                            c=0;
                            myDilaog.dismiss();
                             Query query=myRef2.orderByChild("temail").equalTo(user.getEmail());
                             query.addListenerForSingleValueEvent(new ValueEventListener() {
                                 @Override
                                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                     for(DataSnapshot d:dataSnapshot.getChildren()){
                                         s=d.getValue(Student.class);
                                         if(h1!=null){
                                         if(s.getFullname().equals(h1)) {
                                             ph1=s.getPhonenumer();
                                             if(checkPermission(Manifest.permission.SEND_SMS)){
                                                 SmsManager smsManager = SmsManager.getDefault();
                                                 smsManager.sendTextMessage(ph1,null,"Hello "+s.getFullname()+msg,null,null);
                                             }
                                             else{
                                                 Toast.makeText(mode.this, "denied", Toast.LENGTH_SHORT).show();
                                             }
                                             String ln=s.getLessonsNum();
                                             int i=Integer.parseInt(ln);
                                             String num=String.valueOf(i+1);
                                             s.setLessonsNum(num);
                                             myRef2.child(d.getKey()).setValue(s); }}

                                     }
                                     Toast.makeText(mode.this, "Info Updated.",Toast.LENGTH_SHORT).show();
                                 }

                                 @Override
                                 public void onCancelled(@NonNull DatabaseError databaseError) {

                                 }
                             });
                             //////
                             query.addListenerForSingleValueEvent(new ValueEventListener() {
                                 @Override
                                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                     for(DataSnapshot d:dataSnapshot.getChildren()){
                                         s=d.getValue(Student.class);
                                         if(h2!=null){
                                         if(s.getFullname().equals(h2)) {
                                             ph1=s.getPhonenumer();
                                             if(checkPermission(Manifest.permission.SEND_SMS)){
                                                 SmsManager smsManager = SmsManager.getDefault();
                                                 smsManager.sendTextMessage(ph1,null,"Hello "+s.getFullname()+msg,null,null);
                                             }
                                             else{
                                                 Toast.makeText(mode.this, "denied", Toast.LENGTH_SHORT).show();
                                             }
                                             String ln=s.getLessonsNum();
                                             int i=Integer.parseInt(ln);
                                             String num=String.valueOf(i+1);
                                             s.setLessonsNum(num);
                                             myRef2.child(d.getKey()).setValue(s);}}

                                     }
                                     Toast.makeText(mode.this, "Info Updated.",Toast.LENGTH_SHORT).show();
                                 }

                                 @Override
                                 public void onCancelled(@NonNull DatabaseError databaseError) {

                                 }
                             });
                             ////
                             query.addListenerForSingleValueEvent(new ValueEventListener() {
                                 @Override
                                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                     for(DataSnapshot d:dataSnapshot.getChildren()){
                                         s=d.getValue(Student.class);
                                        if(h3!=null) {
                                         if(s.getFullname().equals(h3)) {
                                             ph1=s.getPhonenumer();
                                             if(checkPermission(Manifest.permission.SEND_SMS)){
                                                 SmsManager smsManager = SmsManager.getDefault();
                                                 smsManager.sendTextMessage(ph1,null,"Hello "+s.getFullname()+msg,null,null);
                                             }
                                             else{
                                                 Toast.makeText(mode.this, "denied", Toast.LENGTH_SHORT).show();
                                             }
                                             String ln=s.getLessonsNum();
                                             int i=Integer.parseInt(ln);
                                             String num=String.valueOf(i+1);
                                             s.setLessonsNum(num);
                                             myRef2.child(d.getKey()).setValue(s);}}

                                     }
                                     Toast.makeText(mode.this, "Info Updated.",Toast.LENGTH_SHORT).show();
                                 }

                                 @Override
                                 public void onCancelled(@NonNull DatabaseError databaseError) {

                                 }
                             });
                         }
                         else{
                             Toast.makeText(mode.this, "Info missing!",Toast.LENGTH_SHORT).show();
                         }
                        }
                    });


                txtclose=(TextView) myDilaog.findViewById(R.id.txtclose);
                final Button timepicker=(Button) myDilaog.findViewById(R.id.btn_timepicker);
                timepicker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PickTime();
                    }
                });
                final Button datepicker=(Button) myDilaog.findViewById(R.id.btn_datepicker);
                datepicker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PickDate();
                    }
                });

                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        c=0;
                        myDilaog.dismiss();
                    }
                });

                myDilaog.show();
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////////////////////////////////

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        TextView txtvewd= findViewById(R.id.tv_date);
        txtvewd.setText(currentDate);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Lesson lll;
                lll=list1.get(position);
                DAATE=lll.getDate(); TIIME=lll.getTime();
                startActivity(new Intent(getApplicationContext(),LessonInfo.class));
            }
        });



    }



}
