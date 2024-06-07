import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class ClassList {

    private TreeMap<String, Student> studentListID;
    private TreeMap<String, Set<Student>> studentListAddress;
    
    public ClassList() {
        studentListID = new TreeMap<String, Student>();
        studentListAddress = new TreeMap<String, Set<Student>>();
    }

    /**
     * Create class list by reading data from file
     * @param br Object used to read files
     * @throws IOException
     */
    public ClassList(BufferedReader br) throws IOException {
        this();
        while (true) {
            String line = br.readLine();
            if (line == null) break;
            String[] tokens = line.split(",");
            Student stT = new Student(tokens);
            addStudent(tokens[0], stT);
            setTranscriptOfStudent(tokens[0]);
        }
        br.close();
    }

    /**
     * function used to add a student to the class list
     * @param stID Id of student
     * @param st   student
     */
    public void addStudent(String stID, Student st) {
        // Check if student or student id is null
        if (st == null || stID == null) {
            throw new IllegalArgumentException("Value is null");
        }
        // Add student into list 
        studentListID.put(stID, st);
        if (!studentListAddress.containsKey(st.address())) {
            studentListAddress.put(st.address(), new TreeSet<>());
        }
        studentListAddress.get(st.address()).add(st);
    }

    public void setTranscriptOfStudent(String Id) {
        try {
            FileReader file = new FileReader(Id + ".txt");
            BufferedReader br = new BufferedReader(file);
            while (true) {
                String line = br.readLine();
                if (line == null) break;
                studentListID.get(Id).addSubject(line);
            }
            br.close();
        } catch(FileNotFoundException e) {
            return;
        } catch (IOException e) {
            e.printStackTrace();
        };
    }

    /**
     * Returns averagr score 
     * @return
     */
    public double averageScoreOfClass() {
        double res = 0;
        for (Student st : listStudent()) {
            res += st.gpa();
        }
        return res / listStudent().size();
    }

    /**
     * The function prints out the 10 students with the highest final score 
     * in the semester
     * @param semester semester requires check
     */
    public void printTopSemesterGpa(int semester) {
        String str = String.format("|%-10s |%-18s |%-5s |\n", 
        "ID", "Fullname", "Gpa");
        str += "-".repeat(40) + ""; 
        System.out.println("Top 10 GPA of the semester: " + semester + "\n" + str);
        for (Student st : semesterGpa(semester).reversed()) {
            str = String.format("|%-10s |%-18s |%-5.2f |", 
            st.Id(), st.fullname(), st.averageScoreOfTerm(semester));
            System.out.println(str);
        }
        System.out.println("-".repeat(40));
    }

    /**
     * Function used to print a list of classes sorted by name
     */
    public void printClassListOrderByName() {
        List<Student> list = listStudent();
        // Sort class list
        Collections.sort(list, Student.orderByFullname());

        System.out.println("List of students order by name:");
        // Call printAllInforOfStudent function
        printAllInforOfStudent(list);
    }

    /**
     * Function used to print a list of classes sorted by age
     */
    public void printClassListOrderByAge() {
        List<Student> list = listStudent();
         // Sort class list
        Collections.sort(list, Student.orderByAge());

        System.out.println("List of students order by age:");
        printAllInforOfStudent(list);
    }
    
    /**
     * Function used to print a list of classes sorted by address
     * Students from the same hometown are printed next to each other
     */
    public void printClassListOrderByAddres() {
        System.out.println("List of students order by address:");
        printAllInforOfStudent(listStudent());
    }

    /**
     * Print out a list of students with the same hometown according to 
     * the parameter passed
     * @param address Address need to check
     */
    public void listStudentBySameAddress(String address) {
        // Check if address is null
        if (address == null) 
            throw new IllegalArgumentException("Address is null");
        Set<Student> set = studentListAddress.get(address);
        if (set.size() == 0) {
            System.out.println("There are no students in " + address + "!");
            return;
        }
        System.out.println("List of students whose hometown in " + address);
        printAllInforOfStudent(new ArrayList<>(set));
    }

    /**
     * Print out a list of students with an average score greater than a 
     * certain score
     * @param limit lowest score
     */
    public void printClassListOrderByGpa(double limit) {
        List<Student> list = new ArrayList<>();
        for (Student st : listStudent()) {
            // Check gpa (> limit)
            if (st.gpa() >= limit) list.add(st);
        }
        Collections.sort(list.reversed(), Student.orderByGpa());

        System.out.println("List of students order by limit gpa:");
        printAllInforOfStudent(list);
    }

    /**
     * Returns list of student from treemap
     * @return list of student from treemap
     */
    public List<Student> listStudent() {
        List<Student> list = new ArrayList<Student>();
        for (String key : studentListAddress.keySet()) {
            for (Student st : studentListAddress.get(key)) {
                list.add(st);
            }
        }
        return list;
    }

    /**
     * Returns a list of top 10 students with the highest average semester scores
     * 
     * @param semester Semeter need to check
     * @return a list of top 10 students with the highest average semester scores
     */
    private List<Student> semesterGpa(int semester) {
        // Use a priority queue with a Comparator that compares with the 
        // average student score of the nth semester
        PriorityQueue<Student> pq = new PriorityQueue<>(new Comparator<Student>() {
            @Override
            public int compare(Student o1 ,Student o2) {
                return Double.compare
                (o1.averageScoreOfTerm(semester), o2.averageScoreOfTerm(semester));
            }
        });

        for (String stId : studentListID.keySet()) {
            pq.add(studentListID.get(stId));
            if (pq.size() > 10) { pq.poll(); }
        }

        List<Student> list = new LinkedList<>();
        while (!pq.isEmpty()) { list.add(pq.poll()); }

        return list;
    }

    /**
     * Print out all student information for all students in the list
     * @param list list of student
     */
    public void printAllInforOfStudent(List<Student> list) {
        String str = String.format("|%-10s |%-18s |%-5s |%-12s |%-12s |\n", 
        "ID", "Fullname", "Gpa", "Birth", "Address");
        str += "-".repeat(68);
        System.out.println(str);
        for (Student st : list) {
            System.out.println(st);
        }
        System.out.println("-".repeat(68));
    }

    public static void main(String[] args) throws IOException {

        FileReader file = new FileReader("classlist.csv");
        BufferedReader br = new BufferedReader(file);

        ClassList cl = new ClassList(br);

        System.out.println("\nTran Van Dung - 221230773\n");
        // cl.printTopSemesterGpa(1);
        cl.listStudentBySameAddress("Nghe An");

        System.out.println("\n\n");
    }
}
