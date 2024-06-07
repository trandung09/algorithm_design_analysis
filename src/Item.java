public class Item {
    
    private int weight;
    private int profit;

    public Item(String[] s) {
        // callback a contructor 
        this(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
    }

    /**
     * @param w weight of item
     * @param p profit of item
     */
    public Item(int w, int p) {
        weight = w;
        profit = p;
    }

    /**
     * @return value of weight in the item
     */
    public int weight() { 
        return weight; 
    }

    /**
     * @return value of profits in the item
     */
    public int profit() { 
        return profit; 
    }

    /**
     * @return a string of item
     */
    public String toString() {
        return "[" + weight + " : " + profit + "]";
    }
} 
