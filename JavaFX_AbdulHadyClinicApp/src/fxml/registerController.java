package fxml;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import utils.ConnectionUtil;

public class registerController implements Initializable {

    @FXML
    private Button getId;

    @FXML
    TextField getYourId;

    @FXML
    TextField yourName;

    @FXML
    TextField username;

    @FXML
    PasswordField password;

    @FXML
    private Button signUp;

    @FXML
    private Label status;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (con == null) {
            System.out.println("SERVER OFF");
        } else {
            System.out.println("SERVER ON");
        }
    }

    public registerController() {
        con = ConnectionUtil.conDB();
    }

    ResultSet Rs;

    @FXML
    private void autoID(ActionEvent event) {
        String sql = "SELECT MAX(id_petugas) FROM petugas";
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            Rs = state.executeQuery(sql);
            while (Rs.next()) {
                int a = Rs.getInt(1);
                getYourId.setText(Integer.toString(a + 1));
            }
        } catch (Exception e) {
            System.out.println("" + e.getMessage());
        }
    }

    @FXML
    private void save(MouseEvent event) throws IOException {
        if (getYourId.getText().isEmpty() || yourName.getText().isEmpty() || username.getText().isEmpty() || password.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Silahkan isi detail data!");
        } else {
            newUser();
            JOptionPane.showMessageDialog(null, "Register berhasil!");
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Login.fxml")));
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    private String newUser() {
        String sql = "INSERT INTO petugas (id_petugas, nama_petugas, username, password) VALUES (?,?,?,?)";
        try {
            preparedStatement = (PreparedStatement) con.prepareStatement(sql);
            preparedStatement.setString(1, getYourId.getText());
            preparedStatement.setString(2, yourName.getText());
            preparedStatement.setString(3, username.getText());
            preparedStatement.setString(4, password.getText());
            preparedStatement.executeUpdate();
            return "Success";
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return "Exception";
        }
    }
    
    @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void toLogin(MouseEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Login.fxml")));
        stage.setScene(scene);
        stage.show();
    }
}
