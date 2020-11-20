package information;

import admins.AdminsController;
import admins.PortfolioData;

import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class InformationController implements Initializable {

    public TableView<PortfolioData> studenttable;
    public TableColumn<PortfolioData, String> idcolumn;
    public TableColumn<PortfolioData, String> namecolumn;
    public TableColumn<PortfolioData, String> poocolumn;
    public TableColumn<PortfolioData, String> coursescolumn;
    public TableColumn<PortfolioData, String> gradecolumn;
    public TableColumn<PortfolioData, String> averagecolumn;

    private dbConnection dc;
    private ObservableList<PortfolioData> portfolioData;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            // Shows all information about the selected student
        try {
            Connection conn = dbConnection.getConnection();
            this.portfolioData = FXCollections.observableArrayList();

            // The first SQL-query selects all information about the student from the various tables
            String sql = "SELECT G.StudentID, G.StudentName, S.City, G.CourseName, G.CourseSeason, G.Grade FROM Grade G INNER JOIN Course C " +
                    "on G.CourseName = C.Name INNER JOIN Student S on G.StudentID = S.ID WHERE S.ID = " + AdminsController.getIDpass();

            // The seconds SQL-query only selects the average grade from the student
            String sqlAvg= "SELECT AVG(G.Grade) FROM Grade G INNER JOIN Student S on G.StudentID = S.ID WHERE S.ID = " + AdminsController.getIDpass();
            // Could probably be done using only a single SQL-query, but this works


            ResultSet rs = conn.createStatement().executeQuery(sql);
            ResultSet rs2 = conn.createStatement().executeQuery(sqlAvg);

            int i = 0;
            while (rs.next()) {
                if (i == 0) {
                    // Adds all data to the first row
                    this.portfolioData.add(new PortfolioData(rs.getString(1), rs.getString(2), rs.getString(4) + " " + rs.getString(5), rs.getString(6), rs.getString(3), rs2.getString(1)));
                    i++;
                } else {
                    // Only adds course data and grade data to the second row
                    // since they are the only rows that has different values.
                    this.portfolioData.add(new PortfolioData(null, null, rs.getString(4) + " " + rs.getString(5), rs.getString(6), null, null));
                }
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.idcolumn.setCellValueFactory(new PropertyValueFactory<PortfolioData, String>("ID"));
        this.namecolumn.setCellValueFactory(new PropertyValueFactory<PortfolioData, String>("Name"));
        this.poocolumn.setCellValueFactory(new PropertyValueFactory<PortfolioData, String>("City"));
        this.coursescolumn.setCellValueFactory(new PropertyValueFactory<PortfolioData, String>("Course"));
        this.gradecolumn.setCellValueFactory(new PropertyValueFactory<PortfolioData, String>("Grade"));
        this.averagecolumn.setCellValueFactory(new PropertyValueFactory<PortfolioData, String>("Avg"));

        this.studenttable.setItems(null);
        this.studenttable.setItems(this.portfolioData);

    }
}
