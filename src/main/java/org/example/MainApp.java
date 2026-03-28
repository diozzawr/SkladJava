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

    // Выносим список данных наверх, чтобы все методы его видели
    private final ObservableList<Product> data = FXCollections.observableArrayList(
            new Product("iPhone 15 Pro", 10, 999.0),
            new Product("MacBook Air M2", 5, 1199.0)
    );

    @Override
    public void start(Stage stage) {
        // --- 1. ТАБЛИЦА ---
        TableView<Product> table = new TableView<>();
        table.setItems(data);

        TableColumn<Product, String> nameCol = new TableColumn<>("Наименование");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, Integer> countCol = new TableColumn<>("Кол-во");
        countCol.setCellValueFactory(new PropertyValueFactory<>("count"));

        TableColumn<Product, Double> priceCol = new TableColumn<>("Цена ($)");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        table.getColumns().addAll(nameCol, countCol, priceCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // --- 2. ПАНЕЛЬ ВВОДА (Нижняя часть) ---
        TextField nameInput = new TextField();
        nameInput.setPromptText("Название");

        TextField countInput = new TextField();
        countInput.setPromptText("Кол-во");
        countInput.setPrefWidth(70);

        TextField priceInput = new TextField();
        priceInput.setPromptText("Цена");
        priceInput.setPrefWidth(70);

        Button addButton = new Button("➕ Добавить");
        Button deleteButton = new Button("🗑 Удалить");

        // --- ЛОГИКА КНОПОК ---

        // Добавление товара
        addButton.setOnAction(e -> {
            try {
                String name = nameInput.getText();
                int count = Integer.parseInt(countInput.getText());
                double price = Double.parseDouble(priceInput.getText());

                if (!name.isEmpty()) {
                    data.add(new Product(name, count, price));
                    nameInput.clear();
                    countInput.clear();
                    priceInput.clear();
                }
            } catch (NumberFormatException ex) {
                // Если ввели буквы вместо цифр
                Alert alert = new Alert(Alert.AlertType.ERROR, "Вводи цифры в поля Кол-во и Цена!");
                alert.show();
            }
        });

        // Удаление выбранного товара
        deleteButton.setOnAction(e -> {
            Product selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                data.remove(selected);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Сначала выбери товар в таблице!");
                alert.show();
            }
        });

        HBox inputPanel = new HBox(10, nameInput, countInput, priceInput, addButton, deleteButton);
        inputPanel.setPadding(new Insets(10));

        // --- КОМПОНОВКА (Layout) ---
        VBox centerArea = new VBox(15, table, inputPanel);
        centerArea.setPadding(new Insets(20));

        // Боковое меню (как в прошлом шаге)
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(180);
        sidebar.setStyle("-fx-background-color: #2c3e50;");
        Label title = new Label("SKLAD ADMIN");
        title.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        sidebar.getChildren().addAll(title, new Separator());

        BorderPane root = new BorderPane();
        root.setLeft(sidebar);
        root.setCenter(centerArea);

        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Warehouse System v1.2");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}