package com.view.elements.Certificado;

import javafx.animation.FadeTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.util.Duration;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.rendering.PDFRenderer;
import com.singleton.UserSession;
import com.dto.PaginaPrincipalDTO;
import com.view.Modo;
import java.awt.print.PageFormat;
import java.awt.print.Paper;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

public class CertificateViewerModal {
    private final Stage modalStage;
    private double WIDTH;
    private double HEIGHT;
    private PDDocument document;
    private PDFRenderer pdfRenderer;
    private int currentPage;
    private ImageView imageView;
    private double scaleFactor = 1.0;

    public void updateDimensions(double WIDTH, double HEIGHT) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }

    public CertificateViewerModal(Window owner, PaginaPrincipalDTO course, File selectedDirectory) {
        updateDimensions(owner.getWidth(), owner.getHeight());
        modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initStyle(StageStyle.TRANSPARENT);
        modalStage.initOwner(owner);
        loadCertificate(course, selectedDirectory);
        setupCloseAnimation();
    }

    private void loadCertificate(PaginaPrincipalDTO course, File selectedDirectory) {
        String fileName = selectedDirectory.getAbsolutePath() + "/" +
                course.getTitle().replace(" ", "_") + "_" +
                UserSession.getInstance().getUserName().replace(" ", "_") +
                "_certificado.pdf";
        String path = fileName.toLowerCase();

        try {
            File certificateFile = new File(path);
            if (!certificateFile.exists()) {
                showError("Certificado não encontrado");
                return;
            }
            document = PDDocument.load(certificateFile);
            pdfRenderer = new PDFRenderer(document);
            showCertificate(course);
        } catch (Exception e) {
            showError("Erro ao carregar o certificado: " + e.getMessage());
        }
    }

    private void showCertificate(PaginaPrincipalDTO course) {
        StackPane backdrop = createBackdrop();
        VBox modalContainer = new VBox(20);
        modalContainer.setPadding(new Insets(30));

        modalContainer.setMaxWidth(WIDTH * 0.8);
        modalContainer.setMaxHeight(HEIGHT * 0.8);
        modalContainer.setMinHeight(HEIGHT * 0.8);
        modalContainer.setMinWidth(WIDTH * 0.8);

        modalContainer.getStyleClass().add("card");
        modalContainer.setEffect(createDropShadow());

        VBox header = createHeader(course);

        VBox content = new VBox(20);
        content.setPadding(new Insets(0, 20, 10, 20));
        content.setAlignment(Pos.CENTER);

        imageView = new ImageView();
        imageView.setPreserveRatio(true);
        currentPage = 0;
        renderPage(currentPage);

        StackPane imageContainer = new StackPane(imageView);
        imageContainer.setAlignment(Pos.CENTER);
        content.getChildren().add(imageContainer);

        VBox footer = new VBox(20);
        footer.setAlignment(Pos.BOTTOM_CENTER);
        VBox.setVgrow(footer, Priority.ALWAYS);
        footer.getChildren().add(createControls());

        modalContainer.getChildren().addAll(header, content, footer);

        backdrop.getChildren().add(modalContainer);
        Scene scene = new Scene(backdrop, WIDTH, HEIGHT);
        scene.setFill(Color.TRANSPARENT);
        modo(scene);

        modalStage.setScene(scene);
        modalStage.showAndWait();

        modalStage.setOnCloseRequest(e -> closeDocument());
    }

    private VBox createHeader(PaginaPrincipalDTO course) {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20, 20, 0, 20));

        Label titleLabel = createStyledLabel("Certificado salvo com sucesso", "Segoe UI Bold", 28);
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

    private HBox createControls() {
        HBox controls = new HBox(10);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(20));
    
        Button printButton = new Button("IMPRIMIR CERTIFICADO");
        printButton.getStyleClass().add("outline-button");
        printButton.setPrefHeight(40);
    
        printButton.setOnAction(e -> {
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
                    try {
                        printerJob.print();
                        showInfo("Documento enviado para impressão com sucesso!");
                    } catch (Exception ex) {
                        showError("Erro ao imprimir: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                showError("Erro ao preparar impressão: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        controls.getChildren().add(printButton);
        return controls;
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

    private DropShadow createDropShadow() {
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.color(0, 0, 0, 0.6));
        shadow.setRadius(25);
        shadow.setSpread(0.2);
        return shadow;
    }

    private void renderPage(int pageIndex) {
        try {
            BufferedImage bImage = pdfRenderer.renderImageWithDPI(pageIndex, 100 * (float) scaleFactor);
            WritableImage fxImage = SwingFXUtils.toFXImage(bImage, null);
            imageView.setImage(fxImage);
            adjustImageSize(bImage);
        } catch (IOException e) {
            showError("Erro ao renderizar o certificado: " + e.getMessage());
        }
    }

    private void adjustImageSize(BufferedImage bImage) {
        double aspectRatio = bImage.getWidth() / (double) bImage.getHeight();
        double maxWidth = WIDTH * 0.7;
        double maxHeight = HEIGHT * 0.5;
        double finalWidth, finalHeight;
        if (aspectRatio > maxWidth / maxHeight) {
            finalWidth = maxWidth;
            finalHeight = maxWidth / aspectRatio;
        } else {
            finalHeight = maxHeight;
            finalWidth = maxHeight * aspectRatio;
        }
        imageView.setFitWidth(finalWidth);
        imageView.setFitHeight(finalHeight);
    }

    private Label createStyledLabel(String text, String fontFamily, double fontSize) {
        Label label = new Label(text);
        label.setFont(Font.font(fontFamily, fontSize));
        label.setWrapText(true);
        return label;
    }

    private void modo(Scene scene) {
        String styleSheet = Modo.getInstance().getModo() ? "/com/estudante/paginaInicial/style/dark/style.css"
                : "/com/estudante/paginaInicial/style/ligth/style.css";
        scene.getStylesheets().add(getClass().getResource(styleSheet).toExternalForm());
    }

    private void setupCloseAnimation() {
        modalStage.setOnCloseRequest(event -> {
            event.consume();
            if (modalStage.getScene() == null)
                return;
            FadeTransition fade = new FadeTransition(Duration.millis(200), modalStage.getScene().getRoot());
            fade.setFromValue(1.0);
            fade.setToValue(0.0);
            fade.setOnFinished(e -> modalStage.hide());
            fade.play();
        });
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(message);
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
}