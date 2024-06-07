package com.example.ais_ecc.munchkin.models;

public class CardAction {
    private String path;
    private String text;

    public CardAction(String path, String text) {
        this.path = path;
        this.text = text;

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
