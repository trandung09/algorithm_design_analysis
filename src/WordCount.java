public class WordCount {
    
    private String word;
    private int    count;

    public WordCount(String word, int count) {
        this.word  = word;
        this.count = count;
    }

    /**
     * Returrn word of WorldCount
     * 
     * @return word of WorldCount
     */
    public String word() {
        return word;
    }

    /**
     * Returns count of WorldCount
     * 
     * @return count of WorldCount
     */
    public int count() {
        return count;
    }

    @Override
    public String toString() {
        return String.format("%.8s %d", word, count);
    }
}
