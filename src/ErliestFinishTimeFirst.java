import java.util.*;

public class ErliestFinishTimeFirst {
    List<Job> jobs;
    List<Job> selected = new ArrayList<>();

    public ErliestFinishTimeFirst(final List<Job> jobs) {
        this.jobs = jobs;
        // call function sovle
        solve();
    }

    /**
     * Sort jobs by finish time and solve by greedy algorithm
     */
    private void solve() {
        // sort jobs by finish time
        Collections.sort(jobs);

        // current time to check
        int currentTime = 0;

        for (Job job : jobs) {
            if (job.start() >= currentTime) {
                selected.add(job);
                currentTime = job.end();
            }
        }
    }

    /**
     * @return the maximum number of jobs
     */
    public int counts() {
        return selected.size();
    }

    /**
     * @return an iterable of selected jobs
     */
    public Iterable<Job> selected() {
        return selected;
    }

    public static void main(String[] args) {

        List<Job> jobs = Arrays.asList(
            new Job(0, 6),  
            new Job(1, 4),
            new Job(3, 5),
            new Job(3, 8),
            new Job(4, 7),
            new Job(5, 9),
            new Job(6, 10),
            new Job(8, 11)
        );

        System.out.println("\n\nTran Van Dung - 221230773\n");

        System.out.println("List of jobs:");
        for (Job job : jobs) {
            System.err.println("  " + job);
        }
        System.out.println();

        ErliestFinishTimeFirst EFTF = new ErliestFinishTimeFirst(jobs);
        
        System.out.println("The maximum number of jobs is " + EFTF.counts());
        for (Job job : EFTF.selected())
            System.out.println("  " + job);

        System.out.println("\n");
    }
}
