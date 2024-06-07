public class Subject implements Comparable<Subject>{
    // name of subject
    private String name;
    // credits and semeter of subject 
    private int credits, semester;

    public Subject(String name, int credits, int semester) {
        this.name = name;
        this.credits = credits; 
        this.semester = semester;
    }

    /**
     * @return name of subject
     */
    public String name() {
        return name;
    }

    /**
     * @return credits of subject
     */
    public int credits() {
        return credits;
    }

    /**
     * @return semeter of subject
     */
    public int semester() {
        return semester;
    }

    /**
     * Printf infomation of subject
     */
    public void printInfoOfSubject() {
        System.out.printf("%-15s |%-8d |%-8d", 
        name, credits, semester);
    }

    /**
     * Compare two subject to sort
     */
    @Override
    public int compareTo(Subject o) {
        return this.name.compareToIgnoreCase(o.name());
    }
}
