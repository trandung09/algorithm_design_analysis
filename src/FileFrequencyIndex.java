import java.io.*;
import java.util.*;

public class FileFrequencyIndex {
    // marker mapping key and value
    private TreeMap<String, TreeMap<String, Integer>> frequency;

    public FileFrequencyIndex() {
        frequency = new TreeMap<>();
    }

    public TreeMap<String, TreeMap<String, Integer>> map() {
        return frequency;
    }

    /**
     * Read data from file and update frequency 
     * 
     * @param path path of the file to read
     */
    public void readFile(String path) { // n log(n) -> n la so tu
        // a temporary treemap to save words and word counts
        TreeMap<String, Integer> temp = new TreeMap<>();
        // read data from file with given file path
        try {
            Scanner sc = new Scanner(new File(path));
            while (sc.hasNextLine()) {
                String[] s = sc.nextLine().split(" ");
                for (String e : s) {
                    // temp doesn't have an s key yet
                    if (temp.containsKey(e) == false)
                        temp.put(e, 1);
                    else temp.put(e, temp.get(e) + 1);
                }
            }
            sc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Update frequency from treemap temp after reading the file
        for (String s : temp.keySet()) {
            // frequency doesn't have an s key yet
            if (frequency.containsKey(s) == false) {
                TreeMap<String, Integer> r = new TreeMap<>();
                r.put(path, temp.get(s));
                frequency.put(s, r);

            } else frequency.get(s).put(path, temp.get(s));
        }
    }
    
    /**
     * Query the file containing the word X and the number 
     * of occurrences of X in the file
     * 
     * @param word words to be queried
     */
    public void query(String word) {
        // if there is no word "word" in frequency
        if (frequency.containsKey(word) == false) {
            throw new
                NoSuchElementException("Could not find the query word");
        }
        TreeMap<String, Integer> temp = frequency.get(word);
        System.out.printf("%-15s| %-8s|\n", "Filename", "Count");
        for (String key : temp.keySet())
            System.out.printf("%-15s| %-8s|\n", key, temp.get(key));
    }

    public static void main(String[] args) {
        
        FileFrequencyIndex ffI = new FileFrequencyIndex();

        ffI.readFile("resume.txt");
        ffI.readFile("summer.txt");
        ffI.readFile("vacation.txt");

        System.out.println("\nTran Van Dung - 221230773\n");

        System.out.println("List of files that have been read:");
        System.out.println("  resume.txt");
        System.out.println("  summer.txt");
        System.out.println("  vacation.txt\n");

        System.out.println("Query word: was");

        ffI.query("was");

        System.out.println("\n");
    }
}
