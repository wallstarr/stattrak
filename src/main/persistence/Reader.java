package persistence;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Player;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

// A class capable of reading JSON files containing players, and turning them into an ArrayList of players.

public class Reader {

    public Reader() {

    }

    // REQUIRES: fileName is a path to a file that actually exists.
    // EFFECTS: turns the players in the file from a JSONArray into an ArrayList of players, returns that arraylist
    public static ArrayList<Player> readPlayers(String fileName) throws IOException {
        File file = new File(fileName);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String players = bufferedReader.readLine();
        Gson gson = new Gson();
        Type foundListType = new TypeToken<ArrayList<Player>>(){}.getType();
        ArrayList<Player> readPlayers = gson.fromJson(players, foundListType);
        return readPlayers;
    }
}
