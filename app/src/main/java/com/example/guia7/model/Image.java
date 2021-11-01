package com.example.guia7.model;

public class Image {
    private String idImg;
    private String URL;

    public Image(String idImg, String URL) {
        this.idImg = idImg;
        this.URL = URL;
    }

    public String getIdImg() {
        return idImg;
    }

    public void setIdImg(String idImg) {
        this.idImg = idImg;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
