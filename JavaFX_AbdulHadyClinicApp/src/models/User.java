package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private final StringProperty id_petugas;
    private final StringProperty nama_petugas;
    private final StringProperty username;
    private final StringProperty password;

    public User() {
        id_petugas = new SimpleStringProperty(this, "id_petugas");
        nama_petugas = new SimpleStringProperty(this, "nama_petugas");
        username = new SimpleStringProperty(this, "username");
        password = new SimpleStringProperty(this, "password");
    }

    public StringProperty id_petugasProperty() {
        return id_petugas;
    }

    public String getid_petugas() {
        return id_petugas.get();
    }

    public void setid_petugas(String ID_Petugas) {
        id_petugas.set(ID_Petugas);
    }

    public StringProperty namaPetugasProperty() {
        return nama_petugas;
    }

    public String getNamaPetugas() {
        return nama_petugas.get();
    }

    public void setNamaPetugas(String Petugas) {
        nama_petugas.set(Petugas);
    }
    
    public StringProperty usernameProperty() {
        return username;
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String Username) {
        username.set(Username);
    }
    
    public StringProperty passwordProperty() {
        return password;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String Username) {
        password.set(Username);
    }
}