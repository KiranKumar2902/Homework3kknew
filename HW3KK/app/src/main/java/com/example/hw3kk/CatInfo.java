package com.example.hw3kk;

import java.io.Serializable;

public class CatInfo implements Serializable {
    public String key;
    public String value;

    public CatInfo(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {

        return key;
    }

    public String getValue() {

        return value;
    }
}