package com.example.aug_pick_image_video_share;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Gallery extends AppCompatActivity implements itemClickListener{

    private RecyclerView imgRec;


private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        imgRec = (RecyclerView) findViewById(R.id.img_rec);

         imgRec.setLayoutManager(new GridLayoutManager(Gallery.this,3));

        imgRec.addItemDecoration(new MarginDecoration(this));

        imgRec.hasFixedSize();

        ArrayList<imageFolder> folds = getPicturePaths();

         // list=folds;

       //  Log.e("File",""+folds.get(0).getFolderName());

        if(ContextCompat.checkSelfPermission(Gallery.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)

            ActivityCompat.requestPermissions(Gallery.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);


     //   if(folds.isEmpty()){
           // empty.setVisibility(View.VISIBLE);


   //     Log.e("Arraylist", "onCreate: "+folds);


            RecyclerView.Adapter folderAdapter = new pictureFolderAdapter(folds,Gallery.this,this );
            imgRec.setAdapter(folderAdapter);
        }

     //   changeStatusBarColor();

  //  }

    private ArrayList<imageFolder> getPicturePaths(){
        ArrayList<imageFolder> picFolders = new ArrayList<>();
        ArrayList<String> picPaths = new ArrayList<>();


        Uri allImagesuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        Log.e("G.g.uri", "allImagesuri: " +allImagesuri);

        // content://media/external/images/media


//        String[] projection = { MediaStore.Video.VideoColumns.DATA ,
//                MediaStore.Video.Media.DISPLAY_NAME,
//                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
//                MediaStore.Video.Media.BUCKET_ID};

        String[] projection = { MediaStore.Images.ImageColumns.DATA ,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_ID};

        Log.e("array", "Projection "+ Arrays.toString(projection));

        // [_data, _display_name, bucket_display_name, bucket_id]

        Cursor cursor = this.getContentResolver().query(allImagesuri, projection, null, null, null);

        Log.e("cursor", "CURSOOOOOR "+ cursor);

        // CURSOOOOOR android.content.ContentResolver$CursorWrapperInner@c860fb2

        try {
            if (cursor != null) {
                cursor.moveToFirst();
            }
            do{
                imageFolder folds = new imageFolder();
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                String folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                String datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

              //  Log.e("videopathee", "getPicturePaths: "+datapath );

                //String folderpaths =  datapath.replace(name,"");
                String folderpaths = datapath.substring(0, datapath.lastIndexOf(folder+"/"));
                folderpaths = folderpaths+folder+"/";
                if (!picPaths.contains(folderpaths)) {
                    picPaths.add(folderpaths);

                    folds.setPath(folderpaths);
                    folds.setFolderName(folder);
                    folds.setFirstPic(datapath);//if the folder has only one picture this line helps to set it as first so as to avoid blank image in itemview
                    folds.addpics();
                    picFolders.add(folds);
                } else{
                    for(int i = 0;i<picFolders.size();i++){
                        if(picFolders.get(i).getPath().equals(folderpaths)){
                            picFolders.get(i).setFirstPic(datapath);
                            picFolders.get(i).addpics();
                        }
                    }
                }
            }
            while(cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return picFolders;
    }

    @Override
    public void onPicClicked(PicHolder holder, int position, ArrayList<pictureFacer> pics) {

    }

    @Override
    public void onPicClicked(String pictureFolderPath, String folderName) {


    }
}