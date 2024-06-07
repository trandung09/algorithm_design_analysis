public class EdmondEdge {
    // Directed edge
    private final Vertex v;  // the start vertex of the edge
    private final Vertex w;  // the end vertex of the edge
    private final double weight;
    private EdmondEdge parent = this;

    public EdmondEdge(Vertex v, Vertex w, double weight) {
        if (weight < 0) 
            throw new IllegalArgumentException("Weight is < 0");
        if (Double.isNaN(weight))
            throw new IllegalArgumentException("Weight is NaN");
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    /**
     * Returns the start vertex of the edge
     * @return the start vertex of the edge
     */
    public Vertex from() {
        return v;
    }

    /**
     * Returns the end vertex of the edge
     * @return the end vertex of the edge
     */
    public Vertex to() {
        return w;
    }

    /**
     * Returns the end vertex of the edge
     * @return the weight of the edge
     */
    public double weight() {
        return weight;
    }

    /**
     * Returns the parent edge of the edge
     * 
     * @return the parent edge of the edge
     */
    public EdmondEdge parentEdge() {
        return parent;
    }

    public void setParentEdge(EdmondEdge parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return String.format("%s -> %s %.2f", v, w, weight);
    }
}
