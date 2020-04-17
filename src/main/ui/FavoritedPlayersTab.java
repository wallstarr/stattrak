package ui;

import model.FavoritedPlayers;
import model.Player;
import persistence.Reader;
import persistence.Writer;

import com.google.gson.Gson;
import exceptions.PlayerAlreadyFavoritedException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Represents the panel for favorited players inside the Search Screen scene for the GUI.
// This visualizes the tab and contains an Active Players list and Past Players list

public class FavoritedPlayersTab {
    private FavoritedPlayers favoritedPlayers;
    private ListView activePlayersTab;
    private ListView pastPlayersTab;
    ArrayList<String> activePlayerStrings;
    ArrayList<String> pastPlayerStrings;
    ObservableList<String> activeObservableList;
    ObservableList<String> pastObservableList;

    private static final String CURRENT_PLAYERS_FILE = "./data/SavedCurrentPlayers";
    private static final String PAST_PLAYERS_FILE = "./data/SavedPastPlayers";

    // EFFECTS: Instantiates a FavoritedPlayersTab, sets the ListViews and lists.
    public FavoritedPlayersTab(ListView activeTab, ListView pastTab, List activeList, List pastList) {
        activePlayersTab = activeTab;
        pastPlayersTab = pastTab;
        favoritedPlayers = new FavoritedPlayers();
        favoritedPlayers.setCurrentSeasonPlayers((ArrayList<Player>) activeList);
        favoritedPlayers.setPastSeasonPlayers((ArrayList<Player>) pastList);
        updateFavoritedPlayers();
    }

    // REQUIRES: playerName must be the name of an active player that is already favorited.
    // EFFECTS: returns the active player with name playerName.
    public Player getActivePlayer(String playerName) {
        int x = activePlayerStrings.indexOf(playerName);
        return favoritedPlayers.getCurrentSeasonPlayers().get(x);
    }

    // REQUIRES: playerName must be the name of a past player that is already favorited.
    // EFFECTS: returns the past player with name playerName.
    public Player getPastPlayer(String playerName) {
        int x = pastPlayerStrings.indexOf(playerName);
        return favoritedPlayers.getPastSeasonPlayers().get(x);
    }

    // EFFECTS: returns true if player is already favorited.
    public boolean playerIsFavorited(Player player) {
        ArrayList<Player> activePlayers = favoritedPlayers.getCurrentSeasonPlayers();
        ArrayList<Player> pastPlayers = favoritedPlayers.getPastSeasonPlayers();
        if (activePlayers.contains(player) || pastPlayers.contains(player)) {
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: loads players that were saved from JSON files SavedCurrentPlayers and SavedPastPlayers
    public void loadPlayers() {
        ArrayList<Player> activeList = new ArrayList<>();
        ArrayList<Player> pastList = new ArrayList<>();
        try {
            activeList = Reader.readPlayers(CURRENT_PLAYERS_FILE);
            pastList = Reader.readPlayers(PAST_PLAYERS_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        favoritedPlayers.setCurrentSeasonPlayers(activeList);
        favoritedPlayers.setPastSeasonPlayers(pastList);
        updateFavoritedPlayers();
    }

    // MODIFIES: JSONFiles SavedCurrentPlayers and SavedPastPlayers
    // EFFECTS: saves the currently favorited players to file
    public void savePlayers() {
        JSONArray currentplayers = playersToJsonArray(favoritedPlayers.getCurrentSeasonPlayers());
        JSONArray pastplayers = playersToJsonArray(favoritedPlayers.getPastSeasonPlayers());
        try {
            FileWriter currentplayersfile = new FileWriter(CURRENT_PLAYERS_FILE);
            persistence.Writer.writeFile(currentplayersfile, currentplayers);
            FileWriter pastplayersfile = new FileWriter(PAST_PLAYERS_FILE);
            Writer.writeFile(pastplayersfile, pastplayers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: converts the inputted arraylist of players into a JSONArray, returns that JSONArray.
    private JSONArray playersToJsonArray(ArrayList<Player> players) {
        Gson gson = new Gson();
        JSONArray playersjsonarray = new JSONArray();
        for (Player p: players) {
            String playerstring = gson.toJson(p);
            JSONObject player = new JSONObject(playerstring);
            playersjsonarray.put(player);
        }
        return playersjsonarray;
    }

    // MODIFIES: this
    // EFFECTS: adds currentPlayer to favorited players, and updates how the panel looks
    public void favoritePlayer(Player currentPlayer) throws PlayerAlreadyFavoritedException {
        favoritedPlayers.addPlayer(currentPlayer);
        updateFavoritedPlayers();
    }

    // MODIFIES: this
    // EFFECTS: removes currentPlayer from favorited players, and updates how the panel looks
    public void unfavoritePlayer(Player currentPlayer) {
        ArrayList<Player> activePlayers = favoritedPlayers.getCurrentSeasonPlayers();
        ArrayList<Player> pastPlayers = favoritedPlayers.getPastSeasonPlayers();
        if (activePlayers.contains(currentPlayer)) {
            activePlayers.remove(currentPlayer);
        } else if (pastPlayers.contains(currentPlayer)) {
            pastPlayers.remove(currentPlayer);
        }
        updateFavoritedPlayers();
    }


    // MODIFIES: this
    // EFFECTS: updates the favorited players tab based
    private void updateFavoritedPlayers() {
        ArrayList<Player> activePlayers = favoritedPlayers.getCurrentSeasonPlayers();
        ArrayList<Player> pastPlayers = favoritedPlayers.getPastSeasonPlayers();
        activePlayerStrings = new ArrayList<String>();
        for (Player p : activePlayers) {
            activePlayerStrings.add(p.getFullName());
        }
        activeObservableList = FXCollections.observableList(activePlayerStrings);
        activePlayersTab.setItems(activeObservableList);

        pastPlayerStrings = new ArrayList<String>();
        for (Player p : pastPlayers) {
            pastPlayerStrings.add(p.getFullName() + " (" + p.getYearOfSeason() + "-" + (p.getYearOfSeason() + 1) + ")");
        }
        pastObservableList = FXCollections.observableList(pastPlayerStrings);
        pastPlayersTab.setItems(pastObservableList);
    }
}
