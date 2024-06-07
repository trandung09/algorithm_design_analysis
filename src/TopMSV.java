

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class TopMSV {

    private static PriorityQueue<Student> pq;
    
    public static void topMStudenByAge(List<Student> list, int length) {
        pq = new PriorityQueue<Student>(length, Student.orderByAge());

        for (Student st : list) {
            pq.add(st);
            if (pq.size() > length) 
                pq.poll();
        }

        ArrayList<Student> tmp = new ArrayList<>();
        while (!pq.isEmpty()) {
            tmp.add(pq.poll());
        }
        for (Student st : tmp.reversed()) System.out.println(st);
    }  


    public static void topMStudenByName(List<Student> list, int length) {
        pq = new PriorityQueue<Student>(length, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                if (o1.firstname().compareToIgnoreCase(o2.firstname()) == 0) {
                    return -o1.lastname().compareToIgnoreCase(o2.lastname());
                }
                return -o1.firstname().compareToIgnoreCase(o2.firstname());
            }
        });

        for (Student st : list) {
            pq.add(st);
            if (pq.size() > length) 
                pq.poll();
        }

        ArrayList<Student> tmp = new ArrayList<>();
        while (!pq.isEmpty()) {
            tmp.add(pq.poll());
        }
        for (Student st : tmp.reversed()) System.out.println(st);
    }  

    public static void topMStudenByGpa(List<Student> list, int length) {
        pq = new PriorityQueue<Student>(length, Student.orderByGpa());

        for (Student st : list) {
            pq.add(st);
            if (pq.size() > length) 
                pq.poll();
        }

        ArrayList<Student> tmp = new ArrayList<>();
        while (!pq.isEmpty()) {
            tmp.add(pq.poll());
        }
        for (Student st : tmp.reversed()) System.out.println(st);
    }  
    
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new FileReader("classlist.csv"));
        ClassList cl = new ClassList(br);

        List<Student> list = cl.listStudent();

        System.out.println("\n\nTran Van Dung - 221230773\n");

        System.out.println("Top 3 student order by fullname:");
        TopMSV.topMStudenByName(list, 3);
        System.out.println();

        System.out.println("Top 3 student order by age:");
        TopMSV.topMStudenByAge(list, 3);
        System.out.println();

        System.out.println("Top 3 student order by gpa:");
        TopMSV.topMStudenByGpa(list, 3);
        System.out.println("\n");
    }
}
