module com {
  requires javafx.controls;
  requires javafx.fxml;
  requires lombok;
  requires org.controlsfx.controls;
  requires java.sql;
  requires java.persistence;
  requires org.hibernate.orm.core;

  opens com.singleton to javafx.fxml;
  opens com.view.auth to javafx.fxml;
  opens com.controller.auth to javafx.fxml;
  opens com.model.auth to org.hibernate.orm.core;
  opens com.model.auth.entity to org.hibernate.orm.core; 

  exports com.singleton;
  exports com.model.auth;
  exports com.controller.auth;
  exports com.view.auth;
}
