package admins;

import information.InformationController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import dbUtil.dbConnection;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.sound.sampled.Port;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminsController implements Initializable {

    public TableView<PortfolioData> _studenttable;
    public TableColumn<PortfolioData, String> _namecolumn;
    public TableColumn<PortfolioData, String> _idcolumn;
    public Label nooneselected;

    private dbConnection dc;
    private ObservableList<PortfolioData> portfolioData;


    private String sqlStudentData = "SELECT * FROM Student";

    private static String IDpass;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Established connection to the database and calls loadAllData()
        this.dc=new dbConnection();
        loadAllData();
    }

    public void loadAllData() {
        try {
            Connection conn = dbConnection.getConnection();
            this.portfolioData = FXCollections.observableArrayList();

            // Executes the SQL-query sqlStudentData and adds all the data to the ObservableList portfolioData
            ResultSet rs2 = conn.createStatement().executeQuery(sqlStudentData);
            while (rs2.next()) {
                // The reasoning behind setting course, grade, city and avg to null is
                // so we can use the same constructor without retrieving irrelevant data
                this.portfolioData.add(new PortfolioData(rs2.getString(3), rs2.getString(1), null, null, null, null));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        // adds the portfolioData to the relevanct columns
        this._idcolumn.setCellValueFactory(new PropertyValueFactory<PortfolioData, String>("ID"));
        this._namecolumn.setCellValueFactory(new PropertyValueFactory<PortfolioData, String>("Name"));

        this._studenttable.setItems(null);
        this._studenttable.setItems(this.portfolioData);
    }



    public void moreInformation(ActionEvent actionEvent) {
        String sqlSelection = "SELECT * from Student WHERE ID = ?";
            // Checks what student is selected and opens a new window with more information about the student
            // -> Go to information controller
        try {
            if (this._studenttable.getSelectionModel().getSelectedItem()!=null) {
                Connection conn = dbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlSelection);
                stmt.setString(1, this._studenttable.getSelectionModel().getSelectedItem().getID());
                setIDpass(this._studenttable.getSelectionModel().getSelectedItem().getID());


                conn.close();

                Stage informationStage = new Stage();
                FXMLLoader informationLoader = new FXMLLoader();
                Pane informationRoot = (Pane) informationLoader.load(getClass().getResource("/information/information.fxml").openStream());
                InformationController informationController = (InformationController) informationLoader.getController();

                Scene scene = new Scene(informationRoot);
                informationStage.setScene(scene);
                informationStage.setTitle("Student information");
                informationStage.setResizable(false);
                informationStage.show();
            }
            this.nooneselected.setText("You must select a student");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void setIDpass(String ID) {
        this.IDpass=ID;
    }

    public static String getIDpass() {
        return IDpass;
    }
}
