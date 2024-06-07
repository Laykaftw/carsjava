module cars {
    exports com.miniprojet;

    requires java.sql;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.miniprojet to javafx.fxml;
}
