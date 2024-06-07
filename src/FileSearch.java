import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

public class FileSearch {

    public FileFrequencyIndex frequency;

    // List of results after query
    private List<FileCount> fileCounts;

    // List of words used for querying
    private List<String> querys;

    // List of selected files (file name)
    private List<String> marked; 

    public FileSearch() {
        frequency = new FileFrequencyIndex();
        fileCounts = new ArrayList<FileCount>();
        querys = new ArrayList<String>();
        marked = new ArrayList<String>();
    }

    /**
     * Read data from file and save to FileFrequencyIndex
     * @param filePath path of the file to read
     */
    public void readFile(String filePath) {
        // call readFile function
        frequency.readFile(filePath);
    }

    /**
     * The method executes the query and prints the results
     * @param words array of words to query
     */
    public void query(String[] words) {
        querys.clear();
        marked.clear();
        fileCounts.clear();

        querys = new ArrayList<String>(Arrays.asList(words));

        String word = words[0];
        // Execute the first query when the result list is empty
        queryIfResultIsEmpty(word);

        // Query the next words
        for (int i = 1; i < words.length; i++) {
            word = words[i];
            queryIfResultIsNotEmpty(word);
        }
        
        // Print out all results after query
        printResultOfQuery();
    }

    /**
     * The query method adds another word if desired
     * @param word words want to query
     */
    public void queryMore(String word) {
        // Check list results
        if (fileCounts.isEmpty()) 
            queryIfResultIsEmpty(word);
        else 
            queryIfResultIsNotEmpty(word);

        querys.add(word);

        // Print out all results after query
        printResultOfQuery();
    }

    /**
     * Method used to query a word in case the query result list is empty
     * @param word word need to be queried
     */
    private void queryIfResultIsEmpty(String word) {
        checkNullString(word);
        // Check if word is not in frequency
        if (!frequency.map().containsKey(word)) {
            printResultOfQuery();
            return;
        }

        // List of fileNames containing word
        TreeMap<String, Integer> values;
        values = frequency.map().get(word);

        for (String fileName : values.keySet()) {
            // Create new FileCount(String fileName)
            FileCount fileCount = new FileCount(fileName);
            fileCount.addWord(word, values.get(fileName));

            // Add fileName into marked list
            marked.add(fileName);
            // Add FileCount into fileCounts (list of results)
            fileCounts.add(fileCount);
        }
    }

    /**
     * Method used to query a word in case the query result list is empty
     * @param word word need to be queried
     */
    private void queryIfResultIsNotEmpty(String word) {
        // Some temporary variables
        List<FileCount> fileCountTemp; 
        List<String> markedTemp;
        TreeMap<String, Integer> values;

        checkNullString(word);
        // Check if word is not in frequency
        if (!frequency.map().containsKey(word)) {
            fileCounts.clear();
            printResultOfQuery();
            return;
        }

        markedTemp = new ArrayList<String>();
        fileCountTemp = new ArrayList<FileCount>();

        // // List of fileNames containing word
        values = frequency.map().get(word);
        for (String fileName : values.keySet()) {
            if (!marked.contains(fileName)) {
                continue;
            }

            // Get the index of fileName in the marked array
            int idx = marked.indexOf(fileName);

            // Adds the queried word and the number of occurrences 
            // to FileCount with the corresponding file name
            fileCounts.get(idx).addWord(word, values.get(fileName));

            // Add selected elements to the temporary-list
            markedTemp.add(fileName);
            fileCountTemp.add(fileCounts.get(idx));
        }

        // Assign references for 2 result list to 2 temporary list
        marked = markedTemp;
        fileCounts = fileCountTemp;
    }

    /**
     * The method prints out all the results after querying
     */
    private void printResultOfQuery() {
        if (fileCounts.size() == 0) {
            System.out.println("\nNo single file contains all the given words!\n");
            return;
        }

        String str = String.format("|%-17s", "File name");
        for (String s : querys) {
            str += String.format("|%-7s", s);
        }
        str += "|\n";
        str += "-".repeat(19 + querys.size() * 8);

        List<FileCount> temp = new ArrayList<>(fileCounts);
        Collections.sort(temp);
        System.out.println(str);
        for (FileCount wc : temp)
            System.out.println(wc);
        System.out.println("-".repeat(19 + querys.size() * 8));
    }

    /**
     * Checks whether a word is null or not
     * @param word word need to be checked
     * @throws NullPointerException ("Query must be not null!")
     */
    private void checkNullString(String word) {
        if (word == null) throw new 
        NullPointerException("Query must be not null!");
    }

    public static void main(String[] args) {
        
        FileSearch fs = new FileSearch();
        fs.readFile("resume.txt");
        fs.readFile("summer.txt");
        fs.readFile("vacation.txt");

        System.out.println("\nTran Van Dung - 221230773\n");

        System.out.println("List of files that have been read:");
        System.out.println("  resume.txt");
        System.out.println("  summer.txt");
        System.out.println("  vacation.txt\n");

        System.out.println("Query word list: was felt");

        String s = "was felt";
        fs.query(s.split(" "));

        System.out.println("\nQuery more: magic");
        s = "magic";
        fs.queryMore(s);

        System.out.println("\n\n");
    }
}
