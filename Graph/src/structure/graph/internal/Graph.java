package structure.graph.internal;

import java.util.*;
import java.io.*;
import java.util.Queue;

public class Graph<VertexType extends Comparable, D extends Number> {
    int size;
    Vertex<VertexType>[] vertices;
    ArrayList<Edge>[] adj;
    HashMap<VertexType, Integer> addr;

    final static int PARAMS_SIZE = 3;
    public Graph(VertexType[] vertices) {
        this.size = vertices.length;
        this.vertices = new Vertex[size];
        this.adj = new ArrayList[size];
        this.addr = new HashMap<>();
        for (int i = 0; i < size; i++) {
            adj[i] = new ArrayList<Edge>();
            this.vertices[i] = new Vertex(vertices[i]);
            this.addr.put(vertices[i], i);
        }
    }

    public Edge setEdge(VertexType from, VertexType to, Double weight) {
        Edge e = getEdge(from, to);
        Vertex toV = vertices[addr.get(to)];
        adj[addr.get(from)].add(new Edge(weight,toV));
        vertices[addr.get(from)].neighbours.add(toV);
        return e;
    }

    public void setEdgeBoth(VertexType from, VertexType to, Double weight) {
        setEdge(from, to, weight);
        setEdge(to, from, weight);
    }

    public Edge getEdge(VertexType from, VertexType to) {
        for (Edge e:
                adj[addr.get(from)]) {
            if (e.to.getV().equals(to)) {
                return e;
            }
        }
        return null;
    }

    public Edge getEdge(Vertex from, Vertex to) {
        for (Edge e:
                adj[addr.get(from.getV())]) {
            if (e.to.equals(to)) {
                return e;
            }
        }
        return null;
    }

    public Edge removeEdge(VertexType from, VertexType to) {
        Edge edge = getEdge(from, to);
        for (int i =0; i < adj[addr.get(from)].size(); i++) {
            Edge e = adj[addr.get(from)].get(i);
            if (e.to.equals(to)) {
                adj[addr.get(from)].remove(i);
                vertices[addr.get(from)].neighbours.add(vertices[addr.get(to)]);
                adj[addr.get(e.to.getV())].remove(e);
                vertices[addr.get(to)].neighbours.add(vertices[addr.get(from)]);
                return edge;
            }
        }
        return edge;
    }

    public boolean areAdjacent(VertexType from, VertexType to) {
        return getEdge(from, to) != null;
    }

    public List<Vertex> neighbours(VertexType v) {
        return vertices[addr.get(v)].neighbours;
    }

    public static Graph<String, Double> loadDirected(String filename) {
        File file = new File(filename);
        Graph<String, Double> graph = null;
        try {
            Scanner scanner = new Scanner(file);
            String[] names = scanner.nextLine().split(" ");
            for (int i = 0; i < names.length; i++) names[i] = names[i].trim();
            graph = new Graph<>(names);
            while (scanner.hasNext()) {
                String from = scanner.next().trim();
                String to = scanner.next().trim();
                double dist = Double.parseDouble(scanner.next().trim());
                graph.setEdge(from, to, dist);
            }
        } catch (FileNotFoundException | NumberFormatException ex) {
            ex.printStackTrace();
        }
        return graph;
    }

    public static Graph loadUnDirected(String filename) {
        File file = new File(filename);
        Graph<String, Double> graph = null;
        try {
            Scanner scanner = new Scanner(file);
            String[] names = scanner.nextLine().split(" ");
            for (int i = 0; i < names.length; i++) names[i] = names[i].trim();
            graph = new Graph<>(names);
            while (scanner.hasNext()) {
                String from = scanner.next().trim();
                String to = scanner.next().trim();
                String costs = scanner.next().trim();
                String[] p = costs.split(":");
                double[] par = new double[PARAMS_SIZE];
                for (int i = 0; i < PARAMS_SIZE; i++) {
                    par[i] = java.lang.Double.parseDouble(p[i]);
                }
                double dist = par[1];
                graph.setEdge(from, to, dist);
                Edge e = graph.adj[graph.addr.get(from)].get(graph.adj[graph.addr.get(from)].size()-1);
                e.setDistance(par[0]);
                e.setCost(par[2]);
                graph.setEdge(to, from, dist);
                e = graph.adj[graph.addr.get(to)].get(graph.adj[graph.addr.get(to)].size()-1);
                e.setDistance(par[0]);
                e.setCost(par[2]);
            }
        } catch (FileNotFoundException | NumberFormatException ex) {
            ex.printStackTrace();
        }
        return graph;
    }

    public void save(String filename) {
        try {
            try (PrintWriter writer = new PrintWriter(filename, "UTF-8")) {
                for (int i = 0; i < size - 1; i++) {
                    writer.print(this.vertices[i]); writer.print(" ");
                }
                if(size > 0) writer.print(this.vertices[size - 1]);
                writer.println();
                for (Vertex v1: vertices)

                    for (Vertex v2: neighbours((VertexType) v1.getV())) {
                        if (!v1.equals(v2)) {
                            writer.print(v1); writer.print(" ");
                            writer.print(v2); writer.print(" ");
                            writer.print(getEdge((VertexType)v1.getV(), (VertexType)v2.getV())); writer.print(" ");
                        }
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void BFS(VertexType v) {
        boolean[] marked = new boolean[size];
        Vertex<VertexType> vertex = vertices[addr.get(v)];
        Queue<Vertex> q = new PriorityQueue<>();
        Vertex current = vertex;
        marked[addr.get(v)] = true;
        do {
            Iterator neighbours = current.neighbours.iterator();
            while(neighbours.hasNext()) {
                Vertex neighbour = (Vertex)neighbours.next();
                if (marked[addr.get(neighbour.getV())]) continue;
                q.add(neighbour);
                marked[addr.get(neighbour)] = true;
            }
            current = q.remove();
        } while(!q.isEmpty());

    }

    public Object[] dejkstra(VertexType source, VertexType destination) {
        int startIndex = this.addr.get(source);
        PriorityQueue<Vertex> q = new PriorityQueue<Vertex>();
        Double[] vertices = new Double[this.size];
        boolean[] marked = new boolean[this.size];
        ArrayList<Vertex> path = new ArrayList<Vertex>();
        double cost = 0;
        for (int i = 0; i < this.size; i++) {
            vertices[i] = Double.MAX_VALUE;
            path.add(null);
            marked[i] = false;
        }
        vertices[startIndex] = 0.0;
        q.add(this.vertices[startIndex]);
        int cIndex = startIndex;
        while(!q.isEmpty()) {
            Vertex current = q.remove();;
            Iterator neighbours = current.neighbours.iterator();
            int i = 0;
            cost =0;
            while(neighbours.hasNext()) {
                Vertex neighbour = (Vertex) neighbours.next();
                if (neighbour.mark) {
                    continue;
                }
                q.add(neighbour);
                int nIndex = this.addr.get(neighbour.getV());
                Edge e = this.getEdge(current, neighbour);
                Double newWeight = (Double) e.w + vertices[cIndex];
                if (newWeight < vertices[nIndex]) {
                    vertices[nIndex] = newWeight;
                    path.set(i, current);
                }
                i++;
                cost += e.cost;

            }
            cIndex = this.addr.get(current.getV());
        }

        Object[] result = new Object[3];
        result[0] = vertices;
        result[1] = path;
        result[2] = cost;
        return result;
    }

    public Vertex<VertexType>[] vertices() {
        return vertices;
    }

}
