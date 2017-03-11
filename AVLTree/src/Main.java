
import java.io.IOException;

/**
 * Created by rom on 17.10.16.
 */
public class Main {
    public static void main(String argv[]) throws IOException {


        // Example of usage
        FCounter counter = new FCounter("input.txt", "output.txt");
        counter.storeChars();
        counter.removeSymbols();
        counter.writeFrequency();

    }
}
