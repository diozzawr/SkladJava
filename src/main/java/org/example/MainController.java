package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, String> nameColumn;
    @FXML private TableColumn<Product, Integer> countColumn;
    @FXML private TableColumn<Product, Double> priceColumn;

    @FXML private TextField loginField, nameInput, countInput, priceInput, searchField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;
    @FXML private VBox adminPanel;
    @FXML private HBox userPanel;
    @FXML private Spinner<Integer> issueAmountSpinner;

    private final ObservableList<Product> productData = FXCollections.observableArrayList();
    private final String FILE_NAME = "products.txt"; // Имя файла для хранения

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        issueAmountSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1));

        // Загружаем данные из файла при запуске
        loadFromFile();

        FilteredList<Product> filteredData = new FilteredList<>(productData, p -> true);
        searchField.textProperty().addListener((obs, old, val) -> {
            filteredData.setPredicate(p -> val == null || val.isEmpty() || p.getName().toLowerCase().contains(val.toLowerCase()));
        });
        productTable.setItems(filteredData);
    }

    // --- ЛОГИКА ФАЙЛОВ ---

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Product p : productData) {
                // Записываем через разделитель ";"
                writer.println(p.getName() + ";" + p.getCount() + ";" + p.getPrice());
            }
        } catch (IOException e) {
            showAlert("Ошибка сохранения", "не удалось записать данные в файл.");
        }
    }

    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            // Если файла нет, добавляем начальные данные (твой список)
            addDefaultProducts();
            saveToFile();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            productData.clear();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    productData.add(new Product(parts[0], Integer.parseInt(parts[1]), Double.parseDouble(parts[2])));
                }
            }
        } catch (IOException | NumberFormatException e) {
            showAlert("Ошибка загрузки", "Файл данных поврежден или отсутствует.");
        }
    }

    private void addDefaultProducts() {
        productData.addAll(
                new Product("Материнская плата ASUS ROG", 12, 18500.0),
                new Product("SSD 512GB Samsung 980", 45, 5200.0),
                new Product("Процессор Intel Core i5-12400F", 8, 14200.0),
                new Product("Видеокарта RTX 3060 Ti", 3, 38000.0),
                new Product("Оперативная память 16GB DDR4", 60, 3900.0),
                new Product("Блок питания 750W Gold", 15, 8700.0),
                new Product("Корпус ATX Meshify", 7, 6500.0),
                new Product("Монитор 27' 144Hz IPS", 10, 21000.0),
                new Product("Жесткий диск 2TB Seagate", 20, 5800.0),
                new Product("Кулер для процессора AK400", 30, 2400.0),
                new Product("Клавиатура Механическая RGB", 15, 4200.0),
                new Product("Мышь Игровая Wireless", 25, 3100.0),
                new Product("Термопаста MX-4 4г", 100, 650.0),
                new Product("Кабель HDMI 2.1 (2м)", 50, 1200.0),
                new Product("Колонки 2.0 Edifier", 5, 9500.0)
        );
    }

    // --- ОБНОВЛЕННЫЕ МЕТОДЫ С АВТОСОХРАНЕНИЕМ ---

    @FXML
    private void handleIssue() {
        Product selected = productTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Внимание", "Выберите товар для выдачи");
            return;
        }

        int amount = issueAmountSpinner.getValue();
        if (selected.getCount() >= amount) {
            selected.setCount(selected.getCount() - amount);
            productTable.refresh();
            saveToFile(); // Сохраняем изменение остатка
            showAlert("Склад", "Выдано: " + amount + " ед. Осталось: " + selected.getCount());
        } else {
            showAlert("Ошибка", "Недостаточно товара на складе!");
        }
    }

    @FXML
    private void handleAdd() {
        if (validateAdminInput()) {
            productData.add(new Product(nameInput.getText(),
                    Integer.parseInt(countInput.getText()), Double.parseDouble(priceInput.getText())));
            saveToFile(); // Сохраняем новый товар
            nameInput.clear(); countInput.clear(); priceInput.clear();
        }
    }

    @FXML
    private void handleUpdatePrice() {
        Product selected = productTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                double newPrice = Double.parseDouble(priceInput.getText());
                if (newPrice > 0) {
                    selected.setPrice(newPrice);
                    productTable.refresh();
                    saveToFile(); // Сохраняем новую цену
                }
            } catch (Exception e) { showAlert("Ошибка", "Введите число"); }
        }
    }

    @FXML
    private void handleDelete() {
        Product selected = productTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            productData.remove(selected);
            saveToFile(); // Сохраняем удаление
        }
    }

    // Остальные методы (handleLogin, loginAsUser, validateAdminInput, showAlert) без изменений
    @FXML
    private void handleLogin() {
        if ("admin".equals(loginField.getText()) && "qwerty".equals(passwordField.getText())) {
            showPanel("Администратор", true, false);
        } else { showAlert("Доступ", "Неверный пароль"); }
    }

    @FXML private void loginAsUser() { showPanel("Пользователь", false, true); }

    private void showPanel(String role, boolean admin, boolean user) {
        statusLabel.setText("Роль: " + role);
        adminPanel.setVisible(admin); adminPanel.setManaged(admin);
        userPanel.setVisible(user); userPanel.setManaged(user);
    }

    private boolean validateAdminInput() {
        try {
            int c = Integer.parseInt(countInput.getText());
            double p = Double.parseDouble(priceInput.getText());
            return !nameInput.getText().isEmpty() && c >= 0 && p > 0;
        } catch (Exception e) { return false; }
    }

    private void showAlert(String title, String text) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title); a.setHeaderText(null); a.setContentText(text);
        a.showAndWait();
    }
}