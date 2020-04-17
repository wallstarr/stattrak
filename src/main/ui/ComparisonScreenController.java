package ui;

import exceptions.PlayerNotFoundException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Player;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

// This is a controller for "StatTrakGuiComparisonScreen.fxml", which enables the user
// to compare two players' statistics.

public class ComparisonScreenController {

    public static final int CURRENTYEAR = 2019;

    private Player playerOne;
    private int playerOneId;
    private JSONObject playerOneData;
    private JSONObject playerOneStats;

    private Player playerTwo;
    private int playerTwoId;
    private JSONObject playerTwoData;
    private JSONObject playerTwoStats;

    private StatGetter statGetter;

    // Graphics related
    @FXML
    private ImageView basketballImage;

    @FXML
    public ImageView playerOneLogo;

    @FXML
    public ImageView playerTwoLogo;

    @FXML
    private Text errorText;

    @FXML
    private Text p1firstname;

    @FXML
    private Text p1lastname;

    @FXML
    private Text p1position;

    @FXML
    private Text p1year;

    @FXML
    private Text p2firstname;

    @FXML
    private Text p2lastname;

    @FXML
    private Text p2position;

    @FXML
    private Text p2year;

    @FXML
    public TextField playerTwoNameSearchBar;

    @FXML
    public TextField playerOneNameSearchBar;

    @FXML
    public TextField playerOneYearSearchBar;

    @FXML
    public TextField playerTwoYearSearchBar;

    @FXML
    public Text p1minutes;

    @FXML
    public Text p1points;

    @FXML
    public Text p1rebounds;

    @FXML
    public Text p1assists;

    @FXML
    public Text p1blocks;

    @FXML
    public Text p1steals;

    @FXML
    public Text p1trueshooting;

    @FXML
    public Text p1fieldgoals;

    @FXML
    public Text p1freethrows;

    @FXML
    public Text p1threepointers;

    @FXML
    public Text p1turnovers;

    @FXML
    public Text p2minutes;

    @FXML
    public Text p2points;

    @FXML
    public Text p2rebounds;

    @FXML
    public Text p2assists;

    @FXML
    public Text p2blocks;

    @FXML
    public Text p2steals;

    @FXML
    public Text p2trueshooting;

    @FXML
    public Text p2fieldgoals;

    @FXML
    public Text p2freethrows;

    @FXML
    public Text p2threepointers;

    @FXML
    public Text p2turnovers;

    @FXML
    public void initialize() {
        Image background = new Image("file:data/basketballimage.png");
        basketballImage.setImage(background);
        basketballImage.smoothProperty();
        statGetter = new StatGetter(errorText);
    }

    // MODIFIES: this
    // EFFECTS: Compares the two players inputted. If one of the players is invalid, it will not compare, and the
    //          previously compared two players will still be on the screen, but an Error message will be displayed.
    @FXML
    private void onCompareClicked() {
        try {
            setPlayerOne();
            setPlayerTwo();
        } catch (PlayerNotFoundException e) {
            return;
        }
        errorText.setText("");
        setNameAndYearGraphics();
        setPlayerLogos();
        comparePlayers();
    }

    // MODIFIES: this
    // EFFECTS: Sets the texts for player data of the current players.
    private void setNameAndYearGraphics() {
        p1firstname.setText(playerOne.getFirstName());
        p1lastname.setText(playerOne.getLastName());
        String p1Position = playerOne.getPosition();
        if (p1Position.equals("F-C") || p1Position.equals("C-F")) {
            p1position.setText("C");
        } else if (p1Position.equals("G-F") || p1Position.equals("F-G")) {
            p1position.setText("F");
        } else {
            p1position.setText(p1Position);
        }
        p1year.setText("(" + playerOne.getYearOfSeason() + "-" + (playerOne.getYearOfSeason() + 1) + ")");

        p2firstname.setText(playerTwo.getFirstName());
        p2lastname.setText(playerTwo.getLastName());
        String p2Position = playerTwo.getPosition();
        if (p1Position.equals("F-C") || p1Position.equals("C-F")) {
            p2position.setText("C");
        } else if (p2Position.equals("G-F") || p2Position.equals("F-G")) {
            p2position.setText("F");
        } else {
            p2position.setText(p2Position);
        }
        p2year.setText("(" + playerTwo.getYearOfSeason() + "-" + (playerTwo.getYearOfSeason() + 1) + ")");
    }

    // MODIFIES: this
    // EFFECTS: sets the Image logo values for the respective currently compared players.
    private void setPlayerLogos() {
        String p1TeamAbbreviation = playerOne.getTeamAbbreviation().toLowerCase();
        Image p1logo = new Image("file:data/teamlogos/" + p1TeamAbbreviation + ".png");
        playerOneLogo.setImage(p1logo);
        playerOneLogo.setOpacity(0.4);

        String p2TeamAbbreviation = playerTwo.getTeamAbbreviation().toLowerCase();
        Image p2logo = new Image("file:data/teamlogos/" + p2TeamAbbreviation + ".png");
        playerTwoLogo.setImage(p2logo);
        playerTwoLogo.setOpacity(0.4);
    }

    // MODIFIES: this
    // EFFECTS: compares the stats of two players, which sets the color values of the Text associated with the players
    private void comparePlayers() {
        HashMap<String, Double> p1Stats = playerOne.getStatsAsNums();
        HashMap<String, Double> p2Stats = playerTwo.getStatsAsNums();
        comparePerGameStat(p1minutes, p1Stats.get("minutespergame"), p2minutes, p2Stats.get("minutespergame"));
        comparePerGameStat(p1points, p1Stats.get("pointspergame"), p2points, p2Stats.get("pointspergame"));
        comparePerGameStat(p1rebounds, p1Stats.get("reboundspergame"), p2rebounds, p2Stats.get("reboundspergame"));
        comparePerGameStat(p1assists, p1Stats.get("assistspergame"), p2assists, p2Stats.get("assistspergame"));
        comparePerGameStat(p1blocks, p1Stats.get("blockspergame"), p2blocks, p2Stats.get("blockspergame"));
        comparePerGameStat(p1steals, p1Stats.get("stealspergame"), p2steals, p2Stats.get("stealspergame"));
        String topg = "turnoverspergame";
        compareTurnovers(p1turnovers, p1Stats.get(topg), p2turnovers, p2Stats.get(topg));
        p1trueshooting.setText(to3DecimalPlaces(p1Stats.get("trueshootingpct") + ""));
        p2trueshooting.setText(to3DecimalPlaces(p2Stats.get("trueshootingpct") + ""));
        setTextForPercentages(p1Stats, p2Stats);
        String ts = "trueshootingpct";
        comparePct(p1fieldgoals, p1Stats.get("fieldgoalpct"), p2fieldgoals, p2Stats.get("fieldgoalpct"));
        comparePct(p1trueshooting, p1Stats.get(ts), p2trueshooting, p2Stats.get(ts));
        comparePct(p1freethrows, p1Stats.get("freethrowpct"), p2freethrows, p2Stats.get("freethrowpct"));
        comparePct(p1threepointers, p1Stats.get("threepointpct"), p2threepointers, p2Stats.get("threepointpct"));

    }

    // MODIFIES: this
    // EFFECTS: compares two per game stats, sets the color to green for the better stat, red for the worse stat.
    private void comparePerGameStat(Text p1Text, Double p1Stat, Text p2Text, Double p2Stat) {
        if (p1Stat > p2Stat) {
            p1Text.setText(p1Stat + "");
            p2Text.setText(p2Stat + "");
            p1Text.setFill(Color.rgb(90, 190, 90));
            p2Text.setFill(Color.rgb(200, 20, 0));
            p1Text.setStyle("-fx-font: 27px\"Hiragino Sans W6\";");
            p2Text.setStyle("-fx-font: 27px\"Hiragino Sans W2\";");
        } else if (p1Stat < p2Stat) {
            p1Text.setText(p1Stat + "");
            p2Text.setText(p2Stat + "");
            p2Text.setFill(Color.rgb(90, 190, 90));
            p1Text.setFill(Color.rgb(200, 20, 0));
            p2Text.setStyle("-fx-font: 27px\"Hiragino Sans W6\";");
            p1Text.setStyle("-fx-font: 27px\"Hiragino Sans W2\";");
        } else {
            p1Text.setText(p1Stat + "");
            p2Text.setText(p2Stat + "");
            p1Text.setFill(Color.rgb(255, 140, 0));
            p2Text.setFill(Color.rgb(255, 140, 0));
            p2Text.setStyle("-fx-font: 27px\"Hiragino Sans W6\";");
            p1Text.setStyle("-fx-font: 27px\"Hiragino Sans W6\";");
        }
    }

    // MODIFIES: this
    // EFFECTS: similar to comparePerGameStat, but does the reverse since less turnovers is better than more turnovers
    private void compareTurnovers(Text p1Text, Double p1Stat, Text p2Text, Double p2Stat) {
        if (p1Stat > p2Stat) {
            p1Text.setText(p1Stat + "");
            p2Text.setText(p2Stat + "");
            p2Text.setFill(Color.rgb(90, 190, 90));
            p1Text.setFill(Color.rgb(200, 20, 0));
            p1Text.setStyle("-fx-font: 27px\"Hiragino Sans W2\";");
            p2Text.setStyle("-fx-font: 27px\"Hiragino Sans W6\";");
        } else if (p1Stat < p2Stat) {
            p1Text.setText(p1Stat + "");
            p2Text.setText(p2Stat + "");
            p1Text.setFill(Color.rgb(90, 190, 90));
            p2Text.setFill(Color.rgb(200, 20, 0));
            p2Text.setStyle("-fx-font: 27px\"Hiragino Sans W2\";");
            p1Text.setStyle("-fx-font: 27px\"Hiragino Sans W6\";");
        } else {
            p1Text.setText(p1Stat + "");
            p2Text.setText(p2Stat + "");
            p1Text.setFill(Color.rgb(255, 140, 0));
            p2Text.setFill(Color.rgb(255, 140, 0));
            p2Text.setStyle("-fx-font: 27px\"Hiragino Sans W6\";");
            p1Text.setStyle("-fx-font: 27px\"Hiragino Sans W6\";");
        }
    }

    // MODIFIES: this
    // EFFECTS: compares two percentage stats, sets the color of the better stat to green, and red for the worse stat.
    private void comparePct(Text p1Text, Double p1Stat, Text p2Text, Double p2Stat) {
        if (p1Stat > p2Stat) {
            p1Text.setFill(Color.rgb(90, 190, 90));
            p2Text.setFill(Color.rgb(200, 20, 0));
            p1Text.setStyle("-fx-font: 27px\"Hiragino Sans W6\";");
            p2Text.setStyle("-fx-font: 27px\"Hiragino Sans W2\";");
        } else if (p1Stat < p2Stat) {
            p2Text.setFill(Color.rgb(90, 190, 90));
            p1Text.setFill(Color.rgb(200, 20, 0));
            p2Text.setStyle("-fx-font: 27px\"Hiragino Sans W6\";");
            p1Text.setStyle("-fx-font: 27px\"Hiragino Sans W2\";");
        } else {
            p1Text.setFill(Color.rgb(255, 140, 0));
            p2Text.setFill(Color.rgb(255, 140, 0));
            p2Text.setStyle("-fx-font: 27px\"Hiragino Sans W6\";");
            p1Text.setStyle("-fx-font: 27px\"Hiragino Sans W6\";");
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the text for the percentages of the players.
    private void setTextForPercentages(HashMap<String, Double> p1Stats, HashMap<String, Double> p2Stats) {
        String p1FGPct = to3DecimalPlaces(p1Stats.get("fieldgoalpct") + "");
        String p1FGString = p1FGPct + " (" + p1Stats.get("fieldgoalsmade") + "/";
        p1fieldgoals.setText(p1FGString + p1Stats.get("fieldgoalsattempted") + ")");
        String p2FGPct = to3DecimalPlaces(p2Stats.get("fieldgoalpct") + "");
        String p2FGString = p2FGPct + " (" + p2Stats.get("fieldgoalsmade") + "/";
        p2fieldgoals.setText(p2FGString + p2Stats.get("fieldgoalsattempted") + ")");

        String p1FTPct = to3DecimalPlaces(p1Stats.get("freethrowpct") + "");
        String p1FTString = p1FTPct + " (" + p1Stats.get("freethrowsmade") + "/";
        p1freethrows.setText(p1FTString + p1Stats.get("freethrowsattempted") + ")");
        String p2FTPct = to3DecimalPlaces(p2Stats.get("freethrowpct") + "");
        String p2FTString = p2FTPct + " (" + p2Stats.get("freethrowsmade") + "/";
        p2freethrows.setText(p2FTString + p2Stats.get("freethrowsattempted") + ")");

        String p13PPct = to3DecimalPlaces(p1Stats.get("threepointpct") + "");
        String p13PString = p13PPct + " (" + p1Stats.get("threepointmade") + "/";
        p1threepointers.setText(p13PString + p1Stats.get("threepointattempted") + ")");
        String p23PPct = to3DecimalPlaces(p2Stats.get("threepointpct") + "");
        String p23PString = p23PPct + " (" + p2Stats.get("threepointmade") + "/";
        p2threepointers.setText(p23PString + p2Stats.get("threepointattempted") + ")");
    }

    // REQUIRES: assumes that the inputted String is a double between 0 and 1
    // EFFECTS: takes a string, adds 0 to it until the length of the string is 5
    private String to3DecimalPlaces(String pct) {
        if (pct.length() < 5) {
            for (; pct.length() < 5; ) {
                pct += "0";
            }
        }
        return pct;
    }


    // MODIFIES: this
    // EFFECTS: Sets playerOne's value
    private void setPlayerOne() throws PlayerNotFoundException {
        String nameInput = playerOneNameSearchBar.getText();
        boolean playerExists = !invalidInputtedNameChecker(nameInput);
        if (!playerExists) {
            errorText.setText("ERROR: Inputted player name for P1 does not exist.");
            throw new PlayerNotFoundException();
        }
        String yearInput = playerOneYearSearchBar.getText();
        JSONObject potentialPlayerData = statGetter.getPlayerData(nameInput);
        int id = potentialPlayerData.getInt("id");
        boolean validYear = strIsNum(yearInput) && !invalidInputtedYearChecker(Integer.parseInt(yearInput), id);
        if (!validYear) {
            String firstpart = "ERROR: Invalid inputted year for P1. Year must be between 1990 and " + CURRENTYEAR;
            String secondpart = ", and player must have played in that year";
            errorText.setText(firstpart + secondpart);
            throw new PlayerNotFoundException();
        }
        playerOneData = potentialPlayerData;
        playerOneId = id;
        int year = Integer.parseInt(yearInput);
        playerOneStats = statGetter.extractAndGetPlayerStatistics(playerOneId, year);
        playerOne = new Player(playerOneData, playerOneStats);
        playerOne.setYearOfSeason(year);
    }

    // MODIFIES: this
    // EFFECTS: Sets playerTwo's value.
    private void setPlayerTwo() throws PlayerNotFoundException {
        String nameInput = playerTwoNameSearchBar.getText();
        boolean playerExists = !invalidInputtedNameChecker(nameInput);
        if (!playerExists) {
            errorText.setText("ERROR: Inputted player name for P2 does not exist.");
            throw new PlayerNotFoundException();
        }
        String yearInput = playerTwoYearSearchBar.getText();
        JSONObject potentialPlayerData = statGetter.getPlayerData(nameInput);
        int id = potentialPlayerData.getInt("id");
        boolean validYear = strIsNum(yearInput) && !invalidInputtedYearChecker(Integer.parseInt(yearInput), id);
        if (!validYear) {
            String firstpart = "ERROR: Invalid inputted year for P2. Year must be between 1990 and " + CURRENTYEAR;
            String secondpart = ", and player must have played in that year";
            errorText.setText(firstpart + secondpart);
            throw new PlayerNotFoundException();
        }
        playerTwoData = potentialPlayerData;
        playerTwoId = id;
        int year = Integer.parseInt(yearInput);
        playerTwoStats = statGetter.extractAndGetPlayerStatistics(playerTwoId, year);
        playerTwo = new Player(playerTwoData, playerTwoStats);
        playerTwo.setYearOfSeason(year);
    }


    // EFFECTS: Sets the stage to the menu scene
    @FXML
    private void onBackToMenuButtonClicked(MouseEvent event) throws IOException {
        AnchorPane main = (AnchorPane) FXMLLoader.load(Main.class.getResource("StattrakGuiMainScreen.fxml"));

        // Citation for line 38 - : https://youtu.be/XCgcQTQCfJQ // Jaret Wright
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(main));
        window.show();
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
            return true;
        }
        JSONObject metadata = statGetter.extractAndGetPlayerStatistics(id, yearofseason).getJSONObject("meta");
        int gamesplayed = metadata.getInt("total_count");
        if (gamesplayed == 0)  {
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
            return true;
        }
        boolean playerdoesntexist = statGetter.getPlayerData(name).isEmpty();
        if (playerdoesntexist) {
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
