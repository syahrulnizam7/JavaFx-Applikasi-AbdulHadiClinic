package fxml;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utils.ConnectionUtil;

public class DashboardController implements Initializable {

    @FXML
    private Label incomes;
    @FXML
    private Label transactions;
    @FXML
    private Label medicines;
    @FXML
    private Label categories;
    @FXML
    private Label tahun;
    @FXML
    private BorderPane borderpane;

    PreparedStatement preparedStatement;
    Connection connection;
    ResultSet rs;
    java.sql.Connection con = null;
    java.sql.PreparedStatement preStatement = null;
    
    int januari = 0;
    int februari = 0;
    int maret = 0;
    int april = 0;
    int mei = 0;
    int juni = 0;
    int juli = 0;
    int agustus = 0;
    int september = 0;
    int oktober = 0;
    int november = 0;
    int desember = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        incomes();
        transactions();
        medicines();
        categories();
        tahun();
        ambil();
        borderpane.setCenter(grafik());

    }
    
    private void tahun() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        tahun.setText(LocalDateTime.now().format(formatter));
}

    @FXML
    private void incomes() {
        String sql = "SELECT sum(total) FROM transaksi_detail";
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                incomes.setText(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
    }

    @FXML
    private void transactions() {
        String sql = "SELECT count(total) FROM transaksi_detail";
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                transactions.setText(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
    }

    @FXML
    private void medicines() {
        String sql = "SELECT sum(jumlah) FROM obat";
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                medicines.setText(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
    }

    @FXML
    private void categories() {
        String sql = "SELECT count(id_kategori) FROM kategori";
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                categories.setText(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
    }

    private void ambil() {
        String sql1 = "Select * from transaksi_detail WHERE tgl_transaksi LIKE '%-01-%'";
        String sql2 = "Select * from transaksi_detail WHERE tgl_transaksi LIKE '%-02-%'";
        String sql3 = "Select * from transaksi_detail WHERE tgl_transaksi LIKE '%-03-%'";
        String sql4 = "Select * from transaksi_detail WHERE tgl_transaksi LIKE '%-04-%'";
        String sql5 = "Select * from transaksi_detail WHERE tgl_transaksi LIKE '%-05-%'";
        String sql6 = "Select * from transaksi_detail WHERE tgl_transaksi LIKE '%-06-%'";
        String sql7 = "Select * from transaksi_detail WHERE tgl_transaksi LIKE '%-07-%'";
        String sql8 = "Select * from transaksi_detail WHERE tgl_transaksi LIKE '%-08-%'";
        String sql9 = "Select * from transaksi_detail WHERE tgl_transaksi LIKE '%-09-%'";
        String sql10 = "Select * from transaksi_detail WHERE tgl_transaksi LIKE '%-10-%'";
        String sql11 = "Select * from transaksi_detail WHERE tgl_transaksi LIKE '%-11-%'";
        String sql12 = "Select * from transaksi_detail WHERE tgl_transaksi LIKE '%-12-%'";

        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            ResultSet rs1 = state.executeQuery(sql1);
            while (rs1.next()) {
                januari=januari+1;
            }
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            ResultSet rs1 = state.executeQuery(sql2);
            while (rs1.next()) {
                februari=februari+1;
            }
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            ResultSet rs1 = state.executeQuery(sql3);
            while (rs1.next()) {
                maret=maret+1;
            }
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            ResultSet rs1 = state.executeQuery(sql4);
            while (rs1.next()) {
                april=april+1;
            }
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            ResultSet rs1 = state.executeQuery(sql5);
            while (rs1.next()) {
                mei=mei+1;
            }
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            ResultSet rs1 = state.executeQuery(sql6);
            while (rs1.next()) {
                juni=juni+1;
            }
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            ResultSet rs1 = state.executeQuery(sql7);
            while (rs1.next()) {
                juli=juli+1;
            }
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            ResultSet rs1 = state.executeQuery(sql8);
            while (rs1.next()) {
                agustus=agustus+1;
            }
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            ResultSet rs1 = state.executeQuery(sql9);
            while (rs1.next()) {
                september=september+1;
            }
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            ResultSet rs1 = state.executeQuery(sql10);
            while (rs1.next()) {
                oktober=oktober+1;
            }
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            ResultSet rs1 = state.executeQuery(sql11);
            while (rs1.next()) {
                november=november+1;
            }
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        try {
            Statement state = ConnectionUtil.conDB().createStatement();
            ResultSet rs1 = state.executeQuery(sql12);
            while (rs1.next()) {
                desember=desember+1;
            }
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        System.out.println(juni);
    }

    private BarChart grafik() {
        CategoryAxis xAxis = new CategoryAxis();

        NumberAxis yAxis = new NumberAxis();

        BarChart barChart = new BarChart(xAxis, yAxis);

        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("Data Pendapatan Per Bulan Tahun 2022");
        dataSeries1.getData().add(new XYChart.Data("Januari", januari));
        dataSeries1.getData().add(new XYChart.Data("Februari", februari));
        dataSeries1.getData().add(new XYChart.Data("Maret", maret));
        dataSeries1.getData().add(new XYChart.Data("April", april));
        dataSeries1.getData().add(new XYChart.Data("Mei", mei));
        dataSeries1.getData().add(new XYChart.Data("Juni", juni));
        dataSeries1.getData().add(new XYChart.Data("Juli", juli));
        dataSeries1.getData().add(new XYChart.Data("Agustus", agustus));
        dataSeries1.getData().add(new XYChart.Data("September", september));
        dataSeries1.getData().add(new XYChart.Data("Oktober", oktober));
        dataSeries1.getData().add(new XYChart.Data("November", november));
        dataSeries1.getData().add(new XYChart.Data("Desember", desember));
        barChart.getData().add(dataSeries1);
        
        Node n = barChart.lookup(".data0.chart-bar");
        n.setStyle("-fx-bar-fill: green");
        n = barChart.lookup(".data1.chart-bar");
        n.setStyle("-fx-bar-fill: green");
        n = barChart.lookup(".data2.chart-bar");
        n.setStyle("-fx-bar-fill: green");
        n = barChart.lookup(".data3.chart-bar");
        n.setStyle("-fx-bar-fill: green");
        n= barChart.lookup(".data4.chart-bar");
        n.setStyle("-fx-bar-fill: green");
        n= barChart.lookup(".data5.chart-bar");
        n.setStyle("-fx-bar-fill: green");
        n = barChart.lookup(".data6.chart-bar");
        n.setStyle("-fx-bar-fill: green");
        n= barChart.lookup(".data7.chart-bar");
        n.setStyle("-fx-bar-fill: green");
        n = barChart.lookup(".data8.chart-bar");
        n.setStyle("-fx-bar-fill: green");
        n = barChart.lookup(".data9.chart-bar");
        n.setStyle("-fx-bar-fill: green");
        n = barChart.lookup(".data10.chart-bar");
        n.setStyle("-fx-bar-fill: green");
        barChart.lookup(".data11.chart-bar");
        n.setStyle("-fx-bar-fill: green");

        barChart.disableProperty();

        return barChart;
    }
}
