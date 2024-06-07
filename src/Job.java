public class Job implements Comparable<Job>{

    // the time job begins 
    private int start;   
    // the time job ends   
    private int end;
    // the profit of job
    private double profit;

    /**
     * Constructor with 3 parameters
     */
    public Job(int start, int end, double profit) {
        this.start = start;
        this.end = end;
        this.profit = profit;
    }

    /**
     * Constructor with 2 parameters
     */
    public Job(int start, int end) {
        this.start = start;
        this.end = end;
    }

    /**
     * @return the time job begins
     */
    public int start() {
        return start;
    }

    /**
     * @return the time job ends
     */
    public int end() {
        return end;
    }

    /**
     * @return the profit of job
     */
    public double profit() {
        return profit;
    }

    /**
     * @return string of start and end time 
     */
    public String toString() {
        return "[start: " + start + ", end: " + end + "]";
    }

    public void allInforOfJob() {
        System.out.println("[start: " + start + ", end: " 
        + end + ", profit: " + profit + "]");
    }

    /**
     * Method of comparing the value of two jobs
     * 
     * @param o other job to compare
     * @return 
     */
    @Override
    public int compareTo(Job o) {
        return Integer.compare(this.end, o.end);
    }
}