package com.example.masterapp;

public class SubCategoryList {
    public String getSubcat_name() {
        return subcat_name;
    }

    public void setSubcat_name(String subcat_name) {
        this.subcat_name = subcat_name;
    }

    public int getSubcat_img() {
        return subcat_img;
    }

    public void setSubcat_img(int subcat_img) {
        this.subcat_img = subcat_img;
    }

    public int getSubcat_id() {
        return subcat_id;
    }

    public void setSubcat_id(int subcat_id) {
        this.subcat_id = subcat_id;
    }

    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }
    String subcat_name;
    int subcat_img,subcat_id,catid;
}
