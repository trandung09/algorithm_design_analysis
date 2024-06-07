import java.util.ArrayList;
import java.util.List;

public class NQueens {
    // number of rows and columns
    private final int N;
    // list of results
    private List<int[]> results; 

    /**
     * Initialization function with N:number of rows and columns
     * 
     * @param N number of rows and columns
     */
    public NQueens(int N) {
        this.N = N;
        this.results = new ArrayList<int[]>();
        // call solve function
        solveNQ();
    }

    /**
     * Problem solving method
     */
    public void solveNQ() {
        int[][] board = new int[N][N];
        // call solveNQRecusive function 
        if (solveNQRecusive(board, 0) == false) {
            System.err.println("Solution does not exist");
        }
    }

    /**
     * @param board two-dimensional array
     * @param col the curent column needs to checked
     * @return {@code true} if all queens are placed 
     */
    private boolean solveNQRecusive(int[][] board, int col) {
        // base case: if all queens are placed then return true 
        if (col == N) {
            results.add(result(board));
            return true;
        }
        boolean res = false;
        for (int i = 0; i < N; i++) {
            // Check if queen can be placed on board[i][col]
            if (isSafe(board, i, col)) {
                // marked board[i][col] if queen can be placed on this
                board[i][col] = 1;
                // callback solveNQRecusive with parameter: col + 1
                if (solveNQRecusive(board, col + 1) == true)
                    res = true;
                // to call backtrack
                board[i][col] = 0; 
            }
        }
        return res;
    }

    /**
     * @param board two-dimensional array
     * @param row-col current row of queen, current column of queen
     * @return {@code true} if queen can be placed on board[row][col]
     *         {@code false} otherwise
     */
    private boolean isSafe(int[][] board, int row, int col) {
        // Check this row on left side
        for (int i = 0; i < col; i++)   
            if (board[row][i] == 1) 
                return false;
        // Check upper diagonal on left side 
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 1) 
                return false;
        // Check lower diagonal on left side 
        for (int i = row, j = col; i < N && j >= 0; i++, j--)  
            if (board[i][j] == 1) 
                return false; 

        return true;
    }

    /**
     * @return number of results of the problem
     */
    public int counts() {
        return results.size();
    }

    public void resultN(int n) {
        if (n < 0 || n > results.size()) {
            System.out.println(
                "n must be > 0 and  <= " + results.size());
            return;
        }
        printResult(results.get(n -1));
    }

    /**
     * Print out all the results of the problem
     */
    public void allResults() {
        if (results.size() == 0) {
            System.out.println(
                "The problem with input " + N + " has no results");
            return;
        }
        for (int i = 0; i < results.size(); i++) {
            System.out.print((i + 1) + ": ");
            // call printResults function
            printResult(results.get(i));
        }
    }

    /**
     * @param board two-dimensional array
     * @return 
     */
    private int[] result(int[][] board) {
        int[] res = new int[N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (board[i][j] == 1) res[j] = i;
        return res;
    }

    /**
     * Printf a result of the problem
     * 
     * @param res a result of the problem
     */
    private void printResult(int[] res) {
        System.out.print("[");
        for (int i = 0; i < res.length; i++) {
            if (i < res.length - 1)
                System.out.print(res[i] + " - ");
            else System.out.print(res[i]);
        }
        System.out.println("]");
    }

    public static void main(String[] args) {

        System.out.println("\nTran Van Dung - 221230773\n");

        System.out.println("N = 5");
        System.out.println("All results with N = 5");
        NQueens queens = new NQueens(5);
        queens.allResults();

        System.out.println("\n");
    }
}
