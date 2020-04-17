package model;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

// Represents an NBA player with a name, team name, position, season, biometrics,
// and total/per game statistics.

public class Player {

    protected int id;
    protected String teamname;
    protected String teamabbreviation;
    protected String firstname;
    protected String lastname;
    protected String position;
    protected int yearofseason;

    protected double gamesplayed;
    protected double totalpoints;
    protected double totaloffensiverebounds;
    protected double totaldefensiverebounds;
    protected double totalrebounds;
    protected double totalassists;
    protected double totalsteals;
    protected double totalblocks;
    protected double totalfieldgoalsattempted;
    protected double totalfieldgoalsmade;
    protected double totalfreethrowsattempted;
    protected double totalfreethrowsmade;
    protected double totalthreepointersattempted;
    protected double totalthreepointersmade;
    protected double totalfouls;
    protected double totalturnovers;
    protected double totalminutes;

    protected double pointspergame;
    protected double reboundspergame;
    protected double offensiveboardspergame;
    protected double defensiveboardspergame;
    protected double assistspergame;
    protected double stealspergame;
    protected double blockspergame;
    protected double fieldgoalsattempted;
    protected double fieldgoalsmade;
    protected double fieldgoalpct;
    protected double freethrowsattempted;
    protected double freethrowsmade;
    protected double freethrowpct;
    protected double threepointersattempted;
    protected double threepointersmade;
    protected double threepointpct;
    protected double turnoverspergame;
    protected double trueshootingpct;
    protected double foulspergame;
    protected double minutespergame;

    protected int heightinfeet;
    protected int additionalheightinches;
    protected int weightinpounds;


    // Constructor:
    public Player(JSONObject playerdata, JSONObject playerstats) {
        addPlayerBasicData(playerdata);
        setPlayerStatisticsAndUpdateTeam(playerstats);
    }

    // Getters:

    public HashMap<String, Double> getStatsAsNums() {
        HashMap<String, Double> stats = new HashMap<String, Double>();
        stats.put("pointspergame", roundPerGameStat(pointspergame));
        stats.put("reboundspergame", roundPerGameStat(reboundspergame));
        stats.put("defrebpergame", roundPerGameStat(defensiveboardspergame));
        stats.put("offrebpergame", roundPerGameStat(offensiveboardspergame));
        stats.put("assistspergame", roundPerGameStat(assistspergame));
        stats.put("stealspergame", roundPerGameStat(stealspergame));
        stats.put("blockspergame", roundPerGameStat(blockspergame));
        stats.put("turnoverspergame", roundPerGameStat(turnoverspergame));
        stats.put("fieldgoalpct", roundPercentages(fieldgoalpct));
        stats.put("freethrowpct", roundPercentages(freethrowpct));
        stats.put("threepointpct", roundPercentages(threepointpct));
        stats.put("fieldgoalsattempted", roundPerGameStat(fieldgoalsattempted));
        stats.put("threepointattempted", roundPerGameStat(threepointersattempted));
        stats.put("freethrowsattempted", roundPerGameStat(freethrowsattempted));
        stats.put("fieldgoalsmade", roundPerGameStat(fieldgoalsmade));
        stats.put("threepointmade", roundPerGameStat(threepointersmade));
        stats.put("freethrowsmade", roundPerGameStat(freethrowsmade));
        stats.put("trueshootingpct", roundPercentages(trueshootingpct));
        stats.put("gamesplayed", gamesplayed);
        stats.put("minutespergame", roundPerGameStat(minutespergame));
        stats.put("foulspergame", roundPerGameStat(foulspergame));
        return stats;
    }

    public int getYearOfSeason() {
        return yearofseason;
    }

    public int getId() {
        return id;
    }

    public String getTeamName() {
        return "Team: " + teamname;
    }

    public String getTeamAbbreviation() {
        return teamabbreviation;
    }

    public String getFullName() {
        return firstname + " " + lastname;
    }

    public String getFirstName() {
        return firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public String getPosition() {
        return position;
    }

    public String getHeight() {
        return heightinfeet + "-" + additionalheightinches;
    }

    public String getWeight() {
        return weightinpounds + " lbs";
    }

    public String getGamesPlayed() {
        return "Games Played: " + String.format("%1.0f", gamesplayed);
    }

    public String getPoints() {
        return roundPerGameStat(pointspergame) + "PTS";
    }

    public String getReboundingStats() {
        double oreb = roundPerGameStat(offensiveboardspergame);
        double dreb = roundPerGameStat(defensiveboardspergame);
        double reb = roundPerGameStat(reboundspergame);
        return reb + "REB (" + dreb + " DREB, " + oreb + " OREB)";
    }

    public String getAssists() {
        double ast = roundPerGameStat(assistspergame);
        return ast + "AST";
    }

    public String getSteals() {
        double stl = roundPerGameStat(stealspergame);
        return stl + "STL";
    }

    public String getBlocks() {
        double blk = roundPerGameStat(blockspergame);
        return blk + "BLK";
    }

    public String getFieldGoalStats() {
        String fgpct = String.format("%1.3f", fieldgoalpct);
        double fga = roundPerGameStat(fieldgoalsattempted);
        double fgm = roundPerGameStat(fieldgoalsmade);
        return "FG%: " + fgpct + " (FGM/FGA: " + fgm + "/" + fga + ")";
    }

    public String getThreePointStats() {
        String tppct = String.format("%1.3f", threepointpct);
        double tpa = roundPerGameStat(threepointersattempted);
        double tpm = roundPerGameStat(threepointersmade);
        return "3P%: " + tppct + " (3PM/3PA: " + tpm + "/" + tpa + ")";
    }

    public String getFreeThrowStats() {
        String ftpct = String.format("%1.3f", freethrowpct);
        double fta = roundPerGameStat(freethrowsattempted);
        double ftm = roundPerGameStat(freethrowsmade);
        return "FT%: " + ftpct + " (FTM/FTA: " + ftm + "/" + fta + ")";
    }

    public String getFoulsPerGame() {
        return roundPerGameStat(foulspergame) + "PF";
    }

    public String getTurnoversPerGame() {
        return roundPerGameStat(turnoverspergame) + "TO";
    }

    public String getTrueShootingPct() {
        double ts = trueshootingpct;
        String str = String.format("%1.3f", ts);
        return "TS%: " + str;
    }

    public String getMinutes() {
        double mpg = roundPerGameStat(minutespergame);
        return mpg + "MPG";
    }

    public void setYearOfSeason(int year) {
        yearofseason = year;
    }

    // REQUIRES: pergameval is supposed to be a number in the tens column
    // EFFECTS: returns a rounded pergame statistic to one decimal place,
    //          functions as a helper for getters
    public static double roundPerGameStat(double pergameval) {
        double newval = pergameval * 10;
        newval = Math.round(newval);
        newval /= 10;
        return newval;
    }

    // REQUIRES: pergameval is supposed to like some percentage from 0-1.
    // EFFECTS: returns a rounded percentage statistic to three decimal places,
    //          functions as a helper for getStatsForComparison();
    public static double roundPercentages(double percentage) {
        double newval = percentage * 1000;
        newval = Math.round(newval);
        newval /= 1000;
        return newval;
    }

    // REQUIRES: JSONObject "data" must be a player object retrieved from API
    // MODIFIES: this
    // EFFECTS: sets basic player data, including id, teamname, name, position, and biometrics if possible
    private void addPlayerBasicData(JSONObject data) {
        id = data.getInt("id");
        teamname = data.getJSONObject("team").getString("full_name");
        firstname = data.getString("first_name");
        lastname = data.getString("last_name");
        position = data.getString("position");
        if (data.get("height_feet") instanceof Integer) {
            heightinfeet = data.getInt("height_feet");
            additionalheightinches = data.getInt("height_inches");
        }
        if (data.get("weight_pounds") instanceof Integer) {
            weightinpounds = data.getInt("weight_pounds");
        }
    }


    // A helper function to extractAndSetPlayerStatistics
    // REQUIRES: JSONObject "playerstats" contains games of a player
    // MODIFIES: this
    // EFFECTS: sets the players statistics and team if this player's team was different for that year
    private void setPlayerStatisticsAndUpdateTeam(JSONObject playerstats) {
        int length = playerstats.getJSONArray("data").length();
        for (int x = 0; x < length; x++) {
            boolean equalsquotation = playerstats.getJSONArray("data").getJSONObject(x).get("min").equals("");
            boolean equalsnull = playerstats.getJSONArray("data").getJSONObject(x).get("min").equals(null);
            if (!(equalsquotation || equalsnull)) {
                addToTotalPointsStats(x, playerstats);
            }
        }
        setTeamForYear(playerstats, length - 1);
        setPerGameStatistics();
    }

    // A helper function to setPlayerStatisticsAndUpdateTeam
    // REQUIRES: playerstats contains team data
    // MODIFIES: this
    // EFFECTS: sets the player's team to whatever it was that year
    private void setTeamForYear(JSONObject playerstats, int lastgame) {
        JSONObject teamdata = playerstats.getJSONArray("data").getJSONObject(lastgame).getJSONObject("team");
        teamname = teamdata.getString("full_name");
        teamabbreviation = teamdata.getString("abbreviation");
    }

    // A helper function to setPlayerStatisticsAndUpdateTeam
    // REQUIRES: playerstats actually contains game stat data
    // MODIFIES: this
    // EFFECTS: adds whatever a player got that game to the total stats
    private void addToTotalPointsStats(int x, JSONObject playerstats) {
        gamesplayed++;
        totalpoints += playerstats.getJSONArray("data").getJSONObject(x).getInt("pts");
        totalassists += playerstats.getJSONArray("data").getJSONObject(x).getInt("ast");
        totalblocks += playerstats.getJSONArray("data").getJSONObject(x).getInt("blk");
        totaldefensiverebounds += playerstats.getJSONArray("data").getJSONObject(x).getInt("dreb");
        totaloffensiverebounds += playerstats.getJSONArray("data").getJSONObject(x).getInt("oreb");
        totalfieldgoalsattempted += playerstats.getJSONArray("data").getJSONObject(x).getInt("fga");
        totalfieldgoalsmade += playerstats.getJSONArray("data").getJSONObject(x).getInt("fgm");
        totalfreethrowsattempted += playerstats.getJSONArray("data").getJSONObject(x).getInt("fta");
        totalfreethrowsmade += playerstats.getJSONArray("data").getJSONObject(x).getInt("ftm");
        totalsteals += playerstats.getJSONArray("data").getJSONObject(x).getInt("stl");
        totalturnovers += playerstats.getJSONArray("data").getJSONObject(x).getInt("turnover");
        totalthreepointersattempted += playerstats.getJSONArray("data").getJSONObject(x).getInt("fg3a");
        totalthreepointersmade += playerstats.getJSONArray("data").getJSONObject(x).getInt("fg3m");
        totalfouls += playerstats.getJSONArray("data").getJSONObject(x).getInt("pf");
        String min = playerstats.getJSONArray("data").getJSONObject(x).getString("min");
        addToTotalMinutes(min);
    }

    // A helper function to addToTotalPointsStats
    // REQUIRES: min is string of format "n:nn" or "nn:nn" where n is some arbitrary number
    // MODIFIES: this
    // EFFECTS: adds minutes of the value of the inputted string
    private void addToTotalMinutes(String min) {
        if (min.length() == 2 || min.length() == 1) {
            totalminutes += Integer.parseInt(min);
        } else if (min.length() == 4) {
            addMinutesLessThanTenMinutes(min);
        } else {
            addMinutesTenOrMoreMinutes(min);
        }
    }

    // A helper to addToTotalMinutes
    // REQUIRES: min is a string of format "n:nn" where n is some arbitrary number
    // MODIFIES: this
    // EFFECTS: adds minutes to totalminutes
    private void addMinutesLessThanTenMinutes(String min) {
        String minutes = min.substring(0, 1);
        double mins = Integer.parseInt(minutes);
        totalminutes += mins;
        String tensseconds = min.substring(2, 3);
        double tsec = Integer.parseInt(tensseconds);
        totalminutes += tsec * 0.167;
        String seconds = min.substring(3, 4);
        double sec = Integer.parseInt(seconds);
        totalminutes += sec * 0.0167;
    }

    // A helper to addToTotalMinutes
    // REQUIRES: min is a string of format "nn:nn" where n is some arbitrary number
    // MODIFIES: this
    // EFFECTS: adds minutes to totalminutes
    private void addMinutesTenOrMoreMinutes(String min) {
        String tensminutes = min.substring(0, 1);
        double tmin = Integer.parseInt(tensminutes);
        totalminutes += tmin * 10;
        String minutes = min.substring(1, 2);
        double mins = Integer.parseInt(minutes);
        totalminutes += mins;
        String tensseconds = min.substring(3, 4);
        double tsec = Integer.parseInt(tensseconds);
        totalminutes += tsec * 0.167;
        String seconds = min.substring(4, 5);
        double sec = Integer.parseInt(seconds);
        totalminutes += sec * 0.0167;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        boolean idIsSame = id == player.id;
        boolean yearIsSame = yearofseason == player.yearofseason;
        boolean firstNameIsSame = firstname.equals(player.firstname);
        boolean lastNameIsSame = lastname.equals(player.lastname);
        return idIsSame && yearIsSame && firstNameIsSame && lastNameIsSame;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, yearofseason);
    }

    // A helper to setPlayerStatisticsAndUpdateTeam
    // REQUIRES: total statistics have already been retrieved and set.
    // MODIFIES: this
    // EFFECTS: sets the values for the per game statistics
    private void setPerGameStatistics() {
        totalrebounds = totaloffensiverebounds + totaldefensiverebounds;
        pointspergame = totalpoints / gamesplayed;
        assistspergame = totalassists / gamesplayed;
        stealspergame = totalsteals / gamesplayed;
        blockspergame = totalblocks / gamesplayed;
        reboundspergame = totalrebounds / gamesplayed;
        offensiveboardspergame = totaloffensiverebounds / gamesplayed;
        defensiveboardspergame = totaldefensiverebounds / gamesplayed;
        fieldgoalsattempted = totalfieldgoalsattempted / gamesplayed;
        fieldgoalsmade = totalfieldgoalsmade / gamesplayed;
        fieldgoalpct = fieldgoalsmade / fieldgoalsattempted;
        freethrowsattempted = totalfreethrowsattempted / gamesplayed;
        freethrowsmade = totalfreethrowsmade / gamesplayed;
        freethrowpct = freethrowsmade / freethrowsattempted;
        threepointersattempted = totalthreepointersattempted / gamesplayed;
        threepointersmade = totalthreepointersmade / gamesplayed;
        threepointpct = totalthreepointersmade / totalthreepointersattempted;
        foulspergame = totalfouls / gamesplayed;
        turnoverspergame = totalturnovers / gamesplayed;
        trueshootingpct = pointspergame / (2 * (fieldgoalsattempted + freethrowsattempted * 0.44));
        minutespergame = totalminutes / gamesplayed;
    }

}