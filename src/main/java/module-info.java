module com {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;

    requires java.desktop;
    opens com to javafx.fxml;
    opens com.view to javafx.fxml; 
    opens com.view.elements to javafx.fxml; 
    opens com.view.login_cadastro to javafx.fxml;
    
    exports com.view.login_cadastro;
    exports com;
}
