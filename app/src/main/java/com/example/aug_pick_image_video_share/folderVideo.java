package com.example.aug_pick_image_video_share;

public class folderVideo {

    private String path;
    private  String folderName;
    private  int NumberOfVideo;
    private  String firstVideo;

    public folderVideo(){

    }

    public folderVideo(String path, String folderName) {
        this.path = path;
        this.folderName = folderName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public int getNumberOfVideo() {
        return NumberOfVideo;
    }

    public void setNumberOfVideo(int numberOfVideo) {
        NumberOfVideo = numberOfVideo;
    }

    public String getFirstVideo() {
        return firstVideo;
    }

    public void setFirstVideo(String firstVideo) {
        this.firstVideo = firstVideo;
    }

    public void addvideo() {
        this.NumberOfVideo++;
    }
}
