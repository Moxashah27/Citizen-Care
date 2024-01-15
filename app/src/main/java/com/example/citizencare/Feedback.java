package com.example.citizencare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

public class Feedback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        TextView text2=findViewById(R.id.text2);
        TextView text3=findViewById(R.id.text3);
        String services="Services";
        SpannableString content1=new SpannableString(services);
        content1.setSpan(new UnderlineSpan(),0,content1.length(),0);
        text2.setText(content1);
        String complaints="Complaints";
        SpannableString content2=new SpannableString(complaints);
        content2.setSpan(new UnderlineSpan(),0,content2.length(),0);
        text3.setText(content2);
    }
}