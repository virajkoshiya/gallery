package com.example.aug_pick_image_video_share;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class V_Main_Fold_adapter extends RecyclerView.Adapter <V_Main_Fold_adapter.V_Folderholder>{

    private ArrayList<folderVideo> v_folders;
    private Context v_fold_context;

    public V_Main_Fold_adapter(ArrayList<folderVideo> v_folders, Context v_fold_context) {
        this.v_folders = v_folders;
        this.v_fold_context = v_fold_context;
    }

    @NonNull
    @Override
    public V_Folderholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(v_fold_context);
        View v = inflater.inflate(R.layout.v_fold_main_item_rec,parent,false);

        return new V_Folderholder (v);
    }

    @Override
    public void onBindViewHolder(@NonNull V_Folderholder holder, int position) {

        final folderVideo f_v = v_folders.get(position);

        Glide.with(v_fold_context)
                .load(f_v.getFirstVideo())
                .apply(new RequestOptions().centerCrop())
                .into(holder.folderVideo);

        String text = ""+f_v.getFolderName();
        String folderSizeString=""+f_v.getNumberOfVideo()+" Media";
        holder.folderVSize.setText(folderSizeString);
        Log.e("sizeof1", "onBindViewHolder: "+ folderSizeString);
        holder.folderVName.setText(text);


        holder.folderVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ABC", "onClick: "+f_v.getPath() );
                Intent intent = new Intent(v.getContext(),V_sub_fold_display.class);
                intent.putExtra("fpath",f_v.getPath());

                Log.e("half", "onClick: "+f_v.getPath() );

                intent.putExtra("fname",f_v.getFolderName());
                v.getContext().startActivity(intent);


                Toast.makeText(v.getContext(), ""+f_v.getPath(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return v_folders.size();
    }

    public class V_Folderholder extends RecyclerView.ViewHolder {

         CardView folderVCard;
         ImageView folderVideo;
         TextView folderVName;
         TextView folderVSize;



        public V_Folderholder(@NonNull View itemView) {
            super(itemView);

            folderVCard = itemView.findViewById(R.id.folder_v_Card);
            folderVideo = itemView. findViewById(R.id.folder_video);
            folderVName = itemView.findViewById(R.id.folder_v_Name);
            folderVSize = itemView.findViewById(R.id.folder_v_Size);
        }
    }


}
