package admins;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PortfolioData {
    private final StringProperty ID;
    private final StringProperty name;
    private final StringProperty course;
    private final StringProperty grade;
    private final StringProperty city;
    private final StringProperty avg;

    public PortfolioData(String ID, String name, String course, String grade, String city, String avg) {
        // Data we can add to the tableviews
        this.ID=new SimpleStringProperty(ID);
        this.name=new SimpleStringProperty(name);
        this.course=new SimpleStringProperty(course);
        this.grade=new SimpleStringProperty(grade);
        this.city=new SimpleStringProperty(city);
        this.avg=new SimpleStringProperty(avg);
    }




    public String getID() {
        return ID.get();
    }

    public StringProperty IDProperty() {
        return ID;
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getCourse() {
        return course.get();
    }

    public StringProperty courseProperty() {
        return course;
    }

    public void setCourse(String course) {
        this.course.set(course);
    }

    public String getGrade() {
        return grade.get();
    }

    public StringProperty gradeProperty() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade.set(grade);
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getAvg() {
        return avg.get();
    }

    public StringProperty avgProperty() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg.set(avg);
    }
}
