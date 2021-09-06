package com.example.aug_pick_image_video_share;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class Video extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private RecyclerView vidRec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        vidRec = (RecyclerView) findViewById(R.id.vid_rec);

        // permission

        if(ContextCompat.checkSelfPermission(Video.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)

            ActivityCompat.requestPermissions(Video.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

        ArrayList<folderVideo> videofolds =  getvideopath();
        // Log.e("V.o.videofolds",""+videofolds.get(3).getFolderName());


        vidRec.setLayoutManager(new GridLayoutManager(Video.this,3));
        vidRec.hasFixedSize();

        RecyclerView.Adapter v_folderAdapter = new V_Main_Fold_adapter(videofolds,this);
        vidRec.setAdapter(v_folderAdapter);


    }

    private ArrayList<folderVideo> getvideopath()
    {
        ArrayList<folderVideo> videosfolder = new ArrayList<>();

        ArrayList<String > videopath = new ArrayList<>();

        Uri allvideouri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        //Log.e("V.g.uri", "getvideopath: "+ allvideouri);

        String[] projectionvideo = { MediaStore.Video.VideoColumns.DATA ,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media.BUCKET_ID};
        //Log.e("V.g.array", "Projection "+ Arrays.toString(projectionvideo));

        Cursor cursor = this.getContentResolver().query(allvideouri,projectionvideo, null, null, null);

        Log.e("V.g.cursor", "getvideopath: "+ cursor);


        try {
            if (cursor != null) {
                cursor.moveToFirst();
            }
            do{
                folderVideo vfolds = new folderVideo();
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
                String folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME));

                String datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));

             //   Log.e("videopathees", "getPicturePaths: "+datapath );

                //String folderpaths =  datapath.replace(name,"");
                String folderpaths = datapath.substring(0, datapath.lastIndexOf(folder+"/"));
                folderpaths = folderpaths+folder+"/";

              //  Log.e("fk", "getvideopath: "+folderpaths );


                if (!videopath.contains(folderpaths)) {
                    videopath.add(folderpaths);

                    vfolds.setPath(folderpaths);
                    vfolds.setFolderName(folder);
                    vfolds.setFirstVideo(datapath);//if the folder has only one picture this line helps to set it as first so as to avoid blank image in itemview
                    vfolds.addvideo();
                    videosfolder.add(vfolds);
                } else{
                    for(int i = 0;i<videosfolder.size();i++){
                        if(videosfolder.get(i).getPath().equals(folderpaths)){
                            videosfolder.get(i).setFirstVideo(datapath);
                            videosfolder.get(i).addvideo();
                        }
                    }
                }
            }
            while(cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return videosfolder;
    }


}