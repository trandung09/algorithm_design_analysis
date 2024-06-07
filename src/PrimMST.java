import java.io.*;
import java.util.*;

public class PrimMST {

    private boolean[] used;     // marker array
    private double[] distTo;   
    private double weight = 0;
    private Edge[] edgeTo;  
    private Queue<Edge> mst;    // edges of minimum spanning tree
    private PriorityQueue<Pair<Integer, Double>> pq;

    /**
     * @param G an undirected graph with non-negative weights
     */
    public PrimMST(EdgeWeightedGraph G) {
        used = new boolean[G.V()];
        distTo = new double[G.V()];
        edgeTo = new Edge[G.V()];
        mst = new ArrayDeque<Edge>();
        pq = new PriorityQueue<Pair<Integer, Double>>();
        // initialization step
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;

        distTo[0] = 0.0;
        pq.add(new Pair<Integer, Double>(0, 0.0));

        // prim algorithm
        while (!pq.isEmpty()) {
            int v = pq.poll().key();
            visit(G, v);
        }
        // add edge of mst to queue (mst)
        for (int v = 1 ; v < edgeTo.length ; v++) {
            mst.add(edgeTo[v]);
            weight += distTo[v];
        }
    }

    /** 
     * @param G an undirected graph with non-negative weights
     * @param v vertex to visit 
     */
    private void visit(EdgeWeightedGraph G, int v) { // O(E * log(V))
        if (used[v]) return;

        used[v] = true;
        for (Edge e : G.adj(v)) {
            int u = e.other(v);
            if (used[u]) continue;

            if (e.weight() < distTo[u]) {
                edgeTo[u] = e;
                distTo[u] = e.weight();

                pq.add(new Pair<Integer,Double>(u, distTo[u]));
            }
        }
    }

    /**
     * @return returns the selected edge iterable
     */
    public Iterable<Edge> edgesOfMst() {
        return mst;
    }

    /**
     * @return the weighted sum of the minimum spanning tree
     */
    public double weight() {
        return weight;
    }

    public static void main(String[] args) throws IOException {
        // Create an edge weighted graph with the input stream
        BufferedReader br = new BufferedReader(new FileReader("prim.txt"));
        EdgeWeightedGraph G = new EdgeWeightedGraph(br);

        System.out.println("\n\nTran Van Dung - 221230773\n");
        System.out.println(G);

        // Create primMST object with G
        PrimMST prim = new PrimMST(G);

        // Print out mininum spanning tree 
        System.out.println("Minimum spanning tree: ");
        for (Edge e : prim.edgesOfMst()) {
            System.out.println("  " + e);
        }
        // print out minimum spanning tree value
        System.out.printf("\nMinimum spanning tree value: %.2f\n\n", prim.weight());
    }
}