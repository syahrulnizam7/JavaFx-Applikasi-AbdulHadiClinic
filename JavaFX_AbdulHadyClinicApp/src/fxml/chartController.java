package fxml;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Sales;
import utils.ConnectionUtil;

public class chartController implements Initializable {

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
        ambil();
        borderpane.setCenter(grafik());
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

    public void barChart() {
        String sql = "SELECT * FROM transaksi_detail";
        ObservableList<Sales> sales = FXCollections.observableArrayList();
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
                sales.add(sls);
            }
            rs.close();
            CategoryAxis xAxis = new CategoryAxis();
            xAxis.setLabel("Names");
            NumberAxis yAxis = new NumberAxis();
            yAxis.setLabel("Salaries");
            
            BarChart barChart = new BarChart(xAxis, yAxis);
            XYChart.Series dataSeries1 = new XYChart.Series();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
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
        //stage.setMaximized(true);
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
        //stage.setMaximized(true);
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("SelectReport.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void toChart(MouseEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        //stage.setMaximized(true);
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
    
    
}
