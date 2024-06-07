import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FordFulkerson {
    private final Double FLOATING_POINT_EPSILON = 1E-10;

    private final int V;      
    private double value;    
    private boolean[] marked;  
    private FlowEdge[] edgeTo;  

    /**
     * Compute a maximum flow and minimum cut in the network {@code G}
     * from vertex {@code s} to vertex {@code t}.
     * @param G the flow network
     * @param s the source vertex
     * @param t the sink vertex
     * @throws IllegalArgumentException if {@code s == t}
     * @throws IllegalArgumentException if initial flow is infeasible
     */
    public FordFulkerson(FlowNetwork G, int s, int t) {
        this.V = G.V();
        validateVertex(s);
        validateVertex(t);
        if (s == t)  
            throw new IllegalArgumentException("Source equals sink");
        if (!isFeasible(G, s, t))
            throw new IllegalArgumentException("Initial flow is infeasible");

        this.value = excess(G, t);
        // while has augmenting path from s to t
        while (hasAugmentingPath(G, s, t)) {
            double bottle = Double.POSITIVE_INFINITY;
            for (int v = t; v != s; v = edgeTo[v].other(v))
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
            // augment flow
            for (int v = t; v != s; v = edgeTo[v].other(v))
                edgeTo[v].addResidualFlowTo(v, bottle);

            value += bottle;
        }

        assert check(G, s, t);
    }

    /**
     * @return the value of the maximum flow
     */
    public double maxFlow() {
        return value;
    }

    /**
     * @param v the vertex
     * @return {@code true} if v is on the side of the mincut
     *         {@code false} otherwise
     */
    public boolean inCut(int v) {
        validateVertex(v);
        return marked[v];
    }

    public Iterable<Integer> edgeInCut() {
        List<Integer> list = new LinkedList<Integer>();
        for (int v = 0; v < V; v ++) {
            if (inCut(v)) list.add(v);
        }
        return list;
    }

    /**
     * @param v the vertex need to check
     */
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("Vertex " + v + " is not between 0 and " + (V-1));
    }

    /**
     * Using breadth-first-search to check augment path
     * @param G the flow network
     * @param s the source vertex
     * @param t the sink vertex
     * @return {@code true} if marked[t] = true and {@code false} otherwise
     */
    private boolean hasAugmentingPath(FlowNetwork G, int s, int t) {
        marked = new boolean[G.V()];
        edgeTo = new FlowEdge[G.V()];

        Queue<Integer> queue = new ArrayDeque<Integer>();
        queue.add(s);
        marked[s] = true;
        // using breadth-first-search
        while(!queue.isEmpty() && !marked[t]) {
            int v = queue.poll();

            for (FlowEdge e : G.adj(v)) {
                int w = e.other(v);
                if (e.residualCapacityTo(w) > 0) {
                    if (!marked[w]) {
                        marked[w] = true;
                        edgeTo[w] = e;
                        queue.add(w);
                    }
                }
            }
        }
        return marked[t];
    }

    /**
     * @return excess flow at vertex v
     */
    private double excess(FlowNetwork G, int v) {
        double excess = 0.0;
        for (FlowEdge e : G.adj(v)) {
            if (v == e.from()) excess -= e.flow();
            else               excess += e.flow();
        }
        return excess;
    }

    /**
     * @return {@code true} if flow is infeasible, {@code false} otherwise
     */
    private boolean isFeasible(FlowNetwork G, int s, int t) {
        // check that capacity constraints are satisfied
        for (int v = 0; v < G.V(); v++) {
            for (FlowEdge e : G.adj(v)) {
                if (e.flow() < -FLOATING_POINT_EPSILON 
                        || e.flow() > e.capacity() + FLOATING_POINT_EPSILON) {
                    System.err.println("Edge does not satisfy capacity constraints: " + e);
                    return false;
                }
            }
        }
         // check that net flow into a vertex equals zero, except at source and sink
        if (Math.abs(value + excess(G, s)) > FLOATING_POINT_EPSILON) {
            System.err.println("Excess at source = " + excess(G, s));
            System.err.println("Max flow         = " + value);
            return false;
        }

        if (Math.abs(value - excess(G, t)) > FLOATING_POINT_EPSILON) {
            System.err.println("Excess at sink   = " + excess(G, t));
            System.err.println("Max flow         = " + value);
            return false;
        }

        for (int v = 0; v < G.V(); v++) {
            if (v == s || v == t) continue;
            else if (Math.abs(excess(G, v)) > FLOATING_POINT_EPSILON) {
                System.err.println("Net flow out of " + v + " doesn't equal zero");
                return false;
            }
        }
        
        return true;
    }

    /**
     * Check optimality conditions of maximum flow
     */
    private boolean check(FlowNetwork G, int s, int t) {
        // check that s is on the source side of min cut and that t is not on source side
        if (!inCut(s)) {
            System.err.println("Source " + s + " is not on source side of min cut");
            return false;
        }

        if (inCut(t)) {
            System.err.println("Sink " + t + " is on source side of min cut");
            return false;
        }
        // check that value of min cut = value of max flow
        double mincutValue = 0.0;
        for (int v = 0; v < G.V(); v++) {
            for (FlowEdge e : G.adj(v)) {
                if ((v == e.from()) && inCut(e.from()) && !inCut(e.to()))
                    mincutValue += e.capacity();
            }
        }

        if (Math.abs(mincutValue - value) > FLOATING_POINT_EPSILON) {
            System.err.println("Max flow value = " + value + ", min cut value = " + mincutValue);
            return false;
        }

        return true;
    }

    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new FileReader("fordfulkerson.txt"));
        FlowNetwork G = new FlowNetwork(br);


        System.out.println("\n\nTran Van Dung - 221230773\n");
        // Print out flow network G
        System.out.println(G);
        
        // Compute maximum flow and min cut
        int s = 0, t = G.V() - 1;
        FordFulkerson FF = new FordFulkerson(G, s, t);

        System.out.println("Max flow from " + s + " to " + t);
        for (int v = 0; v < G.V(); v++) {
            for (FlowEdge e : G.adj(v)) {
                if (v == e.from() && e.flow() > 0) 
                    System.out.println("  " +  e);
            }
        }
        // Print out min cut
        System.out.print("\nMin cut: { ");
        for (int v : FF.edgeInCut()) {
            System.out.print(v + ", ");
        }
        System.out.println(" }");

        for (int v : FF.edgeInCut()) {
            for (FlowEdge e : G.adj(v)) {
                if (v == e.from() && !FF.inCut(e.to())) {
                    System.out.println("  " + e);
                }
            }
        }
        // Print out max flow value of maximum flow
        System.out.println("\nMax flow value: " + FF.maxFlow() + "\n");
    }
}
