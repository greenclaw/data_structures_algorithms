
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by rome on 9/22/16.
 */
public class Reader {
    private BufferedReader reader;

    public Reader(String inputPath) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(new File(inputPath)));
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }

}