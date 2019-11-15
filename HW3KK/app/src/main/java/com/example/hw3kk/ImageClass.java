package com.example.hw3kk;

import java.io.Serializable;
import java.util.ArrayList;

public class ImageClass implements Serializable {
    private String width;
    private String id;
    private String url;
    private ArrayList<BreedClass> breeds;
    private String height;

    public String getWidth() {

        return width;
    }

    public String getId() {

        return id;
    }

    public String getUrl() {

        return url;
    }

    public ArrayList<BreedClass> getBreeds() {

        return breeds;
    }

    public String getHeight() {

        return height;
    }

}