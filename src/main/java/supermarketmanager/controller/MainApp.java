package supermarketmanager.controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class MainApp extends Application {
    Stage primaryStage;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/supermarketmanager/ui/UI.fxml"));
        Scene scene = new Scene(loader.load());
        this.primaryStage = stage;
        primaryStage.setTitle("Supermarket Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
