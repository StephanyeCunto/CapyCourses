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
    opens com.view.login_cadastro.cadastro to javafx.fxml;
    opens com.view.login_cadastro.elements to javafx.fxml;
    opens com.view.professor to javafx.fxml;
    opens com.view.estudante to javafx.fxml;
    
    exports com;
    exports com.view;
    exports com.view.login_cadastro;
    exports com.view.login_cadastro.cadastro;
    exports com.view.login_cadastro.elements;
    exports com.view.professor;
    exports com.view.estudante;

}