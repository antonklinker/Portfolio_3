package loginapp;

public enum option {
    // Creates options for the combobox used to login
    Admin , Student;

    private option() {

    }

    public String value() {
        return name();
    }

    public static option fromvalue(String v) {
        return valueOf(v);
    }
}
