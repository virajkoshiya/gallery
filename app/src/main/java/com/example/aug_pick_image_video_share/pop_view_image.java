package com.example.aug_pick_image_video_share;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class pop_view_image extends AppCompatActivity {

    private ImageView imagepop;

    private TextView popImgName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_view_image);

        imagepop = (ImageView) findViewById(R.id.imagepop);

        popImgName = (TextView) findViewById(R.id.pop_img_name);


        String folderpath = getIntent().getStringExtra("imagepath");

        String popimgname=getIntent().getStringExtra("imgname");
        popImgName.setText(popimgname);

        Glide.with(this)
                .load(folderpath)
                .apply(new RequestOptions().fitCenter())
                .into(imagepop);

    }
}