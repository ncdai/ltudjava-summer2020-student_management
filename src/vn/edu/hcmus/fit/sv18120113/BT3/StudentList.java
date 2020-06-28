package vn.edu.hcmus.fit.sv18120113.BT3;

import java.io.*;
import java.util.*;

/**
 * vn.edu.hcmus.fit.sv18120113.BT3
 *
 * @created by ncdai3651408 - StudentID : 18120113
 * @Date 5/16/20 - 09:56
 * @Description
 */
public class StudentList {
    List<Student> studentList = new ArrayList<>();

//    StudentList () {
//        Student student1 = new Student("18120113", "Nguyen Chanh Dai", 10, "18120113.jpg", "Giuong 1 - P1009 - KTX Khu B - DHQG HCM", "Giai 3 - KHKT QG");
//        Student student2 = new Student("18120138", "Vo Duc Minh", 9.9, "18120138.jpg", "Giuong 2 - P1009 - KTX Khu B - DHQG HCM", "Giai 3 - KHKT QG");
//        Student student3 = new Student("18120141", "Tran Bao Nguyen", 9, "18120141.jpg", "Giuong 3 - P1009 - KTX Khu B - DHQG HCM", "Giai 3 - HSG QG");
//        Student student4 = new Student("18120000", "Vo Nguyen Truong Di", 8, "18120000.jpg", "Giuong 4 - P1009 - KTX Khu B - DHQG HCM", "");
//
//        this.add(student1);
//        this.add(student2);
//        this.add(student3);
//        this.add(student4);
//    }

    private int findIndex (String studentId) {
        for (int i = 0; i < this.studentList.size(); ++i) {
            if (this.studentList.get(i).getId().equals(studentId)) {
                return i;
            }
        }
        return -1;
    }

    public boolean idAlreadyExists(String studentId) {
        int index = this.findIndex(studentId);
        return index != -1;
    }

    public Student get (String studentId) {
        int index = findIndex(studentId);
        if (index == -1) return null;

        return this.studentList.get(index);
    }

    public List<Student> getList () {
        return this.studentList;
    }

    public void setList (List<Student> studentList) {
        this.studentList = studentList;
    }

    public boolean add (Student student) {
        int index = findIndex(student.getId());
        if (index != -1) {
            return false;
        }

        this.studentList.add(student);
        return true;
    }

    public boolean update (String studentId, String fullName, double score, String image, String address, String note) {
        int index = findIndex(studentId);
        if (index == -1) return false;

        this.studentList.get(index).update(fullName, score, image, address, note);
        return true;
    }

    public boolean remove (String studentId) {
        int index = findIndex(studentId);
        if (index == -1) return false;

        this.studentList.remove(index);
        return true;
    }

    public void printHeader () {
        System.out.println("[STUDENT LIST]");
        System.out.printf("%-16s", "ID");
        System.out.printf("%-32s", "Full Name");
        System.out.printf("%-16s", "Score");
        System.out.printf("%-32s", "Image");
        System.out.printf("%-64s", "Address");
        System.out.printf("%s", "Note");
        System.out.println();
    }

    public void print () {
        this.printHeader();
        for (Student student : this.studentList) {
            student.print();
            System.out.println();
        }
    }

    private int compareScoreDesc (Student a, Student b) {
        return Double.compare(b.getScore(), a.getScore());
    }
    private int compareScoreAsc (Student a, Student b) {
        return Double.compare(a.getScore(), b.getScore());
    }

    private int compareIdDesc (Student a, Student b) {
        return Integer.compare(b.getId().compareTo(a.getId()), 0);
    }
    private int compareIdAsc (Student a, Student b) {
        return Integer.compare(a.getId().compareTo(b.getId()), 0);
    }

    public void sort (String field, String type) {
        if (field.equals("id")) {
            if (type.equals("DESC")) {
                this.studentList.sort(this::compareIdDesc);
            } else {
                this.studentList.sort(this::compareIdAsc);
            }
        }

        if (field.equals("score")) {
            if (type.equals("DESC")) {
                this.studentList.sort(this::compareScoreDesc);
            } else {
                this.studentList.sort(this::compareScoreAsc);
            }
        }
    }
}
