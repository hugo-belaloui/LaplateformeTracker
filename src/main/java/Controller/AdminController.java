package controller;

import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.User;
import utils.SessionManager;
import utils.StageManager;
import java.util.ArrayList;

public class AdminController {

    @FXML private ListView<String> userListView;
    @FXML private Label emailLabel;
    @FXML private Label roleLabel;

    private ArrayList<User> users = new ArrayList<>();
    private User selectedUser = null;

    @FXML
    public void initialize() {
        loadUsers();

        // show details when a user is selected
        userListView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    for (User u : users) {
                        if (u.getEmail().equals(newValue)) {
                            selectedUser = u;
                            emailLabel.setText(u.getEmail());
                            roleLabel.setText(u.getRole());
                            break;
                        }
                    }
                }
            });
    }

    private void loadUsers() {
        users = User.findAll();
        ArrayList<String> emails = new ArrayList<>();
        for (User u : users) {
            emails.add(u.getEmail());
        }
        userListView.setItems(FXCollections.observableArrayList(emails));
    }

    @FXML
    public void handleAddUser() {
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Add User");
        dialog.setHeaderText("Create a new user");

        ButtonType addButton = new ButtonType("Add");
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField emailField    = new TextField();
        TextField passwordField = new TextField();
        TextField roleField     = new TextField();
        roleField.setPromptText("admin / teacher / student");

        grid.add(new Label("Email:"),    0, 0);
        grid.add(emailField,             1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField,          1, 1);
        grid.add(new Label("Role:"),     0, 2);
        grid.add(roleField,              1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(buttonClicked -> {
            if (buttonClicked == addButton) {
                String email    = emailField.getText().trim();
                String password = passwordField.getText().trim();
                String role     = roleField.getText().trim();

                if (!email.isEmpty() && !password.isEmpty() && !role.isEmpty()) {
                    return new User(null, email, password, role);
                }
            }
            return null;
        });

        Optional<User> result = dialog.showAndWait();
        result.ifPresent(user -> {
            user.save();
            loadUsers();
        });
    }

    @FXML
    public void handleDeleteUser() {
        if (selectedUser == null) return;

        selectedUser.delete();
        selectedUser = null;
        emailLabel.setText("-");
        roleLabel.setText("-");
        loadUsers();
    }

    @FXML
    public void handleLogout() {
        SessionManager.clear();
        StageManager.switchScene("/View/Login.fxml");
    }
}
