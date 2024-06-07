import java.io.*;
import java.util.Stack;

public class Knapsack {

    private int N; // size of items array
    private int W; // maximum capacity
    private int totalProfit = 0;
    private int realWeight = 0;
    private Item[] items;
    private boolean[] take; // marker array

    public Knapsack(String path) {
        // call init knapsack function 
        initKnapsack(path);
    }

    /**
     * Read resource data from file for object properties
     * 
     * @param path file path to read
     */
    private void initKnapsack(String path) {
        // call read file function
        readDataFromFile(path);
        // call solve function
        solve();
    }

    /**
     * @return total profit of problem
     */
    public int totalProfit() {
        return totalProfit;
    }

    /**
     * @return real weight of problem
     */
    public int realWeight() {
        return realWeight;
    }

    /**
     * @return returns the selected item iterable
     */
    public Iterable<Item> items() {
        Stack<Item> st = new Stack<Item>();
        for (int n = 1; n <= N; n++)
            if (take[n])
                st.push(items[n]);
        return st;
    }

    /**
     * Printf all selected item
     */
    public void selectedItem() {
        System.out.printf("%-5s |%-7s |%-5s \n", "Index", "Weight", "Profit");
        for (int n = 1; n <= N; n++) {
            if (take[n] == false)
                continue;
            System.out.printf("%-5d |%-7d |%-5d \n", n, items[n].weight(), items[n].profit());
        }
    }

    /**
     * Read resource data from file for object properties
     * 
     * @param path file path to read
     */
    private void readDataFromFile(String path) {
        System.out.println("\n\nTran Van Dung - 221230773\n");
        System.out.println("List of items:");
        System.out.printf("%-5s |%-7s |%-5s \n", "Index", "Weight", "Profit");
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            N = Integer.parseInt(br.readLine()); // size of items array
            W = Integer.parseInt(br.readLine()); // maximum capacity

            items = new Item[N + 1];
            take = new boolean[N + 1];

            for (int n = 1; n <= N; n++) {
                String[] tokens = br.readLine().split(" ");
                System.out.printf("%-5s |%-7s |%-5s \n", Integer.toString(n),tokens[0], tokens[1]);

                items[n] = new Item(tokens);
            }

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculate the results of the problem total profit, real weight,
     * items have been selected
     */
    private void solve() { // O(N * W)

        int[][] opt = new int[N + 1][W + 1];
        boolean[][] sol = new boolean[N + 1][W + 1];

        for (int n = 1; n <= N; n++) {
            for (int w = 1; w <= W; w++) {
                // not slected item n
                int option1 = opt[n - 1][w];
                // chosen item n
                int option2 = Integer.MIN_VALUE;
                if (items[n].weight() <= w)
                    option2 = opt[n - 1][w - items[n].weight()] + items[n].profit();

                opt[n][w] = Math.max(option1, option2);
                sol[n][w] = option2 > option1;
            }
        }

        totalProfit = opt[N][W];

        for (int n = N, w = W; n > 0; n--) {
            if (sol[n][w]) {
                // marker selected item
                take[n] = true;
                w = w - items[n].weight();
                realWeight += items[n].weight();
            } else {
                take[n] = false;
            }
        }
    }

    public static void main(String[] args) {

        Knapsack knapSack = new Knapsack("knap.txt");

        System.out.println("\nSelected items:");
        knapSack.selectedItem();

        System.out.println("\ntotal profit: " + knapSack.totalProfit());
        System.out.println("real weight: " + knapSack.realWeight());

        System.out.println("\n");
    }
}
