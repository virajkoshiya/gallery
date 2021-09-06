package com.example.aug_pick_image_video_share;

import static androidx.core.view.ViewCompat.setTransitionName;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class picture_Adapter extends RecyclerView.Adapter<PicHolder> {

    private ArrayList<pictureFacer> pictureList;
    private Context pictureContx;
    private final itemClickListener picListerner;


    ArrayList<String> list=new ArrayList<>();

    public picture_Adapter(ArrayList<pictureFacer> pictureList, Context pictureContx, itemClickListener picListerner) {
        this.pictureList = pictureList;
        this.pictureContx = pictureContx;
        this.picListerner = picListerner;
    }

    @NonNull
    @Override
    public PicHolder onCreateViewHolder(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View cell = inflater.inflate(R.layout.pic_holder_item, container, false);
        return new PicHolder(cell);
    }

    @Override
    public void onBindViewHolder(@NonNull final PicHolder holder, final int padposition) {

        final pictureFacer image = pictureList.get(padposition);
             // list.add(image.getImageUri());

        Glide.with(pictureContx)
                .load(image.getPicturePath())
                .apply(new RequestOptions().centerCrop())
                .into(holder.picture);

        setTransitionName(holder.picture, String.valueOf(padposition) + "_image");

        holder.picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                Utils.list_arr = pictureList;
                Intent intent = new Intent(v.getContext(),Single.class);
                intent.putExtra("image_list",padposition);
               v.getContext().startActivity(intent);

               // picListerner.onPicClicked(holder,position, pictureList);



                Toast.makeText(pictureContx.getApplicationContext(), " open single image", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }
}
