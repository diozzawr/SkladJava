package org.example;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        Label titleLabel = new Label("Warehouse System");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label statusLabel = new Label("Статус: Система готова к работе ✅");
        statusLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #27ae60;");

        VBox root = new VBox(20, titleLabel, statusLabel);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #ecf0f1;");

        Scene scene = new Scene(root, 450, 350);

        stage.setTitle("Система управления складом v1.0");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}