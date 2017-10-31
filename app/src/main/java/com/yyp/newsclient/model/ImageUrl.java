package com.yyp.newsclient.model;

import java.io.Serializable;

public class ImageUrl implements Serializable {
    public String url;
    public String description;

    public ImageUrl(String url, String description) {
        this.url = url;
        this.description = description;
    }
}