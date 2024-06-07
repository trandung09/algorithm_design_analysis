public class FlowEdge {

    private final int v; // from
    private final int w; // to
    private final double capacity; // capacity
    private double flow; // flow

    /**
     * Initializes an edge from vertex {@code v} to vertex {@code w} with
     * the given {@code capacity} and zero flow.
     * 
     * @param v        the tail vertex
     * @param w        the head vertex
     * @param capacity the capacity of the edge
     * @throws IllegalArgumentException if {@code capacity < 0.0}
     */
    public FlowEdge(int v, int w, double capacity) {
        validateVertex(v);
        validateVertex(w);
        if (capacity < 0)
            throw new IllegalArgumentException("edge capacity must be > 0");
        this.v = v;
        this.w = w;
        this.flow = 0;
        this.capacity = capacity;
    }

    /**
     * Initializes an edge from vertex {@code v} to vertex {@code w} with
     * the given {@code capacity} and {@code flow}.
     * 
     * @param v        the tail vertex
     * @param w        the head vertex
     * @param capacity the capacity of the edge
     * @param flow     the flow on the edge
     * @throws IllegalArgumentException if {@code capacity} is negative
     * @throws IllegalArgumentException unless {@code flow} is between
     *                                  {@code 0.0} and {@code capacity}.
     */
    public FlowEdge(int v, int w, double capacity, double flow) {
        validateVertex(v);
        validateVertex(v);
        if (capacity < 0)
            throw new IllegalArgumentException("Edge capacity must be > 0");
        if (flow > capacity || flow < 0)
            throw new IllegalArgumentException("Flow must be between 0 and " + capacity);
        this.v = v;
        this.w = w;
        this.flow = flow;
        this.capacity = capacity;
    }

    /**
     * Initializes a flow edge from another flow edge.
     * 
     * @param e the edge to copy
     */
    public FlowEdge(FlowEdge e) {
        this.v = e.v;
        this.w = e.w;
        this.flow = e.flow;
        this.capacity = e.capacity;
    }

    /**
     * @return the tail vertex of the flow edge.
     */
    public int from() {
        return v;
    }

    /**
     * @return the head vertex of the flow edge
     */
    public int to() {
        return w;
    }

    /**
     * @return the flow of the flow edge
     */
    public double flow() {
        return flow;
    }

    /**
     * @return the capacity of the flow edge
     */
    public double capacity() {
        return capacity;
    }

    /**
     * @param vertex one endpoint of the edge
     * @return the endpoint of the edge that is different from the given vertex
     * @throws IllegalArgumentException if {@code vertex} is not one of the edge
     */
    public int other(int vertex) {
        if (vertex == v)
            return w;
        else if (vertex == w)
            return v;
        else
            throw new IllegalArgumentException("Invalid endpoint");
    }

    /**
     * Trả về dung lượng còn lại của cạnh theo hướng đến vertex
     * 
     * @param vertex one endpoint of the edge
     * @return the residual capacity of the edge in the direction
     *         to the given {@code vertex}.
     */
    public double residualCapacityTo(int vertex) {
        validateVertex(vertex);
        if (vertex == v)
            return flow;
        else if (vertex == w)
            return capacity - flow;
        else
            throw new IllegalArgumentException("Invalid endpoint");
    }

    /**
     * @param vertex one endpoint of the edge
     * @param data  amount by which to increase flow
     */
    public void addResidualFlowTo(int vertex, double data) {
        validateVertex(vertex);
        if (data < 0)
            throw new IllegalArgumentException("Data must be nonnegative");

        if (vertex == v)
            flow -= data;
        else if (vertex == w)
            flow += data;
        else
            throw new IllegalArgumentException("Nnvalid endpoint");

        if (flow < 0.0)
            throw new IllegalArgumentException("Flow is negative");
        if (flow > capacity)
            throw new IllegalArgumentException("Flow exceeds capacity");
    }

    /**
     * @param v the vertex needs to check
     * @throws IllegalArgumentException if {@code v} is negative integer
     */
    private void validateVertex(int v) {
        if (v < 0)
            throw new IllegalArgumentException("Vertex index must be > 0");
    }

    /**
     * @return a string representation of the edge
     */
    public String toString() {
        return String.format("%d->%d %.2f/%.2f", v, w, flow, capacity);
    }
}
