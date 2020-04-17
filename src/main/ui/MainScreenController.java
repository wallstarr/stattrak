package ui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

// Represents the home screen for this application. Upon initializing the scene an animation will play and
// the user is given two options: Search for a player, or compare two players.

public class MainScreenController {

    // note - This is basically an overridden method for Controllers.
    // EFFECTS: initializes the main screen, which involves:
    //             - Setting the background image and its animation
    //             - Setting the logo image
    public void initialize() throws IOException {
        buttonSimulateTool.setOpacity(0);
        stattrakImage.setImage(new Image("file:data/logo.png"));
        ArrayList<Image> backgroundImages = new ArrayList<Image>();
        Image img1 = new Image("file:data/background.png");
        Image img2 = new Image("file:data/background2.jpeg");
        backgroundImages.add(img1);
        backgroundImages.add(img2);
        backgroundImages.add(img1);
        bgImage.setPreserveRatio(false);
        bgImage2.setPreserveRatio(false);
        for (int x = 0; x < backgroundImages.size() - 1; x++) {
            Image image1 = backgroundImages.get(x);
            Image image2 = backgroundImages.get(x + 1);
            KeyFrame initImg1 = new KeyFrame(Duration.seconds(0), new KeyValue(bgImage.imageProperty(), image1));
            KeyFrame initImg2 = new KeyFrame(Duration.seconds(0.1), new KeyValue(bgImage2.imageProperty(), image2));
            KeyFrame noOpImg2 = new KeyFrame(Duration.seconds(2), new KeyValue(bgImage2.opacityProperty(), 0));
            KeyFrame fadeInImg1 = new KeyFrame(Duration.seconds(2), new KeyValue(bgImage.opacityProperty(), 1));
            KeyFrame fadeInImg2 = new KeyFrame(Duration.seconds(8), new KeyValue(bgImage2.opacityProperty(), 1));
            KeyFrame noOpImg1 = new KeyFrame(Duration.seconds(8), new KeyValue(bgImage.opacityProperty(), 0));
            Timeline fading = new Timeline(initImg1, initImg2, noOpImg2, fadeInImg1, fadeInImg2, noOpImg1);
            fading.play();
        }
    }

    @FXML
    private ImageView stattrakImage;

    @FXML
    private ImageView bgImage;

    @FXML
    private ImageView bgImage2;

    @FXML
    private Button buttonSearchTool;

    @FXML
    private Button buttonCompareTool;

    @FXML
    private Button buttonSimulateTool;

    // EFFECTS: Sets the scene to the search tool scene
    @FXML
    void onSearchToolClicked(MouseEvent event) throws IOException {
        AnchorPane search = (AnchorPane) FXMLLoader.load(Main.class.getResource("StattrakGuiSearchScreen.fxml"));

        // Citation - : https://youtu.be/XCgcQTQCfJQ // Jaret Wright
        Stage window = (Stage)((Button)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(search));
        window.show();
    }

    // EFFECTS: sets the focus to the search tool button
    @FXML
    void onSearchToolEntered(MouseEvent event) {
        buttonSearchTool.requestFocus();
    }

    // EFFECTS: sets the scene to the comparison tool scene
    @FXML
    void onCompareToolClicked(MouseEvent event) throws IOException {
        String file = "StattrakGuiComparisonScreen.fxml";
        AnchorPane search = (AnchorPane) FXMLLoader.load(Main.class.getResource(file));

        // Citation for line 38 - : https://youtu.be/XCgcQTQCfJQ // Jaret Wright
        Stage window = (Stage)((Button)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(search));
        window.show();
    }

    // EFFECTS: sets the focus to the compare tool button
    @FXML
    void onCompareToolEntered(MouseEvent event) {
        buttonCompareTool.requestFocus();
    }

    // note: Not part of the final product for this project, I will try to implement this feature
    // in my own time during the summer, so please ignore this method.
    @FXML
    void onSimulateToolClicked(MouseEvent event) {

    }

    // note: same as onSimulateToolClicked();
    @FXML
    void onSimulateToolEntered(MouseEvent event) {
        buttonSimulateTool.requestFocus();
    }

}
