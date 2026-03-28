package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        // --- 1. БОКОВОЕ МЕНЮ (Sidebar) ---
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(200);
        sidebar.setStyle("-fx-background-color: #2c3e50;"); // Темный цвет

        Label menuTitle = new Label("СКЛАД v1.1");
        menuTitle.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        Button btnInventory = createMenuButton("📦 Склад");
        Button btnOrders = createMenuButton("📜 Заказы");
        Button btnSettings = createMenuButton("⚙️ Настройки");

        sidebar.getChildren().addAll(menuTitle, new Separator(), btnInventory, btnOrders, btnSettings);

        // --- 2. ВЕРХНЯЯ ПАНЕЛЬ (Header) ---
        HBox header = new HBox();
        header.setPadding(new Insets(15, 20, 15, 20));
        header.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");

        TextField searchField = new TextField();
        searchField.setPromptText("Поиск товара...");
        searchField.setPrefWidth(300);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label userLabel = new Label("Администратор: Akbar");
        userLabel.setStyle("-fx-font-weight: bold;");

        header.getChildren().addAll(searchField, spacer, userLabel);

        // --- 3. ЦЕНТРАЛЬНАЯ ЧАСТЬ (Контент) ---
        // Сюда мы потом вставим таблицу, которую делает твой напарник
        StackPane contentArea = new StackPane();
        contentArea.setPadding(new Insets(20));
        contentArea.getChildren().add(new Label("Тут будет таблица товаров..."));

        // --- ГЛАВНАЯ КОМПОНОВКА ---
        BorderPane mainLayout = new BorderPane();
        mainLayout.setLeft(sidebar);   // Меню слева
        mainLayout.setTop(header);     // Шапка сверху
        mainLayout.setCenter(contentArea); // Контент в центре

        Scene scene = new Scene(mainLayout, 1000, 700);
        stage.setTitle("Warehouse Management System");
        stage.setScene(scene);
        stage.show();
    }

    // Вспомогательный метод для красивых кнопок
    private Button createMenuButton(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #ecf0f1; -fx-alignment: CENTER_LEFT; -fx-font-size: 14px; -fx-cursor: hand;");

        // Эффект при наведении
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-alignment: CENTER_LEFT; -fx-font-size: 14px;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #ecf0f1; -fx-alignment: CENTER_LEFT; -fx-font-size: 14px;"));

        return btn;
    }

    public static void main(String[] args) {
        launch(args);
    }
}