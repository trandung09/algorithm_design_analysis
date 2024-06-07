import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class DijkstraSDP {
    // merked vertex
    private boolean[] used;
    // distTo[v] = minimum total weight from v to s
    private double[] distFrom;
    private DirectedEdge[] edgeFrom;
    private PriorityQueue<Pair<Integer, Double>> pq;

    /**
     * @param G the edge-weighted digraph
     * @param u
     */
    public DijkstraSDP(EdgeWeightedDigraph G, int u) {
        distFrom = new double[G.V()];
        edgeFrom = new DirectedEdge[G.V()];
        used = new boolean[G.V()];
        pq = new PriorityQueue<Pair<Integer, Double>>();

        for (int v = 0; v < G.V(); v++)
            distFrom[v] = Double.POSITIVE_INFINITY;;

        distFrom[u] = 0;
        pq.add(new Pair<Integer, Double>(u, 0.0));

        while (!pq.isEmpty()) {  // O((E + V)* log(V))
            int v = pq.poll().key();
            relax(G, v);
        }
    }

    /**
     * Relax vertex v and put other endpoints on priority queue if changed
     * 
     * @param G the edge-weighted digraph
     * @param v the vertex to relex
     */
    private void relax(EdgeWeightedDigraph G, int v) { // O(E * Log(V))
        if (used[v] == true)
            return;

        used[v] = true;

        for (DirectedEdge e : G.adj(v)) {
            int w = e.from();
            if (distFrom[w] > distFrom[v] + e.weight()) {
                edgeFrom[w] = e;
                distFrom[w] = distFrom[v] + e.weight();
                pq.add(new Pair<Integer, Double>(w, distFrom[w]));
            }
        }
    }

    /**
     * @param v the vertex needs to checked
     * @throws IllegalArgumentException if v < 0 || v > distTo.length
     */
    private void validateVertex(int v) {
        int V = distFrom.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("Vertex " + v + " is not between 0 and " + (V - 1));
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
     * Returns the length of a shortest path from the source vertex {@code s}
     * to vertex {@code v}.
     * 
     * @param v the destination vertex
     * @return Infinity if no such path from v to s
     */
    public double distFrom(int v) {
        validateVertex(v);
        return distFrom[v];
    }

    /**
     * Returns a shortest path from the source {@code s} to vertex {@code v}
     * 
     * @param v the destination vertex
     * @return a shortest path from the source {@code s} to vertex {@code v}
     */
    public Iterable<DirectedEdge> pathFrom(int v) {
        validateVertex(v);
        if (hasPathFrom(v) == false)
            return null;

        LinkedList<DirectedEdge> path = new LinkedList<>();
        for (DirectedEdge e = edgeFrom[v]; e != null; e = edgeFrom[e.to()]) {
            path.add(e);
        }
        return path;
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
        // if has path from v to w
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

    /**
     * Print out the all path from {@code v} to {@code w} if there has a path
     * 
     * @param w the destination vertex
     */
    public void printAllPathTo(int w) {
        validateVertex(w);
        for (int v = 0; v < distFrom.length; v++)
            printPathFrom(v, w);
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("dijkstra.txt"));
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(br);
        
        System.out.println("\n\nTran Van Dung - 221230773\n");
        
        System.out.println(G);
        
        DijkstraSDP dijkstraSDP = new DijkstraSDP(G, 6);
        System.out.println("Shortest path from every vertex to vertex 6:");
        dijkstraSDP.printAllPathTo(6);

        System.out.println("\n");
    }
}
