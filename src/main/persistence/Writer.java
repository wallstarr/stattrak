package persistence;

import org.json.JSONArray;

import java.io.FileWriter;
import java.io.IOException;

// A class capable of writing a JSONArray of players to file.

public class Writer {

    public Writer() {

    }

    // MODIFIES: the file
    // EFFECTS: writes the jsonarray "list" to the specified file
    public static void writeFile(FileWriter file, JSONArray list) throws IOException {
        file.write(list.toString());
        file.flush();
    }
}
