package com.example.aug_pick_image_video_share;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class listadapter extends RecyclerView.Adapter <listadapter.detailsholder>{

    private ArrayList<pictureFacer> folders;
    private Context folderContx;
    private itemClickListener listenToClick;

    public listadapter(ArrayList<pictureFacer> folders, Context folderContx, itemClickListener listenToClick) {
        this.folders = folders;
        this.folderContx = folderContx;
        this.listenToClick = listenToClick;
    }

    @NonNull
    @Override
    public detailsholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View cell = inflater.inflate(R.layout.list_item_with_details,parent,false);

        return new detailsholder(cell);


    }


    public void onBindViewHolder(@NonNull detailsholder holder, int position) {
        final pictureFacer folder = folders.get(position);
        Glide.with(folderContx)
                .load(folder.getPicturePath())
                .apply(new RequestOptions().centerCrop())
                .into(holder.listimg);

        Log.e("ppath", "onBindViewHolder: "+folder.getPicturePath() );

        String path = folder.getPicturePath();
      //  holder.listpath.setText(path);
        File file = new File(folder.getPicturePath());
        Uri uri= FileProvider.getUriForFile(folderContx,folderContx.getPackageName()+".provider",file);

        Bitmap  bitmap;
        try {
            bitmap= MediaStore.Images.Media.getBitmap(folderContx.getContentResolver(), uri);
            int imageWidth=bitmap.getWidth();

            int imageHeight=bitmap.getHeight();
          //  holder.listimgresolution.setText(""+imageWidth+" * "+imageHeight);
            Log.e("Uri_image",""+bitmap);
        } catch (IOException e) {
            Log.e("bitmapnull", "onBindViewHolder: "+e );
            e.printStackTrace();
        }




        String name = folder.getPicturName();


        holder.listimgname.setText(name);

        String size = folder.getPictureSize();
        double s = Double.parseDouble(size);


        DecimalFormat f = new DecimalFormat("0.00");

        double kb = s/1024;

        if (kb<=1024)
        {
            holder.listimgsize.setText(f.format(kb)+"kb");
        }
        else {
            double mb = kb/1024;

            holder.listimgsize.setText(f.format(mb)+"mb");
        }

        Date lastModified = new Date(file.lastModified());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDateString = formatter.format(lastModified);

        holder.listimgdate.setText(formattedDateString);



        Date lastModifiedtime = new Date(file.lastModified());
        SimpleDateFormat formattertime = new SimpleDateFormat("HH:mm:ss");


        String formatteStringtime = formattertime.format(lastModifiedtime);

      //  holder.listimgtime.setText(formatteStringtime);


holder.moreoption.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        PopupMenu morepop = new PopupMenu(folderContx,holder.moreoption);
        morepop.inflate(R.menu.list_details_more_pop);
        morepop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                final pictureFacer folder = folders.get(position);

                String path = folder.getPicturePath();
                String size = folder.getPictureSize();

                Date lastModifiedtime = new Date(file.lastModified());
                SimpleDateFormat formattertime = new SimpleDateFormat("HH:mm:ss");


                String ttime = formattertime.format(lastModifiedtime);

              String img_name = folder.getPicturName();

                moreitem(item,path,size,ttime,folder.getPicturName(),position,img_name);


                return false;

            }
        });

     morepop.show();
    }
});



    }

    @Override
    public int getItemCount() {
        return folders.size();
    }

    public class detailsholder extends RecyclerView.ViewHolder {

        private ImageView listimg,moreoption;
        private TextView listpath;
        private TextView listimgname;
        private TextView listimgsize;
        private TextView listimgresolution;
        private TextView listimgdate;
        private TextView listimgtime;
        private TextView more_imgptha;



        public detailsholder(@NonNull View itemView) {
            super(itemView);



            more_imgptha=(TextView) itemView.findViewById(R.id.moredimgpath);
            listimg = (ImageView) itemView.findViewById(R.id.listimg);
            moreoption = (ImageView) itemView.findViewById(R.id.dot_more);
         //   listpath = (TextView) itemView.findViewById(R.id.listpath);
            listimgname = (TextView) itemView.findViewById(R.id.listimgname);
            listimgsize = (TextView) itemView.findViewById(R.id.listimgsize);
          //  listimgresolution = (TextView) itemView.findViewById(R.id.listimgresolution);
            listimgdate = (TextView) itemView.findViewById(R.id.listimgdate);
         //   listimgtime = (TextView) itemView.findViewById(R.id.listimgtime);

        }
    }

    public void moreitem(MenuItem item,String path, String size,String time, String imgname,int position,String pname)
    {

        switch (item.getItemId())
        {
            case R.id.m_view:


                Intent intent = new Intent(folderContx,pop_view_image.class);
                intent.putExtra("imgname",imgname);
                intent.putExtra("imagepath",path);

               folderContx.startActivity(intent);


                Toast.makeText(folderContx, "view", Toast.LENGTH_SHORT).show();
                break;
            case R.id.m_delet:

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(folderContx);
                alertDialog.setTitle("Do You Want to Delete the Record");
                alertDialog.setCancelable(false)
                        .setMessage("Click yes to delete")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                File file = new File("file://"+path);


                                Log.e("filedelet", "File path "+file );
                                if(file.exists()) {
                                    file.delete();
                                }
                                notifyDataSetChanged();

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {



                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(folderContx, "No", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog1 = alertDialog.create();

                alertDialog1.show();

                Toast.makeText(folderContx, "delet", Toast.LENGTH_SHORT).show();
                break;
            case R.id.m_rename:

                final AlertDialog.Builder alertrename = new AlertDialog.Builder(folderContx);
                alertrename.setTitle(pname);

                View renameView = LayoutInflater.from(folderContx).inflate(R.layout.rename_alert_dialog,null);

                EditText rename = renameView.findViewById(R.id.rename_edt);
                Button ok = renameView.findViewById(R.id.ok_btn);



                AlertDialog renamealertDialog = alertrename.create();

                renamealertDialog.show();


                Toast.makeText(folderContx, "Rename", Toast.LENGTH_SHORT).show();
                break;

            case R.id.m_details:

                final AlertDialog.Builder alertDialogdetails = new AlertDialog.Builder(folderContx);

                alertDialogdetails.setTitle("Details of the image");

                View myView = LayoutInflater.from(folderContx).inflate(R.layout.alert_dialog_details,null);
                 TextView paths = myView.findViewById(R.id.moredimgpath);
                TextView sizee = myView.findViewById(R.id.moredimgsize);
                TextView resolutionn = myView.findViewById(R.id.moredimgresolution);
                TextView timee = myView.findViewById(R.id.moreimgtime);


// image resolition and image path

              paths.setText(path);

              File file = new File(path);
                Uri uri= FileProvider.getUriForFile(folderContx,folderContx.getPackageName()+".provider",file);

                Bitmap  bitmap;
                try {
                    bitmap= MediaStore.Images.Media.getBitmap(folderContx.getContentResolver(), uri);
                    int imageWidth=bitmap.getWidth();
                    int imageHeight=bitmap.getHeight();

                    resolutionn.setText(imageWidth+" X "+imageHeight);
                            //setText(imageWidth+" X "+imageHeight);

                    //  holder.listimgresolution.setText(""+imageWidth+" * "+imageHeight);
                    Log.e("res","resoooo"+resolutionn);
                } catch (IOException e) {
                    Log.e("bitmapnull", "onBindViewHolder: "+e );
                    e.printStackTrace();
                }



// image size in mb kb

                String si = size;
                double s = Double.parseDouble(size);


                DecimalFormat f = new DecimalFormat("0.00");

                double kb = s/1024;

                if (kb<=1024)
                {
                    sizee.setText(f.format(kb)+"kb");
                }
                else {
                    double mb = kb/1024;

                    sizee.setText(f.format(mb)+"mb");
                }

// image time


timee.setText(time);




                final View customLayout =myView;


                alertDialogdetails.setView(customLayout);


                AlertDialog alertDialog1details = alertDialogdetails.create();

                alertDialogdetails.show();

                Toast.makeText(folderContx, "details", Toast.LENGTH_SHORT).show();

                break;

            default:
                break;
        }
        // Toast.makeText(getApplicationContext(),"Test",Toast.LENGTH_SHORT).show();
    }


}
