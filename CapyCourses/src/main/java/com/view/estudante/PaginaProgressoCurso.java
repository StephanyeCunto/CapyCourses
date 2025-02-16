package com.view.estudante;

import com.view.Modo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FillTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class PaginaProgressoCurso implements Initializable {
  @FXML private VBox content;
  @FXML private ImageView sunIcon;
  @FXML private ImageView moonIcon;
  @FXML private StackPane thumbContainer;
  @FXML private Rectangle background;
  @FXML private HBox toggleButtonHBox;
  @FXML private Button themeToggleBtn;
  @FXML private StackPane toggleButtonStackPane;
  @FXML private GridPane container;

  @SuppressWarnings("unused")
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    content.getChildren().add(start());

    changeMode();
    toggleButtonStackPane.setOnMouseClicked(e -> toggle());
    sunIcon.setImage(new Image(getClass().getResourceAsStream("/com/login_cadastro/img/sun.png")));
    moonIcon.setImage(
        new Image(getClass().getResourceAsStream("/com/login_cadastro/img/moon.png")));
    toggleInitialize();
  }

  private VBox start() {
    HBox hBox1 = new HBox(20);
    hBox1.setAlignment(Pos.CENTER);
    hBox1.setPadding(new Insets(30, 30, 30, 30));

    VBox progressCard = createStatCard("Progresso Geral", "75%");
    VBox modulesCard = createStatCard("Módulos Completados", "6/8");
    VBox studyTimeCard = createStatCard("Tempo Total de Estudo", "32h");

    hBox1.getChildren().addAll(progressCard, modulesCard, studyTimeCard);

    HBox hBox2 = new HBox(20);
    hBox2.setAlignment(Pos.CENTER);
    hBox2.setPadding(new Insets(30, 30, 30, 30));

    ObservableList<PieChart.Data> pieChartData =
        FXCollections.observableArrayList(
            new PieChart.Data("Assistir Vídeos", 40),
            new PieChart.Data("Praticar Exercícios", 35),
            new PieChart.Data("Ler Materiais", 25));
    PieChart activityDistribution = new PieChart(pieChartData);
    activityDistribution.setTitle("Distribuição de Atividades");
    activityDistribution.setLegendVisible(true);
    activityDistribution.setLabelsVisible(false);
    activityDistribution.getStyleClass().add("stat-card-container");
    HBox.setHgrow(activityDistribution, javafx.scene.layout.Priority.ALWAYS);

    CategoryAxis xAxis = new CategoryAxis();
    xAxis.setLabel("Módulos");
    NumberAxis yAxis = new NumberAxis();
    yAxis.setLabel("Progresso (%)");
    BarChart<String, Number> moduleProgress = new BarChart<>(xAxis, yAxis);
    moduleProgress.setTitle("Progresso por Módulo");
    moduleProgress.getStyleClass().add("stat-card-container");
    HBox.setHgrow(moduleProgress, javafx.scene.layout.Priority.ALWAYS);

    BarChart.Series<String, Number> progressData = new BarChart.Series<>();
    progressData.setName("Progresso");
    progressData
        .getData()
        .addAll(
            new BarChart.Data<>("Módulo 1", 80),
            new BarChart.Data<>("Módulo 2", 60),
            new BarChart.Data<>("Módulo 3", 90),
            new BarChart.Data<>("Módulo 4", 70));
    moduleProgress.getData().add(progressData);

    hBox2.getChildren().addAll(activityDistribution, moduleProgress);

    VBox recentActivitiesBox = new VBox(10);
    recentActivitiesBox.getStyleClass().add("stat-card-container-text");
    recentActivitiesBox.setPadding(new Insets(0, 30, 30, 30));

    Label activitiesTitle = new Label("Atividades Recentes");
    activitiesTitle.getStyleClass().add("activities-title");

    TableView<Object> recentActivities = new TableView<>();
    recentActivities.getStyleClass().add("table-view-custom");
    VBox.setVgrow(recentActivities, javafx.scene.layout.Priority.ALWAYS);

    TableColumn<Object, String> dateColumn = new TableColumn<>("Data");
    dateColumn.setPrefWidth(120);

    TableColumn<Object, String> activityColumn = new TableColumn<>("Atividade");
    activityColumn.setPrefWidth(300);

    TableColumn<Object, String> courseColumn = new TableColumn<>("Curso");
    courseColumn.setPrefWidth(250);

    TableColumn<Object, String> moduleColumn = new TableColumn<>("Módulo");
    moduleColumn.setPrefWidth(250);

    TableColumn<Object, String> statusColumn = new TableColumn<>("Status");
    statusColumn.setPrefWidth(120);

    recentActivities
        .getColumns()
        .addAll(dateColumn, activityColumn, courseColumn, moduleColumn, statusColumn);

    recentActivitiesBox.getChildren().addAll(activitiesTitle, recentActivities);

    VBox root = new VBox(20);
    root.getChildren().addAll(hBox1, hBox2, recentActivitiesBox);

    return root;
  }

  private VBox createStatCard(String title, String value) {
    VBox statCard = new VBox();
    statCard.getStyleClass().add("stat-card-container");
    HBox.setHgrow(statCard, javafx.scene.layout.Priority.ALWAYS);

    Label titleLabel = new Label(title);
    titleLabel.getStyleClass().add("stat-card-container-text");

    Label valueLabel = new Label(value);
    valueLabel.getStyleClass().add("stat-card-container-static");

    statCard.getChildren().addAll(titleLabel, valueLabel);
    return statCard;
  }

  private void toggle() {
    // Modo.getInstance().getModo() == dark
    Modo.getInstance().setModo();

    TranslateTransition thumbTransition =
        new TranslateTransition(Duration.millis(200), thumbContainer);
    thumbTransition.setToX(!Modo.getInstance().getModo() ? 12.0 : -12.0);
    thumbTransition.play();

    FillTransition fillTransition = new FillTransition(Duration.millis(200), background);
    fillTransition.setFromValue(
        !Modo.getInstance().getModo() ? Color.web("#FFA500") : Color.web("#4169E1"));
    fillTransition.setToValue(
        !Modo.getInstance().getModo() ? Color.web("#4169E1") : Color.web("#FFA500"));
    fillTransition.play();

    changeMode();

    updateIconsVisibility();
  }

  private void updateIconsVisibility() {
    sunIcon.setVisible(Modo.getInstance().getModo());
    moonIcon.setVisible(!Modo.getInstance().getModo());
  }

  private void toggleInitialize() {
    if (!Modo.getInstance().getModo()) {
      background.getStyleClass().add("dark");
      sunIcon.setVisible(Modo.getInstance().getModo());
      moonIcon.setVisible(!Modo.getInstance().getModo());
      TranslateTransition thumbTransition =
          new TranslateTransition(Duration.millis(200), thumbContainer);
      thumbTransition.setToX(!Modo.getInstance().getModo() ? 12.0 : -12.0);
      thumbTransition.play();
    } else {
      TranslateTransition thumbTransition =
          new TranslateTransition(Duration.millis(200), thumbContainer);
      thumbTransition.setToX(Modo.getInstance().getModo() ? -12.0 : 12.0);
      thumbTransition.play();
      background.getStyleClass().remove("dark");
      sunIcon.setVisible(Modo.getInstance().getModo());
      moonIcon.setVisible(!Modo.getInstance().getModo());
    }
  }

  private void changeMode() {
    container.getStylesheets().clear();
    if (!Modo.getInstance().getModo()) {
      background.getStyleClass().add("dark");
      container
          .getStylesheets()
          .add(
              getClass()
                  .getResource("/com/estudante/progresso/style/ligth/style.css")
                  .toExternalForm());
    } else {
      background.getStyleClass().remove("dark");
      container
          .getStylesheets()
          .add(
              getClass()
                  .getResource("/com/estudante/progresso/style/dark/style.css")
                  .toExternalForm());
    }
  }
}
