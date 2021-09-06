package com.example.aug_pick_image_video_share;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class Start_button extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;


    private Button btnImg;
    private ImageView img;
    private Button btnVideo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_button);


        btnImg = (Button) findViewById(R.id.btn_img);
        img = (ImageView) findViewById(R.id.img);
        btnVideo = (Button) findViewById(R.id.btn_video);

        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Start_button.this,Gallery.class);
                startActivity(intent);


            }
        });

        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Start_button.this,Video.class);
                startActivity(intent);


            }
        });



    }

}