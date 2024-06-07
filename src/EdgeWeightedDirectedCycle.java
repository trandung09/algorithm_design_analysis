import java.util.LinkedList;

public class EdgeWeightedDirectedCycle {
    
    private boolean[] marked;
    private boolean[] onStack;
    private DirectedEdge[] edgeFrom;
    private LinkedList<DirectedEdge> cycle;

    /**
     * Contructor: find a directed cycle
     * 
     * @param G the edge-weighted digraph
     */
    public EdgeWeightedDirectedCycle(EdgeWeightedDigraph G) {
        marked  = new boolean[G.V()];
        onStack = new boolean[G.V()];
        edgeFrom  = new DirectedEdge[G.V()];

        for (int v = 0; v < G.V(); v++) 
            if (!marked[v]) dfs(G, v);
    }

    /**
     * @param G the edge-weighted digraph
     * @param v begin vertex to search cycle
     */
    public void dfs(EdgeWeightedDigraph G, int v) { // O(V * E)
        marked[v] = true;
        onStack[v] = true;

        for (DirectedEdge e : G.adj(v)) {
            int w = e.from();

            if (cycle != null) return;
            // if w not marked
            if (!marked[w]) {
                edgeFrom[w] = e; // edgeTo[v] = previous edge on path to v
                dfs(G, w);
            }
            // if w maked and onStack
            else if (onStack[w]) {
                cycle = new LinkedList<DirectedEdge>();

                DirectedEdge f = e;
                while (f.to() != w) {
                    cycle.add(f);
                    f = edgeFrom[f.to()];
                }
                cycle.add(f);

                return;
            }
        }

        onStack[v] = false;
    }

    /**
     * @return {@code true} if has cycle
     *         {@code false} otherwise
     */
    public boolean hasCycle() {
        return cycle != null;
    }

    /**
     * @return a directed cycle (as an iterable) if the edge-weighted digraph
     *         has a directed cycle, and {@code null} otherwise
     */
    public LinkedList<DirectedEdge> cycle() {
        return cycle;
    }


    /**
     * @return total weight of the cycle (if graph has cycle)
     */
    public double totalWeightOfCycle() {
        double result = 0;
        for (DirectedEdge e : cycle)    
            result += e.weight();

        return result;
    }
}
