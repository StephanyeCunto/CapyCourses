module com {
  requires javafx.controls;
  requires javafx.fxml;

  opens com.singleton to javafx.fxml;
  opens com.view.auth to javafx.fxml;

  exports com.singleton;
}
