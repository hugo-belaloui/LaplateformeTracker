package Utils;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageManager {
    private static Stage _currentStage;

    public static void setStage(Stage newStage) {
        StageManager._currentStage = newStage;
    }

    public static int switchScene(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(StageManager.class.getResource(fxmlPath));
            _currentStage.setScene(new Scene(root));
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
    }
}