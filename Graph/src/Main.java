import structure.graph.internal.Graph;
import structure.graph.internal.Vertex;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by rom on 22.11.16.
 */
public class Main {

    public static void main(String args[]) throws IOException {
        Graph<String,Double> graph = Graph.loadUnDirected("russia.txt");
        File file = new File("input.txt");
        File output = new File("output.txt");
        Scanner scanner = new Scanner(file);
        BufferedWriter writer = new BufferedWriter(new FileWriter(output));
        while (scanner.hasNext()) {
            String from = scanner.next().trim();
            String to = scanner.next().trim();
            double kilos = Double.parseDouble(scanner.next().trim());
            Object[] dejkstra = graph.dejkstra(from, to);
            Double[] vert = (Double[]) dejkstra[0];
            ArrayList<Vertex> path = (ArrayList<Vertex>)dejkstra[1];
            double cost = (double)dejkstra[2];

            Stack<Vertex> s = new Stack();
            Vertex target = path.get(0);
            Double time = 0.0;
            for (int i = 0; i < vert.length; i++) {
                if (vert[i] < 100000000) {
                    time += vert[i];
                    s.push(target);
                    target = path.get(i);
                } else continue;

            }

            System.out.println(from + " " + to + " " + kilos + " " + time + " " + (cost * kilos));
            writer.write(from + " " + to + " " + kilos + " " + time + " " + (cost * kilos) + "\n");
        }
        writer.close();

    }
}
