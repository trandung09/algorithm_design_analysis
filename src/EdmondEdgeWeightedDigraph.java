import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class EdmondEdgeWeightedDigraph {
    
    private int E;  // number of edges in the graph
    private TreeMap<Vertex, List<EdmondEdge>> adj;
    private TreeMap<Vertex, List<EdmondEdge>> reAdj;

    /**
     * Initialize empty graph
     */
    public EdmondEdgeWeightedDigraph() {
        this.E = 0;
        this.adj   = new TreeMap<Vertex, List<EdmondEdge>>();
        this.reAdj = new TreeMap<Vertex, List<EdmondEdge>>();
    }

    /**
     * Initialize the graph by reading data from an existing file
     * 
     * @param br The object reads input data from file(Buffered reader)
     * @throws IOException 
     */
    public EdmondEdgeWeightedDigraph(BufferedReader br) 
        throws IOException {
        this();
        int tE = Integer.parseInt(br.readLine());

        for (int i = 0; i < tE; i++) {
            String[] tokens = br.readLine().split(" ");
            Vertex v = new Vertex(tokens[0]);
            Vertex w = new Vertex(tokens[1]);
            double weight = Double.parseDouble(tokens[2]);

            addEdge(new EdmondEdge(v, w, weight));
        }
    }

    /**
     * Initialize a graph by copying data from another graph
     */
    public EdmondEdgeWeightedDigraph(EdmondEdgeWeightedDigraph G) {
        this();
        this.E = G.E();
        for (EdmondEdge e : G.edges()) {
            this.addEdge(e);
        }
    }

    /**
     * @return number of vertices in the graph
     */
    public int V() {
        return adj.size();
    }

    /**
     * @return number of edges in the graph
     */
    public int E() {
        return E;
    }

    /**
     * Add an edge and update the graph
     */
    public void addEdge(EdmondEdge e) {
        Vertex v = e.from(); 
        Vertex w = e.to();
        // Check to initialize adjacency lists for added edge vertices
        if (!adj.containsKey(v)) 
            adj.put(v, new LinkedList<EdmondEdge>());
        if (!adj.containsKey(w)) 
            adj.put(w, new LinkedList<EdmondEdge>());
        if (!reAdj.containsKey(v)) 
            reAdj.put(v, new LinkedList<EdmondEdge>());
        if (!reAdj.containsKey(w)) 
            reAdj.put(w, new LinkedList<EdmondEdge>());

        // Add edges to the incoming and outgoing adjacency lists
        adj.get(v).add(e);
        reAdj.get(w).add(e);

        E++; // Increase the number of edges by 1
    }

    /**
     * Check the validate of an vertex
     * @param v the vertex need to check
     */
    public void validateVertex(Vertex v) {
        if (!adj.containsKey(v))
        throw new 
            IllegalArgumentException("Vertex " + v + " is not in graph");
    }

    /**
     * @return {@true} if the graph contains that vertex 
     * and {false} in otherwise
     */
    public boolean contains(Vertex v) {
        return adj.containsKey(v);
    }

    /**
     * Returns list of adjacent edges starting from vertex e
     * 
     * @return list of adjacent edges starting from vertex e
     */
    public Iterable<EdmondEdge> adj(Vertex v) {
        validateVertex(v);
        return adj.get(v);
    }

    /**
     * Returns list of adjacent edges ending from vertex e
     * 
     * @return list of adjacent edges ending from vertex e
     */
    public Iterable<EdmondEdge> reAdj(Vertex v) {
        validateVertex(v);
        return reAdj.get(v);
    }

    /**
     * Returns number of edges entering out from vertex w
     * 
     * @return number of edges entering out from vertex w
     */
    public int indegree(Vertex w) {
        validateVertex(w);
        return reAdj.get(w).size();
    }

    /**
     * Returns number of edges coming out from vertex v
     * 
     * @return number of edges coming out from vertex v
     */
    public int outdegree(Vertex v) {
        validateVertex(v);
        return adj.get(v).size();
    }

    /**
     * Returns list all edges of the graph
     * 
     * @return list all edges of the graph
     */
    public Iterable<EdmondEdge> edges() {
        List<EdmondEdge> list = new LinkedList<EdmondEdge>();
        for (Vertex v : adj.keySet()) {
            for (EdmondEdge e : adj(v)) {
                list.add(e);
            }
        }
        return list;
    }

    /**
     * Returns list all vertex of the graph
     * 
     * @return list all vertex of the graph
     */
    public Iterable<Vertex> vertices() {
        return adj.keySet();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(adj.size() + " " + E + "\n");
        for (Vertex v : adj.keySet()) {
            builder.append(v + ": ");
            for (EdmondEdge e : adj.get(v)) {
                builder.append(e + "\n");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
    
    public static void main(String[] args) throws IOException, FileNotFoundException{
        
        BufferedReader in = new BufferedReader(new FileReader("edmond.txt"));
        EdmondEdgeWeightedDigraph G = new EdmondEdgeWeightedDigraph(in);
        System.out.println("Number of vertex: " + G.V());
        System.out.print("All of vertexs: ");
        for(Vertex v : G.vertices()){
            System.out.print(v + " ");
        }

        System.out.println();
        System.out.println("Graph:");
        System.out.println(G);
    }
}
