package com.example.aug_pick_image_video_share;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class V_sub_fold_display extends AppCompatActivity {


    private CardView head;
    private RelativeLayout header;
    private TextView vSubFoldername;
    private ImageView vSubList;
    private ImageView vSubPopupmenu;
    private RecyclerView recycler;
    String folderpath;

    ArrayList<V_sub_fold_facer> all_v_s_fold = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vsub_fold_display);

        head = (CardView) findViewById(R.id.head);
        header = (RelativeLayout) findViewById(R.id.header);
        vSubFoldername = (TextView) findViewById(R.id.v_sub_foldername);
        vSubList = (ImageView) findViewById(R.id.v_sub_list);
        vSubPopupmenu = (ImageView) findViewById(R.id.v_sub_popupmenu);
        recycler = (RecyclerView) findViewById(R.id.recycler);


        String folderpath = getIntent().getStringExtra("fpath");

        Log.e("folderpathsub", "onCreate: "+folderpath);

        vSubFoldername.setText(getIntent().getStringExtra("fname"));


        all_v_s_fold=new ArrayList<>();
       all_v_s_fold=getAll_video_BysubFolder(folderpath);



        recycler.setLayoutManager(new GridLayoutManager(V_sub_fold_display.this,3));
        recycler.hasFixedSize();

        RecyclerView.Adapter v_sub = new v_sub_fold_adapter(all_v_s_fold,this);
        recycler.setAdapter(v_sub);


    }

// https://www.11zon.com/zon/android/how-to-get-all-video-files-in-android-programmatically.php

//    public void getVideos(String path) {
//        ContentResolver contentResolver = getContentResolver();
//        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//        String[] projection = { MediaStore.Video.VideoColumns.DATA ,MediaStore.Video.Media.DISPLAY_NAME,
//                MediaStore.Video.Media.SIZE};
//        Cursor cursor = contentResolver.query( uri,projection , MediaStore.Video.Media.DATA + " like ? ", new String[] {"%"+path+"%"}, null);
//
//        //looping through all rows and adding to list
//
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//
//                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
//
//
//
//                String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
//                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
//
//           //     Log.e("title", "getVideos: "+data );
//
//                VideoModel  videoModel  = new VideoModel ();
//                videoModel .setVideoTitle(title);
//                videoModel .setVideoUri(Uri.parse(data));
//              //  videoModel .setVideoDuration(timeConversion(Long.parseLong(duration)));
//               // videoArrayList.add(videoModel);
//
//            } while (cursor.moveToNext());
//        }
//
//      //  VideoAdapter  adapter = new VideoAdapter (this, videoArrayList);
//      //  recyclerView.setAdapter(adapter);
//
//    }

    // old method

    public ArrayList<V_sub_fold_facer> getAll_video_BysubFolder(String path){
        ArrayList<V_sub_fold_facer> sub_fold_videos = new ArrayList<>();
        Uri allVideosuri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.Video.VideoColumns.DATA ,MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.SIZE};
        Cursor cursor = V_sub_fold_display.this.getContentResolver().query( allVideosuri, projection, MediaStore.Video.Media.DATA + " like ? ", new String[] {"%"+path+"%"}, null);
        try {
            cursor.moveToFirst();
            do{
                V_sub_fold_facer videofacer = new V_sub_fold_facer();

                int data = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
                String list=cursor.getString(data);
                Log.e("File",""+list);

                videofacer.setV_s_f_name(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)));

             //   Log.e("videoname", "doInBackground: "+(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME))));
                videofacer.setV_s_f_Path(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)));

                String datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));

                 Log.e("videopathff", "doInBackground: "+datapath);

                videofacer.setV_s_f_Size(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)));

                videofacer.getV_s_f_Path();

            //    Log.e("lll", "doInBackground: "+ );

                sub_fold_videos.add(videofacer);

            }while(cursor.moveToNext());
            cursor.close();
            ArrayList<V_sub_fold_facer> reSelection = new ArrayList<>();
            for(int i = sub_fold_videos.size()-1;i > -1;i--){
                reSelection.add(sub_fold_videos.get(i));
            }
            sub_fold_videos = reSelection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sub_fold_videos;
    }
}