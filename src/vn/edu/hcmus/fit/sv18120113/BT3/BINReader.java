package vn.edu.hcmus.fit.sv18120113.BT3;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * vn.edu.hcmus.fit.sv18120113.BT3
 *
 * @created by ncdai3651408 - StudentID : 18120113
 * @Date 5/16/20 - 20:27
 * @Description
 */
public class BINReader {
    public static List<Student> readStudentListFromBIN (String fileName) {
        List<Student> studentList = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream(fileName);
            DataInputStream dis = new DataInputStream(fis);

            int size = dis.readInt();

            for (int i = 0; i < size; ++i) {
                String id = dis.readUTF();
                String fullName = dis.readUTF();
                double score = dis.readDouble();
                String image = dis.readUTF();
                String address = dis.readUTF();
                String note = dis.readUTF();

                Student student = new Student(id, fullName, score, image, address, note);
                studentList.add(student);
            }

            return studentList;

        } catch (IOException ioe) {
            return null;
        }
    }

    public static String saveStudentListToBIN (String fileName, List<Student> studentList) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            DataOutputStream dos = new DataOutputStream(fos);

            dos.writeInt(studentList.size());

            for (Student student : studentList) {
                dos.writeUTF(student.getId());
                dos.writeUTF(student.getFullName());
                dos.writeDouble(student.getScore());
                dos.writeUTF(student.getImage());
                dos.writeUTF(student.getAddress());
                dos.writeUTF(student.getNote());
            }

            dos.close();
            return getPathToBIN(fileName);

        } catch (IOException ioe) {
            return null;
        }
    }

    public static String getPathToBIN (String fileName) {
        return Paths.get(fileName).toAbsolutePath().normalize().toString();
    }
}
