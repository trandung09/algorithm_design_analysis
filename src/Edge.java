public class Edge implements Comparable<Edge> {

    private final int v;
    private final int w;
    private final double weight;

    public Edge(int v, int w, double weight) {
        if (v < 0 || w < 0) 
            throw new IllegalArgumentException("Vertex is negative number");
        if (Double.isNaN(weight))
            throw new IllegalArgumentException("Weight is NaN");
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    /**
     * @return the weight of this edge
     */
    public double weight() {
        return weight;
    }

    /**
     * @return either endpoint of this edge
     */
    public int either() {
        return v;
    }

    /**
     * @param data one endpoint of this edge
     * @return the other endpoint of this edge
     * @throws IllegalArgumentException exception 
     *         if the vertex is not one endpoint of this edge
     */
    public int other(int data) {
        if (data == v) return w;
        if (data == w) return v;
        else 
            throw new IllegalArgumentException("Illegal endpoint");
    }

    /**
     * @return a string representation of this edge
     */
    public String toString() {
        return String.format("%d-%d %.3f", v, w, weight);
    }

    /**
     * @param  that the other edge
     * @return a negative integer, zero, or positive integer depending on whether
     *         the weight of this is less than, equal to, or greater than the
     *         argument edge
     */
    @Override
    public int compareTo(Edge o) {
        return Double.compare(this.weight, o.weight);
    }

    public static void main(String[] args) {
        
        Edge edge = new Edge(1, 2, 10);
        System.out.println(edge);
    }
}
