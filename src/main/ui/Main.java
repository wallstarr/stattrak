package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

// This Main class launches the program, and sets the scene to home screen (StatTrakGuiMainScreen.fxml)

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch();
    }

    // Citation: Dennis Kriechel
    // https://www.youtube.com/watch?v=uPm1n-cyupU&ab_channel=DennisKriechel
    @Override
    public void start(Stage stage) throws Exception {
        Parent mainPane = (AnchorPane) FXMLLoader.load(Main.class.getResource("StattrakGuiMainScreen.fxml"));
        stage.setScene(new Scene(mainPane));
        stage.show();
        stage.setResizable(false);
    }
}