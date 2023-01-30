package fxml;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import models.Category;
import models.Medicine;
import utils.ConnectionUtil;

public class medicineController implements Initializable {

    @FXML
    Label status;
    @FXML
    TextField medicineID;
    @FXML
    TextField medicineName;
    @FXML
    TextField amount;
    @FXML
    TextField categoryID;
    @FXML
    TextField categoryName;
    @FXML
    DatePicker expirationDate;
    @FXML
    TextField price;
    @FXML
    TextField keyword;
    @FXML
    ChoiceBox<String> selectKey;

    @FXML
    private TableView<Category> tblData2;
    @FXML
    private TableColumn<Category, String> CategoryIDColumn2;
    @FXML
    private TableColumn<Category, String> CategoryColumn2;

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

    PreparedStatement preparedStatement;
    Connection connection;
    ResultSet rs;
    java.sql.Connection con = null;
    java.sql.PreparedStatement preStatement = null;

    public medicineController() {
        connection = (Connection) ConnectionUtil.conDB();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectKey.getItems().addAll("id_obat", "nama_obat");
        selectKey.getSelectionModel().select("id_obat");
        table();
    }

    @FXML
    private void save(MouseEvent event) {
        if (medicineID.getText().isEmpty() || medicineName.getText().isEmpty() || amount.getText().isEmpty() || categoryID.getText().isEmpty() || categoryName.getText().isEmpty() || expirationDate.getValue().toString().isEmpty() || price.getText().isEmpty()) {
            soundButton2();
            status.setText("Tambahkan rincian data!");
        } else {
            insertData();
            soundButton();
        }
    }

    private String insertData() {
        try {
            String st = "INSERT INTO obat (id_obat, nama_obat, jumlah, id_kategori, kategori, tgl_kadaluarsa, harga) VALUES (?,?,?,?,?,?,?)";
            preparedStatement = (PreparedStatement) connection.prepareStatement(st);
            preparedStatement.setString(1, medicineID.getText());
            preparedStatement.setString(2, medicineName.getText());
            preparedStatement.setString(3, amount.getText());
            preparedStatement.setString(4, categoryID.getText());
            preparedStatement.setString(5, categoryName.getText());
            preparedStatement.setString(6, expirationDate.getValue().toString());
            preparedStatement.setString(7, price.getText());
            preparedStatement.executeUpdate();
            table();
            soundButton();
            clearFields();
            return "Success";
        } catch (SQLException ex) {
            status.setText("Tambahkan rincian data!");
            return "";
        }
    }

    private void clearFields() {
        medicineID.clear();
        medicineName.clear();
        amount.clear();
        categoryID.clear();
        categoryName.clear();
        expirationDate.setValue(null);
        price.clear();
        status.setText("");
    }

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

        tblData.setRowFactory(tv -> {
            TableRow<Medicine> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                    int myIndex = tblData.getSelectionModel().getSelectedIndex();
                    medicineID.setText(String.valueOf(tblData.getItems().get(myIndex).getId()));
                    medicineName.setText(tblData.getItems().get(myIndex).getName());
                    amount.setText(String.valueOf(tblData.getItems().get(myIndex).getJumlah()));
                    categoryID.setText(tblData.getItems().get(myIndex).getid_kategori());
                    categoryName.setText(tblData.getItems().get(myIndex).getKategori());
                    expirationDate.setValue(tblData.getItems().get(myIndex).getExp().toLocalDate());
                    price.setText(String.valueOf(tblData.getItems().get(myIndex).getHarga()));
                }
            });
            return myRow;
        });
    }

    @FXML
    private void autoID(ActionEvent event) {
        String sql = "SELECT MAX(id_obat) FROM obat";
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                int a = rs.getInt(1);
                medicineID.setText(Integer.toString(a + 1));
            }
            soundButton();
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
    }

    @FXML
    private void autoID2(ActionEvent event) {
        String sql = "SELECT nama_kategori FROM kategori where id_kategori='" + categoryID.getText() + "'";
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                String a = rs.getString(1);
                categoryName.setText(a);
            }
            soundButton();
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
    }

    Stage dialogStage = new Stage();

    @FXML
    private void getCategory(ActionEvent event) {
        try {
            // dialogStage.initStyle(StageStyle.TRANSPARENT);
            Scene scene;
            scene = new Scene(FXMLLoader.load(getClass().getResource("CategoryTable.fxml")));
            // scene.setFill(Color.TRANSPARENT);
            dialogStage.setScene(scene);
            dialogStage.show();
        } catch (IOException ex) {
            Logger.getLogger(medicineController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void Update(ActionEvent event) {
        String id = medicineID.getText();
        String nama = medicineName.getText();
        int jumlah = Integer.parseInt(amount.getText());
        String idkategori = categoryID.getText();
        String kategori = categoryName.getText();
        String tgl = expirationDate.getValue().toString();
        String harga = price.getText();
        try {
            Statement st = ConnectionUtil.conDB().createStatement();
            st.executeUpdate("UPDATE obat SET nama_obat='" + nama
                    + "', jumlah='" + jumlah
                    + "', id_kategori='" + idkategori
                    + "', kategori='" + kategori
                    + "', tgl_kadaluarsa='" + tgl
                    + "', harga='" + harga
                    + "' WHERE id_obat='" + id + "';");
            table();
            JOptionPane.showMessageDialog(null, "Data berhasil diupdate!");
            st.close();
            clearFields();
            soundButton();
            tblData.refresh();
        } catch (SQLException ex) {
            soundButton2();
            JOptionPane.showMessageDialog(null, "Data gagal diupdate !");
        }
    }

    @FXML
    public void Delete(ActionEvent event) {
        String id = medicineID.getText();
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            state.executeUpdate("DELETE FROM obat WHERE id_obat='" + id + "';");
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus !");
            table();
            soundButton();
            tblData.refresh();
        } catch (SQLException ex) {
            soundButton2();
            JOptionPane.showMessageDialog(null, "Data Gagal Dihapus !");
        }
    }

    @FXML
    public void Search(ActionEvent event) {
        String key = keyword.getText();
        String pencarian = selectKey.getValue().toString();
        if (key.isEmpty()) {
            status.setTextFill(Color.TOMATO);
            status.setText("Please, input keyword!");
        } else {
            ObservableList<Medicine> medicineData = FXCollections.observableArrayList();
            String sql = "SELECT * FROM obat WHERE " + pencarian + " LIKE '%" + key + "%'";
            try {
                Statement state = ConnectionUtil.conDB().createStatement();
                rs = state.executeQuery(sql);
                while (rs.next()) {
                    Medicine st = new Medicine();
                    st.setId(rs.getString("id_obat"));
                    st.setName(rs.getString("nama_obat"));
                    st.setJumlah(Integer.parseInt(rs.getString("jumlah")));
                    st.setid_kategori(rs.getString("id_kategori"));
                    st.setKategori(rs.getString("kategori"));
                    st.setExp(rs.getDate("tgl_kadaluarsa"));
                    st.setHarga(Integer.parseInt(rs.getString("harga")));
                    medicineData.add(st);
                }
                tblData.setItems(medicineData);
                soundButton();
                tblData.refresh();
            } catch (SQLException ex) {
                soundButton2();
                status.setText("Please, enter correct keyword!");
            }
        }
    }

    @FXML
    private void toMedicine(MouseEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("MedicineCategory.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void toCategory(MouseEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();;
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("MedicineCategory.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void toSales(MouseEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        //stage.setMaximized(true);
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