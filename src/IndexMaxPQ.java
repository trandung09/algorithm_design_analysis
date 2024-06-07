import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * pq[]: ghi nhớ chỉ số của phần tử có độ ưu tiên theo mức từ cao đến thấp
 * + pq[1] = index của phần tử trong keys[] có độ ưu tiên lớn nhất
 * + heapify_up và down làm việc với mảng này
 * 
 * qp[]: ngược lại với pq, mảng này ghi nhớ chỉ số trong pq của phần tử
 * + tổng quát: qp[pq[i]] = pq[qp[i]] = i
 */

public class IndexMaxPQ<Key extends Comparable<Key>> implements Iterable<Integer> {
    
    private int n;        // size of PQ
    private int[] pq;     // binary heap using 1-based indexing
    private int[] qp;
    private Key[] keys;   // 

    public IndexMaxPQ(int maxN) {

        if (maxN < 0) throw new IllegalArgumentException();
        n = 0;
        keys = (Key[]) new Comparable[maxN + 1]; 
        pq = new int[maxN + 1];
        qp = new int[maxN + 1];

        for (int i = 0; i <= maxN; i++)
            qp[i] = -1;
    }

    /**
     * @param i an index for checked
     * @return {@code true} if qp[i] != -1
     *         {@code false} otherwise
     */
    public boolean contains(int i) {
        return qp[i] != -1;
    }

    /**
     * @return {@code true} if priority queue is empty
     *         {@code false} otherwise    
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * @return size of priority queue (or the number of keys[])
     */
    public int size() {
        return n;
    }

    /**
     * @param i the index of element want to add 
     * @param key value of element want to add
     */
    public void insert(int i, Key key) { // O(logn)
        if (contains(i)) return;
        n++;
        qp[i] = n;
        pq[n] = i;
        keys[i] = key;
        swim(n);
    }

    /**
     * @return the index of keys[] has highest priority
     * @throws NoSuchElementException if this priority queue is empty
     */
    public int maxIndex() {
        if (n == 0) 
            throw new NoSuchElementException("Priority queue is empty");
        return pq[1];
    }

    /**
     * @return the key of keys[] has highest priority
     * @throws NoSuchElementException if this priority queue is empty
     */
    public Key maxKey() {
        if (n == 0) 
            throw new NoSuchElementException("Priority queue is empty");
        return keys[pq[1]];
    }

    /**
     * @param i the index of key want to search
     * @return key of keys[] has index = i
     * @throws NoSuchElementException if could not find key in keys[] with index i 
     */
    public Key keyOf(int i) {
        if (!contains(i))
            throw new NoSuchElementException("No such element in this priority queue");
        return keys[i];
    }

    /**
     * @param i the index of key has to change
     * @param key the new key with index i want to change in keys[]
     * @throws NoSuchElementException if the index i has not in keys[]
     */
    public void changeKey(int i, Key key) { // O(logn)
        if (!contains(i)) 
            throw new NoSuchElementException("No such element with index i");
        keys[i] = key;
        swim(qp[i]);
        sink(qp[i]);
    }

    /**
     * Removes a maximum key and returns its associated index.
     * 
     * @return associated index of maximum key in priority queue
     */
    public int delMax() { // O(logn)
        if (n == 0) return -1;
        int min = pq[1];
        exch(1, n--);
        sink(1);

        qp[min] = -1;        
        keys[min] = null;   
        pq[n+1] = -1;       
        return min;
    }

    /**
     * Remove key with index i in keys[]
     * 
     * @param i the index of key in keys[] want ro delete
     */ 
    public void delete(int i) { // O(logn)
        if (!contains(i)) return;
        int index = qp[i];
        exch(index, n--);
        swim(index);
        sink(index);
        keys[i] = null;
        qp[i] = -1;
    }

    /**
     * @param i an index in pq[] that needs to compare
     * @param j an index in pq[] that needs to compare
     * @return {@code true} if keys[pq[i]] < keys[pq[j]]
     *         {@code false} otherwise
     */
    public boolean less(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) < 0;
    }

    /**
     * @param i an index in pq[] that needs to swap
     * @param j an index in pq[] that needa to swap
     */
    public void exch(int i, int j) {
        int temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    /**
     * An helper function to updates index of elements in order from bottom to top
     * 
     * @param k start index to implement swim functions
     */
    public void swim(int k) { // O(logn)
        while (k > 1 && less(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
    }

    /**
     * An helper function to updates index of elements in order from top to bottom
     * 
     * @param k start index to implement swim functions
     */
    public void sink(int k) { // O(logn)
        /* par: j => left: j*2 and right: j*2 + 1 */
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && less(j, j+1)) j++;
            if (!less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    /**
     * @return an iterator that iterates over the keys in descending order
     */
    @Override
    public Iterator<Integer> iterator() {
        return new IndexMaxPQIterator();
    }

    /**
     * The private class implements the Iterator interface to create an iterator for this class 
     */
    private class IndexMaxPQIterator implements Iterator<Integer> {

        private IndexMaxPQ<Key> pqIterator; // new copy IndexMaxPQ

        public IndexMaxPQIterator() {
            pqIterator = new IndexMaxPQ<>(pq.length - 1); // pq[] = new int[maxN + 1]
            for (int i = 1; i <= n; i++)
                pqIterator.insert(pq[i], keys[pq[i]]);
        }

        @Override
        public boolean hasNext() {
            return !pqIterator.isEmpty();
        }

        @Override
        public Integer next() {
            if (!hasNext()) throw new NoSuchElementException();
            return pqIterator.delMax();
        }
    }

    public static void main(String[] args) {
        
        // String[] strings = {"D", "C", "B", "A"};
        String[] strings = { "it", "was", "the", "best", "of", "times", "it", "was", "the", "worst" };

        IndexMaxPQ<String> pq = new IndexMaxPQ<>(strings.length);

        System.out.println("\n\nTran Van Dung - 221230773\n");

        System.out.println("List of variables:");
        System.out.print("[");
        for (int i = 0; i < strings.length; i++) {
            pq.insert(i, strings[i]);
            System.out.print(strings[i]);
            if (i < strings.length - 1) 
                System.out.print(", ");
        }
        System.out.print("]");

        System.out.println("\n");

        System.out.println("Priority of words in the queue:");
        for (int i : pq) 
            System.out.println(i + " " + strings[i]);

        System.out.println("\n");
    }
}
