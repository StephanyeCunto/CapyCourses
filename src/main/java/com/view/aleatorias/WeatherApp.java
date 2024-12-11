package com.view.aleatorias;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;


public class WeatherApp extends Application {

    private static final String API_KEY = ""; // Substitua pela sua chave de API do OpenWeatherMap
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?lat=-21.1642&lon=-43.2121&units=metric&appid=YOUR_API_KEY" + API_KEY;

    @Override
    public void start(Stage primaryStage) {
        Label locationLabel = new Label("Localização: Carregando...");
        Label temperatureLabel = new Label("Temperatura: -- °C");

        VBox root = new VBox(10, locationLabel, temperatureLabel);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 20; -fx-font-size: 16;");

        Scene scene = new Scene(root, 400, 200);
        primaryStage.setTitle("Clima Atual");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Substitua "São Paulo" pela cidade desejada ou obtenha do usuário
        String city = "São Paulo";
        getWeatherData(city, locationLabel, temperatureLabel);
    }

    private void getWeatherData(String city, Label locationLabel, Label temperatureLabel) {
        String url = API_URL.replace("{CITY}", city);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                    JSONObject json = new JSONObject(response);
                    String location = json.getString("name");
                    double temperature = json.getJSONObject("main").getDouble("temp");

                    // Atualiza a interface JavaFX
                    javafx.application.Platform.runLater(() -> {
                        locationLabel.setText("Localização: " + location);
                        temperatureLabel.setText("Temperatura: " + temperature + " °C");
                    });
                })
                .exceptionally(e -> {
                    javafx.application.Platform.runLater(() -> {
                        locationLabel.setText("Erro ao obter localização.");
                        temperatureLabel.setText("Erro ao obter temperatura.");
                    });
                    e.printStackTrace();
                    return null;
                });
    }

    public static void main(String[] args) {
        launch();
    }
}
