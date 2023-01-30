package fxml;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import models.Category;
import utils.ConnectionUtil;

public class categoryController implements Initializable {

    @FXML
    Label status;
    @FXML
    TextField categoryID;
    @FXML
    TextField categoryName;
    @FXML
    private TableView<Category> tblData;
    @FXML
    private TableColumn<Category, String> CategoryIDColumn;
    @FXML
    private TableColumn<Category, String> CategoryColumn;

    PreparedStatement preparedStatement;
    Connection connection;
    ResultSet rs;

    public categoryController() {
        connection = (Connection) ConnectionUtil.conDB();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        table();
    }

    @FXML
    private void toMedicine(MouseEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Medicine.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void toCategory(MouseEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("MedicineCategory.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void toSales(MouseEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Sales.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void toSelectReport(MouseEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("SelectReport.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void toChart(MouseEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Chart.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void toDashboard(MouseEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Dashboard.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void autoID(ActionEvent event) {
        String sql = "SELECT MAX(id_kategori) FROM kategori";
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                int a = rs.getInt(1);
                categoryID.setText(Integer.toString(a + 1));
            }
            soundButton();
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
    }

    @FXML
    private void save(ActionEvent event) {
        //check if not empty
        if (categoryID.getText().isEmpty() || categoryName.getText().isEmpty()) {
            soundButton2();
            status.setText("Tambahkan rincian data!");
        } else {
            insertData();
            soundButton();
        }
    }

    private String insertData() {
        try {
            String st = "INSERT INTO kategori (id_kategori, nama_kategori) VALUES (?,?)";
            preparedStatement = (PreparedStatement) connection.prepareStatement(st);
            preparedStatement.setString(1, categoryID.getText());
            preparedStatement.setString(2, categoryName.getText());
            preparedStatement.executeUpdate();

            table();
            clearFields();
            return "Success";
        } catch (SQLException ex) {
            status.setText("Tambahkan rincian data!");
            return "";
        }
    }

    private void clearFields() {
        categoryID.clear();
        categoryName.clear();
    }

    ObservableList<Category> kategori;

    public void table() {
        String sql = "SELECT * FROM kategori";
        kategori = FXCollections.observableArrayList();
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            rs = state.executeQuery(sql);
            {
                while (rs.next()) {
                    Category cate = new Category();
                    cate.setid_kategori(rs.getString("id_kategori"));
                    cate.setKategori(rs.getString("nama_kategori"));
                    kategori.add(cate);
                }
            }
            tblData.setItems(kategori);
            CategoryIDColumn.setCellValueFactory(f -> f.getValue().id_kategoriProperty());
            CategoryColumn.setCellValueFactory(f -> f.getValue().kategoriProperty());
        } catch (SQLException ex) {
            Logger.getLogger(categoryController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tblData.setRowFactory(tv -> {
            TableRow<Category> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                    int myIndex = tblData.getSelectionModel().getSelectedIndex();
                    categoryID.setText(tblData.getItems().get(myIndex).getid_kategori());
                    categoryName.setText(tblData.getItems().get(myIndex).getKategori());
                }
            });
            return myRow;
        });
    }

    @FXML
    public void Update(ActionEvent event) {
        String idkategori = categoryID.getText();
        String kategori = categoryName.getText();
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            state.executeUpdate("UPDATE kategori SET nama_kategori='" + kategori
                    + "' WHERE id_kategori='" + idkategori + "';");
            table();
            soundButton();
            JOptionPane.showMessageDialog(null, "Data berhasil diupdate!");
            state.close();
            clearFields();
            tblData.refresh();
        } catch (SQLException ex) {
            soundButton2();
            JOptionPane.showMessageDialog(null, "Data gagal diupdate !");
        }
    }

    @FXML
    public void Delete(ActionEvent event) {
        String id = categoryID.getText();
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            state.executeUpdate("DELETE FROM kategori WHERE id_kategori='" + id + "';");
            soundButton();
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus !");
            table();
            clearFields();
            tblData.refresh();
        } catch (SQLException ex) {
            soundButton2();
            JOptionPane.showMessageDialog(null, "Data Gagal Dihapus !");
        }
    }

    @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    final AudioClip tone = new AudioClip(getClass().getResource("/images/sfxbutton.mp3").toString());
    final AudioClip tone2 = new AudioClip(getClass().getResource("/images/sfxwrong.mp3").toString());

    public void soundButton() {
        tone.play();
    }

    public void soundButton2() {
        tone2.play();
    }
}