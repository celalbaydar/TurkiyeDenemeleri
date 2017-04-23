package com.turkiyedenemeleri.model;

/**
 * Created by celal on 23/04/2017.
 */

public class User {
    String kullaniciadi;
    String telefon;
    String resim_url;
    int suspended;
    int il;
    int cinsiyet;

    public String getKullaniciadi() {
        return kullaniciadi;
    }

    public void setKullaniciadi(String kullaniciadi) {
        this.kullaniciadi = kullaniciadi;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getResim_url() {
        return resim_url;
    }

    public void setResim_url(String resim_url) {
        this.resim_url = resim_url;
    }

    public int getSuspended() {
        return suspended;
    }

    public void setSuspended(int suspended) {
        this.suspended = suspended;
    }

    public int getIl() {
        return il;
    }

    public void setIl(int il) {
        this.il = il;
    }

    public int getCinsiyet() {
        return cinsiyet;
    }

    public void setCinsiyet(int cinsiyet) {
        this.cinsiyet = cinsiyet;
    }
}
