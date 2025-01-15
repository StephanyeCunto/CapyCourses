package com.view.elements.Forum;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.List;

import com.controller.elements.LoadForumController;
import com.dto.ForumComentarioDTO;
import com.singleton.UserSession;

public class ForumDetailView extends VBox {
    private static final double CONTENT_WIDTH = 1000;
    private static final double CONTENT_SPACING = 24;
    private static final String FONT_FAMILY = "Segoe UI";
    private static final String JSON_PATH = "capycourses/target/classes/com/json/forum.json";
    LoadForumController controller = new LoadForumController();
    private boolean liked = controller.curtiu(CreateJsonForum.getSavedTitle(JSON_PATH),
            UserSession.getInstance().getUserName());

    private String author;
    private String title;
    private String description;
    private String category;
    private String dateTime;
    private int view;
    private int like;
    private int comments;
    private String question;
    private List<ForumComentarioDTO> comentarios;
    private VBox contentContainer;

    public ForumDetailView() {
        loadForumData();
        initialize();
    }

    public VBox getView() {
        return this;
    }

    private void loadForumData() {
        author = CreateJsonForum.getSavedAuthor(JSON_PATH);
        title = CreateJsonForum.getSavedTitle(JSON_PATH);
        description = CreateJsonForum.getSavedDescription(JSON_PATH);
        category = CreateJsonForum.getSavedCategory(JSON_PATH);
        dateTime = CreateJsonForum.getSavedDateTime(JSON_PATH);
        view = CreateJsonForum.getSavedView(JSON_PATH);
        like = CreateJsonForum.getSavedLike(JSON_PATH);
        comments = CreateJsonForum.getSavedComments(JSON_PATH);
        question = CreateJsonForum.getSavedQuestion(JSON_PATH);

        comentarios = new LoadForumController().loadComentario(title);
    }

    private void initialize() {
        setAlignment(Pos.TOP_CENTER);
        setSpacing(CONTENT_SPACING);
        setPadding(new Insets(32, 16, 32, 16));
        contentContainer = createContentContainer();

        contentContainer.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                showLikeAnimation(event.getX(), event.getY());
                handleLike();
            }
        });

        getChildren().add(contentContainer);
        addFadeInAnimation(this);
    }

    private VBox createContentContainer() {
        VBox container = new VBox(CONTENT_SPACING);
        container.setMaxWidth(CONTENT_WIDTH);
        container.setPadding(new Insets(40));

        Tooltip tooltip = new Tooltip("Dê um duplo clique para curtir");
        tooltip.setShowDelay(Duration.millis(200));
        Tooltip.install(container, tooltip);

        container.getStyleClass().add("card");

        container.getChildren().addAll(
                createHeaderSection(),
                createMainContentSection(),
                createAuthorSection(),
                createQuestionSection(),
                createInteractionSection(),
                createCommentsSection());

        return container;
    }

    private VBox createQuestionSection() {
        VBox section = new VBox(24);

        Label questionText = new Label(question);
        questionText.setWrapText(true);
        questionText.getStyleClass().add("description");

        section.getChildren().addAll(questionText);
        return section;
    }

    private VBox createCommentsSection() {
        VBox section = new VBox(24);

        Label commentsTitle = new Label("Comentários");
        commentsTitle.getStyleClass().add("commentsTitle");


        VBox commentsContainer = new VBox(16);
        commentsContainer.getStyleClass().add("comments-container");

        if (comentarios != null && !comentarios.isEmpty()) {
            for (int i = comentarios.size() - 1; i >= 0; i--) {
                ForumComentarioDTO comment = comentarios.get(i);
                commentsContainer.getChildren().add(createCommentBox(comment));
            }
        } else {
            Label noComments = new Label("Ainda não há comentários. Seja o primeiro a comentar!");
            noComments.setStyle("-fx-text-fill: #9ca3af;");
            noComments.getStyleClass().add("description");
            commentsContainer.getChildren().add(noComments);
        }

        section.getChildren().addAll(commentsTitle, commentsContainer);
        return section;
    }

    private VBox createCommentBox(ForumComentarioDTO comment) {
        VBox commentBox = new VBox(12);
        commentBox.getStyleClass().add("comment-box");
        commentBox.setPadding(new Insets(16));
        commentBox.setStyle("-fx-background-color: #f3f4f6; -fx-background-radius: 8;");

        HBox authorBox = new HBox(12);
        authorBox.setAlignment(Pos.CENTER_LEFT);

        StackPane avatar = createCommentAvatar(comment.getUsuario());

        Label authorName = new Label(comment.getUsuario());
        authorName.getStyleClass().add("authorAvatar");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label commentDate = new Label(comment.getData());
        commentDate.setWrapText(true);
        commentDate.getStyleClass().add("commentDate");


        Label commentText = new Label(comment.getComentario());
        commentText.setWrapText(true);

        commentText.setStyle("-fx-text-fill: #9ca3af;");
        commentText.getStyleClass().add("description");

        authorBox.getChildren().addAll(avatar, authorName, spacer, commentText);

        commentBox.getChildren().addAll(authorBox, commentDate);
        return commentBox;
    }

    private StackPane createCommentAvatar(String usuario) {
        StackPane avatarCircle = new StackPane();
        avatarCircle.setMinSize(48, 48);
        avatarCircle.setMaxSize(48, 48);
        avatarCircle.setStyle(
                "-fx-background-color: #3b82f6; " +
                        "-fx-background-radius: 24;");

        Label nameInitial = new Label(getInitials(usuario));
        nameInitial.getStyleClass().add("nameInitial");


        avatarCircle.getChildren().add(nameInitial);
        return avatarCircle;
    }

    private VBox createHeaderSection() {
        VBox header = new VBox(16);
        header.setPadding(new Insets(0, 0, 0, 0));

        HBox topBar = new HBox(20);
        topBar.setAlignment(Pos.CENTER_LEFT);

        Label categoryLabel = createCategoryLabel();
        Label dateLabel = createDateLabel();

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBar.getChildren().addAll(categoryLabel, spacer, dateLabel);
        header.getChildren().addAll(topBar, createTitle());

        return header;
    }

    private VBox createMainContentSection() {
        VBox content = new VBox(24);

        Label descriptionLabel = new Label(description);
        descriptionLabel.setWrapText(true);
        descriptionLabel.getStyleClass().add("description");

        content.getChildren().add(descriptionLabel);
        return content;
    }

    private HBox createAuthorSection() {
        HBox authorBox = new HBox(16);
        authorBox.setAlignment(Pos.CENTER_LEFT);
        authorBox.setPadding(new Insets(0, 0, 20, 0));

        StackPane avatar = createAuthorAvatar();

        VBox authorInfo = new VBox(4);
        Label authorLabel = new Label(author);
        authorLabel.getStyleClass().add("author");

        authorInfo.getChildren().add(authorLabel);
        authorBox.getChildren().addAll(avatar, authorInfo);

        return authorBox;
    }

    private VBox createInteractionSection() {
        VBox section = new VBox(16);
        section.setPadding(new Insets(20, 0, 0, 0));

        HBox statsArea = createStatsArea();

        TextArea commentInput = createFloatingCommentInput();

        section.getChildren().addAll(statsArea, commentInput);
        return section;
    }

    private void loadLikeCount(Label likeCount) {
        if (liked) {
            likeCount.setText("♥️ " + like);
            likeCount.setStyle("-fx-text-fill: #ff4d6d;");
            likeCount.getStyleClass().add("likeCount");
        } else {
            likeCount.setText("🤍" + like);
            likeCount.setStyle("-fx-text-fill: #9ca3af;");
            likeCount.getStyleClass().add("likeCount");

        }
    }

    private HBox createStatsArea() {
        HBox statsArea = new HBox(24);
        statsArea.setAlignment(Pos.CENTER_LEFT);
        Label likeCount = new Label();
        loadLikeCount(likeCount);

        likeCount.setOnMouseClicked(event -> {
            liked = !liked;
            showLikeAnimation(event.getX(), event.getY());
            if (!liked) {
                like--;
                controller.desCurtir(CreateJsonForum.getSavedTitle(JSON_PATH), UserSession.getInstance().getUserName());
            } else {
                like++;
                controller.curtir(CreateJsonForum.getSavedTitle(JSON_PATH), UserSession.getInstance().getUserName());

            }
            loadLikeCount(likeCount);
        });

        Label commentCount = new Label("💭 " + comments);
        commentCount.setStyle("-fx-text-fill: #9ca3af;");
        commentCount.getStyleClass().add("likeCount");

        Label viewCount = new Label("👁 " + view);
        viewCount.setStyle("-fx-text-fill: #9ca3af;");
        commentCount.getStyleClass().add("likeCount");

        statsArea.getChildren().addAll(likeCount, commentCount, viewCount);
        return statsArea;
    }

    private TextArea createFloatingCommentInput() {
        TextArea commentInput = new TextArea();
        commentInput.setPromptText("Pressione Enter para comentar...");
        commentInput.setPrefRowCount(1);
        commentInput.setWrapText(true);
        commentInput.getStyleClass().add("floating-input");
        commentInput.getStyleClass().add("custom-text-area");

        commentInput.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                commentInput.setPrefRowCount(3);
            } else if (commentInput.getText().trim().isEmpty()) {
                commentInput.setPrefRowCount(1);
            }
        });

        commentInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && !event.isShiftDown()) {
                event.consume();
                String comment = commentInput.getText().trim();
                if (!comment.isEmpty()) {
                    handleCommentSubmission(comment, commentInput);
                    controller.addComentario(title, comment);
                }
            }
        });

        return commentInput;
    }

    private Label createCategoryLabel() {
        Label categoryLabel = new Label(category.toUpperCase());
        categoryLabel.getStyleClass().add("author");
        return categoryLabel;
    }

    private Label createTitle() {
        Label titleLabel = new Label(title);
        titleLabel.setWrapText(true);
        titleLabel.getStyleClass().add("title");
        return titleLabel;
    }

    private Label createDateLabel() {
        Label date = new Label(dateTime);
        date.getStyleClass().add("date");

        return date;
    }

    private StackPane createAuthorAvatar() {
        StackPane avatarCircle = new StackPane();
        avatarCircle.setMinSize(48, 48);
        avatarCircle.setMaxSize(48, 48);
        avatarCircle.setStyle(
                "-fx-background-color: #3b82f6; " +
                        "-fx-background-radius: 24;");

        Label nameInitial = new Label(getInitials(author));
        nameInitial.getStyleClass().add("nameInitial");


        avatarCircle.getChildren().add(nameInitial);
        return avatarCircle;
    }

    private void showLikeAnimation(double x, double y) {
        Label heart = new Label("❤️");
        heart.setStyle("-fx-font-size: 48px;");
        heart.setMouseTransparent(true);

        StackPane.setMargin(heart, new Insets(y - 24, 0, 0, x - 24));
        getChildren().add(heart);

        ParallelTransition animation = new ParallelTransition(
                createScaleTransition(heart),
                createFadeTransition(heart));

        animation.setOnFinished(e -> getChildren().remove(heart));
        animation.play();
    }

    private ScaleTransition createScaleTransition(Node node) {
        ScaleTransition scale = new ScaleTransition(Duration.millis(700), node);
        scale.setFromX(0.5);
        scale.setFromY(0.5);
        scale.setToX(1.5);
        scale.setToY(1.5);
        return scale;
    }

    private FadeTransition createFadeTransition(Node node) {
        FadeTransition fade = new FadeTransition(Duration.millis(700), node);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        return fade;
    }

    private void handleCommentSubmission(String comment, TextArea commentInput) {
        comments++;
        CreateJsonForum.saveForum(author, title, description, category, dateTime, view, like, comments, question,
                JSON_PATH);


        PauseTransition pause = new PauseTransition(Duration.millis(200));
        pause.setOnFinished(event -> {

        loadForumData();
            contentContainer.getChildren().clear();
            contentContainer.getChildren().addAll(
                createHeaderSection(),
                createMainContentSection(),
                createAuthorSection(),
                createQuestionSection(),
                createInteractionSection(),
                createCommentsSection());
            showCommentSuccessAnimation(commentInput);
        });
        pause.play();
        
        commentInput.clear();
        commentInput.setPromptText("Comentário enviado! Pressione Enter para comentar novamente...");
        updateCommentCount();
    }

    private void showCommentSuccessAnimation(TextArea commentInput) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), commentInput);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.7);
        fadeOut.setCycleCount(2);
        fadeOut.setAutoReverse(true);
        fadeOut.play();
    }

    private void handleLike() {
        CreateJsonForum.saveForum(author, title, description, category, dateTime, view, like, comments, question,
                JSON_PATH);
        updateLikeCount();
    }

    private void updateLikeCount() {
        lookup(".label").getParent().lookupAll(".label").stream()
                .filter(node -> node instanceof Label && ((Label) node).getText().contains("❤️"))
                .findFirst()
                .ifPresent(label -> ((Label) label).setText("❤️ " + like));
    }

    private void updateCommentCount() {
        lookup(".label").getParent().lookupAll(".label").stream()
                .filter(node -> node instanceof Label && ((Label) node).getText().contains("💭"))
                .findFirst()
                .ifPresent(label -> ((Label) label).setText("💭 " + comments));
    }

    private void addFadeInAnimation(Region node) {
        FadeTransition fade = new FadeTransition(Duration.millis(500), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    private static String getInitials(String name) {
        if (name == null || name.trim().isEmpty())
            return "";
        String[] parts = name.split(" ");
        StringBuilder initials = new StringBuilder();
        for (int i = 0; i < Math.min(2, parts.length); i++) {
            if (!parts[i].isEmpty()) {
                initials.append(parts[i].charAt(0));
            }
        }
        return initials.toString().toUpperCase();
    }
}