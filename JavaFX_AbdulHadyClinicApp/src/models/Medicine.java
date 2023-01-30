package models;

import java.sql.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Medicine {

    private final StringProperty id_obat;
    private final StringProperty nama_obat;
    private final IntegerProperty jumlah;
    private final StringProperty id_kategori;
    private final StringProperty kategori;
    private final ObjectProperty tgl_kadaluarsa;
    private final IntegerProperty harga;

    public Medicine() {
        id_obat = new SimpleStringProperty(this, "id_obat");
        nama_obat = new SimpleStringProperty(this, "nama_obat");
        jumlah = new SimpleIntegerProperty(this, "jumlah");
        id_kategori = new SimpleStringProperty(this, "id_kategori");
        kategori = new SimpleStringProperty(this, "kategori");
        tgl_kadaluarsa = new SimpleObjectProperty(this, "tgl_kadaluarsa");
        harga = new SimpleIntegerProperty(this, "harga");
    }

    public StringProperty idProperty() {
        return id_obat;
    }

    public String getId() {
        return id_obat.get();
    }

    public void setId(String newId) {
        id_obat.set(newId);
    }

    public StringProperty nameProperty() {
        return nama_obat;
    }

    public String getName() {
        return nama_obat.get();
    }

    public void setName(String Nama_Obat) {
        nama_obat.set(Nama_Obat);
    }

    public IntegerProperty jumlahProperty() {
        return jumlah;
    }

    public int getJumlah() {
        return jumlah.get();
    }

    public void setJumlah(int Jumlah) {
        jumlah.set(Jumlah);
    }

    public StringProperty id_kategoriProperty() {
        return id_kategori;
    }

    public String getid_kategori() {
        return id_kategori.get();
    }

    public void setid_kategori(String ID_Kategori) {
        id_kategori.set(ID_Kategori);
    }
    
    public StringProperty kategoriProperty() {
        return kategori;
    }

    public String getKategori() {
        return kategori.get();
    }

    public void setKategori(String Kategori) {
        kategori.set(Kategori);
    }
    
    public ObjectProperty expProperty() {
        return tgl_kadaluarsa;
    }

    public Date getExp() {
        return (Date) tgl_kadaluarsa.get();
    }

    public void setExp(Date EXP) {
        tgl_kadaluarsa.set(EXP);
    }
    
    public IntegerProperty hargaProperty() {
        return harga;
    }

    public int getHarga() {
        return harga.get();
    }

    public void setHarga(int Harga) {
        harga.set(Harga);
    } 
}