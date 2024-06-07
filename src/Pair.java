public class Pair<K extends Comparable<K>, V extends Comparable<V>>
        implements Comparable<Pair<K, V>> {
            
    private K key; // key of pair
    private V value; // value of pair

    /**
     * Initialization function with 2 parameters key and value
     */
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * @return key of pair
     */
    public K key() {
        return key;
    }

    /**
     * @return value of pair
     */
    public V value() {
        return value;
    }

    public String toString() {
        return key + " " + value;
    }

    /**
     * Override from Compareble interface to compare two pair
     * 
     * @param o a other pair to compare with this pair
     * @return {@code 1}, {@code 0}, {@code -1} if this pair >, =, < other pair
     */
    @Override
    public int compareTo(Pair<K, V> o) {
        if (this.value.compareTo(o.value) > 0)
            return 1;
        else if (this.value.compareTo(o.value) < 0)
            return -1;
        else {
            if (this.key.compareTo(o.key) < 0)
                return 1;
            else if (this.key.compareTo(o.key) > 0)
                return -1;
        }
        return 0;
    }
}