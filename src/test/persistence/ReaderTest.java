package persistence;

import model.Player;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest {

    private static final String TEST_FILE = "./data/ReaderTestFile";

    @Test
    public void testReadPlayers() {
        try {
            ArrayList<Player> players = Reader.readPlayers(TEST_FILE);
            Player player = players.get(0);
            assertEquals("John Wall", player.getFullName());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testDummyConstructor() {
        Reader reader = new Reader();
        assertTrue(reader.equals(reader));
    }
}
