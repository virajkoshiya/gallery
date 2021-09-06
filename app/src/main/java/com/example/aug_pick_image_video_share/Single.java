package com.example.aug_pick_image_video_share;

import static androidx.core.view.ViewCompat.setTransitionName;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Single extends AppCompatActivity implements View.OnClickListener {



    private ImageView infoimage;


    private int imageposition;
    private ImageView image;
    private ViewPager imagePager;
    ArrayList<pictureFacer> list=new ArrayList<>();


    private LinearLayout whatsapp;
    private LinearLayout facebook;
    private LinearLayout instagram;
    private LinearLayout all;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);
        imagePager = findViewById(R.id.imagePager);

        infoimage = (ImageView) findViewById(R.id.infoimage);

        whatsapp = (LinearLayout) findViewById(R.id.whatsapp);
        facebook = (LinearLayout) findViewById(R.id.facebook);
        instagram = (LinearLayout) findViewById(R.id.instagram);
        all = (LinearLayout) findViewById(R.id.all);

        whatsapp.setOnClickListener(this);
        facebook.setOnClickListener(this);
        instagram.setOnClickListener(this);
        all.setOnClickListener(this);





        list=Utils.list_arr;

        imageposition=getIntent().getIntExtra("image_list",0);

      //  Log.e("img_position", "POSITION "+imageposition );



        ImagesPagerAdapter adapterView = new ImagesPagerAdapter(this,list);
        imagePager.setAdapter(adapterView);
        imagePager.setCurrentItem(imageposition);

        infoimage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String imageUri =  list.get(imagePager.getCurrentItem()).getPicturePath();

                File file=new File(imageUri);

                String imagepath = file.getPath();
                String imagename = file.getName();
                long imagelength = file.length();

                Intent intent = new Intent(v.getContext(),image_details.class);
                intent.putExtra("imagepath", imagepath);
                intent.putExtra("imagename", imagename);
                intent.putExtra("imagelength", imagelength);

                Bitmap bitmap;

                try {

                    Uri uri= FileProvider.getUriForFile(Single.this,getPackageName()+".provider",file);


                    bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);

                    int imageWidth=bitmap.getWidth();

                    int imageHeight=bitmap.getHeight();

                    intent.putExtra("width",imageWidth);

                    intent.putExtra("height",imageHeight);

                    Date lastModified = new Date(file.lastModified());
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String formattedDateString = formatter.format(lastModified);



                    Date lastModifiedtime = new Date(file.lastModified());
                    SimpleDateFormat formattertime = new SimpleDateFormat("HH:mm:ss");
                    String formatteStringtime = formattertime.format(lastModifiedtime);

                    intent.putExtra("date",formattedDateString);
                    intent.putExtra("time",formatteStringtime);




                } catch (IOException e) {
                    e.printStackTrace();
                }

                v.getContext().startActivity(intent);
            }
        });


    }


    public void onClick(View v) {

        String imageUri =  list.get(imagePager.getCurrentItem()).getPicturePath();

        switch (v.getId()) {
            case R.id.whatsapp:

                Intent intentwhatsapp = new Intent(Intent.ACTION_SEND);
                intentwhatsapp.setType("image/jpeg");
                intentwhatsapp.putExtra(Intent.EXTRA_STREAM, Uri.parse(imageUri));
                intentwhatsapp.setPackage("com.whatsapp");//package name of the app
                startActivity(Intent.createChooser(intentwhatsapp, "Share Image"));

                Toast.makeText(this, "whatsapp", Toast.LENGTH_SHORT).show();
                break;


            case R.id.facebook:

                Intent intentfacebook = new Intent(Intent.ACTION_SEND);
                intentfacebook.setType("image/jpeg");
                intentfacebook.putExtra(Intent.EXTRA_STREAM, Uri.parse(imageUri));
                intentfacebook.setPackage("com.Facebook.katana");//package name of the app
                startActivity(Intent.createChooser(intentfacebook, "Share Image"));

                Toast.makeText(this, "facebook", Toast.LENGTH_SHORT).show();
                break;


            case R.id.instagram:

                File file=new File(imageUri);

                Uri uri= FileProvider.getUriForFile(Single.this,getPackageName()+".provider",file);

              //  Tue Sep 10 10:07:36 GMT+05:30 2019




                Intent intentinstagram = new Intent(Intent.ACTION_SEND);
                intentinstagram.setType("image/jpeg");
                intentinstagram.putExtra(Intent.EXTRA_STREAM, uri);
                intentinstagram.setPackage("com.instagram.android");//package name of the app
                startActivity(Intent.createChooser(intentinstagram, "Share Image"));



                Toast.makeText(this, "instagram", Toast.LENGTH_SHORT).show();
                break;


            case R.id.all:

                Intent intentall = new Intent(Intent.ACTION_SEND);
                intentall.setType("image/jpeg");
                intentall.putExtra(Intent.EXTRA_STREAM, Uri.parse(imageUri));
                startActivity(Intent.createChooser(intentall, "Share Image"));

                Toast.makeText(this, "Share your image", Toast.LENGTH_SHORT).show();
                break;

        }
    }


    private class ImagesPagerAdapter extends PagerAdapter {

        Context context;
        ArrayList<pictureFacer> list;


        public ImagesPagerAdapter(Context context, ArrayList<pictureFacer> pc) {
            this.context = context;
            this.list = pc;

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
           View inflater=LayoutInflater.from(context).inflate(R.layout.picture_browser_pager,null);
            image = inflater.findViewById(R.id.image);


            pictureFacer pic=list.get(position);

            Glide.with(context)
                    .load(pic.getPicturePath())
                    .apply(new RequestOptions().fitCenter())
                    .into(image);

            ViewPager pager= (ViewPager) container;
            pager.addView(inflater,0);

            return inflater;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object view) {
            ViewPager pager= (ViewPager) container;
            View view1= (View) view;
            pager.removeView(view1);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == ((View) object);
        }


    }
}