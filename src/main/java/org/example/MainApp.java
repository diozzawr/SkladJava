package org.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        // --- 1. БОКОВОЕ МЕНЮ (Sidebar) ---
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(200);
        sidebar.setStyle("-fx-background-color: #2c3e50;");

        Label menuTitle = new Label("СКЛАД v1.1");
        menuTitle.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        sidebar.getChildren().addAll(menuTitle, new Separator(),
                createMenuButton("📦 Склад"),
                createMenuButton("📜 Заказы"),
                createMenuButton("⚙️ Настройки"));

        // --- 2. ВЕРХНЯЯ ПАНЕЛЬ (Header) ---
        HBox header = new HBox();
        header.setPadding(new Insets(15, 20, 15, 20));
        header.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");

        TextField searchField = new TextField();
        searchField.setPromptText("Поиск товара...");
        searchField.setPrefWidth(300);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(searchField, spacer, new Label("Администратор: Akbar"));

        // --- 3. ТАБЛИЦА (Контент от напарника) ---
        TableView<Product> table = new TableView<>();

        TableColumn<Product, String> nameCol = new TableColumn<>("Наименование");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name")); // Берет из getName()

        TableColumn<Product, Integer> countCol = new TableColumn<>("Кол-во");
        countCol.setCellValueFactory(new PropertyValueFactory<>("count")); // Берет из getCount() - ВАЖНО

        TableColumn<Product, Double> priceCol = new TableColumn<>("Цена ($)");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price")); // Берет из getPrice()

        table.getColumns().addAll(nameCol, countCol, priceCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<Product> data = FXCollections.observableArrayList(
                new Product("iPhone 15 Pro", 10, 999.0),
                new Product("MacBook Air M2", 5, 1199.0),
                new Product("AirPods Pro 2", 25, 249.0)
        );
        table.setItems(data);

        // --- ГЛАВНАЯ КОМПОНОВКА ---
        BorderPane mainLayout = new BorderPane();
        mainLayout.setLeft(sidebar);
        mainLayout.setTop(header);
        mainLayout.setCenter(new StackPane(table)); // Таблица в центре
        StackPane.setMargin(table, new Insets(20));

        Scene scene = new Scene(mainLayout, 1000, 700);
        stage.setTitle("Warehouse Management System");
        stage.setScene(scene);
        stage.show();
    }

    private Button createMenuButton(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-alignment: CENTER_LEFT; -fx-cursor: hand;");
        return btn;
    }

    public static void main(String[] args) { launch(args); }
}