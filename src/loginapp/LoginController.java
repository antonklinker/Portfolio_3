package loginapp;

import admins.AdminsController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.layout.Pane;
import students.StudentsController;

public class LoginController implements Initializable {
    public Label loginStatus;
    LoginModel loginModel;
    public TextField usernamefield;
    public PasswordField passwordfield;
    public Label dbstatus;
    public ComboBox<option> divisioncombobox;
    public Button loginbutton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Checks if we have connection and sets the label
        loginModel = new LoginModel();
        if (this.loginModel.isDatabaseConnected()) {
            this.dbstatus.setTextFill(Color.GREEN);
            this.dbstatus.setText("Connected to database");
        } else {
            this.dbstatus.setTextFill(Color.RED);
            this.dbstatus.setText("Not connected to database");
        }

        this.divisioncombobox.setItems(FXCollections.observableArrayList(option.values()));
    }


    public void loginMethod(ActionEvent actionEvent) {
        // Activated once the login button is pressed.
        // Checks if the entered credentials matches the correct credentials
        // in our database by calling the isLogin() method from the LoginModel class
        try {
            if (this.divisioncombobox.getValue()!=null) {
                if (this.loginModel.isLogin(this.usernamefield.getText(), this.passwordfield.getText(), ((option) this.divisioncombobox.getValue()).toString())) {
                    Stage stage = (Stage) this.loginbutton.getScene().getWindow();
                    stage.close();
                    switch (((option) this.divisioncombobox.getValue()).toString()) {
                        case "Admin":
                            adminLogin();
                            break;
                        case "Student":
                            studentLogin();
                            break;
                    }
                } else {
                    this.loginStatus.setText("WRONG CREDENTIALS");
                }
            } else {
                this.loginStatus.setText("Pick a division");
            }
        } catch (Exception localException) {
            localException.printStackTrace();
        }

    }

    public void studentLogin() {
        // This is called when you login using student credentials.
        // Creates a new window called Student Dashboard but we haven't done anything with it
        try {
            Stage userStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/students/students.fxml").openStream());
            StudentsController studentsController = (StudentsController) loader.getController();

            Scene scene = new Scene(root);
            userStage.setScene(scene);
            userStage.setTitle("Student Dashboard");
            userStage.setResizable(false);
            userStage.show();


        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void adminLogin() {
        // Called when you login with admin credentials or if you press the bypass button
        // -> Go to AdminsController
        try {
            Stage adminStage = new Stage();
            FXMLLoader adminLoader = new FXMLLoader();
            Pane adminRoot = (Pane) adminLoader.load(getClass().getResource("/admins/admins.fxml").openStream());
            AdminsController adminsController = (AdminsController) adminLoader.getController();

            Scene scene = new Scene(adminRoot);
            adminStage.setScene(scene);
            adminStage.setTitle("Admin Dashboard");
            adminStage.setResizable(false);
            adminStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void bypassLogin(ActionEvent actionEvent) {
        // Called when you press the login button
        // -> Go to AdminsController
        Stage stage = (Stage) this.loginbutton.getScene().getWindow();
        stage.close();
        adminLogin();

    }
}
