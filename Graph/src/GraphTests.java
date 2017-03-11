/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import structure.graph.internal.Graph;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 *
 * @author s.protasov
 */
public class GraphTests {

    static long ts = -1;

    public static void tic() {
        ts = System.nanoTime();
    }

    /**
     *
     * @return milliseconds
     */
    public static float tac() {
        return (System.nanoTime() - ts) / 1e6f;
    }

    public static long mem() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        //TODO use your graph class
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("graph.txt")));
        Graph<String, Integer> g = null;
        int SIZES = 5;
        String[] names = new String[]{"sparce", "low", "medium", "high", "dense"};
        float[] ratios = new float[]{0.03f, 0.25f, 0.5f, 0.75f, 0.97f};

        String[] params = new String[] { "mem", "addedge", "removeedge", "addnode", "removemnode", "areadj", "adjnodes" };

        // stores all resulting information as [matrix][size][methods]
        float[][][] cube = new float[names.length][SIZES][params.length];

        // for each density
        for (int i = 0; i < names.length; i++) {
            // for each size
            System.gc();
            System.out.printf("%s graph [%.2f]:\n", names[i], ratios[i]);
            writer.write(String.format("%s graph [%.2f]:\n", names[i], ratios[i]));
            //TODO: tune your numbers here
            for (int s = 0, size=100;  s < SIZES; s++,size *=2) {
                System.gc();
                System.out.printf("    %d nodes:\n", size);
                writer.write(String.format("%d \n",size));

                //TODO: adjust getGraph method for your class
                g = getGraph(size, ratios[i]);

                // ------------------ MEM ----------------------------------
                float memory =  mem() / 1024;
                cube[i][s][0] = memory;
                System.out.printf("        Mem: %.0f KB\n", memory);
                writer.write(String.format("%.0f \n",memory));
                //-----------------------------------------------------------

                Random rand = new Random();
                int N = 100;
                float time;

                //--------------------- ADD EDGE ----------------------------
                tic();
                for (int x = 0; x < N; x++) {
                    int from = rand.nextInt(size), to = rand.nextInt(size);
                    //TODO: your code here
                    g.setEdge(g.vertices()[from].getV(), g.vertices()[to].getV(), 1.0);
                }
                time = tac() / N;
                cube[i][s][1] = time;
                System.out.printf("        Add edge: %f ms\n", time);
                writer.write(String.format("%f \n",time));
                //------------------------------------------------------------

                //--------------------- REMOVE EDGE --------------------------
                tic();
                for (int x = 0; x < N; x++) {
                    int from = rand.nextInt(size), to = rand.nextInt(size);
                    //TODO: your code here
                    g.removeEdge(g.vertices()[from].getV(), g.vertices()[to].getV());
                }
                time = tac() / N;
                cube[i][s][2] = time;
                System.out.printf("        Remove edge: %f ms\n", time);
                writer.write(String.format("%.0f \n",time));
                //------------------------------------------------------------

                //--------------------- ADD VERTEX ---------------------------
                tic();
                for (int x = 0; x < N; x++) {
                    int from = rand.nextInt(size), to = rand.nextInt(size);
                    //TODO: your code here

                }
                time = tac() / N;
                cube[i][s][3] = time;
                System.out.printf("        Add vertex: %f ms\n", time);
                writer.write(String.format("%f \n",time));
                //------------------------------------------------------------

                //--------------------- REMOVE VERTEX ------------------------
                tic();
                for (int x = 0; x < N; x++) {
                    int from = rand.nextInt(size), to = rand.nextInt(size);
                    //TODO: your code here

                }
                time = tac() / N;
                cube[i][s][4] = time;
                System.out.printf("        Remove vertex: %f ms\n", time);
                writer.write(String.format("%f \n",time));
                //------------------------------------------------------------

                //--------------------- ARE ADJACENT ------------------------
                tic();
                for (int x = 0; x < N; x++) {
                    int from = rand.nextInt(size), to = rand.nextInt(size);
                    //TODO: your code here
                    g.areAdjacent(g.vertices()[from].getV(), g.vertices()[to].getV());
                }
                time = tac() / N;
                cube[i][s][5] = time;
                System.out.printf("        Are adjacent: %f ms\n", time);
                writer.write(String.format("%f \n",time));
                //------------------------------------------------------------

                //--------------------- ADJACENT NODES -----------------------
                tic();
                for (int x = 0; x < N; x++) {
                    int from = rand.nextInt(size);
                    //TODO: your code here
                    g.neighbours(g.vertices()[from].getV());
                }
                time = tac() / N;
                cube[i][s][6] = time;
                System.out.printf("        Adjacent nodes: %f ms\n", time);
                writer.write(String.format("%f \n",time));
                //------------------------------------------------------------
            }
        }
        writer.close();
    }

    /**
     *
     * @param nodes number of nodes
     * @param edgeRatio 0..1 of all possible edges
     * @return generated graph
     */
    public static Graph<String, Integer> getGraph(int nodes, float edgeRatio) {
        Random rand = new Random();
        String[] ids = new String[nodes];
        for (int i = 0; i < nodes; i++) {
            ids[i] = Integer.toString(new Integer(i).hashCode());
        }
        String[] unique =  new HashSet<>((List<String>)Arrays.asList(ids)).toArray(new String[0]);
        // keeping only unique values
        Graph<String, Integer> g = new Graph<>(unique);
        if (edgeRatio > 0.5) {
            int total = nodes * (nodes - 1) / 2;
            int count = total;
            // add all nodes
            for (int i = 0; i < nodes; i++) {
                for (int j = i + 1; j < nodes; j++) {
                    //TODO: place your code here
                    g.setEdgeBoth(g.vertices()[i].getV(), g.vertices()[j].getV(), 1.0);
                }
            }

            // remove while more
            while (edgeRatio * total < count) {
                int from = rand.nextInt(nodes), to = rand.nextInt(nodes);
                //TODO: place your code here
                if (from != to) {
                    if (g.removeEdge(g.vertices()[from].getV(), g.vertices()[to].getV()) != null) {
                        count--;
                    }
                }
            }
        } else {
            int total = nodes * (nodes - 1) / 2;
            int count = 0;
            // add while less
            while (edgeRatio * total > count) {
                int from = rand.nextInt(nodes), to = rand.nextInt(nodes);
                //TODO: place your code here
                if (from != to) {
                    if (g.setEdge(g.vertices()[from].getV(), g.vertices()[to].getV(), 1.0) == null) {
                        count++;
                    }
                }
            }
        }
        return g;
    }
}
