package structure.graph.internal;

class Edge<EdgeWeight> {
    public Edge(EdgeWeight w, Vertex to) {
        this.w = w;
        this.to = to;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    double distance;
    double cost;
    EdgeWeight w;
    Vertex to;

}