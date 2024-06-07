

import helper.Job;

import java.util.*;

public class WeightedIntervalScheduling {
    // list of jobs to be processed
    private List<Job> jobs;

    /**
     * @param jobs list of jobs
     */
    public WeightedIntervalScheduling(List<Job> jobs) {
        this.jobs = jobs;
    }

    /**
     * @param n index of the element to find
     * @return largest index i < j such: job i is compatible with j.
     */
    private int latestNonConflict(int n) { // O(logn)
        int low = 0;
        int high = n;
        /* using binary search to find the index of the 
         *largest preceding compatible element */
        while (low <= high) {
            int mid = (low + high) / 2;
            if (jobs.get(mid).end() <= jobs.get(n).start()) {
                if (jobs.get(mid + 1).end() <= jobs.get(n).start()) 
                    low = mid + 1;
                else return mid;
            }
            else high = mid - 1; 
        }
        return -1;
    }

    /**
     * Using dynamic programming to solve 
     * 
     * @return the maximum profit 
     */
    private double findMaxProfitDP() { // O(n * logn)
        int N = jobs.size();
        double[] maxProfit = new double[N];
        // base case
        maxProfit[0] = jobs.get(0).profit();
        // dynamic programming
        for (int n = 1; n < N ; n++) {
            int k = latestNonConflict(n); // p(n)
            double curResult = jobs.get(n).profit();
            if (k != -1)
                curResult += maxProfit[k];
            // result[n] = max(vn + OPT(p(n)), OPT(n - 1))
            maxProfit[n] = Math.max(curResult, maxProfit[n - 1]);
        }
        return maxProfit[N - 1];
    }

    /**
     * @return the maximum profit 
     */
    public double maxProfit() {
        // sort jobs by finish time
        Collections.sort(jobs);
        return findMaxProfitDP();
    }

    public static void main(String[] args) {
        
        List<Job> jobs = Arrays.asList( 
                new Job( 0, 6, 60 ),
                new Job( 1, 4, 30 ),
                new Job( 3, 5, 10 ),
                new Job( 5, 7, 30 ),
                new Job( 5, 9, 50 ),
                new Job( 7, 8, 10 )
        );

        System.out.println("\n\nTran Van Dung - 221230773\n");
        System.out.println("List of job:");
        for (Job job : jobs) {
            job.allInforOfJob();
        }
        
        WeightedIntervalScheduling WIS = new WeightedIntervalScheduling(jobs);
        System.out.printf("\nThe maximum profit is %.2f\n\n", WIS.maxProfit());
    }
}
