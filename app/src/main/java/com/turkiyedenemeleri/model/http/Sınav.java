package com.turkiyedenemeleri.model.http;

/**
 * Created by celal on 27/04/2017.
 */

public class Sınav {
    String sınavid;
    String sınavturu;
    String aciklama;
    String baslama;
    String bitis;
    String turadi;
    String turAciklama;
    String ilksınavtarih;
    String kayıtlıkullanıcı;

    public String getSınavid() {
        return sınavid;
    }

    public void setSınavid(String sınavid) {
        this.sınavid = sınavid;
    }

    public String getSınavturu() {
        return sınavturu;
    }

    public void setSınavturu(String sınavturu) {
        this.sınavturu = sınavturu;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getBaslama() {
        return baslama;
    }

    public void setBaslama(String baslama) {
        this.baslama = baslama;
    }

    public String getBitis() {
        return bitis;
    }

    public void setBitis(String bitis) {
        this.bitis = bitis;
    }

    public String getTuradi() {
        return turadi;
    }

    public void setTuradi(String turadi) {
        this.turadi = turadi;
    }

    public String getTurAciklama() {
        return turAciklama;
    }

    public void setTurAciklama(String turAciklama) {
        this.turAciklama = turAciklama;
    }

    public String getIlksınavtarih() {
        return ilksınavtarih;
    }

    public void setIlksınavtarih(String ilksınavtarih) {
        this.ilksınavtarih = ilksınavtarih;
    }

    public String getKayıtlıkullanıcı() {
        return kayıtlıkullanıcı;
    }

    public void setKayıtlıkullanıcı(String kayıtlıkullanıcı) {
        this.kayıtlıkullanıcı = kayıtlıkullanıcı;
    }
}
