package com.example.drutor;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;

import java.util.Calendar;

public class mode<currentDate> extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef2 = database.getReference("Students");
    DatabaseReference myRef = database.getReference("Tutors");
    public static TextView tvdate;
    public static TextView tvtime;
    private TextView textViewTime;
    private TimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;
    FirebaseUser user;
    Dialog myDilaog;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);

        user = FirebaseAuth.getInstance().getCurrentUser();
        Button add;
        add=findViewById(R.id.add);
        myDilaog=new Dialog(this);

        add.setOnClickListener(new View.OnClickListener() {
            Calendar calendar = Calendar.getInstance();
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

                lv=myDilaog.findViewById(R.id.lv);;
                final ArrayAdapter<String> arrayAdapter;
                final ArrayList <String> list;
                list=new ArrayList<String>();
                arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,list);

                 tvdate=(TextView) myDilaog.findViewById(R.id.tvdate);
                 tvtime=(TextView) myDilaog.findViewById(R.id.tvtime);

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
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////


}
