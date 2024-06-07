import java.util.Set;
import java.util.List;
import java.util.TreeSet;

public class Vertex implements Comparable<Vertex> {

    private String name; // name of vertex
    private Set<String> child = new TreeSet<String>();

    public Vertex(String name) {
        this.name = name;
        this.child.add(this.toString());
    }

    public Vertex(List<Vertex> vertexs) {
        name = "";
        for (Vertex v : vertexs) {
            name += v + " ";
        }

        name = name.trim().replace(' ', ',');
        for (String s : name.split("\\,")) {
            child.add(s);
        }
    }

    /**
     * Check if the vertex is already in the list of child vertices
     * @return true if vertex is cantains, false in otherwise
     */
    public boolean contains(String v) {
        for (String s : v.split("\\,")) {
            if (!child.contains(s)) return false;
        }
        return true;
    }

    /**
     * Check if the vertex is already in the list of child vertices
     * @return true if vertex is cantains, false in otherwise
     */
    public boolean contains(Vertex v) {
        for (String s : v.child) {
            if (!child.contains(s)) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(Vertex o) {
        return this.name.compareTo(o.name);    }

    @Override
    public boolean equals(Object o) {
        return this.compareTo((Vertex) o) == 0;
    }
}
