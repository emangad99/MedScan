package com.example.medscan;

public class screen_item {

    String title,desc;
    int screen_img;

    public screen_item(String title, String desc, int screen_img) {
        this.title = title;
        this.desc = desc;
        this.screen_img = screen_img;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setScreen_img(int screen_img) {
        this.screen_img = screen_img;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public int getScreen_img() {
        return screen_img;
    }
}
