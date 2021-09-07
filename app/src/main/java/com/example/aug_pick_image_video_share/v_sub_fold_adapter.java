package com.example.aug_pick_image_video_share;

import static androidx.core.view.ViewCompat.setTransitionName;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class v_sub_fold_adapter extends RecyclerView.Adapter {


  public ArrayList<V_sub_fold_facer> sub_videos;
    Context sub_v_ad;

    public v_sub_fold_adapter(ArrayList<V_sub_fold_facer> sub_videos, Context sub_v_ad) {
        this.sub_videos = sub_videos;
        this.sub_v_ad = sub_v_ad;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(sub_v_ad);
      View v = inflater.inflate(R.layout.v_sub_holder_item,parent,false);
      return new sub_video_holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        sub_video_holder holder1 = (sub_video_holder) holder;

        final V_sub_fold_facer sub_v_facer = sub_videos.get(position);

        Glide.with(sub_v_ad)
                .load(sub_v_facer.getV_s_f_Path())
                .apply(new RequestOptions().centerCrop())
                .into(holder1.video);

        ((sub_video_holder) holder).video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.sub_videos=sub_videos;
                Intent intent = new Intent(v.getContext(),play_video.class);
                int p=position;
                String path = sub_v_facer.getV_s_f_Path();
                intent.putExtra("play",path);
                intent.putExtra("position",p);

             //   Log.e("pathss", "onClick: "+path );

                v.getContext().startActivity(intent);

                Toast.makeText(v.getContext(), "yess", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return sub_videos.size();
    }

    public class sub_video_holder extends RecyclerView.ViewHolder {

         ImageView video;


        public sub_video_holder(@NonNull View itemView) {
            super(itemView);

            video=itemView.findViewById(R.id.sub_video);


        }
    }

}
