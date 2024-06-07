import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class DijkstraAllPairs {
    // number of vertices in the graph
    private final int V;
    private List<DirectedEdge>[] edgeFrom;
    private List<List<List<DirectedEdge>>>[] allPaths;

    /**
     * Initialization function with a directed graph, initializes the properties
     * Call the function to find all paths from one vertex to other (all vertex)
     * @param G an edge weighted digraph
     */
    public DijkstraAllPairs(EdgeWeightedDigraph G) {
        this.V = G.V();

        allPaths = (LinkedList<List<List<DirectedEdge>>>[]) new LinkedList[G.V()];
        edgeFrom = (LinkedList<DirectedEdge>[]) new LinkedList[G.V()];

        // call the init function
        initializes(G);
        // call the function to find all paths between two vertex
        findAllPathsBetweenTwoVertex();
    }

    private void initializes(EdgeWeightedDigraph G) {
        // initialize all properties of the object
        for (int v = 0; v < V; v++) {
            edgeFrom[v] = new LinkedList<DirectedEdge>();
            allPaths[v] = new LinkedList<List<List<DirectedEdge>>>();
            for (int n = 0; n < V; n++) {
                List<List<DirectedEdge>> tmp = new LinkedList<List<DirectedEdge>>();
                allPaths[v].add(tmp);
            }
        }
        // find edges coming out of v
        for (int v = 0; v < G.V(); v++) {
            for (DirectedEdge e : G.adj(v)) {
                int u = e.from();
                edgeFrom[u].add(e);
            }
        }
    }

    /**
     * @param v the vertex need to check
     * @throws IllegalArgumentException if v < 0 || v >= V - 1
     */
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException(v + " is not between 0 and " + (V - 1));
    }

    /**
     * Build an array of all paths, each element in the array is a 
     * list of paths in order to other vertices.
     */
    private void findAllPathsBetweenTwoVertex() {
        // secondary ingredients
        List<DirectedEdge> path;
        boolean[] visited;
        // using deepth-first-search to find all paths 
        // from v to w (w != v)
        for (int v = 0; v < V; v++) {
            visited = new boolean[V];
            path = new LinkedList<DirectedEdge>();

            for (int w = 0; w < V; w++) {
                if (w == v) continue;
                dfs(v, w, visited, path);
            }
        }
    }

    /**
     * @param s the source vertex
     * @param t the sink vertex
     * @param visited an marker arrays to call backtrack
     * @param path one of paths from source vertex to sink vertex
     */
    private void dfs(int s, int t, boolean[] visited, List<DirectedEdge> path) {
        validateVertex(s);
        validateVertex(t);
        // marker[s] = true
        visited[s] = true;
        // has path from s to t
        if (s == t) {
            // get source vertex and sink vertex
            int v = path.getFirst().from();
            int w = path.getLast().to();
            allPaths[v].get(w).add(new LinkedList<>(path));
        }
        // visit all edges adjacent to vertex s
        for (DirectedEdge e : edgeFrom[s]) {
            int n = e.to();
            if (!visited[n]) {
                path.add(e);
                dfs(n, t, visited, path);
                // bactrack
                path.remove(path.size() - 1);
            }
        }
        // backtrack
        visited[s] = false;
    }

    /**
     * @return {@code true} if has path from s to t
     *         {@code false} otherwise
     */
    public boolean hasPathBetween(int s, int t) {
        return allPaths[s].get(t).size() > 0;
    }

    /**
     * @param s the source vertex
     * @param t the sink vertex
     * @return a list of all paths from source vertex to sink vertex
     */
    public List<List<DirectedEdge>> allPathsBetween(int s, int t) {
        return allPaths[s].get(t);
    }

    /**
     * Print out all paths from source (s) to sink vertex (t)
     * @param s the source vertex
     * @param t the sink vertex
     */
    public void printAllPathsBetween(int s, int t) {
        List<List<DirectedEdge>> paths = allPaths[s].get(t);
        if (paths.size() == 0) {
            System.out.println(s + " to " + t + "     no path");
        }
        else {
            System.out.println("All paths from " + s + " to " + t);
            for (List<DirectedEdge> l : paths) {
                System.out.print(s + " to " + t + "  ");
                for (DirectedEdge e : l)
                    System.out.print(e + "  ");
                System.out.println();
            }
        } System.out.println("");
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("dijkstra.txt"));
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(br);
        System.out.println(G);

        DijkstraAllPairs DAP = new DijkstraAllPairs(G);

        for (int v = 1; v < G.V(); v++) {
            DAP.printAllPathsBetween(4, v);
            System.out.println();
        }
    }
}