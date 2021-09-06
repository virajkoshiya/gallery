package com.example.aug_pick_image_video_share;

import android.print.PrintAttributes;

/**
 * Author CodeBoy722
 *
 * Custom class for holding data of images on the device external storage
 */
public class pictureFacer {

    private String picturName;
    private String picturePath;
    private  String pictureSize;
    private  String imageUri;
    private Boolean selected = false;

    // new
    private String date;
    private String time;

    private String resolution;


    public pictureFacer(){

    }

    public pictureFacer(String picturName, String picturePath, String pictureSize, String imageUri, Boolean selected, String date, String time, String resolution) {
        this.picturName = picturName;
        this.picturePath = picturePath;
        this.pictureSize = pictureSize;
        this.imageUri = imageUri;
        this.selected = selected;
        this.date = date;
        this.time = time;
        this.resolution = resolution;
    }

    //    public pictureFacer(String picturName, String picturePath, String pictureSize, String imageUri) {
//        this.picturName = picturName;
//        this.picturePath = picturePath;
//        this.pictureSize = pictureSize;
//        this.imageUri = imageUri;
//    }




    // old get set

//    public String getPicturName() {
//        return picturName;
//    }
//
//    public void setPicturName(String picturName) {
//        this.picturName = picturName;
//    }
//
//    public String getPicturePath() {
//        return picturePath;
//    }
//
//    public void setPicturePath(String picturePath) {
//        this.picturePath = picturePath;
//    }
//
//    public String getPictureSize() {
//        return pictureSize;
//    }
//
//    public void setPictureSize(String pictureSize) {
//        this.pictureSize = pictureSize;
//    }
//
//    public String getImageUri() {
//        return imageUri;
//    }
//
//    public void setImageUri(String imageUri) {
//        this.imageUri = imageUri;
//    }
//
//    public Boolean getSelected() {
//        return selected;
//    }
//
//    public void setSelected(Boolean selected) {
//        this.selected = selected;
//    }


    public String getPicturName() {
        return picturName;
    }

    public void setPicturName(String picturName) {
        this.picturName = picturName;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getPictureSize() {
        return pictureSize;
    }

    public void setPictureSize(String pictureSize) {
        this.pictureSize = pictureSize;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
}
