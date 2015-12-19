package com.example.bradperkins.project4.utilities;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by bradperkins on 12/15/15.
 */
public class Photo extends ArrayList<Photo> implements Serializable {

    private static final long serialVersionUID = -7791154359356162736L;

    private String imageName;
    private String name;
    private String detail;


    private int uri;
    private LatLng coordinates;

    public Photo(String _name, String _detail, int _uri, LatLng _coordinates) {
        name = _name;
        detail = _detail;
        uri = _uri;
        coordinates = _coordinates;
    }


    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUri() {
        return uri;
    }

    public void setUri(int uri) {
        this.uri = uri;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }


}
