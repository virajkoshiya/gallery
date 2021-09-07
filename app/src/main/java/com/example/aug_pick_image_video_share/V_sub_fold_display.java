package com.example.aug_pick_image_video_share;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class V_sub_fold_display extends AppCompatActivity {

    boolean check=false;
    private CardView head;
    private RelativeLayout header;
    private TextView vSubFoldername;
    private ImageView vSubList;
    private ImageView vSubPopupmenu;
    private RecyclerView recycler;
    String folderpath;

    ArrayList<V_sub_fold_facer> all_v_s_fold = new ArrayList<>();
    ArrayList<V_sub_fold_facer> videos = new ArrayList<>();
    ArrayList<V_sub_fold_facer> allvideos=new ArrayList<>();



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

         folderpath = getIntent().getStringExtra("fpath");

        Log.e("folderpathsub", "onCreate: "+folderpath);

        vSubFoldername.setText(getIntent().getStringExtra("fname"));


      //  all_v_s_fold=new ArrayList<>();
    //   all_v_s_fold=getAll_video_BysubFolder(folderpath);

//        recycler.setLayoutManager(new GridLayoutManager(V_sub_fold_display.this,3));
//        recycler.hasFixedSize();
//
//        RecyclerView.Adapter v_sub = new v_sub_fold_adapter(all_v_s_fold,this);
//        recycler.setAdapter(v_sub);

        if(allvideos.isEmpty()){
          //  load.setVisibility(View.VISIBLE);
            videos.clear();
            //     allpictures = getAllImagesByFolder(foldePath);

            new refresh(folderpath,MediaStore.Video.Media.DISPLAY_NAME).execute();

        }else{

        }

        vSubList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(check==true)
                {
                    check=false;

                      new refresh(folderpath,MediaStore.Video.Media.DISPLAY_NAME).execute();

                 //   Toast.makeText(v.getContext(), "True", Toast.LENGTH_SHORT).show();
                }
                else  if(check==false)
                {
                    check=true;
                    new refresh(folderpath,MediaStore.Video.Media.DISPLAY_NAME).execute();

                 //   Toast.makeText(v.getContext(), "false", Toast.LENGTH_SHORT).show();
                }


            }
        });

        vSubPopupmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu pop=new PopupMenu(V_sub_fold_display.this,v);

                pop.inflate(R.menu.pop_vid_list);
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


    }

    public boolean menuItem(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.vid_date:

                new refresh(folderpath,MediaStore.Video.Media.DATE_ADDED).execute();
                Toast.makeText(this, "Sorted by Date : "+item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.vid_size:
              //  Toast.makeText(this, "Sorted by size", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.vid_name:

                new refresh(folderpath,MediaStore.Video.Media.DISPLAY_NAME).execute();


                Toast.makeText(this, "Sorted by name", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
        // Toast.makeText(getApplicationContext(),"Test",Toast.LENGTH_SHORT).show();
    }




    public class refresh extends AsyncTask<Void,Void,Void>{

        String path;
        String Orderby;

        public refresh(String path, String orderby) {
            this.path = path;
            this.Orderby = orderby;
            allvideos.clear();
        }


        @Override
        protected Void doInBackground(Void... voids) {


            Uri video = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            String[] projection = { MediaStore.Video.VideoColumns.DATA ,MediaStore.Video.Media.DISPLAY_NAME,
                    MediaStore.Video.Media.SIZE};
            Cursor cursor = V_sub_fold_display.this.getContentResolver().query( video, projection, MediaStore.Video.Media.DATA + " like ? ", new String[] {"%"+path+"%"}, Orderby);
            try {
                cursor.moveToFirst();
                do{
                    V_sub_fold_facer vid = new V_sub_fold_facer();

                    vid.setV_s_f_name(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)));
                  //  Log.e("vvvvv", "doInBackground: "+(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME))));
                    vid.setV_s_f_Path(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)));

                    vid.setV_s_f_Size(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)));

                    vid.getV_s_f_Path();

                    //  Log.e("ll", "doInBackground: "+  pic.getPicturePath());


                    // String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));


                    videos.add(vid);
                }while(cursor.moveToNext());
                cursor.close();
                ArrayList<V_sub_fold_facer> reSelection = new ArrayList<>();
                for(int i = videos.size()-1;i > -1;i--){
                    reSelection.add(videos.get(i));
                }
                videos = reSelection;
                allvideos=videos;
            }
            catch (Exception e) {
                e.printStackTrace();

            }

            return null;


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
                recycler.setLayoutManager(new LinearLayoutManager(V_sub_fold_display.this));
                recycler.setAdapter(new vid_list_adapter(allvideos,V_sub_fold_display.this));

            }
            else
            {
                recycler.setLayoutManager( new GridLayoutManager(V_sub_fold_display.this,3));
                recycler.setAdapter(new v_sub_fold_adapter(allvideos,V_sub_fold_display.this));

            }

        }
    }
}