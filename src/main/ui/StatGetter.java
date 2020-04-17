package ui;

import javafx.scene.text.Text;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

// This class utilizes the API to parse through a stream representing JSONObjects into actual
// JSONObjects.

public class StatGetter {

    private Text errorText;

    public StatGetter(Text errorText) {
        this.errorText = errorText;
    }

    // REQUIRES: name is valid, ie: there is only one space (between first and last name) and actually
    //           exists in the league
    // MODIFIES: this
    // EFFECTS: retrieves basic player data, such as name, team last played for, position, weight, and height
    public JSONObject getPlayerData(String playername) {
        JSONObject playerpage = null;
        try {
            String modifiedplayername = playername.replaceAll("\\s", "%20");
            URL url = new URL("https://balldontlie.io/api/v1/players?search=" + modifiedplayername);
            InputStream stream = url.openStream();
            InputStreamReader isr = new InputStreamReader(stream);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder jsoncontents = new StringBuilder();
            String str;

            while ((str = reader.readLine()) != null) {
                jsoncontents.append(str);
            }

            String playerpagesstring = jsoncontents.toString();
            playerpage = new JSONObject(playerpagesstring);

        } catch (IOException e) {
            errorText.setText("ERROR: No internet connection or API servers are down");
        }
        return checkForSomeNumberOfPlayers(playerpage, playername);
    }

    // REQUIRES: JSONObject "obj" actually contains player data retrieved from API.
    // EFFECTS: checks the number of players. if the number of players is 1, return that player if name matches. if the
    //          number of players is 0, return a new JSONObject which indicates that there are no players. if there are
    //          multiple due to naming (eg: there is a John Wall but also a John Wallace), check the naming and return
    //          the playerdata of the player with the matching name.
    private JSONObject checkForSomeNumberOfPlayers(JSONObject obj, String playername) {
        if (obj.getJSONObject("meta").getInt("total_count") == 1) {
            int spaceposition = playername.indexOf(" ");
            String inputfname = playername.substring(0, spaceposition);                       // inputted first name
            String inputlname = playername.substring(spaceposition + 1, playername.length()); // inputted last name

            JSONObject player = obj.getJSONArray("data").getJSONObject(0);
            String retrievedfirstname = player.getString("first_name");
            String retrievedlastname = player.getString("last_name");
            if (inputfname.equalsIgnoreCase(retrievedfirstname) && inputlname.equalsIgnoreCase(retrievedlastname)) {
                return obj.getJSONArray("data").getJSONObject(0);
            }
            return new JSONObject();
        } else if (obj.getJSONObject("meta").getInt("total_count") == 0) {
            return new JSONObject();
        } else {
            return checkMultiplePlayers(obj, playername);
        }
    }

    // A helper function for checkForSomeNumberOfPlayers. This is called when there are multiple players in the
    // retrieved data.
    // REQUIRES: JSONObject "obj" actually contains player data retrieved from the API.
    // EFFECTS: returns the playerdata of the player that matches String playername.
    private JSONObject checkMultiplePlayers(JSONObject obj, String playername) {
        int spaceposition = playername.indexOf(" ");
        String inputfname = playername.substring(0, spaceposition);                       // inputted first name
        String inputlname = playername.substring(spaceposition + 1, playername.length()); // inputted last name

        int arrayindex = 0;
        JSONObject playerData = new JSONObject();

        for (; arrayindex < obj.getJSONObject("meta").getInt("total_count"); arrayindex++) {
            JSONObject player = obj.getJSONArray("data").getJSONObject(arrayindex);
            String retrievedfirstname = player.getString("first_name");
            String retrievedlastname = player.getString("last_name");
            if (inputfname.equalsIgnoreCase(retrievedfirstname) && inputlname.equalsIgnoreCase(retrievedlastname)) {
                playerData = obj.getJSONArray("data").getJSONObject(arrayindex);
                break;
            }
        }
        return playerData;
    }

    // REQUIRES: player id has been set
    // MODIFIES: this
    // EFFECTS: retrieves the statistical game data from the API of the player in the inputted season
    public JSONObject extractAndGetPlayerStatistics(int id, int yearofseason) {
        JSONObject playerstats = null;
        try {
            String linkpart1 = "https://www.balldontlie.io/api/v1/stats?seasons[]=";
            String linkpart2 = yearofseason + "&player_ids[]=" + id + "&per_page=100&postseason=false";
            URL url = new URL(linkpart1 + linkpart2);
            InputStream stream = url.openStream();
            InputStreamReader isr = new InputStreamReader(stream);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder jsoncontents = new StringBuilder();

            String str;
            while ((str = reader.readLine()) != null) {
                jsoncontents.append(str);
            }

            String statpages = jsoncontents.toString();
            playerstats = new JSONObject(statpages);

        } catch (IOException e) {
            // Make the Error TextField say "No internet connection or servers are down"
            errorText.setText("ERROR: No internet connection or API servers are down");
        }
        return playerstats;
    }
}
