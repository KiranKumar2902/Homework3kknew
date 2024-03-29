package com.example.hw3kk;

import java.io.Serializable;

public class BreedClass implements Serializable {
    private String id;
    private String name;
    private String description;
    private WeightClass weight;
    private String temperament;
    private String origin;
    private String life_span;
    private String wikipedia_url;
    private String dog_friendly;


    public String getId() {

        return id;
    }

    public String getName() {

        return name;
    }

    public String getDescription() {

        return description;
    }

    public WeightClass getWeight() {

        return weight;
    }

    public String getTemperament() {

        return temperament;
    }

    public String getOrigin() {

        return origin;
    }

    public String getLife_span() {

        return life_span;
    }

    public String getWikipedia_url() {

        return wikipedia_url;
    }

    public String getDog_friendly() {

        return dog_friendly;
    }


}