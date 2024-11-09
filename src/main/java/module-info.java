module com {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;

    requires java.desktop;
    requires org.controlsfx.controls;

    opens com to javafx.fxml;
    opens com.view to javafx.fxml; 
    opens com.view.elements to javafx.fxml; 
    opens com.view.login_cadastro to javafx.fxml;
    opens com.view.professor to javafx.fxml;
    
    exports com;
    exports com.view;
    exports com.view.login_cadastro;
    exports com.view.professor;
}
