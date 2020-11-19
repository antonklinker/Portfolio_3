package information;

import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class InformationController implements Initializable {
    public TableView studenttable;
    public TableColumn idcolumn;
    public TableColumn namecolumn;
    public TableColumn poocolumn;
    public TableColumn coursescolumn;
    public TableColumn gradecolumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
