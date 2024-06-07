public class DirectedEdge {

    private final int v;
    private final int w;
    private final double weight;

    /**
     * Initializes a directed edge from vertex v to vertex w with
     * the given weight
     */
    public DirectedEdge(int v, int w, double weight) {
        if (v < 0 || w < 0) 
            throw new 
            IllegalArgumentException("Vertex is negative number");
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    /**
     * @return the tail vertex of the directed edge
     */
    public int from() {
        return v;
    }

    /**
     * @return the head vertex of the directed edge
     */
    public int to() {
        return w;
    }

    /**
     * @return the weight of the directed edge
     */
    public double weight() {
        return weight;
    }

    /**
     * @return a string representation of the directed edge
     */
    public String toString() {
        return v + "->" + w + " " + String.format("%.2f", weight);
    }
}