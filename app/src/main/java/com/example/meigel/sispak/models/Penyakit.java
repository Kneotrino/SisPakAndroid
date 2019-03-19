package com.example.meigel.sispak.models;

/**
 * Created by meigel on 1/16/19.
 */

public class Penyakit {
    private int id;
    private String kode,name,penanganan,desc,img,desc1,desc2,desc3;
    private double p;

    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getDesc2() {
        return desc2;
    }

    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }

    public String getDesc3() {
        return desc3;
    }

    public void setDesc3(String desc3) {
        this.desc3 = desc3;
    }

    public Penyakit(int id, String kode, String name, String penanganan, String desc,  String desc1, String desc2, String desc3, String img) {
        this.id = id;
        this.kode = kode;
        this.name = name;
        this.penanganan = penanganan;
        this.desc = desc;
        this.img = img;
        this.desc1 = desc1;
        this.desc2 = desc2;
        this.desc3 = desc3;
    }

    public Penyakit(String kode, String name, String penanganan, String desc, String img, String desc1, String desc2, String desc3) {
        this.kode = kode;
        this.name = name;
        this.penanganan = penanganan;
        this.desc = desc;
        this.img = img;
        this.desc1 = desc1;
        this.desc2 = desc2;
        this.desc3 = desc3;
    }

    public Penyakit(int id, String kode, String name, String penanganan, String desc, String img) {
        this.id = id;
        this.kode = kode;
        this.name = name;
        this.penanganan = penanganan;
        this.desc = desc;
        this.img = img;
    }

    public Penyakit(String kode, String name, String penanganan, String desc, String img) {
        this.kode = kode;
        this.name = name;
        this.penanganan = penanganan;
        this.desc = desc;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPenanganan() {
        return penanganan;
    }

    public void setPenanganan(String penanganan) {
        this.penanganan = penanganan;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
