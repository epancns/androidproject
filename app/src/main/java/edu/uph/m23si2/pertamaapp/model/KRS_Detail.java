package edu.uph.m23si2.pertamaapp.model;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class KRS_Detail extends RealmObject {
    @PrimaryKey
    private int krs_detailID;
    private KRS krs;
    private int nilai;
    private Matakuliah matakuliah;
    private String jam_masuk;

    public KRS_Detail(){

    }

    public KRS_Detail(int krs_detailID, KRS krs, int nilai, Matakuliah matakuliah, String jam_masuk) {
        this.krs_detailID = krs_detailID;
        this.krs = krs;
        this.nilai = nilai;
        this.matakuliah = matakuliah;
        this.jam_masuk = jam_masuk;
    }

    public int getKrs_detailID() {
        return krs_detailID;
    }

    public void setKrs_detailID(int krs_detailID) {
        this.krs_detailID = krs_detailID;
    }

    public KRS getKrs() {
        return krs;
    }

    public void setKrs(KRS krs) {
        this.krs = krs;
    }

    public int getNilai() {
        return nilai;
    }

    public void setNilai(int nilai) {
        this.nilai = nilai;
    }

    public Matakuliah getMatakuliah() {
        return matakuliah;
    }

    public void setMatakuliah(Matakuliah matakuliah) {
        this.matakuliah = matakuliah;
    }

    public String getJam_masuk() {
        return jam_masuk;
    }

    public void setJam_masuk(String jam_masuk) {
        this.jam_masuk = jam_masuk;
    }
}
