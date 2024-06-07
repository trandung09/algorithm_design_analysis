import java.util.Comparator;

public class StudentHM implements Comparable<StudentHM> {
    
    private String Id = null;
    private VDate  birth;
    private String firstname;
    private String lastname;
    private String address;

    public StudentHM(String firstname, String lastname, VDate birth, String address) {
        this.firstname = firstname;
        this.birth = birth;
        this.lastname = lastname;
        this.address = address;
    }

    public StudentHM(String firstname, String lastname, String birth, String address) {
        this(firstname, lastname, new VDate("/", birth), address);
    }

    public StudentHM(String[] tokens) {
        this(tokens[0], tokens[1], tokens[2], tokens[3]);
    }

    public String Id() {
        return Id;
    }

    public VDate birth() {
        return birth;
    }

    public String firstname() {
        return firstname;
    }

    public String lastname() {
        return lastname;
    }

    public String address() {
        return address;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String fullname() {
        return lastname + " " + firstname;
    }

    @Override
    public int compareTo(StudentHM o) {
        return this.Id.compareTo(o.Id());
    }

    public static Comparator<StudentHM> comparator() {
        return (o1, o2) -> {
        if (o1.firstname.compareToIgnoreCase(o2.firstname()) == 0) {
            if (o1.lastname.compareToIgnoreCase(o2.lastname()) == 0) {
                return o1.birth.compareTo(o2.birth());
            }
            return o1.lastname.compareToIgnoreCase(o2.lastname());
        }
        return o1.firstname.compareToIgnoreCase(o2.firstname());
        };
    }

    @Override
    public int hashCode() {
        int hash = 15;
        int fname = firstname.hashCode();
        int lname = lastname.hashCode();
        int aress = address.hashCode();
        int vdate = birth.hashCode();

        return hash * (hash * fname + lname + vdate) + aress;
    }

    @Override
    public String toString() {
        return String.format("|%-10s |%-18s |%-12s |%-12s |\n", 
        Id, fullname(), birth, address);
    }    
}
