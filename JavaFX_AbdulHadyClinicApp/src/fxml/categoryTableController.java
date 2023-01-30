package fxml;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import models.Category;
import utils.ConnectionUtil;

public class categoryTableController implements Initializable {
    @FXML
    TableView<Category> tblDatas;
    @FXML
    TableView<Category> tblobat;
    @FXML
    TableColumn<Category, String> CategoryIDColumns;
    @FXML
    TableColumn<Category, String> CategoryColumns;

    PreparedStatement preparedStatement;
    Connection connection;
    ResultSet rs;

    ObservableList<Category> category;
    
    DisplayDatabase tabelobat = new DisplayDatabase();

    public void table2() {
        String sql = "SELECT * FROM kategori";
        category = FXCollections.observableArrayList();
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            rs = state.executeQuery(sql);
            {
                while (rs.next()) {
                    Category cate = new Category();
                    cate.setid_kategori(rs.getString("id_kategori"));
                    cate.setKategori(rs.getString("nama_kategori"));
                    category.add(cate);
                }
            }
            tblDatas.setItems(category);
            CategoryIDColumns.setCellValueFactory(f -> f.getValue().id_kategoriProperty());
            CategoryColumns.setCellValueFactory(f -> f.getValue().kategoriProperty());
        } catch (SQLException ex) {
            Logger.getLogger(categoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table2();
    }
    
    @FXML
    public void obat(ActionEvent event) {
            tabelobat.buildData(tblobat, "Select * from transaksi_detail;");
            tblobat.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
}}