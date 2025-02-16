package com.view.elements.Forum;

import com.dto.ForumDTO;
import com.model.ForumDatabase;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class LoadForumView {
  private static final double CARD_WIDTH = 1000;
  private static final double GRID_SPACING = 20;
  private static final double CONTENT_SPACING = 20;
  private static final String FONT_FAMILY = "Segoe UI";

  private static ForumDatabase forumDatabase = new ForumDatabase();
  private static List<ForumDTO> forum;
  private static final GridPane forumGrid = new GridPane();
  private static final VBox forumContainer = new VBox();

  public static VBox loadForum(String status) {
    //  forum = status.equals("todos") ?
    //         forumDatabase.getAllForums() :
    //        forumDatabase.getForumsByAuthor(UserSession.getInstance().getUserName());

    initializeGrid();
    displayForums();
    setupResponsiveBehavior();

    forumContainer.setStyle("-fx-background-color: transparent;");
    return forumContainer;
  }

  private static void initializeGrid() {
    forumGrid.getChildren().clear();
    forumGrid.setHgap(GRID_SPACING);
    forumGrid.setVgap(GRID_SPACING);
    forumGrid.setPadding(new Insets(GRID_SPACING * 1.5));
    forumGrid.setAlignment(Pos.CENTER);

    forumContainer.setAlignment(Pos.CENTER);
    forumContainer.getChildren().clear();
    forumContainer.getChildren().add(forumGrid);
    forumContainer.setSpacing(GRID_SPACING);
  }

  private static void displayForums() {
    int numColumns = 1;
    int index = 0;

    for (ForumDTO forumDTO : forum) {
      VBox forumBox = createForumBox(forumDTO);
      addFadeInAnimation(forumBox);

      forumBox.setStyle("-fx-transition: all 0.3s ease;");
      forumBox.setOnMouseEntered(e -> forumBox.setStyle("-fx-scale-x: 1.02; -fx-scale-y: 1.02;"));
      forumBox.setOnMouseExited(e -> forumBox.setStyle("-fx-scale-x: 1; -fx-scale-y: 1;"));

      forumGrid.add(forumBox, index % numColumns, index / numColumns);
      index++;
    }
  }

  private static void setupResponsiveBehavior() {
    forumContainer
        .widthProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              int columns = calculateColumns();
              reorganizeGrid(columns);
            });
  }

  private static VBox createForumBox(ForumDTO forum) {
    VBox forumBox = new VBox();
    forumBox.getStyleClass().add("card");
    forumBox.setPrefWidth(CARD_WIDTH);
    forumBox.setAlignment(Pos.TOP_LEFT);
    forumBox.setPadding(new Insets(20));
    forumBox.setSpacing(CONTENT_SPACING);

    VBox content = createForumContent(forum);
    HBox footer = createFooter(forum);

    forumBox.getChildren().addAll(content, footer);
    return forumBox;
  }

  private static VBox createForumContent(ForumDTO forum) {
    HBox header = createHeader(forum);
    Label titleLabel = createStyledLabel((String) forum.getTitle(), 24);
    titleLabel.getStyleClass().add("title");

    HBox authorSection = createAuthorSection(forum);

    Label descriptionLabel = createStyledLabel((String) forum.getDescription(), 16);
    descriptionLabel.getStyleClass().add("description");

    VBox content = new VBox(CONTENT_SPACING);
    content.setAlignment(Pos.TOP_LEFT);
    content.getChildren().addAll(header, titleLabel, authorSection, descriptionLabel);

    return content;
  }

  private static HBox createHeader(ForumDTO forum) {
    HBox header = new HBox(10);
    header.setAlignment(Pos.CENTER_LEFT);

    Label categoryLabel = createCategoryLabel((String) forum.getCategory());

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    Label dateLabel = createDateLabel((String) forum.getDateTime());

    header.getChildren().addAll(categoryLabel, spacer, dateLabel);
    return header;
  }

  private static Label createCategoryLabel(String category) {
    Label label = createStyledLabel(category.toUpperCase(), 12);
    label.getStyleClass().addAll("category", "category-tag");
    return label;
  }

  private static HBox createAuthorSection(ForumDTO forum) {
    HBox authorBox = new HBox(12);
    authorBox.setAlignment(Pos.CENTER_LEFT);

    StackPane avatar = createAuthorAvatar((String) forum.getAuthor());

    VBox authorInfo = new VBox(4);
    Label authorLabel = createStyledLabel("Por " + forum.getAuthor(), 14);
    authorLabel.getStyleClass().add("author");

    authorInfo.getChildren().add(authorLabel);
    authorBox.getChildren().addAll(avatar, authorInfo);

    return authorBox;
  }

  private static StackPane createAuthorAvatar(String author) {
    StackPane avatar = circleName(author);

    return avatar;
  }

  private static StackPane circleName(String author) {
    StackPane avatarCircle = new StackPane();
    avatarCircle.setMinSize(80, 80);
    avatarCircle.setMaxSize(80, 80);
    avatarCircle.getStyleClass().add("avatar-circle");
    String initials = getInitials(author);
    Label nameInitial = new Label(initials);
    nameInitial.setFont(Font.font("Franklin Gothic Medium", 24));
    nameInitial.setStyle("-fx-text-fill: white;");

    StackPane stackPane = new StackPane();
    stackPane.getChildren().addAll(avatarCircle, nameInitial);

    return stackPane;
  }

  private static HBox createFooter(ForumDTO forum) {
    HBox footer = new HBox(15);
    footer.setAlignment(Pos.CENTER_LEFT);

    Button viewButton = createViewButton(forum);

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    HBox stats = createForumStats(forum);

    footer.getChildren().addAll(viewButton, spacer, stats);
    return footer;
  }

  private static HBox createForumStats(ForumDTO forum) {
    HBox stats = new HBox(20);
    stats.setAlignment(Pos.CENTER_LEFT);

    Label viewsLabel = createStatsLabel("👁 " + forum.getView(), "views-count");
    Label likesLabel = createStatsLabel("👍 " + forum.getLike(), "likes-count");
    Label commentsLabel = createStatsLabel("💬 " + forum.getComments(), "comments-count");

    stats.getChildren().addAll(viewsLabel, likesLabel, commentsLabel);
    return stats;
  }

  private static Label createStatsLabel(String text, String styleClass) {
    Label label = createStyledLabel(text, 14);
    label.getStyleClass().addAll("stats-label", styleClass);
    return label;
  }

  private static Label createDateLabel(String date) {
    Label label = createStyledLabel(date, 12);
    label.getStyleClass().add("date-label");
    return label;
  }

  private static Button createViewButton(ForumDTO forum) {
    Button button = new Button("Ver Detalhes");
    button.getStyleClass().add("outline-button");
    button.setPrefWidth(150);
    button.setOnAction(
        e -> {
          forumDatabase.incrementViewCount(forum.getTitle());
          try {
            String fxmlPath = "/com/estudante/forum/paginaDoForum.fxml";
            URL resource = LoadForumView.class.getResource(fxmlPath);

            if (resource == null) {
              System.err.println("FXML não encontrado: " + fxmlPath);
              return;
            }

            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();

            PaginaDoForumView controller = loader.getController();
            if (controller != null) {
              controller.setForumData(forum);
            }

            Scene scene = button.getScene();
            scene.setRoot(root);
          } catch (IOException ex) {
            System.err.println("Erro ao carregar página do fórum: " + ex.getMessage());
            ex.printStackTrace();
          }
        });

    return button;
  }

  private static Label createStyledLabel(String text, double fontSize) {
    Label label = new Label(text);
    label.setFont(Font.font(FONT_FAMILY, fontSize));
    label.getStyleClass().add("label");
    label.setWrapText(true);
    return label;
  }

  private static String getInitials(String name) {
    if (name == null || name.trim().isEmpty()) return "";
    String[] parts = name.split(" ");
    StringBuilder initials = new StringBuilder();
    for (int i = 0; i < Math.min(2, parts.length); i++) {
      if (!parts[i].isEmpty()) {
        initials.append(parts[i].charAt(0));
      }
    }
    return initials.toString().toUpperCase();
  }

  private static void addFadeInAnimation(VBox forumBox) {
    FadeTransition fade = new FadeTransition(Duration.millis(400), forumBox);
    fade.setFromValue(0);
    fade.setToValue(1);
    fade.play();
  }

  private static int calculateColumns() {
    double width = forumContainer.getWidth();
    return width > 0 ? Math.max(1, (int) (width / 200)) : 1;
  }

  private static void reorganizeGrid(int columns) {
    forumGrid.getChildren().clear();
    displayForums();
  }
}
