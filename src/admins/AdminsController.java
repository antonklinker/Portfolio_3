package admins;

import information.InformationController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    public TextField id;
    public TextField firstname;
    public TextField lastname;
    public TextField email;
    public DatePicker dob;
    public TableView<StudentData> studenttable;
    public TableColumn<StudentData, String> idcolumn;
    public TableColumn<StudentData, String> firstnamecolumn;
    public TableColumn<StudentData, String> lastnamecolumn;
    public TableColumn<StudentData, String> emailcolumn;
    public TableColumn<StudentData, String> dobcolumn;

    public TableView<PortfolioData> _studenttable;
    public TableColumn<PortfolioData, String> _namecolumn;
    public TableColumn<PortfolioData, String> _idcolumn;

    private dbConnection dc;
    private ObservableList<StudentData> data;
    private ObservableList<PortfolioData> portfolioData;

    private String sql = "SELECT * FROM students";
    //private String sqlGradeData = "SELECT * FROM GRADE";
    private String sqlStudentData = "SELECT * FROM Student";

    private static String IDpass;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dc=new dbConnection();
        loadAllData();
    }

    public void loadAllData() {
        try {
            Connection conn = dbConnection.getConnection();
            this.portfolioData = FXCollections.observableArrayList();

            //ResultSet rs = conn.createStatement().executeQuery(sqlGradeData);
            ResultSet rs2 = conn.createStatement().executeQuery(sqlStudentData);
            while (rs2.next()) {
                this.portfolioData.add(new PortfolioData(rs2.getString(3), rs2.getString(1), null, null, null, null));
            }
            /*while (rs.next()) {
                this.portfolioData.add(new PortfolioData(rs.getString(4), rs.getString(3),
                        rs.getString(1) + " " + rs.getString(2), rs.getString(5), null));
            }*/
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        this._idcolumn.setCellValueFactory(new PropertyValueFactory<PortfolioData, String>("ID"));
        this._namecolumn.setCellValueFactory(new PropertyValueFactory<PortfolioData, String>("Name"));

        this._studenttable.setItems(null);
        this._studenttable.setItems(this.portfolioData);
    }

    public void loadStudentData(ActionEvent event) {
        try {
            Connection conn = dbConnection.getConnection();
            this.data = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                this.data.add(new StudentData(rs.getString(1), rs.getString(2),
                              rs.getString(3), rs.getString(4), rs.getString(5)));
            }

        } catch (SQLException ex) {
            System.err.println("Error: " + ex);
        }
        this.idcolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("ID"));
        this.firstnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("firstName"));
        this.lastnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("lastName"));
        this.emailcolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("email"));
        this.dobcolumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("DOB"));

        this.studenttable.setItems(null);
        this.studenttable.setItems(this.data);
    }

    public void addStudent(ActionEvent event) {
        String sqlInsert = "INSERT INTO students(id,fname,lname,email,DOB) VALUES (?,?,?,?,?)";

        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlInsert);

            stmt.setString(1,this.id.getText());
            stmt.setString(2,this.firstname.getText());
            stmt.setString(3,this.lastname.getText());
            stmt.setString(4,this.email.getText());
            stmt.setString(5,this.dob.getEditor().getText());

            stmt.execute();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearForm(ActionEvent event) {
        this.id.setText("");
        this.firstname.setText("");
        this.lastname.setText("");
        this.email.setText("");
        this.dob.setValue(null);

    }

    public void moreInformation(ActionEvent actionEvent) {
        //String sqlInsert = "INSERT INTO students(id,fname,lname,email,DOB) VALUES (?,?,?,?,?)";
        String sqlSelection = "SELECT * from Student WHERE ID = ?";
        //System.out.println(this._studenttable.getSelectionModel().getSelectedItem().getID());

        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlSelection);
            stmt.setString(1, this._studenttable.getSelectionModel().getSelectedItem().getID());
            setIDpass(this._studenttable.getSelectionModel().getSelectedItem().getID());

            //ResultSet rs = conn.createStatement().executeQuery(sqlSelection);
            //while (rs.next()) {
                //System.out.println(rs.getString(4));
            //}


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
