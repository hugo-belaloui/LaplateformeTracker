package Controller;

import Utils.StageManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import Utils.SessionManager;
import Model.User;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    public void handleLogin() {
        String email    = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // check fields are not empty
        if (email.isEmpty() || password.isEmpty()) {
            showError("Please fill in all fields.");
            return;
        }

        // look up user in database
        User user = User.findByEmail(email);

        // wrong email or password
        if (user == null || !user.getPassword().equals(password)) {
            showError("Wrong email or password.");
            return;
        }

        // save session
        SessionManager.setEmail(user.getEmail());
        SessionManager.setRole(user.getRole());

        // go to the right dashboard
        if (user.getRole().equals("admin")) {
            StageManager.switchScene("/View/AdminDashboard.fxml");
        } else if (user.getRole().equals("teacher")) {
            StageManager.switchScene("/View/TeacherDashboard.fxml");
        } else if (user.getRole().equals("student")) {
            StageManager.switchScene("/View/StudentDashboard.fxml");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText("Login failed");
        alert.setContentText(message);
        alert.showAndWait();
    }
}