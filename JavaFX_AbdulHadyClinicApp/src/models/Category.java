package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Category {
    private final StringProperty id_kategori;
    private final StringProperty kategori;

    public Category() {
        id_kategori = new SimpleStringProperty(this, "id_kategori");
        kategori = new SimpleStringProperty(this, "kategori");
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
}