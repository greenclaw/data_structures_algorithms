package structure.graph.internal;

import java.util.ArrayList;

public class Vertex<VertexType extends Comparable> implements Comparable<Vertex>{
    public Vertex(VertexType v) {
        this.v = v;
        this.neighbours = new ArrayList<Vertex>(0);
        this.mark = false;
    }

    boolean mark;
    public void setMark() {
        mark = true;
    }
    public VertexType getV() {
        return v;
    }

    public boolean equals(Vertex v) {
        return v.getV().compareTo(this.getV()) == 0;
    }
    ArrayList<Vertex> neighbours;

    VertexType v;

    public int compareTo(Vertex o) {
        if (getV().compareTo(o.getV()) < 0) {
            return -1;
        } else
            if (getV().compareTo(o.getV()) == 0) {
            return 0;
        } else return 1;
    }
}