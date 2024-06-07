import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;

public class EdgeWeightedDigraph {
    // number of vertices
    private final int V;
    // number of edges
    private int E;
    // outdegree of vertex v (deg+)
    private int[] outdegree;
    // list of edges entering vertex v
    private LinkedList<DirectedEdge>[] adj;

    /**
     * Contructor with V is the number of vertices in the edge weighted digraph
     * 
     * @param V the number of vertices in the graph
     */
    public EdgeWeightedDigraph(int V) {
        if (V < 0)
            throw new IllegalArgumentException("Number of vertices is negative");
        this.V = V;
        this.E = 0;
        outdegree = new int[V];
        adj = (LinkedList<DirectedEdge>[]) new LinkedList[V];
        for (int v = 0; v < V; v++)
            adj[v] = new LinkedList<DirectedEdge>();
    }

    /**
     * @param br the input stream
     * @throws IOException an exception occurs while reading data
     */
    public EdgeWeightedDigraph(BufferedReader br)
            throws IOException {
        this(Integer.parseInt(br.readLine()));
        int E = Integer.parseInt(br.readLine());
        for (int i = 0; i < E; i++)
            addEdge(createdEdge(br.readLine().split(" ")));
    }

    /**
     * @param G the edge weighted digraph
     */
    public EdgeWeightedDigraph(EdgeWeightedDigraph G) {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < V; v++)
            outdegree[v] = G.outdegree(v);
        for (int v = 0; v < V; v++) {
            for (DirectedEdge e : G.adj(v))
                adj[v].add(e);
        }
    }

    /**
     * @return the number of vertices in this edge-weighted digraph
     */
    public int V() {
        return V;
    }

    /**
     * @return the number of edges in this edge-weighted digraph
     */
    public int E() {
        return E;
    }

    /**
     * @param v the vertex
     * @return in degree of vertex v
     */
    public int outdegree(int v) {
        validateVertex(v);
        return outdegree[v];
    }

    /**
     * @param v the vertex
     * @return out degree of vertex v
     */
    public int indegree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    /**
     * @param v the vertex
     */
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException(v + " is not between 0 and " + (V - 1));
    }

    /**
     * Adds the directed edge e to this edge-weighted digraph.
     * 
     * @param e edge want to add
     */
    public void addEdge(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        validateVertex(v);
        validateVertex(w);
        adj[w].add(e);
        outdegree[v]++;
        E++;
    }

    /**
     * @param v the vertex
     * @return linked list of vertex v
     */
    public LinkedList<DirectedEdge> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    /**
     * @return all edges in this edge-weighted digraph, as an Linked list
     */
    public LinkedList<DirectedEdge> edges() {
        LinkedList<DirectedEdge> list = new LinkedList<>();
        for (int v = 0; v < V; v++) {
            for (DirectedEdge e : adj[v])
                list.add(e);
        }
        return list;
    }

    /**
     * @param s data array to created new directed edge
     * @return new new directed edge
     */
    private DirectedEdge createdEdge(String[] s) {
        int v = Integer.parseInt(s[0]);
        int w = Integer.parseInt(s[1]);
        validateVertex(v);
        validateVertex(w);
        double weight = Double.parseDouble(s[2]);

        return new DirectedEdge(v, w, weight);
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
            for (DirectedEdge e : adj[v]) {
                s.append(e + " ");
            }
            s.append("\n");
        }
        return s.toString();
    }
}