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
import javax.swing.JOptionPane;
import models.Sales;
import utils.ConnectionUtil;

public class salesController implements Initializable {

    @FXML
    Label status;
    @FXML
    TextField salesID;
    @FXML
    TextField customerName;
    @FXML
    TextField medicineID;
    @FXML
    TextField medicineName;
    @FXML
    TextField price;
    @FXML
    TextField amount;
    @FXML
    TextField total;
    @FXML
    DatePicker transDate;
    @FXML
    TextField keyword;
    @FXML
    ChoiceBox<String> selectKey;
    @FXML
    private TableView<Sales> tblData;
    @FXML
    private TableColumn<Sales, String> IDColumn;
    @FXML
    private TableColumn<Sales, String> NameColumn;
    @FXML
    private TableColumn<Sales, String> IDMedicineColumn;
    @FXML
    private TableColumn<Sales, String> MedicineNameColumn;
    @FXML
    private TableColumn<Sales, Integer> PriceColumn;
    @FXML
    private TableColumn<Sales, Integer> AmountColumn;
    @FXML
    private TableColumn<Sales, Integer> TotalColumn;
    @FXML
    private TableColumn<Sales, Date> DateColumn;

    PreparedStatement preparedStatement;
    Connection connection;
    ResultSet rs;

    public salesController() {
        connection = (Connection) ConnectionUtil.conDB();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectKey.getItems().addAll("id_transaksi", "nama");
        selectKey.getSelectionModel().select("id_transaksi");
        table();
    }
    Stage dialogStage = new Stage();

    @FXML
    private void getObat(ActionEvent event) {
        try {
            // dialogStage.initStyle(StageStyle.TRANSPARENT);
            Scene scene;
            scene = new Scene(FXMLLoader.load(getClass().getResource("MedicineTable.fxml")));
            // scene.setFill(Color.TRANSPARENT);
            dialogStage.setScene(scene);
            dialogStage.show();
        } catch (IOException ex) {
            Logger.getLogger(medicineController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void autoID(ActionEvent event) {
        ResultSet Rs;
        String sql = "SELECT MAX(id_transaksi) FROM transaksi_detail";
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            Rs = state.executeQuery(sql);
            while (Rs.next()) {
                int a = Rs.getInt(1);
                salesID.setText(Integer.toString(a + 1));
            }
            soundButton();
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
    }

    @FXML
    private void getMedicine(ActionEvent event) {
        String medicine = medicineID.getText();
        if (medicine.isEmpty()) {
            // soundButton2();
            status.setTextFill(Color.TOMATO);
            status.setText("Please, input Medicine ID!");
        } else {
            // soundButton();
            String sql = "SELECT * FROM obat WHERE id_obat = '" + medicine + "'";
            try {
                Statement state = ConnectionUtil.conDB().createStatement();
                ResultSet Rs = state.executeQuery(sql);
                while (Rs.next()) {
                    String a = Rs.getString("nama_obat");
                    medicineName.setText(a);
                    String b = Rs.getString("harga");
                    price.setText(b);
                    soundButton();
                    status.setText("Successs get medicine ID!");
                }
            } catch (SQLException ex) {
                soundButton2();
                status.setText("Please, enter correct medicine ID!");
            }
        }
    }

    @FXML
    private void save(ActionEvent event) throws SQLException {
        int stokBaru = cekStokMedicine() - Integer.parseInt(amount.getText());
        String medicine = medicineID.getText();
        if (salesID.getText().isEmpty() || customerName.getText().isEmpty()) {
            soundButton2();
            status.setText("Tambahkan rincian data!");
        } else {
            Statement state = ConnectionUtil.conDB().createStatement();
            state.executeUpdate("UPDATE obat SET jumlah ='" + stokBaru + "' WHERE id_obat = '" + medicine + "'");
            soundButton();
            insertData();
        }
    }

    private int cekStokMedicine() {
        String medicine = medicineID.getText();
        int stokLama = 0;
        String sql = "SELECT jumlah FROM obat WHERE id_obat = '" + medicine + "'";
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            ResultSet Rs = state.executeQuery(sql);
            while (Rs.next()) {
                stokLama = Integer.parseInt(Rs.getString("jumlah"));
            }
        } catch (SQLException ex) {
            System.out.println("" + ex.getMessage());
        }
        return stokLama;
    }
    
    private int cekStokSales() {
        String sales = salesID.getText();
        int stokLama = 0;
        String sql = "SELECT jml_beli FROM transaksi_detail WHERE id_transaksi = '" + sales + "'";
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            ResultSet Rs = state.executeQuery(sql);
            while (Rs.next()) {
                stokLama = Integer.parseInt(Rs.getString("jml_beli"));
            }
        } catch (SQLException ex) {
            System.out.println("" + ex.getMessage());
        }
        return stokLama;
    }
    
    private String insertData() {
        try {
            String st = "INSERT INTO transaksi_detail (id_transaksi, nama, id_obat, nama_obat, harga, jml_beli, total, tgl_transaksi) VALUES (?,?,?,?,?,?,?,?)";
            preparedStatement = (PreparedStatement) connection.prepareStatement(st);
            preparedStatement.setString(1, salesID.getText());
            preparedStatement.setString(2, customerName.getText());
            preparedStatement.setString(3, medicineID.getText());
            preparedStatement.setString(4, medicineName.getText());
            preparedStatement.setString(5, price.getText());
            preparedStatement.setString(6, amount.getText());
            preparedStatement.setString(7, total.getText());
            preparedStatement.setString(8, transDate.getValue().toString());
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
        salesID.clear();
        customerName.clear();
        medicineID.clear();
        medicineName.clear();
        price.clear();
        amount.clear();
        total.clear();
        transDate.setValue(null);
        status.setText("");
    }

    public void table() {
        String sql = "SELECT * FROM transaksi_detail";
        ObservableList<Sales> sales = FXCollections.observableArrayList();
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            rs = state.executeQuery(sql);
            {
                while (rs.next()) {
                    Sales sls = new Sales();
                    sls.setId(rs.getString("id_transaksi"));
                    sls.setNama(rs.getString("nama"));
                    sls.setId_obat(rs.getString("id_obat"));
                    sls.setNamaObat(rs.getString("nama_obat"));
                    sls.setHarga(Integer.parseInt(rs.getString("harga")));
                    sls.setJumlah(Integer.parseInt(rs.getString("jml_beli")));
                    sls.setTotal(Integer.parseInt(rs.getString("total")));
                    sls.setTgl(rs.getDate("tgl_transaksi"));
                    sales.add(sls);
                }
            }
            tblData.setItems(sales);
            IDColumn.setCellValueFactory(f -> f.getValue().idProperty());
            NameColumn.setCellValueFactory(f -> f.getValue().namaProperty());
            IDMedicineColumn.setCellValueFactory(f -> f.getValue().id_obatProperty());
            MedicineNameColumn.setCellValueFactory(f -> f.getValue().namaObatProperty());
            PriceColumn.setCellValueFactory(f -> f.getValue().hargaProperty().asObject());
            AmountColumn.setCellValueFactory(f -> f.getValue().jumlahProperty().asObject());
            TotalColumn.setCellValueFactory(f -> f.getValue().totalProperty().asObject());
            DateColumn.setCellValueFactory(f -> f.getValue().tglProperty());
        } catch (SQLException ex) {
            Logger.getLogger(salesController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tblData.setRowFactory(tv -> {
            TableRow<Sales> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                    int myIndex = tblData.getSelectionModel().getSelectedIndex();
                    salesID.setText(String.valueOf(tblData.getItems().get(myIndex).getId()));
                    customerName.setText(tblData.getItems().get(myIndex).getNama());
                    medicineID.setText(tblData.getItems().get(myIndex).getId_obat());
                    medicineName.setText(tblData.getItems().get(myIndex).getNamaObat());
                    price.setText(String.valueOf(tblData.getItems().get(myIndex).getHarga()));
                    amount.setText(String.valueOf(tblData.getItems().get(myIndex).getJumlah()));
                    total.setText(String.valueOf(tblData.getItems().get(myIndex).getTotal()));
                    transDate.setValue(tblData.getItems().get(myIndex).getTgl().toLocalDate());
                }
            });
            return myRow;
        });
    }

    @FXML
    public void Update(ActionEvent event) {
        String id = salesID.getText();
        String nama = customerName.getText();
        String id_obat = medicineID.getText();
        String nama_obat = medicineName.getText();
        int harga = Integer.parseInt(price.getText());
        int jumlah = Integer.parseInt(amount.getText());
        int totalBeli = Integer.parseInt(total.getText());
        String tgl = transDate.getValue().toString();
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            if (cekStokSales() > jumlah) {
                int stok1 = cekStokSales() - jumlah;
                int stokBaru = cekStokMedicine() + stok1;
                state.executeUpdate("UPDATE obat SET jumlah ='" + stokBaru + "' WHERE id_obat = '" + id_obat + "'");
            } else {
                int stok2 = cekStokSales() - jumlah;
                int stokBaru = cekStokMedicine() + stok2;
                state.executeUpdate("UPDATE obat SET jumlah ='" + stokBaru + "' WHERE id_obat = '" + id_obat + "'");
            }
            state.executeUpdate("UPDATE transaksi_detail SET nama='" + nama
                    + "', id_obat='" + id_obat
                    + "', nama_obat='" + nama_obat
                    + "', harga='" + harga
                    + "', jml_beli='" + jumlah
                    + "', total='" + totalBeli
                    + "', tgl_transaksi='" + tgl
                    + "' WHERE id_transaksi='" + id + "';");
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
        String id = salesID.getText();
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            state.executeUpdate("DELETE FROM transaksi_detail WHERE id_transaksi='" + id + "';");
            soundButton();
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus !");
            table();
            tblData.refresh();
            clearFields();
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
            table();
        } else {
            ObservableList<Sales> salesData = FXCollections.observableArrayList();
            String sql = "SELECT * FROM transaksi_detail WHERE " + pencarian + " LIKE '%" + key + "%'";
            try {
                Statement state = ConnectionUtil.conDB().createStatement();
                rs = state.executeQuery(sql);
                while (rs.next()) {
                    Sales sls = new Sales();
                    sls.setId(rs.getString("id_transaksi"));
                    sls.setNama(rs.getString("nama"));
                    sls.setId_obat(rs.getString("id_obat"));
                    sls.setNamaObat(rs.getString("nama_obat"));
                    sls.setHarga(Integer.parseInt(rs.getString("harga")));
                    sls.setJumlah(Integer.parseInt(rs.getString("jml_beli")));
                    sls.setTotal(Integer.parseInt(rs.getString("total")));
                    sls.setTgl(rs.getDate("tgl_transaksi"));
                    salesData.add(sls);
                }
                status.setTextFill(Color.GREEN);
                soundButton();
                status.setText("Searching Success!");
                tblData.setItems(salesData);
                tblData.refresh();
            } catch (SQLException ex) {
                soundButton2();
                status.setText("Please, enter correct keyword!");
            }
        }
    }

    @FXML
    private void getTotal(ActionEvent event) {
        String medicine = medicineID.getText();
        if (medicine.isEmpty()) {
            //  soundButton2();
            status.setTextFill(Color.TOMATO);
            status.setText("Please, get data!");
        } else {
            // soundButton();
            String sql = "SELECT * FROM obat WHERE id_obat = '" + medicine + "'";
            try {
                Statement state = ConnectionUtil.conDB().createStatement();
                ResultSet Rs = state.executeQuery(sql);
                while (Rs.next()) {
                    int a = Integer.parseInt(Rs.getString("harga"));
                    int b = Integer.parseInt(amount.getText());
                    int hasil = a * b;
                    String Total = String.valueOf(hasil);
                    total.setText(Total);
                    soundButton();
                    status.setText("Successs get medicine ID!");
                }
            } catch (SQLException ex) {
                soundButton2();
                status.setText("Please, enter amount!");
            }
        }
    }
    
    @FXML
    private void toMedicine(MouseEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        //stage.setMaximized(true);
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
