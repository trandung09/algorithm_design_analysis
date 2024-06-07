import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Bellman-Ford: Single destination Shortest paths 
 */
public class BellmanFordSDP {
    // distTo[v] = minimum total weight from v to s
    private double[] distFrom; 
    // number of calls to relex       
    private int cost;             
    // merked vertex
    private boolean[] onQueue;      
    private DirectedEdge[] edgeFrom; 
    private Queue<Integer> queue;
    private LinkedList<DirectedEdge> cycle;

    /**
     * @param G the edge-weighted digraph
     * @param s 
     */
    public BellmanFordSDP(EdgeWeightedDigraph G, int s) {
        distFrom = new double[G.V()];
        edgeFrom = new DirectedEdge[G.V()];
        onQueue = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            distFrom[v] = Double.POSITIVE_INFINITY;
        distFrom[s] = 0;

        // Bellman-Ford algorithm
        queue = new ArrayDeque<Integer>();
        queue.add(s);
        onQueue[s] = true;

        while (!queue.isEmpty() && !hasNegativeCycle()) { // O(V * E)
            int v = queue.poll();
            onQueue[v] = false;
            relax(G, v);
        }
    }

    /**
     * Relax vertex v and put other endpoints on queue if changed
     * 
     * @param G the edge-weighted digraph
     * @param v the vertex to relex
     */
    private void relax(EdgeWeightedDigraph G, int v) { // O(E)
        for (DirectedEdge e : G.adj(v)) {

            int w = e.from();
            if (distFrom[w] > distFrom[v] + e.weight()) {
                distFrom[w] = distFrom[v] + e.weight();
                edgeFrom[w] = e;
                if (!onQueue[w]) {
                    queue.add(w);
                    onQueue[w] = true;
                }
            }

            if (cost++ % G.V() == 0) {
                findNegativeCycle();
                if (hasNegativeCycle()) return; // found a negative cycle
            }
        }
    }

    /**
     * @return {@code true} if digraph has negative
     *         {@code false} otherwise
     */ 
    public boolean hasNegativeCycle() {
        return cycle != null;
    }

    /**
     * By finding a cycle in predecessor graph 
     */
    public void findNegativeCycle() { // O(V * E)
        int V = edgeFrom.length;
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(V);
        for (int v = 0; v < V; v++) {
            if (edgeFrom[v] != null) {
                G.addEdge(edgeFrom[v]);
            }
        }

        EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(G);
        cycle = finder.cycle();
    }

    /**
     * @param v the vertex needs to checked
     * @throws IllegalArgumentException if v < 0 || v > distTo.length
     */
    private void validateVertex(int v) {
        int V = distFrom.length;
        if (v < 0 || v >= distFrom.length)
            throw new 
            IllegalArgumentException(v + " is not between 0 and " + (V-1));
    }

    /**
     * Returns the length of a shortest path from the source vertex {@code s} 
     * to vertex {@code v}.
     * 
     * @param v the destination vertex
     * @return Infinity if no such path from v to s
     */
    public double distFrom(int v) {
        validateVertex(v);
        if (hasNegativeCycle())
            throw new UnsupportedOperationException("Has negative cycle");
        return distFrom[v];
    }

    /**
     * @param v the destination vertex
     * @return {@code true} if there is a path from the source vertex
     *         {@code s} to vertex {@code v}, and {@code false} otherwise
     */
    public boolean hasPathFrom(int v) {
        validateVertex(v);
        return distFrom[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * Returns a shortest path from the source {@code s} to vertex {@code v}
     * 
     * @param v the destination vertex
     * @return a shortest path from the source {@code s} to vertex {@code v}
     */
    public LinkedList<DirectedEdge> pathFrom(int v) {
        validateVertex(v);
        if (hasNegativeCycle())
            throw new UnsupportedOperationException("Has negative cycle");
        if (!hasPathFrom(v)) 
            return null;

        LinkedList<DirectedEdge> path = new LinkedList<>();
        for (DirectedEdge e = edgeFrom[v]; e != null; e = edgeFrom[e.to()]) {
            path.add(e);
        }
        return path;
    }

    /**
     * @return a negative cycle reachable from the soruce vertex {@code s} 
     * as an {@code LinkedList} of edges, and {@code null} if there is no such cycle
     */
    public LinkedList<DirectedEdge> negativeCycle() {
        return cycle;
    }

    /**
     * Print out the path from {@code v} to {@code w} if there has a path
     * 
     * @param v the source vertex
     * @param w the destination vertex
     */
    public void printPathFrom(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        if (hasPathFrom(v)) {
            System.out.printf("%d to %d (%.2f)  ", v, w, distFrom(v));
            for (DirectedEdge e : pathFrom(v))
                System.out.print(e + "  ");
            System.out.println();
        }
        else {
            System.out.printf("%d to %d         no path\n", v, w);
        }
    }

    public void printAllPathTo(int w) {
        validateVertex(w);
        if (hasNegativeCycle()) {
            System.out.println("Directed graphs have negative cycles");
        }
        else {
            for (int v = 0; v < distFrom.length; v++) 
                printPathFrom(v, w);
        }
    }

    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new FileReader("bellman.txt"));
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(br);
        System.out.println("\n\nTran Van Dung - 221230773\n");
        System.out.println(G);

        BellmanFordSDP bFordSDP = new BellmanFordSDP(G, 6);
        
        System.out.println("Shortest path from every vertex to vertex 6");
        bFordSDP.printAllPathTo(6);
        System.out.println("\n");
    }
}


