import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;

public class EdgeWeightedGraph {
    // number of vertices
    private final int V;
    // number of edges
    private int E;
    // list of edges entering vertex v
    public LinkedList<Edge>[] adj;

    /**
     * Initializes an empty edge-weighted graph with {@code V} vertices and 0 edges.
     *
     * @param  V the number of vertices
     * @throws IllegalArgumentException if {@code V < 0}
     */
    public EdgeWeightedGraph(int V) {
        if (V < 0) {
            throw new IllegalArgumentException
                ("Number of vertices must be nonnegative");
        }
        this.V = V;
        this.E = 0;
        this.adj = (LinkedList<Edge>[]) new LinkedList[V];
        for (int v = 0; v < V; v++)
            adj[v] = new LinkedList<Edge>();
    }

    /**
     * Initializes an edge-weighted graph from an input stream.
     * 
     * @param br the input stream
     * @throws IOException an exception occurs while reading data
     */
    public EdgeWeightedGraph(BufferedReader br) throws IOException {
        this(Integer.parseInt(br.readLine()));
        int E = Integer.parseInt(br.readLine());
        for (int i = 0; i < E; i++) 
            addEdge(createdEdge(br.readLine().split(" ")));
    }

    /**
     * Initializes a new edge-weighted graph that is a deep copy of {@code G}.
     *
     * @param  G the edge-weighted graph to copy
     */
    public EdgeWeightedGraph(EdgeWeightedGraph G) {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < V; v++) {
            for (Edge e : G.adj(v)) 
                adj[v].add(e);
        }
    }

    /**
     * @param v the vertex to check
     */
    public void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException
                ("Vertex " + v + " is not between 0 and " + (V - 1));
    }

    /**
     * @return the number of vertices in this edge-weighted graph
     */
    public int V() {
        return V;
    }

    /**
     * @return the number of edges in this edge-weighted graph
     */
    public int E() {
        return E;
    }

    /**
     * @param  v the vertex
     * @return the degree of vertex {@code v}   
     */
    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    /**
     * Adds the undirected edge {@code e} to this edge-weighted graph.
     *
     * @param  e the edge
     */
    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        validateVertex(v);
        validateVertex(w);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    /**
     * Returns the edges incident on vertex {@code v}.
     *
     * @param  v the vertex
     * @return the edges incident on vertex {@code v} as an Iterable
     */
    public LinkedList<Edge> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    /**
     * @return all edges in this edge-weighted graph, as an iterable
     */
    public LinkedList<Edge> edges() {
        LinkedList<Edge> list = new LinkedList<Edge>();
        for (int v = 0; v < V; v++) {
            for (Edge e : adj[v]) {
                if (e.other(v) > v) 
                    list.add(e);
            }
        }
        return list;
    }

    /**
     * @param s data array to created new directed edge
     * @return new new directed edge
     */
    private Edge createdEdge(String[] s) {
        int v = Integer.parseInt(s[0]);
        int w = Integer.parseInt(s[1]);
        validateVertex(v);
        validateVertex(w);
        double weight = Double.parseDouble(s[2]);

        return new Edge(v, w, weight);
    }

    /**
     * @return the number of vertices V, followed by the number of 
     *         edges E, followed by the V adjacency lists of edges
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        String stmp = "G(V = " + V + ", E = " + E + ")\n";
        s.append(stmp);
        s.append(V + " " + E + "\n");
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (Edge e : adj[v]) {
                s.append(e + " ");
            }
            s.append("\n");
        }
        return s.toString();
    }
}
