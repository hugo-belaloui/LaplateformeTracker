import Utils.StageManager;
import Db.InitDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import atlantafx.base.theme.PrimerLight;

public class LaPlateformeTracker extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());

        InitDatabase.init(); 

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Login.fxml"));
        VBox root = loader.load();

        StageManager.setStage(primaryStage);
        primaryStage.setTitle("LaPlateformeTracker");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}