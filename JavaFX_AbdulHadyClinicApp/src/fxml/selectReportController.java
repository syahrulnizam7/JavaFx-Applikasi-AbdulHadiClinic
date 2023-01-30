package fxml;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utils.ConnectionUtil;

public class selectReportController implements Initializable {

    @FXML
    private ChoiceBox choice;
    @FXML
    private TableView<?> tabel;
    @FXML
    private BorderPane borderpane;

    PreparedStatement preparedStatement;
    Connection connection;
    ResultSet rs;
    java.sql.Connection con = null;
    java.sql.PreparedStatement preStatement = null;

    Stage Stage = new Stage();

    DisplayDatabase tabell = new DisplayDatabase();

    public selectReportController() {
        connection = (Connection) ConnectionUtil.conDB();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        choice.getItems().addAll("Medicine", "Sales");
        choice.getSelectionModel().select("Sales");
        tabell.buildData(tabel, "Select * from transaksi_detail;");
        tabel.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    @FXML
    public void Search(ActionEvent event) {
        if (choice.getValue().equals("Sales")) {
            tabell.buildData(tabel, "Select * from transaksi_detail;");
            tabel.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } else {
            tabell.buildData(tabel, "Select * from obat;");
            tabel.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }
    }

    public void report(ActionEvent event) {
        printSetup(tabel, Stage);
    }

    private void printSetup(Node node, Stage owner) {
        // Create the PrinterJob		
        PrinterJob job = PrinterJob.createPrinterJob();

        if (job == null) {
            return;
        }

        // Show the print setup dialog
        boolean proceed = job.showPrintDialog(owner);

        if (proceed) {
            print(job, node);
        }
    }

    private void print(PrinterJob job, Node node) {
        boolean printed = job.printPage(node);

        if (printed) {
            job.endJob();
        }
    }

}
