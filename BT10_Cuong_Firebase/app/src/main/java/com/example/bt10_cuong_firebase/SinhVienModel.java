package com.example.bt10_cuong_firebase;

public class SinhVienModel {
    String name, masv, img;
    SinhVienModel(){

    }

    public SinhVienModel(String name, String masv, String img) {
        this.name = name;
        this.masv = masv;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
