package structure.heap.internal;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Comparator;

/**
 * This class history of contributions from file and write
 * to output file the highest contributor of each hour
 */

public class Checker {
    Heap<Contribution> timeHeap; // This heap stores contributions in ascending time order
    Heap<Contribution> feeHeap;  // This heap stores contributions in descending fee order
    // Comparator for time comparing
    Comparator<Contribution> timeComparator = (o1, o2) -> {
        if (o1.getTime().compareTo(o2.getTime()) < 0) return 1;
        if (o1.getTime().compareTo(o2.getTime()) > 0) return -1;
        return 0;
    };
    // Comparator for fee comparing
    Comparator<Contribution> feeComparator = (o1, o2) -> {
        if (o1.getFee() > o2.getFee()) return 1;
        if (o1.getFee() < o2.getFee()) return -1;
        if (o1.getTime().compareTo(o2.getTime()) < 0) return 1;
        if (o1.getTime().compareTo(o2.getTime()) > 0) return -1;
        return 0;
    };

    // Interval borders of time for contributions checking
    private LocalDateTime from;
    private LocalDateTime to;
    BufferedReader reader;
    BufferedWriter writer;

    public Checker(String input, String output) throws IOException {
        reader = new BufferedReader(new FileReader(new File(input)));
        writer = new BufferedWriter(new FileWriter(output));
    }

    // Write top contributor per hour
    public void writeTopContribututions() throws IOException {
        readContributions();              // reading contributions and interval from file
        LocalDateTime current = from;
        while(current.compareTo(to) <= 0) {
            Contribution contrib = topHourContribution(current);
            writer.write(current + ": " + contrib.getContributor() + "\n");
            current = current.plusHours(1);
        }
        writer.close();
    }

    private void readContributions() throws IOException {
        String[] lines = readLines();
        parseInterval(lines[0]);              // parse first line and store interval
        int contribNumber = lines.length - 1; // number of element in heaps
        timeHeap = new Heap(contribNumber, timeComparator);
        feeHeap = new Heap(contribNumber, feeComparator);

        // parse all contributions and store in time order
        for (int i = 1; i < lines.length; i++){
            Contribution contrib = parseContribution(lines[i]);
            timeHeap.add(contrib);
        }
    }


    private void parseInterval(String string) {
        String[] params = string.split(" ");
        from = LocalDateTime.parse(params[1]);
        to = LocalDateTime.parse(params[3]);
    }

    // Returns an Contribution object after parsing given string
    private Contribution parseContribution(String string) {
        String[] params = string.split(" ");
        String name = params[0] + " " + params[1].replace(":", "");
        int fee = Integer.valueOf(params[2]);
        LocalDateTime time = LocalDateTime.parse(params[4]);
        return new Contribution(time, fee, name);
    }

    // Returns top gorgeous contributor till concrete hour
    private Contribution topHourContribution(LocalDateTime date) {
        while(!timeHeap.isEmpty() &&
                timeHeap.root().getTime().compareTo(date) <= 0) {
            feeHeap.add(timeHeap.delete());
        }
        return feeHeap.root();
    }

    private String[] readLines() {
        // Get stream of lines from input
        // file and convert to Array of strings
        return reader.lines().toArray(size -> new String[size]);
    }
}
