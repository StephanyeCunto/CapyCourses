module com {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires lombok;
    requires org.json;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

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
    exports com.model.login_cadastro to com.fasterxml.jackson.databind;
    exports com.controller.login_cadastro to com.fasterxml.jackson.databind;
}