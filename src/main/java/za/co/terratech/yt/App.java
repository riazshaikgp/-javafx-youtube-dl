package za.co.terratech.yt;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    static Scene scene;
    static Stage stage;
    
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setResizable(false);
        stage.setTitle("Terratech Youtube Downloader");
        try {
            
            scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/MainWindow.fxml")),660,500);
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public static Scene getScene() {
        return scene;
    }

    public static Stage getStage() {
        return stage;
    }

    
}