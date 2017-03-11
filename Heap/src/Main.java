import structure.heap.internal.Checker;

import java.io.IOException;


/**
 * Created by rom on 24.10.16.
 */
public class Main {

    public static void main(String args[]) throws IOException {
        Checker checker = new Checker("input.txt", "output.txt");
        checker.writeTopContribututions();
    }
}
