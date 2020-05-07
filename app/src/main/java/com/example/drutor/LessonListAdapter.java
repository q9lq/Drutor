package com.example.drutor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LessonListAdapter extends ArrayAdapter<Lesson> {
    private static final String TAG = "LessonListAdapter";
    private Context mContext;
    int mResource;

    public LessonListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Lesson> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String date = getItem(position).getDate();
        String time = getItem(position).getTime();

        Lesson lesson = new Lesson(date,time);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvDate = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvTime = (TextView) convertView.findViewById(R.id.textView2);

        tvDate.setText(date);
        tvTime.setText(time);

        return  convertView;
    }
}
