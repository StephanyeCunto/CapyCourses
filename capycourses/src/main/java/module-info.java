module com {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires lombok;
    requires org.json;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires itextpdf;
    
    requires java.desktop;
    requires org.controlsfx.controls;
    requires javafx.web;
    requires org.apache.pdfbox;
    requires javafx.swing;  

    opens com.singleton to javafx.fxml;
    opens com.view to javafx.fxml;
    opens com.view.elements.Biblioteca to javafx.fxml;
    opens com.view.elements.Forum to javafx.fxml;
    opens com.view.elements.Calendario to javafx.fxml;
    opens com.view.elements.Carousel to javafx.fxml;
    opens com.view.elements.Certificado to javafx.fxml;
    opens com.view.elements.Courses to javafx.fxml;
    opens com.view.elements.MenuEstudante to javafx.fxml;
    opens com.view.login_cadastro to javafx.fxml;
    opens com.view.login_cadastro.cadastro to javafx.fxml;
    opens com.view.login_cadastro.elements to javafx.fxml;
    opens com.view.professor to javafx.fxml;
    opens com.view.estudante to javafx.fxml;

    exports com.singleton;
    exports com.view;
    exports com.view.login_cadastro;
    exports com.view.login_cadastro.cadastro;
    exports com.view.login_cadastro.elements;
    exports com.view.professor;
    exports com.view.estudante;
    exports com.view.elements.Biblioteca;
    exports com.view.elements.Forum;
    exports com.view.elements.Calendario;
    exports com.view.elements.Carousel;
    exports com.view.elements.Certificado;
    exports com.view.elements.Courses;
    exports com.view.elements.MenuEstudante;
    exports com.model.login_cadastro to com.fasterxml.jackson.databind;
    exports com.controller.login_cadastro to com.fasterxml.jackson.databind;
    exports com.controller.elements to com.fasterxml.jackson.databind;
}

