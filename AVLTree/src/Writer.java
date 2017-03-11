import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Writer is a wrapper for writing an strings
 */
public class Writer {
    protected BufferedWriter writer;

    public Writer(String outputPath) throws IOException {
        writer = new BufferedWriter(new FileWriter(outputPath));
    }

    public void write(String string) throws IOException {
        writer.write(string);
    }

}