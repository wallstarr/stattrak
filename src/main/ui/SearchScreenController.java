package ui;


import model.Player;
import exceptions.PlayerAlreadyFavoritedException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

// This is a controller for "StatTrakGuiSearchScreen.fxml", which enables the user
// to search for player statistics and favorite players, and save them to file.

public class SearchScreenController {

    // Player related
    public static final int CURRENTYEAR = 2019;
    private Player currentPlayer;
    private int currentPlayerId;
    private JSONObject currentPlayerData;
    private JSONObject currentPlayerStats;

    private FavoritedPlayersTab favoritedPlayersTab;

    private StatGetter statGetter;

    // Saving/Loading related
    private static final String CURRENT_PLAYERS_FILE = "./data/SavedCurrentPlayers";
    private static final String PAST_PLAYERS_FILE = "./data/SavedPastPlayers";

    // Graphics/Visual interface related

    @FXML
    private Rectangle darkTintRectangle;

    @FXML
    private ImageView searchIcon;

    @FXML
    private ImageView logoImage;

    @FXML
    public ImageView backgroundImage;

    @FXML
    private Text errorText;

    @FXML
    private TextField nameSearchBar;

    @FXML
    private TextField yearSearchBar;

    @FXML
    public ListView activePlayersTab;

    @FXML
    public ListView pastPlayersTab;

    @FXML
    public Button favoriteButton;

    @FXML
    public Button unfavoriteButton;

    @FXML
    public Button searchButton;

    @FXML
    public Text pointsText;

    @FXML
    public Text reboundsText;

    @FXML
    public Text assistsText;

    @FXML
    public Text trueShootingText;

    @FXML
    public Text minutesText;

    @FXML
    public Text stealsText;

    @FXML
    public Text blocksText;

    @FXML
    public Text tovText;

    @FXML
    public Text foulsText;

    @FXML
    public Text gamesText;

    @FXML
    public Text fgpctText;

    @FXML
    public Text fgaText;

    @FXML
    public Text fgmText;

    @FXML
    public Text threepctText;

    @FXML
    public Text thrpaText;

    @FXML
    public Text thrpmText;

    @FXML
    public Text ftpctText;

    @FXML
    public Text ftaText;

    @FXML
    public Text ftmText;

    @FXML
    public Text statText;

    @FXML
    public Text positionText;

    @FXML
    public Text firstNameText;

    @FXML
    public Text lastNameText;

    @FXML
    public Text teamText;

    @FXML
    public Text heightText;

    @FXML
    public Text weightText;


    // note - This is basically an overridden method for Controllers.
    // EFFECTS: initializes the main screen, which involves:
    //             - Setting the beginning button visibility (invisible)
    //             - Setting the style for the ListViews of the favorited players tab
    //             - Opacities for aesthetic
    public void initialize() {
        ArrayList<Player> activePlayers = new ArrayList<Player>();
        ArrayList<Player> pastPlayers = new ArrayList<Player>();
        favoritedPlayersTab = new FavoritedPlayersTab(activePlayersTab, pastPlayersTab, activePlayers, pastPlayers);
        activePlayersTab.setStyle("-fx-font: 24px\"Hiragino Sans W4\";");
        pastPlayersTab.setStyle("-fx-font: 20px\"Hiragino Sans W4\";");
        darkTintRectangle.setOpacity(0.3);
        Image searchImage = new Image("file:data/searchIcon.png");
        searchIcon.setImage(searchImage);
        searchIcon.setOpacity(0.98);
        searchButton.setOpacity(0.5);
        favoriteButton.setVisible(false);
        unfavoriteButton.setVisible(false);
        statGetter = new StatGetter(errorText);
    }

    // MODIFIES: this
    // EFFECTS: displays the player clicked in the active players tab
    @FXML
    public void onActivePlayersClicked(MouseEvent mouseEvent) {
        String playerName = activePlayersTab.getSelectionModel().getSelectedItem().toString();
        currentPlayer = favoritedPlayersTab.getActivePlayer(playerName);
        displayDataAndStats();
        searchButton.requestFocus();
    }

    // MODIFIES: this
    // EFFECTS: displays the player clicked in the past players tab
    @FXML
    public void onPastPlayersClicked(MouseEvent mouseEvent) {
        String playerName = pastPlayersTab.getSelectionModel().getSelectedItem().toString();
        currentPlayer = favoritedPlayersTab.getPastPlayer(playerName);
        displayDataAndStats();
        searchButton.requestFocus();
    }

    // MODIFIES: this
    // EFFECTS: favorites a player, placing him in the favorited players tab
    @FXML
    public void onFavoriteClicked(MouseEvent event) {
        try {
            favoritedPlayersTab.favoritePlayer(currentPlayer);
        } catch (PlayerAlreadyFavoritedException e) {
            // This exception will only get caught if for some reason setButtonVisibilityAndFocus() fails to
            // update the favorited status of the currently viewed player, ie: if a favorited player is infact already
            // favorited but the "Favorite" button is still visible (as opposed to the "Unfavorite" button.
            errorText.setText("ERROR: This player has already been favorited");
            return;
        }
        setButtonVisibilityAndFocus();
    }


    // MODIFIES: this
    // EFFECTS: unfavorites a player, removing him from the favorited players tab
    @FXML
    public void onUnfavoriteClicked(MouseEvent event) {
        favoritedPlayersTab.unfavoritePlayer(currentPlayer);
        setButtonVisibilityAndFocus();
    }


    // MODIFIES: this
    // EFFECTS: loads saved favorited players from file
    @FXML
    public void onLoadClicked(MouseEvent mouseEvent) {
        favoritedPlayersTab.loadPlayers();
        setButtonVisibilityAndFocus();
        searchButton.requestFocus();
    }

    // MODIFIES: SavedCurrentPlayers, SavedPastPlayers
    // EFFECTS: saves the favorited players to file
    @FXML
    public void onSaveClicked(MouseEvent mouseEvent) {
        favoritedPlayersTab.savePlayers();
    }

    // EFFECTS: displays the data and stats of the man player.
    private void displayDataAndStats() {
        displayMainStats();
        displaySecondaryStats();
        displayEfficiency();
        displayData();
        setLogo();
        setTeamStadiumBackground();
        setButtonVisibilityAndFocus();
    }

    // EFFECTS: sets the visibilities of the buttons, depending on if the player is favorited or not
    private void setButtonVisibilityAndFocus() {
        if (favoritedPlayersTab.playerIsFavorited(currentPlayer)) {
            unfavoriteButton.setVisible(true);
            favoriteButton.setVisible(false);
            unfavoriteButton.requestFocus();
        } else {
            unfavoriteButton.setVisible(false);
            favoriteButton.setVisible(true);
            favoriteButton.requestFocus();
        }
    }

    // EFFECTS: displays the player data (like height, weight, team, etc) of the current player
    private void displayData() {
        String position = currentPlayer.getPosition();
        if (position.equals("F-C") || position.equals("C-F")) {
            positionText.setText("C");
        } else if (position.equals("G-F") || position.equals("F-G")) {
            positionText.setText("F");
        } else {
            positionText.setText(position);
        }
        firstNameText.setText(currentPlayer.getFirstName());
        lastNameText.setText(currentPlayer.getLastName());
        String weight = currentPlayer.getWeight();
        if (weight.equals("0 lbs")) {
            weightText.setText("WT: N/A");
        } else {
            weightText.setText("WT: " + currentPlayer.getWeight());
        }
        String height = currentPlayer.getHeight();
        if (height.equals("0-0")) {
            heightText.setText("HT: N/A");
        } else {
            heightText.setText("HT: " + currentPlayer.getHeight());
        }
        teamText.setText("Team: " + currentPlayer.getTeamAbbreviation());
    }

    // EFFECTS: displays the main 5 game stats of the current player:
    //                - Points
    //                - Rebounds
    //                - Assists
    //                - True Shooting
    //                - Minutes per game
    private void displayMainStats() {
        pointsText.setText(currentPlayer.getStatsAsNums().get("pointspergame") + "");
        reboundsText.setText(currentPlayer.getStatsAsNums().get("reboundspergame") + "");
        assistsText.setText(currentPlayer.getStatsAsNums().get("assistspergame") + "");
        trueShootingText.setText(toThreeDecimalPlaces(currentPlayer.getStatsAsNums().get("trueshootingpct") + ""));
        minutesText.setText(currentPlayer.getStatsAsNums().get("minutespergame") + "");
        int playerYear = currentPlayer.getYearOfSeason();
        int playerYearPlusOne = playerYear + 1;
        statText.setText("Stats (" + playerYear + "-" + playerYearPlusOne + "):");
    }

    // EFFECTS: displays the secondary stats of the current player.
    private void displaySecondaryStats() {
        stealsText.setText(currentPlayer.getStatsAsNums().get("stealspergame") + "");
        blocksText.setText(currentPlayer.getStatsAsNums().get("blockspergame") + "");
        tovText.setText(currentPlayer.getStatsAsNums().get("turnoverspergame") + "");
        foulsText.setText(currentPlayer.getStatsAsNums().get("foulspergame") + "");
        String gamesplayed = currentPlayer.getStatsAsNums().get("gamesplayed") + "";
        if (gamesplayed.length() == 3) {
            gamesText.setText(gamesplayed.substring(0, 1));
        } else {
            gamesText.setText(gamesplayed.substring(0, 2));
        }
    }

    // EFFECTS: displays the efficiencies of the current player
    private void displayEfficiency() {
        fgpctText.setText(toThreeDecimalPlaces(currentPlayer.getStatsAsNums().get("fieldgoalpct") + ""));
        threepctText.setText(toThreeDecimalPlaces(currentPlayer.getStatsAsNums().get("threepointpct") + ""));
        ftpctText.setText(toThreeDecimalPlaces(currentPlayer.getStatsAsNums().get("freethrowpct") + ""));
        fgaText.setText(currentPlayer.getStatsAsNums().get("fieldgoalsattempted") + "");
        thrpaText.setText(currentPlayer.getStatsAsNums().get("threepointattempted") + "");
        ftaText.setText(currentPlayer.getStatsAsNums().get("freethrowsattempted") + "");
        fgmText.setText(currentPlayer.getStatsAsNums().get("fieldgoalsmade") + "");
        thrpmText.setText(currentPlayer.getStatsAsNums().get("threepointmade") + "");
        ftmText.setText(currentPlayer.getStatsAsNums().get("freethrowsmade") + "");
    }

    // REQUIRES: pct is a percentage like "0.494" or "0.65"
    private String toThreeDecimalPlaces(String pct) {
        if (pct.length() < 5) {
            for (; pct.length() < 5; ) {
                pct += "0";
            }
        }
        return pct;
    }

    // EFFECTS: sets the current player's team's logo
    private void setLogo() {
        String teamAbbreviation = currentPlayer.getTeamAbbreviation().toLowerCase();
        Image logo = new Image("file:data/teamlogos/" + teamAbbreviation + ".png");
        logoImage.setImage(logo);
        logoImage.setOpacity(0.4);
    }

    // EFFECTS: sets the current player's team's stadium background
    private void setTeamStadiumBackground() {
        String teamAbbreviation = currentPlayer.getTeamAbbreviation().toLowerCase();
        Image background = new Image("file:data/backgroundImages/" + teamAbbreviation + ".png");
        backgroundImage.setImage(background);
        backgroundImage.setPreserveRatio(false);
    }

    @FXML
    private void onBackToMenuButtonClicked(MouseEvent event) throws IOException {
        AnchorPane main = (AnchorPane) FXMLLoader.load(Main.class.getResource("StattrakGuiMainScreen.fxml"));

        // Citation for line 38 - : https://youtu.be/XCgcQTQCfJQ // Jaret Wright
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(main));
        window.show();
    }


    // MODIFIES: this
    // EFFECTS: Searches for the player inputted. If player inputted is invalid, then Error text will show.
    //          If player is valid, then the players stats and data will be displayed. 
    @FXML
    public void onSearchButtonClicked() {
        String nameInput = nameSearchBar.getText();
        boolean playerExists = !invalidInputtedNameChecker(nameInput);
        if (!playerExists) {
            return;
        }
        String yearInput = yearSearchBar.getText();
        JSONObject potentialPlayerData = statGetter.getPlayerData(nameInput);
        int id = potentialPlayerData.getInt("id");
        boolean validYear = strIsNum(yearInput) && !invalidInputtedYearChecker(Integer.parseInt(yearInput), id);
        if (!validYear) {
            return;
        }
        currentPlayerData = potentialPlayerData;
        currentPlayerId = id;
        int year = Integer.parseInt(yearInput);
        currentPlayerStats = statGetter.extractAndGetPlayerStatistics(currentPlayerId, year);
        currentPlayer = new Player(currentPlayerData, currentPlayerStats);
        currentPlayer.setYearOfSeason(year);
        errorText.setText("");
        displayDataAndStats();
    }

    // EFFECTS: checks if the inputted string is a number, and sets error if input is not a number
    private boolean strIsNum(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            errorText.setText("ERROR: Input a year, not characters");
            return false;
        }
    }

    // EFFECTS: checks if the inputted year is valid for the respective player.
    private boolean invalidInputtedYearChecker(int yearofseason, int id) {
        if (yearofseason > CURRENTYEAR || yearofseason < 1990) {
            // put something to indicate that the inputted season must be between the current year and 1990
            errorText.setText("ERROR: Inputted year must be between 1990 and " + CURRENTYEAR);
            return true;
        }
        JSONObject metadata = statGetter.extractAndGetPlayerStatistics(id, yearofseason).getJSONObject("meta");
        int gamesplayed = metadata.getInt("total_count");
        if (gamesplayed == 0)  {
            errorText.setText("ERROR: Inputted player did not play during this season");
            return true;
        }
        return false;
    }

    // Helper function to onNameEntered
    // EFFECTS: returns true if the name has !(1 space || 2 spaces), invalid characters, or simply is not an actual
    //          player that has played
    private boolean invalidInputtedNameChecker(String name) {
        boolean containsnotoneortwospaces = (numberofspaces(name) != 2 && numberofspaces(name) != 1);
        boolean containsillegalchars = illegalCharsChecker(name);
        if (containsillegalchars || containsnotoneortwospaces) {
            errorText.setText("ERROR: Inputted name does not have one space or contains illegal characters.");
            return true;
        }
        boolean playerdoesntexist = statGetter.getPlayerData(name).isEmpty();
        if (playerdoesntexist) {
            errorText.setText("ERROR: This player does not exist");
            return true;
        }
        return false;
    }

    // Helper function to invalidInputtedNameChecker()
    // EFFECTS: returns the number of spaces in a string
    private int numberofspaces(String name) {
        int numofspaces = 0;
        for (char c : name.toCharArray()) {
            if (c == ' ') {
                numofspaces++;
            }
        }
        return numofspaces;
    }

    // Helper function to invalidInputtedNameChecker()
    // EFFECTS: returns true if there are invalid chars (!, &, ?, or /, which can mess up requesting data from
    //          the servers.
    private boolean illegalCharsChecker(String name) {
        boolean hasillegalchars = false;
        for (char c: name.toCharArray()) {
            if (c == '&' || c == '!' || c == '?' || c == '/') {
                hasillegalchars = true;
            }
        }
        return hasillegalchars;
    }
}
