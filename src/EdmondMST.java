import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.TreeMap;

public class EdmondMST {

    private double weight;                              // weight of MST
    private Queue<EdmondEdge> mst = new ArrayDeque<>(); // edges in MST
    private TreeMap<Vertex, EdmondEdge> edgeTo;         // canh co weight nho nhat di vao w

    /**
     * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
     * @param G the edge-weighted graph
     */
    public EdmondMST(EdmondEdgeWeightedDigraph G, String root) {
        Vertex r = new Vertex(root);
        edgeTo   = new TreeMap<>();
        if (!G.contains(r)) 
            throw new IllegalArgumentException("Vertex " + root + " is not in graph");
        EdmondEdgeWeightedDigraph temp_mst = new EdmondEdgeWeightedDigraph();
        
        //chon cac canh di den dinh w be nhat tru node goc
        for (Vertex w : G.vertices()) {
            if (w.equals(r)) continue;
            EdmondEdge min_edge = new EdmondEdge(w, w,Double.MAX_VALUE);
            for (EdmondEdge e : G.reAdj(w)) {
                if (e.weight() < min_edge.weight()) min_edge = e;
            }
            //khong the tao mst
            if (min_edge.weight() == Double.MAX_VALUE) 
                throw new IllegalArgumentException("Cannot create minium spainning tree");
            edgeTo.put(w, min_edge);
            temp_mst.addEdge(min_edge);
        }
        
        //check cycle
        EdmondEdgeWeightedDirectedCycle cycle = new EdmondEdgeWeightedDirectedCycle(temp_mst);
        //co cycle
        if (cycle.hasCycle()) {
            //create super node
            List<Vertex> bag = new LinkedList<>();
            for (Vertex v : cycle.v_cycle()) {
                bag.add(v);
            }
            Vertex s_node = new Vertex(bag);
            //create G'
            EdmondEdgeWeightedDigraph temp_G = new EdmondEdgeWeightedDigraph();
            for (EdmondEdge e : G.edges()) {
                Vertex v = e.from();
                Vertex w = e.to();
                if (s_node.contains(v)) v = s_node;
                if (s_node.contains(w)) w = s_node;
                if (v == w) continue;
                if (w.equals(r)) continue;
                //create E'
                // tru vi dang xet voi do thi ban dau
                EdmondEdge temp_e = new EdmondEdge(v, w, e.weight() - edgeTo.get(e.to()).weight());
                temp_e.setParentEdge(e);
                temp_G.addEdge(temp_e);
            }
            // create mst
            edgeTo = new TreeMap<>();
            // gọi dep quy
            EdmondMST mini = new EdmondMST(temp_G, root);
            for (EdmondEdge e : mini.edges()) {
                edgeTo.put(e.parentEdge().to(), e.parentEdge());
                mst.add(e.parentEdge());
                weight += e.parentEdge().weight();
            }
            for (EdmondEdge e : cycle.cycle()) {
                //khong lay cac canh da chon khi co lai
                if (!edgeTo.containsKey(e.to())) {
                    edgeTo.put(e.to(),e);
                    mst.add(e);
                    weight += e.weight();
                }
            }
        } 
        else {
            for(EdmondEdge e: temp_mst.edges()){
                mst.add(e);
                weight += e.weight();
            }
        }
    }

    /**
     * Returns the edges in a minimum spanning tree (or forest)
     * 
     * @return the edges in a minimum spanning tree (or forest)
     */
    public Iterable<EdmondEdge> edges() {
        return mst;
    }

    /**
     * Returns the sum of the edge weights in a minimum spanning tree (or forest)
     * 
     * @return the sum of the edge weights in a minimum spanning tree (or forest)
     */
    public double weight() {
        return weight;
    }

    public static void main(String[] args) throws IOException, FileNotFoundException{
        
        BufferedReader br = new BufferedReader(new FileReader("edmond.txt"));
        EdmondEdgeWeightedDigraph G = new EdmondEdgeWeightedDigraph(br);

        EdmondMST mst = new EdmondMST(G, "r");

        System.out.println("\n\nTran Van Dung - 221230773\n");

        System.out.println("Graph G:");
        for (EdmondEdge e : G.edges()) {
            System.out.println(e);
        }

        System.out.println();
        System.out.println("Edge of MST:");
        for (EdmondEdge e : mst.edges()) {
            System.err.println(e);
        }

        System.out.printf("\nMax weight: %.2f\n", mst.weight());
        System.out.println("\n\n");
    }
}
