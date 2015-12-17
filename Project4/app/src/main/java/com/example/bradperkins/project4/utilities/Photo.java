package com.example.bradperkins.project4.utilities;

import java.io.Serializable;

/**
 * Created by bradperkins on 12/15/15.
 */
public class Photo implements Serializable{

    private static final long serialVersionUID = 42L;

    public String photoName;
    public String photoTitle;
    public String photoUri;

    public String photoLat;
    public String photoLng;
    public String position;


    public Photo(String _photoName, String _photoTitle, String _photoUri, String _photoLat, String _photoLng) {

        photoName = _photoName;
        photoTitle = _photoTitle;
        photoUri = _photoUri;
        photoLat = _photoLat;
        photoLng = _photoLng;
    }

    public void getData(Photo photo){
        photoName = photo.photoName;
        photoTitle = photo.photoTitle;
        photoUri = photo.photoUri;
        photoLat = photo.photoLat;
        photoLng = photo.photoLng;
    }


    public void setData(Photo photo){
        photoName = photo.photoName;
        photoTitle = photo.photoTitle;
        photoUri = photo.photoUri;
        photoLat = photo.photoLat;
        photoLng = photo.photoLng;
    }


    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getPhotoTitle() {
        return photoTitle;
    }

    public void setPhotoTitle(String photoTitle) {
        this.photoTitle = photoTitle;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public String getPhotoLat() {
        return photoLat;
    }

    public void setPhotoLat(String photoLat) {
        this.photoLat = photoLat;
    }

    public String getPhotoLng() {
        return photoLng;
    }

    public void setPhotoLng(String photoLng) {
        this.photoLng = photoLng;
    }

    public String getPosition() {
        return position;
    }
}
