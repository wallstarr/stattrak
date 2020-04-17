package model;

import exceptions.PlayerAlreadyFavoritedException;

import java.util.ArrayList;

// Represents a favorited players tab, with Active Players and Past Players.

public class FavoritedPlayers {

    public static final int CURRENTYEAR = 2019;

    ArrayList<Player> currentSeasonPlayers;
    ArrayList<Player> pastSeasonPlayers;

    public FavoritedPlayers() {
        currentSeasonPlayers = new ArrayList<Player>();
        pastSeasonPlayers = new ArrayList<Player>();
    }

    // Getters & Setters:

    public ArrayList<Player> getCurrentSeasonPlayers() {
        return currentSeasonPlayers;
    }

    public ArrayList<Player> getPastSeasonPlayers() {
        return pastSeasonPlayers;
    }

    public void setCurrentSeasonPlayers(ArrayList<Player> currentSeasonPlayers) {
        this.currentSeasonPlayers = currentSeasonPlayers;
    }

    public void setPastSeasonPlayers(ArrayList<Player> pastSeasonPlayers) {
        this.pastSeasonPlayers = pastSeasonPlayers;
    }

    // MODIFIES: this
    // EFFECTS: adds a player to its appropiate list if the player has not already been added to favorites.
    //          If said player has already been favorited, then addPlayer throws a PlayerAlreadyFavoritedException.
    public void addPlayer(Player player) throws PlayerAlreadyFavoritedException {
        boolean alreadyfavorited = checkIfPlayerAlreadyFavorited(player);
        if (!alreadyfavorited) {
            int yearofseason = player.getYearOfSeason();
            if (yearofseason == CURRENTYEAR) {
                currentSeasonPlayers.add(player);
            } else {
                pastSeasonPlayers.add(player);
            }
        } else {
            throw new PlayerAlreadyFavoritedException();
        }
    }

    // A helper function to addPlayer.
    // EFFECTS: checks if a player has already been added to favorites.
    private boolean checkIfPlayerAlreadyFavorited(Player player) {
        String fullName = player.getFullName();
        int playerYear = player.getYearOfSeason();
        boolean alreadyfavorited = false;

        if (playerYear == CURRENTYEAR) {
            for (Player p: currentSeasonPlayers) {
                if (p.getFullName().equals(fullName)) {
                    alreadyfavorited = true;
                }
            }
        } else {
            for (Player p: pastSeasonPlayers) {
                if (p.getFullName().equals(fullName) && playerYear == p.getYearOfSeason()) {
                    alreadyfavorited = true;
                }
            }
        }
        return alreadyfavorited;
    }
}