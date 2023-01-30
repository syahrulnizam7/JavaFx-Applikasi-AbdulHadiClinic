package models;

import java.sql.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Sales {
    private final StringProperty id_transaksi;
    private final StringProperty nama;
    private final StringProperty id_obat;
    private final StringProperty nama_obat;
    private final IntegerProperty harga;
    private final IntegerProperty jml_beli;
    private final IntegerProperty total;
    private final ObjectProperty tgl_transaksi;
    
    public Sales() {
        id_transaksi = new SimpleStringProperty(this, "id_transaksi");
        nama = new SimpleStringProperty(this, "nama");
        id_obat = new SimpleStringProperty(this, "id_obat");
        nama_obat = new SimpleStringProperty(this, "nama_obat");
        harga = new SimpleIntegerProperty(this, "harga");
        jml_beli = new SimpleIntegerProperty(this, "jml_beli");
        total = new SimpleIntegerProperty(this, "total");
        tgl_transaksi = new SimpleObjectProperty(this, "tgl_transaksi");

    }

    public StringProperty idProperty() {
        return id_transaksi;
    }

    public String getId() {
        return id_transaksi.get();
    }

    public void setId(String newId) {
        id_transaksi.set(newId);
    }

    public StringProperty namaProperty() {
        return nama;
    }

    public String getNama() {
        return nama.get();
    }

    public void setNama(String Nama) {
        nama.set(Nama);
    }

    public StringProperty id_obatProperty() {
        return id_obat;
    }

    public String getId_obat() {
        return id_obat.get();
    }

    public void setId_obat(String ID_Obat) {
        id_obat.set(ID_Obat);
    }

    public StringProperty namaObatProperty() {
        return nama_obat;
    }

    public String getNamaObat() {
        return nama_obat.get();
    }

    public void setNamaObat(String Nama_Obat) {
        nama_obat.set(Nama_Obat);
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

    public IntegerProperty jumlahProperty() {
        return jml_beli;
    }

    public int getJumlah() {
        return jml_beli.get();
    }

    public void setJumlah(int Jumlah) {
        jml_beli.set(Jumlah);
    }

    public IntegerProperty totalProperty() {
        return total;
    }

    public int getTotal() {
        return total.get();
    }

    public void setTotal(int Total) {
        total.set(Total);
    }

    public ObjectProperty tglProperty() {
        return tgl_transaksi;
    }

    public Date getTgl() {
        return (Date) tgl_transaksi.get();
    }

    public void setTgl(Date tgl) {
        tgl_transaksi.set(tgl);
    }
}