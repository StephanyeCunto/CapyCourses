package com.view.elements;

import javafx.animation.FadeTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.util.Duration;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.rendering.PDFRenderer;
import com.dto.BibliotecaDTO;
import com.dto.PaginaPrincipalDto;
import com.view.Modo;

import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;

public class BibliotecaModal {
    private final Stage modalStage;
    private double WIDTH;
    private double HEIGHT;
    private PDDocument document;
    private PDFRenderer pdfRenderer;
    private int currentPage;
    private int totalPages;

    private Label pageLabel;
    private double scaleFactor = 1.0;
    private Label zoomLabel;

    private ScrollPane scrollPane;
    private ImageView imageView;
    private VBox contentContainer; 

    private void loadDocument(String path, BibliotecaDTO bibliotecaDTO) {
        try {
            File documentFile = new File(path);
            if (!documentFile.exists()) {
                showError("Arquivo PDF não encontrado: " + path);
                return;
            }
            document = PDDocument.load(documentFile);
            pdfRenderer = new PDFRenderer(document);
            totalPages = document.getNumberOfPages();
            showDocument(bibliotecaDTO);
        } catch (Exception e) {
            showError("Erro ao carregar o documento: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showDocument(BibliotecaDTO bibliotecaDTO) {
        StackPane backdrop = createBackdrop();
        VBox modalContainer = createModalContainer();

        VBox header = createHeader(bibliotecaDTO);
        VBox content = createContent();
        VBox footer = createFooter();

        modalContainer.getChildren().addAll(header, content, footer);
        backdrop.getChildren().add(modalContainer);

        Scene scene = new Scene(backdrop, WIDTH, HEIGHT);
        scene.setFill(Color.TRANSPARENT);
        setupKeyboardShortcuts(scene);
        applyTheme(scene);

        modalStage.setScene(scene);
        modalStage.showAndWait();
    }

    private VBox createModalContainer() {
        VBox modalContainer = new VBox(20);
        modalContainer.setPadding(new Insets(30));
        modalContainer.setMaxWidth(WIDTH * 0.8);
        modalContainer.setMaxHeight(HEIGHT * 0.8);
        modalContainer.setMinHeight(HEIGHT * 0.8);
        modalContainer.setMinWidth(WIDTH * 0.8);
        modalContainer.getStyleClass().add("card");
        modalContainer.setEffect(createDropShadow());
        return modalContainer;
    }

    private VBox createFooter() {
        VBox footer = new VBox(20);
        footer.setAlignment(Pos.BOTTOM_CENTER);
        VBox.setVgrow(footer, Priority.NEVER);
        footer.getChildren().add(createControls());
        return footer;
    }

    private HBox createControls() {
        HBox controls = new HBox(10);
        controls.setPadding(new Insets(20));
    
        Button prevButton = new Button("←");
        Button nextButton = new Button("→");
        prevButton.getStyleClass().addAll("simple-button");
        nextButton.getStyleClass().addAll("simple-button");
        nextButton.setPrefHeight(30);
        prevButton.setPrefHeight(30);
    
        pageLabel = new Label((currentPage + 1) + " / " + totalPages);
        pageLabel.setStyle("-fx-font-size: 14px;");
        pageLabel.getStyleClass().add("title");
    
        prevButton.setOnAction(e -> navigatePages(-1));
        nextButton.setOnAction(e -> navigatePages(1));
    
        Button zoomOutButton = new Button("-");
        Button zoomInButton = new Button("+");
        zoomInButton.setPrefHeight(30);
        zoomOutButton.setPrefHeight(30);

        zoomLabel = new Label(String.format("%.0f%%", scaleFactor * 100));
        zoomLabel.setStyle("-fx-font-size: 14px;");
        zoomLabel.getStyleClass().add("titulo");

        zoomOutButton.getStyleClass().add("simple-button");
        zoomInButton.getStyleClass().add("simple-button");
    
        zoomOutButton.setOnAction(e -> adjustZoom(-0.1));
        zoomInButton.setOnAction(e -> adjustZoom(0.1));
    
        Button saveButton = createActionButton("SALVAR PDF", "outline-button", e -> savePDF());
        Button printButton = createActionButton("IMPRIMIR PDF", "simple-button", e -> printPDF());
        saveButton.setPrefHeight(30);
        printButton.setPrefHeight(30);
    
        HBox navigationControls = new HBox(10, prevButton, pageLabel, nextButton);
        navigationControls.setAlignment(Pos.CENTER_LEFT);
    
        HBox zoomControls = new HBox(10, zoomOutButton, zoomLabel, zoomInButton);
        zoomControls.setAlignment(Pos.CENTER_LEFT);
    
        HBox buttonsSavePrint = new HBox(10, saveButton, printButton);
        buttonsSavePrint.setAlignment(Pos.CENTER_RIGHT);
    
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
    
        controls.getChildren().addAll(navigationControls, new Separator(Orientation.VERTICAL), zoomControls, spacer, buttonsSavePrint);
    
        return controls;
    }

    private Button createActionButton(String text, String styleClass,
            javafx.event.EventHandler<javafx.event.ActionEvent> action) {
        Button button = new Button(text);
        button.getStyleClass().add(styleClass);
        button.setPrefHeight(40);
        button.setOnAction(action);
        return button;
    }

    private void navigatePages(int delta) {
        int newPage = currentPage + delta;
        if (newPage >= 0 && newPage < totalPages) {
            currentPage = newPage;
            renderPage(currentPage);
            updatePageLabel();
        }
    }

    private void updateZoomLabel() {
        if (zoomLabel != null) {
            zoomLabel.setText(String.format("%.0f%%", scaleFactor * 100));
            zoomLabel.getStyleClass().add("title");
        }
    }

    private void savePDF() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(modalStage);
        if (file != null) {
            try {
                document.save(file);
                showInfo("PDF salvo com sucesso!");
            } catch (IOException ex) {
                showError("Erro ao salvar o PDF: " + ex.getMessage());
            }
        }
    }

    private void printPDF() {
        try {
            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
            if (printServices.length == 0) {
                showError("Nenhuma impressora encontrada");
                return;
            }

            PageFormat pageFormat = new PageFormat();
            Paper paper = new Paper();
            paper.setSize(612, 792);
            paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight());
            pageFormat.setPaper(paper);

            PDFPrintable printable = new PDFPrintable(document);
            java.awt.print.PrinterJob printerJob = java.awt.print.PrinterJob.getPrinterJob();
            printerJob.setPrintable(printable, pageFormat);

            if (printerJob.printDialog()) {
                printerJob.print();
                showInfo("Documento enviado para impressão com sucesso!");
            }
        } catch (Exception ex) {
            showError("Erro ao imprimir: " + ex.getMessage());
        }
    }

    private void updatePageLabel() {
        pageLabel.setText((currentPage + 1) + " / " + totalPages);
    }

    private StackPane createBackdrop() {
        StackPane backdrop = new StackPane();
        backdrop.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        backdrop.setOnMouseClicked(event -> {
            if (event.getTarget() == backdrop) {
                modalStage.close();
            }
        });
        return backdrop;
    }

    private VBox createHeader(BibliotecaDTO bibliotecaDTO) {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20, 20, 0, 20));

        Label titleLabel = createStyledLabel(bibliotecaDTO.getTitle(), "Segoe UI Bold", 28);
        titleLabel.getStyleClass().add("title");

        Button closeButton = new Button("X");
        closeButton.getStyleClass().add("register-button");
        closeButton.setOnAction(e -> modalStage.close());
        HBox closeButtonBox = new HBox(closeButton);
        closeButtonBox.setAlignment(Pos.TOP_RIGHT);

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        header.getChildren().add(titleLabel);

        HBox mainHeader = new HBox();
        mainHeader.getChildren().addAll(header, closeButtonBox);
        HBox.setHgrow(header, Priority.ALWAYS);

        content.getChildren().addAll(mainHeader);

        return content;
    }

    private Label createStyledLabel(String text, String fontFamily, double fontSize) {
        Label label = new Label(text);
        label.setFont(Font.font(fontFamily, fontSize));
        label.setWrapText(true);
        return label;
    }

    private DropShadow createDropShadow() {
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.color(0, 0, 0, 0.6));
        shadow.setRadius(25);
        shadow.setSpread(0.2);
        return shadow;
    }

    private void setupCloseAnimation() {
        modalStage.setOnCloseRequest(event -> {
            event.consume();
            if (modalStage.getScene() != null) {
                FadeTransition fade = new FadeTransition(Duration.millis(200), modalStage.getScene().getRoot());
                fade.setFromValue(1.0);
                fade.setToValue(0.0);
                fade.setOnFinished(e -> {
                    modalStage.hide();
                    closeDocument();
                });
                fade.play();
            }
        });
    }

    private void applyTheme(Scene scene) {
        String styleSheet = Modo.getInstance().getModo()
                ? "/com/estudante/paginaInicial/style/dark/style.css"
                : "/com/estudante/paginaInicial/style/ligth/style.css";
        scene.getStylesheets().add(getClass().getResource(styleSheet).toExternalForm());
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(modalStage);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(modalStage);
        alert.showAndWait();
    }

    private void closeDocument() {
        try {
            if (document != null) {
                document.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void setupKeyboardShortcuts(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.isControlDown()) {
                switch (event.getCode()) {
                    case EQUALS: 
                    case PLUS:
                    case ADD:
                        adjustZoom(0.1);
                        break;
                    case MINUS:
                    case SUBTRACT:
                        adjustZoom(-0.1);
                        break;
                }
            } else {
                switch (event.getCode()) {
                    case LEFT:
                        navigatePages(-1);
                        break;
                    case RIGHT:
                        navigatePages(1);
                        break;
                    case ESCAPE:
                        modalStage.close();
                        break;
                }
            }
        });
        scene.setOnScroll(event -> {
            if (event.isControlDown()) {
                double delta = event.getDeltaY() > 0 ? 0.1 : -0.1;
                adjustZoom(delta);
                event.consume();
            }
        });
    }

    public void updateDimensions(double WIDTH, double HEIGHT) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }

    public BibliotecaModal(Window owner, String selectedDirectory, BibliotecaDTO bibliotecaDTO) {
        updateDimensions(owner.getWidth(), owner.getHeight());
        modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initStyle(StageStyle.TRANSPARENT);
        modalStage.initOwner(owner);
        loadDocument(selectedDirectory, bibliotecaDTO);
        setupCloseAnimation();
    }
   
    private VBox createContent() {
        contentContainer = new VBox(20);
        contentContainer.setPadding(new Insets(0));
        contentContainer.setAlignment(Pos.TOP_CENTER);
    
        VBox pdfContainer = new VBox();
        pdfContainer.setMinHeight(HEIGHT * 0.5);
        pdfContainer.setMaxHeight(HEIGHT * 0.5);
        pdfContainer.setMinWidth(WIDTH * 0.7);
        pdfContainer.setMaxWidth(WIDTH * 0.7);
        pdfContainer.setAlignment(Pos.TOP_CENTER);

        imageView = new ImageView();
        imageView.setPreserveRatio(true);
    
        scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-padding: 0;");
        scrollPane.setPannable(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        
        StackPane imageContainer = new StackPane(imageView);
        imageContainer.setAlignment(Pos.TOP_CENTER);
        scrollPane.setContent(imageContainer);

        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        
        pdfContainer.getChildren().add(scrollPane);
        contentContainer.getChildren().add(pdfContainer);
    
        currentPage = 0;
        renderPage(currentPage);
    
        return contentContainer;
    }
    
    private void renderPage(int pageIndex) {
        try {
            float dpi = 150 * (float) scaleFactor;
            BufferedImage bImage = pdfRenderer.renderImageWithDPI(pageIndex, dpi);
            WritableImage fxImage = SwingFXUtils.toFXImage(bImage, null);
            imageView.setImage(fxImage);
    
            double maxWidth = WIDTH * 0.7;
            double maxHeight = HEIGHT * 0.5;
            
            double widthScale = maxWidth / bImage.getWidth();
            double heightScale = maxHeight / bImage.getHeight();
            double scale = Math.min(widthScale, heightScale);
            
            scale *= scaleFactor;
            
            double finalWidth = bImage.getWidth() * scale;
            double finalHeight = bImage.getHeight() * scale;
            
            imageView.setFitWidth(finalWidth);
            imageView.setFitHeight(finalHeight);
            
            StackPane imageContainer = (StackPane) scrollPane.getContent();
            imageContainer.setMinWidth(maxWidth);
            imageContainer.setMinHeight(Math.max(maxHeight, finalHeight));
            
            if (finalHeight <= maxHeight) {
                imageContainer.setAlignment(Pos.TOP_CENTER);
            }
            
        } catch (IOException e) {
            showError("Erro ao renderizar a página: " + e.getMessage());
        }
    }

    private void adjustZoom(double delta) {
        double newScale = scaleFactor + delta;
        if (newScale >= 0.25 && newScale <= 3.0) {
            scaleFactor = newScale;
            
            double oldHvalue = scrollPane.getHvalue();
            double oldVvalue = scrollPane.getVvalue();
            
            renderPage(currentPage);
            
            scrollPane.setHvalue(oldHvalue);
            scrollPane.setVvalue(oldVvalue);
             zoomLabel.getStyleClass().add("title");
            updateZoomLabel();
        }
    }
}