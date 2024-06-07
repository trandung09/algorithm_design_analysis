import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class HashMapStudent {

    private HashMap<StudentHM, String> hm;

    public HashMapStudent() {
        hm = new HashMap<StudentHM, String>();
    }

    /**
     * /**
     * Create class list by reading data from file
     * @param br Object used to read files
     * @throws IOException
     */
    public HashMapStudent(BufferedReader br) 
        throws IOException {
        this();
        ArrayList<StudentHM> tmp = new ArrayList<>();
        while (true) {
            String line = br.readLine();
            if (line == null) break;
            String[] tokens = line.split(",");
            tmp.add(new StudentHM(tokens));
        }
        // System.out.println("Student list without ID:");
        Collections.sort(tmp, StudentHM.comparator());
        // StringBuilder builder = new StringBuilder();
        // String s = String.format("|%-10s |%-18s |%-12s |%-12s |\n", 
        // "ID", "Fullname", "Birth", "Address");
        // String line = "-".repeat(61) + "\n"; 
        // builder.append(s + line);

        // for (StudentHM st : tmp) {
        //     builder.append(st);
        // }
        // builder.append(line);

        // System.out.println(builder);
        int index = 0;
        
        for (StudentHM st : tmp) {
            ++index;
            String stID = createStudentId(index);
            addStudent(st, stID);
        }
        br.close();
    }  

    /**
     * Function to add students to hashmap
     * @param st
     * @param stID
     */
    private void addStudent(StudentHM st, String stID) {
        if (st == null)
        throw new 
        IllegalArgumentException("Student is null");

        st.setId(stID);
        hm.put(st, stID);
    }

    /**
     * Initialize student ID according to student index
     * @param index
     * @return Student id
     */
    private String createStudentId(int index) {
        String defaultID = "2212";
        String tmpStID = "0".repeat(4 - Integer.toString(index).length());
        return defaultID + tmpStID + Integer.toString(index);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        String s = String.format("|%-10s |%-18s |%-12s |%-12s |\n", 
        "ID", "Fullname", "Birth", "Address");
        String line = "-".repeat(61) + "\n"; 
        builder.append(s + line);
        ArrayList<StudentHM> tmp = new ArrayList<>();
        for (StudentHM st : hm.keySet()) {
            tmp.add(st);
        }
        Collections.sort(tmp);
        for (StudentHM st : tmp) {
            builder.append(st);
        }
        builder.append(line);
        return builder.toString();
    }

    public static void main(String[] args) throws IOException {

        System.out.println("\n\nTran Van Dung - 221230773\n");
        
        FileReader file = new FileReader("studenthm.txt");
        BufferedReader br = new BufferedReader(file);
        HashMapStudent hst = new HashMapStudent(br);

        System.out.println("List of students after creating ID:");
        System.out.println(hst.toString());
    }
}