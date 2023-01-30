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
import models.User;
import utils.ConnectionUtil;

public class settingController implements Initializable {
    @FXML
    Label status;
    @FXML
    TextField id_petugas;  
    @FXML
    TextField nama_petugas;
    @FXML
    TextField username;
    @FXML
    TextField password;
    @FXML
    private TableView<User> tblData;
    @FXML
    private TableColumn<User, String> IDPetugasColumn;
    @FXML
    private TableColumn<User, String> NamaPetugasColumn;
    @FXML
    private TableColumn<User, String> UsernameColumn;
    @FXML
    private TableColumn<User, String> PasswordColumn;
    
    PreparedStatement preparedStatement;
    Connection connection;
    ResultSet rs;

    public settingController() {
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
    private void autoID(ActionEvent event){
        String sql = "SELECT MAX(id_petugas) FROM petugas";
        try{
            Statement state  = ConnectionUtil.conDB().createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()){
                int a = rs.getInt(1);
                id_petugas.setText(Integer.toString(a + 1));
            }
            soundButton();
        }catch (SQLException e){
            System.out.println(""+ e.getMessage());
            soundButton2();
        }
    }
    
    @FXML
    private void save(ActionEvent event) {
        if (id_petugas.getText().isEmpty() || nama_petugas.getText().isEmpty() || username.getText().isEmpty() || password.getText().isEmpty()) {
            soundButton2();
            status.setText("Tambahkan rincian data!");
        } else {
            insertData();
            soundButton();
        }
    }
    
    private String insertData() {
        try {
            String st = "INSERT INTO petugas (id_petugas, nama_petugas, username, password) VALUES (?,?,?,?)";
            preparedStatement = (PreparedStatement) connection.prepareStatement(st);
            preparedStatement.setString(1, id_petugas.getText());
            preparedStatement.setString(2, nama_petugas.getText());
            preparedStatement.setString(3, username.getText());
            preparedStatement.setString(4, password.getText());
            preparedStatement.executeUpdate();
            soundButton();
            table();
            clearFields();
            return "Success";
        } catch (SQLException ex) {
            soundButton2();
            status.setText("Tambahkan rincian data!");
            return "";
        }
    }
    
    private void clearFields() {
        id_petugas.clear();
        nama_petugas.clear();
        username.clear();
        password.clear();
        status.setText("");
    }
    
    ObservableList<User> Petugas;
    public void table() {
        String sql = "SELECT * FROM petugas";
        Petugas = FXCollections.observableArrayList();
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            rs = state.executeQuery(sql);
            {
                while (rs.next()) {
                    User user = new User();
                    user.setid_petugas(rs.getString("id_petugas"));
                    user.setNamaPetugas(rs.getString("nama_petugas"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    Petugas.add(user);
                }
            }
            tblData.setItems(Petugas);
            IDPetugasColumn.setCellValueFactory(f -> f.getValue().id_petugasProperty());
            NamaPetugasColumn.setCellValueFactory(f -> f.getValue().namaPetugasProperty());
            UsernameColumn.setCellValueFactory(f -> f.getValue().usernameProperty());
            PasswordColumn.setCellValueFactory(f -> f.getValue().passwordProperty());
        } catch (SQLException ex) {
            Logger.getLogger(categoryController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tblData.setRowFactory(tv -> {
            TableRow<User> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                    int myIndex = tblData.getSelectionModel().getSelectedIndex();
                    id_petugas.setText(tblData.getItems().get(myIndex).getid_petugas());
                    nama_petugas.setText(tblData.getItems().get(myIndex).getNamaPetugas());
                    username.setText(tblData.getItems().get(myIndex).getUsername());
                    password.setText(tblData.getItems().get(myIndex).getPassword());
                }
            });
            return myRow;
        });
    }
    
    @FXML
    public void Update(ActionEvent event) {
        String id = id_petugas.getText();
        String nama = nama_petugas.getText();
        String user = username.getText();
        String pass = password.getText();
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            state.executeUpdate("UPDATE petugas SET nama_petugas='" + nama
                    + "', username='" + user
                    + "', password='" + pass
                    + "' WHERE id_petugas='" + id + "';");
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
        String id = id_petugas.getText();
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            state.executeUpdate("DELETE FROM petugas WHERE id_petugas='" + id + "';");
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
    public void  soundButton()
    {
    tone.play();
    }
    public void  soundButton2()
    {
    tone2.play();
    }
}