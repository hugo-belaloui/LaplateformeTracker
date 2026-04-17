package Controller;

import View.StageManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        if (!username.equals("admin") && !password.equals("admin")) {
            errorLogin();
            return ;
        }
        if (StageManager.switchScene("/View/TeacherDashboard.fxml") == 1) {
                System.err.println("Error loading FXML file.");
                return ;
        }
    }

    private void errorLogin() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText("Login failed");
        alert.setContentText("Username or password entered is incorect.");
        alert.showAndWait();
    }
}
