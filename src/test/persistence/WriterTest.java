package persistence;

import org.json.JSONArray;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class WriterTest {
    private static final String TEST_FILE = "./data/WriterTestFile";

    @Test
    public void testWriteFile() {
        String jsonArrayData = "[{\"totalfreethrowsmade\":284,\"freethrowsmade\":3.5949367088607596,\"reboundspergame\":4.632911392405063,\"firstname\":\"John\",\"totalfieldgoalsmade\":519,\"fieldgoalsmade\":6.569620253164557,\"totalblocks\":45,\"threepointtpct\":0.2995391705069124,\"turnoverspergame\":3.848101265822785,\"fieldgoalpct\":0.4451114922813036,\"totaldefensiverebounds\":330,\"totalrebounds\":366,\"totalminutes\":2836.6204000000002,\"fieldgoalsattempted\":14.759493670886076,\"foulspergame\":2.278481012658228,\"totalfieldgoalsattempted\":1166,\"totalturnovers\":304,\"totalthreepointersattempted\":217,\"assistspergame\":10.025316455696203,\"totalthreepointersmade\":65,\"trueshootingpct\":0.5232856452975975,\"totalfouls\":180,\"id\":467,\"yearofseason\":2014,\"additionalheightinches\":4,\"freethrowpct\":0.7845303867403314,\"pointspergame\":17.556962025316455,\"freethrowsattempted\":4.582278481012659,\"gamesplayed\":79,\"offensiveboardspergame\":0.45569620253164556,\"threepointersattempted\":2.7468354430379747,\"lastname\":\"Wall\",\"minutespergame\":35.90658734177215,\"totalfreethrowsattempted\":362,\"threepointersmade\":0.8227848101265823,\"stealspergame\":1.7468354430379747,\"totalsteals\":138,\"totaloffensiverebounds\":36,\"defensiveboardspergame\":4.177215189873418,\"weightinpounds\":210,\"totalassists\":792,\"position\":\"G\",\"totalpoints\":1387,\"teamname\":\"Washington Wizards\",\"blockspergame\":0.569620253164557,\"heightinfeet\":6}]";
        JSONArray arr = new JSONArray(jsonArrayData);
        try {
            FileWriter testFile = new FileWriter(TEST_FILE);
            Writer.writeFile(testFile, arr);
            File file = new File(TEST_FILE);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String filedata = bufferedReader.readLine();
            assertTrue(jsonArrayData.equals(filedata));
        } catch (IOException e) {
            fail();
        }

    }

    @Test
    public void testDummyConstructor() {
        Writer writer = new Writer();
        assertTrue(writer.equals(writer));
    }
}
