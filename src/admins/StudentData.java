package admins;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StudentData {
    // nevermind this comment

    private final StringProperty ID;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty email;
    private final StringProperty DOB;

    public StudentData(String ID, String firstname, String lastname, String email, String DOB) {
        this.ID=new SimpleStringProperty(ID);
        this.firstName=new SimpleStringProperty(firstname);
        this.lastName=new SimpleStringProperty(lastname);
        this.email=new SimpleStringProperty(email);
        this.DOB=new SimpleStringProperty(DOB);

    }
}
