package com.turkiyedenemeleri.model;

/**
 * Created by celal on 28/04/2017.
 */

public class SınavBolum {
    String bölüm;
    String resimbase;
    int sorusayısı;

    public String getResimbase() {
        return resimbase;
    }

    public void setResimbase(String resimbase) {
        this.resimbase = resimbase;
    }

    public String getBölüm() {
        return bölüm;
    }

    public void setBölüm(String bölüm) {
        this.bölüm = bölüm;
    }

    public int getSorusayısı() {
        return sorusayısı;
    }

    public void setSorusayısı(int sorusayısı) {
        this.sorusayısı = sorusayısı;
    }
}
