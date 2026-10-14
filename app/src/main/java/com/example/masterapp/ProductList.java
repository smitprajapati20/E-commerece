package com.example.masterapp;

public class ProductList {
    String Pname;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    String rating;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String description;
    int Pid;
    int Pimg;

    public int getSubcat_id() {
        return subcat_id;
    }

    public void setSubcat_id(int subcat_id) {
        this.subcat_id = subcat_id;
    }

    public String getPname() {
        return Pname;
    }

    public void setPname(String pname) {
        Pname = pname;
    }

    public int getPid() {
        return Pid;
    }

    public void setPid(int pid) {
        Pid = pid;
    }

    public int getPimg() {
        return Pimg;
    }

    public void setPimg(int pimg) {
        Pimg = pimg;
    }

    public int getO_rate() {
        return O_rate;
    }

    public void setO_rate(int o_rate) {
        O_rate = o_rate;
    }

    public int getD_rate() {
        return D_rate;
    }

    public void setD_rate(int d_rate) {
        D_rate = d_rate;
    }

    int subcat_id;
    int O_rate;
    int D_rate;
}
