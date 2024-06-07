import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Student implements Comparable<Student> {

    private String Id;       // id of student
    private String firstname;     // name of student
    private String lastname;
    private String address;
    private Double gpa;
    private VDate birth; // student's date of birth
    private TreeMap<Subject, Double> transcript = new TreeMap<>();

    public Student() {
    }

    /**
     * Constructor with all of the object's properties
     */

    public Student(String id, String firstname, String lastname,  
    double averageScore, VDate birth, String address) {
        Id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.gpa = averageScore;
        this.birth = birth;
    }

    public Student(String id, String firstname, String lastname,  
    double averageScore, String birth, String address) {
        Id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.gpa = averageScore;
        this.birth = new VDate("/", birth);
    }

    public Student(String[] tokens) {
        this(tokens[0], tokens[1], tokens[2], Double.parseDouble
        (tokens[3]), tokens[4], tokens[5]);
    }

    public Student(boolean In, String[] tokens) {
        this.Id = tokens[0];
        this.firstname = tokens[1];
        this.lastname = tokens[2];
    }

    /**
     * Add subjects and score to student transcript
     * 
     * @param sub additional subject of student needed
     * @param score student's score of student
     */
    public void addSubject(Subject sub, double score) {
        // check null subject and score
        if (sub == null)
            throw new NullPointerException("Subject is null!");
        if (score < 0 || score > 10)
            throw new IllegalArgumentException("Score must be between 0 and 10!");

        transcript.put(sub, score);
    }

    
    /**
     * Add subjects and score to student transcript
     * 
     * @param sub additional subject of student needed
     * @param score student's score of student
     */
    public void addSubject(String subject) {
        StringTokenizer st = new StringTokenizer(subject, ",");
        String name = st.nextToken();
        int credits = Integer.parseInt(st.nextToken());
        int semeters = Integer.parseInt(st.nextToken());
        double score = Double.parseDouble(st.nextToken());

        transcript.put(new Subject(name, credits, semeters), score);
    }

    /**
     * Returns the average score from the student transcript
     * 
     * @return the average score from the student transcript
     */
    public double averageScore() {
        double totalScore = 0;
        int totalCredits = 0;

        for (Subject sub : transcript.keySet()) {
            totalScore += transcript.get(sub) * sub.credits();
            totalCredits += sub.credits();
        }

        gpa = totalScore / totalCredits;

        return gpa;
    }

    /**
     * Set Gpa for student
     * @param gpa
     */
    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    /**
     * Returns gpa score of student;
     * @return
     */
    public double gpa() {
        return gpa;
    }

    /**
     * Returns the average score from the student transcript
     * 
     * @param semeters the semester must calculate the average score
     */
    public double averageScoreOfTerm(int semeters) {
        double totalScore = 0;
        int totalCredits = 0;
        // check semeter
        if (semeters < 0)
            throw new IllegalArgumentException("Semeter must be > 0");

        for (Subject sub : transcript.keySet()) {
            if (sub.semester() == semeters) {
                totalScore += transcript.get(sub) * sub.credits();
                totalCredits += sub.credits();
            }
        }
        return totalScore / totalCredits;
    }

    /**
     * Print out all infomation of student
     */
    public void printInfOfStudent() {
        System.out.println("Student ID: " + Id + "\nFullname: " + lastname
                + " " + firstname + "\nDate of birth: " + birth);
        System.out.println();
    }

    /**
     * @return transcript of student (an tree map)
     */
    public TreeMap<Subject, Double> transcript() {
        return transcript;
    }

    /**
     * @return id of student
     */
    public String Id() {
        return Id;
    }

    /**
     * @return name of student
     */
    public String firstname() { 
        return firstname; 
    }
    
    /**
     * @return surname of student
     */
    public String lastname() { 
        return lastname; 
    }

    /**
     * @return address of student
     */
    public String address() { 
        return address; 
    }

    public String fullname() {
        return lastname + " " + firstname;
    }

    public VDate birth() {
        return birth;
    }

    @Override
    public int compareTo(Student o) {
        // order by fullname
        if (this.firstname.compareToIgnoreCase(o.firstname) == 0) {
            return this.lastname.compareToIgnoreCase(o.lastname);
        } else 
        return this.firstname.compareToIgnoreCase(o.firstname);
    }

    @Override
    public String toString() {
        return String.format("|%-10s |%-18s |%-5.2f |%-12s |%-12s |", 
        Id, fullname(), gpa, birth, address);
    } 

    /**
     * Returns an object compared by fullname of student
     * 
     * @return an object compared by fullname of student
     */
    public static Comparator<Student> orderByFullname() {
        // using lambda expression
        return (o1, o2) -> {
            if (o1.firstname.compareToIgnoreCase(o2.firstname) == 0) {
                return o1.lastname.compareToIgnoreCase(o2.lastname);
            } else 
            return o1.firstname.compareToIgnoreCase(o2.firstname);
        };
    }

    /**
     * Returns an object compared by average score of student
     * 
     * @return an object compared by average score of student
     */
    public static Comparator<Student> orderByGpa() {
        return (o1, o2) -> {
            return Double.compare(o1.gpa(), o2.gpa());
        };
    }

    /**
     * Returns an object compared by age of student
     * 
     * @return an object compared by age of student
     */
    public static Comparator<Student> orderByAge() {
        return (o1, o2) -> {
            return o1.birth.compareTo(o2.birth);
        };
    }

    public static void main(String[] args) {
        
    }
}
