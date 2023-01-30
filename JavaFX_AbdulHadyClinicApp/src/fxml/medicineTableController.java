package fxml;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Medicine;
import utils.ConnectionUtil;

public class medicineTableController implements Initializable {
    @FXML
    private TableView<Medicine> tblData;
    @FXML
    private TableColumn<Medicine, String> IDColumn;
    @FXML
    private TableColumn<Medicine, String> NameColumn;
    @FXML
    private TableColumn<Medicine, Integer> AmountColumn;
    @FXML
    private TableColumn<Medicine, String> CategoryIDColumn;
    @FXML
    private TableColumn<Medicine, String> CategoryColumn;
    @FXML
    private TableColumn<Medicine, Date> EXPColumn;
    @FXML
    private TableColumn<Medicine, Integer> PriceColumn;
    ResultSet rs;
    
    ObservableList<Medicine> medicine;

    public void table() {
        String sql = "SELECT * FROM obat";
        medicine = FXCollections.observableArrayList();
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            rs = state.executeQuery(sql);
            {
                while (rs.next()) {
                    Medicine st = new Medicine();
                    st.setId(rs.getString("id_obat"));
                    st.setName(rs.getString("nama_obat"));
                    st.setJumlah(Integer.parseInt(rs.getString("jumlah")));
                    st.setid_kategori(rs.getString("id_kategori"));
                    st.setKategori(rs.getString("kategori"));
                    st.setExp(rs.getDate("tgl_kadaluarsa"));
                    st.setHarga(Integer.parseInt(rs.getString("harga")));
                    medicine.add(st);
                }
            }
            tblData.setItems(medicine);
            IDColumn.setCellValueFactory(f -> f.getValue().idProperty());
            NameColumn.setCellValueFactory(f -> f.getValue().nameProperty());
            AmountColumn.setCellValueFactory(f -> f.getValue().jumlahProperty().asObject());
            CategoryIDColumn.setCellValueFactory(f -> f.getValue().id_kategoriProperty());
            CategoryColumn.setCellValueFactory(f -> f.getValue().kategoriProperty());
            EXPColumn.setCellValueFactory(f -> f.getValue().expProperty());
            PriceColumn.setCellValueFactory(f -> f.getValue().hargaProperty().asObject());
        } catch (SQLException ex) {
            Logger.getLogger(medicineController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table();
    }
}
