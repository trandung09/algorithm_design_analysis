import java.util.TreeMap;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class EdmondEdgeWeightedDirectedCycle {
    
    private boolean[] marked;        
    private boolean[] onStack;
    private Vertex[] vertices;
    private EdmondEdge[] edgeTo;
    private Stack<Vertex> v_cycle;
    private Stack<EdmondEdge> cycle;
    private TreeMap<Vertex, Integer> index_v;

    public EdmondEdgeWeightedDirectedCycle(EdmondEdgeWeightedDigraph G) {
        marked  = new boolean[G.V()];
        onStack = new boolean[G.V()];
        edgeTo  = new EdmondEdge[G.V()];
        vertices= new Vertex[G.V()];
        index_v = new TreeMap<>();
        int i = 0;
        for (Vertex v : G.vertices()) {
            vertices[i] = v;
            index_v.put(v,i);
            i++;
        }
        for (i = 0; i < G.V(); i++)
            if (!marked[i]) dfs(G, i);
    }

    /**
     * Use a depth-first search algorithm to search for cycles 
     * starting from a given vertex and graph
     */
    private void dfs(EdmondEdgeWeightedDigraph G, int i_v) {
        onStack[i_v] = true;
        marked[i_v] = true;
        Vertex v = vertices[i_v];
        for (EdmondEdge e : G.adj(v)) {
            Vertex w = e.to();
            int i_w = index_v.get(w);
            // short circuit if directed cycle found
            if (cycle != null) return;

            // found new vertex, so recur
            else if (!marked[i_w]) {
                edgeTo[i_w] = e;
                dfs(G, i_w);
            }

            // trace back directed cycle
            else if (onStack[i_w]) {
                cycle = new Stack<EdmondEdge>();
                v_cycle = new Stack<Vertex>(); 

                EdmondEdge f = e;
                while (!f.from().equals(w)) {
                    cycle.push(f);
                    v_cycle.push(f.to());
                    f = edgeTo[index_v.get(f.from())];
                }
                cycle.push(f);
                v_cycle.push(f.to());
                
                return;
            }
        }
        // to call backtrack    
        onStack[i_v] = false; // to call backtrack
    }

    /**
     * Returns true if graph has a cycle, false in otherwise
     * 
     * @return true if graph has a cycle, false in otherwise
     */
    public boolean hasCycle() {
        return cycle != null;
    }

    /**
     * Returns list all edge of cycle if graph has a cycle
     * 
     * @return list all edge of cycle 
     */
    public Iterable<EdmondEdge> cycle() {
        return cycle;
    }

    /**
     * Returns list all vertex of cycle if graph has a cycle
     * 
     * @return list all vertex of cycle 
     */Iterable<Vertex> v_cycle() {
        return v_cycle;
    }

    public static void main(String[] args) throws IOException, FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader("edmond.txt"));
        EdmondEdgeWeightedDigraph G = new EdmondEdgeWeightedDigraph(br);

        System.out.println(G);

        // find a directed cycle
        EdmondEdgeWeightedDirectedCycle finder = new EdmondEdgeWeightedDirectedCycle(G);
        if (finder.hasCycle()) {
            System.out.print("Cycle: ");
            for (EdmondEdge e : finder.cycle()) {
                System.out.print(e + " ");
            }
            System.out.println();
        } else {
            System.out.println("No directed cycle");
        }
    }
}
