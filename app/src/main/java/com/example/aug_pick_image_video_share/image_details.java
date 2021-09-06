package com.example.aug_pick_image_video_share;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.DecimalFormat;

public class image_details extends AppCompatActivity {


    private TextView imgpath;
    private TextView imgname;
    private TextView size;
    private TextView imgresolution;

    private TextView date;
    private TextView time;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);

        imgpath = (TextView) findViewById(R.id.imgpath);
        imgname = (TextView) findViewById(R.id.imgname);
        size = (TextView) findViewById(R.id.imglength);
        imgresolution = (TextView) findViewById(R.id.imgresolution);

        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);

         String imagepath= getIntent().getStringExtra("imagepath");
         imgpath.setText(imagepath);

        String imagename= getIntent().getStringExtra("imagename");
        imgname.setText(imagename);

        double imglength= getIntent().getLongExtra("imagelength",0);


        DecimalFormat f = new DecimalFormat("0.00");

        double kb = imglength/1024;

        if (kb<=1024)
        {
            size.setText(f.format(kb)+"kb");
        }
        else {
            double mb = kb/1024;

            size.setText(f.format(mb)+"mb");
        }

        int width = getIntent().getIntExtra("width",0);
        int height= getIntent().getIntExtra("height",0);

        imgresolution.setText("  "+width+" * "+height);

        String datestring = getIntent().getStringExtra("date");
        String timestring = getIntent().getStringExtra("time");

        date.setText(datestring);
        time.setText(timestring);






    }
}