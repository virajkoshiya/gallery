package com.example.aug_pick_image_video_share;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.androidcodeman.simpleimagegallery.fragments.pictureBrowserFragment;
//import com.androidcodeman.simpleimagegallery.utils.picture_Adapter;

import java.io.File;
import java.util.ArrayList;

public class ImageDisplay extends AppCompatActivity implements itemClickListener {

    RecyclerView imageRecycler;
    ArrayList<pictureFacer> allpictures=new ArrayList<>();
    ProgressBar load;
    String foldePath;
    TextView folderName;
    private ImageView popupmenu;
    ArrayList<pictureFacer> images = new ArrayList<>();
     boolean check=false;
    private ImageView list,moreoption;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        folderName = findViewById(R.id.foldername);

        popupmenu = (ImageView) findViewById(R.id.popupmenu);

        list = (ImageView) findViewById(R.id.list);

        moreoption=(ImageView) findViewById(R.id.dot_more);

        folderName.setText(getIntent().getStringExtra("name"));

        foldePath =  getIntent().getStringExtra("img");

       // Log.e("ppppp", "onCreate: "+foldePath );


        allpictures = new ArrayList<>();

        imageRecycler = findViewById(R.id.recycler);
        imageRecycler.addItemDecoration(new MarginDecoration(this));
        imageRecycler.hasFixedSize();
        load = findViewById(R.id.loader);


        if(allpictures.isEmpty()){
            load.setVisibility(View.VISIBLE);
            images.clear();
       //     allpictures = getAllImagesByFolder(foldePath);

            new RefreshData(foldePath,MediaStore.Images.Media.DISPLAY_NAME).execute();

        }else{

        }

        // pop sorting event lisning

        popupmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu pop=new PopupMenu(ImageDisplay.this,v);

                pop.inflate(R.menu.popupfilter);
                pop.show();
                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        menuItem(item);
                        return false;
                    }
                });

            }
        });



  list.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if(check==true)
        {
           check=false;


            new RefreshData(foldePath,MediaStore.Images.Media.DISPLAY_NAME).execute();

            Toast.makeText(v.getContext(), "True", Toast.LENGTH_SHORT).show();
        }
        else  if(check==false)
        {

           check=true;
            new RefreshData(foldePath,MediaStore.Images.Media.DISPLAY_NAME).execute();

            Toast.makeText(v.getContext(), "false", Toast.LENGTH_SHORT).show();
        }

    }
});



    }


    @Override
    public void onPicClicked(PicHolder holder, int position, ArrayList<pictureFacer> pics) {

    }

    @Override
    public void onPicClicked(String pictureFolderPath,String folderName) {

    }


    public boolean menuItem(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.date:

                new RefreshData(foldePath,MediaStore.Images.Media.DATE_ADDED).execute();
                Toast.makeText(this, "Sorted by Date : "+item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.size:
                Toast.makeText(this, "Sorted by size", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.name:

              new RefreshData(foldePath,MediaStore.Images.Media.DISPLAY_NAME).execute();


              //  Toast.makeText(this, "Sorted by name", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
      // Toast.makeText(getApplicationContext(),"Test",Toast.LENGTH_SHORT).show();
    }




public class RefreshData extends AsyncTask<Void, Void,Void>

{
String path;
String orderby;

    public RefreshData(String path, String orderby) {
        this.path = path;
        this.orderby = orderby;
        allpictures.clear();
        images.clear();
    }


    @Override
    protected Void doInBackground(Void... voids) {
            Uri allVideosuri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            String[] projection = { MediaStore.Images.ImageColumns.DATA ,MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.SIZE};
            Cursor cursor = ImageDisplay.this.getContentResolver().query( allVideosuri, projection, MediaStore.Images.Media.DATA + " like ? ", new String[] {"%"+path+"%"}, orderby);
            try {
                cursor.moveToFirst();
                do{
                    pictureFacer pic = new pictureFacer();

                    pic.setPicturName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)));

                 pic.setPicturePath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));
                  //  Log.e("vvvvvv", "doInBackground: "+(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))));
                    pic.setPictureSize(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)));

                    pic.getPicturePath();

                  //  Log.e("ll", "doInBackground: "+  pic.getPicturePath());


                   // String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));


                    images.add(pic);
                }while(cursor.moveToNext());
                cursor.close();
                ArrayList<pictureFacer> reSelection = new ArrayList<>();
                for(int i = images.size()-1;i > -1;i--){
                    reSelection.add(images.get(i));
                }
                images = reSelection;
                allpictures=images;
            }
            catch (Exception e) {
                e.printStackTrace();

            }
          return  null;
        }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        if(check)
        {
            imageRecycler.setLayoutManager(new LinearLayoutManager(ImageDisplay.this));
            imageRecycler.setAdapter(new listadapter(allpictures,ImageDisplay.this,null));
            load.setVisibility(View.GONE);
        }
        else
        {
            imageRecycler.setLayoutManager( new GridLayoutManager(ImageDisplay.this,3));
            imageRecycler.setAdapter(new picture_Adapter(allpictures,ImageDisplay.this,null));
            load.setVisibility(View.GONE);
        }

    }


}


}
