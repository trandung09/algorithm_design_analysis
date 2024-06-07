import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class FlowNetwork {

    private final int V;
    private int E;
    private List<FlowEdge>[] adj;

    /**
     * Initializes an empty flow network with {@code V} vertices and 0 edges.
     * @param V the number of vertices
     * @throws IllegalArgumentException if {@code V < 0}
     */
    public FlowNetwork(int V) {
        if (V < 0) {
            throw new IllegalArgumentException("Number of vertices must be > 0");
        }
        this.V = V;
        this.E = 0;
        this.adj = (LinkedList<FlowEdge>[]) new LinkedList[V];
        for (int v = 0; v < V; v++)
            adj[v] = new LinkedList<FlowEdge>();
    }

    /**
     * Initializes an empty flow network with other flow network
     * @param G the flow network to copy
     * @throws IllegalArgumentException if {@code V < 0}
     */
    public FlowNetwork(FlowNetwork G) {
        this.V = G.V;
        this.E = G.E();
        for (int v = 0; v < G.V(); v++) {
            for (FlowEdge e : G.adj(v)) 
                adj[v].add(e);
        }
    }

    /**
     * Initializes an empty flow network with the input stream
     * @param br the input stream
     * @throws IOException if inut stream has an exception
     */
    public FlowNetwork(BufferedReader br) 
            throws IOException {
        this(Integer.parseInt(br.readLine()));
        int E = Integer.parseInt(br.readLine());
        for (int i = 0; i < E; i++) {
            addEdge(createdFlowEdge(br.readLine().split(" ")));
        }
    }

    /**
     * @return the number of vertices in the edge-weighted graph
     */
    public int V() {
        return V;
    }

    /**
     * @return the number of edges in the edge-weighted graph
     */
    public int E() {
        return E;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("Vertex " + v + " must be between 0 and " + (V - 1));
    }

    /**
     * Adds the edge {@code e} to the network.
     * @param e the edge
     */
    public void addEdge(FlowEdge e) {
        int v = e.from();
        int w = e.to();
        validateVertex(v);
        validateVertex(w);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    /**
     * @param v the vertex
     * @return the edges incident on vertex {@code v} as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<FlowEdge> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    /**
     * @return list of all edges - excludes self loops
     */
    public Iterable<FlowEdge> edges() {
        List<FlowEdge> list = new LinkedList<FlowEdge>();
        for (int v = 0; v < V; v++)
            for (FlowEdge e : adj(v)) {
                if (e.to() != v)
                    list.add(e);
            }
        return list;
    }

    
    /**
     * @param s data array to created new directed edge
     * @return new new directed edge
     */
    private FlowEdge createdFlowEdge(String[] s) {
        int v = Integer.parseInt(s[0]);
        int w = Integer.parseInt(s[1]);
        validateVertex(v);
        validateVertex(w);
        double capacity = Double.parseDouble(s[2]);

        return new FlowEdge(v, w, capacity);
    }

    /**
     * Returns a string representation of the flow network.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        String stmp = "F(V = " + V + ", E = " + E +")\n";
        s.append(stmp);
        s.append(V + " " + E + "\n");
        for (int v = 0; v < V; v++) {
            s.append(v + ":  ");
            for (FlowEdge e : adj[v]) {
                if (e.to() != v) s.append(e + "  ");
            }
            s.append("\n");
        }
        return s.toString();
    }
}
