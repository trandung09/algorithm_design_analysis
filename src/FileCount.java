import java.util.ArrayList;
import java.util.List;

public class FileCount implements Comparable<FileCount> {
    
    private String           fileName;
    private List<WordCount> worldCounts;

    public FileCount(String fileName) {
        this.fileName    = fileName;
        this.worldCounts = new ArrayList<WordCount>();
    }

    /**
     * Returns numbers of all world
     * @return numbers of all world
     */
    public int numbersOfAllWord() {
        return worldCounts.stream()
                        .mapToInt(WordCount::count).sum();
    }
    
    /**
     * Returns file path of the file
     * 
     * @return file path of the file
     */
    public String fileName() {
        return fileName;
    }

    /**
     * Returns list of world counts
     * 
     * @return list of world counts
     */
    public List<WordCount> worldCounts() {
        return worldCounts;
    }

    /**
     * Add an world count into worldCounts
     */
    public void addWord(String word, int count) {
        worldCounts.add(new WordCount(word, count));
    }

    @Override
    public int compareTo(FileCount o) {
        if (numbersOfAllWord() < o.numbersOfAllWord()) return 1;
        else {
            if (numbersOfAllWord() > o.numbersOfAllWord()) return -1;
            else return fileName.compareToIgnoreCase(o.fileName());
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("|%-17s", fileName));
        for (WordCount wc : worldCounts) {
            builder.append(String.format("|%-7d", wc.count()));
        }
        builder.append("|");

        return builder.toString();
    }
}
