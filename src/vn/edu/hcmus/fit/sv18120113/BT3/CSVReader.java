package vn.edu.hcmus.fit.sv18120113.BT3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * vn.edu.hcmus.fit.sv18120113.BT3
 *
 * @created by ncdai3651408 - StudentID : 18120113
 * @Date 5/16/20 - 19:24
 * @Description
 */
public class CSVReader {
    public static List<Student> readStudentListFromCSV (String fileName) {
        List<Student> studentList = new ArrayList<>();

        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);

            String line;
            int index = -1;

            while ((line = br.readLine()) != null) {
                ++index;
                if (index == 0) continue;

                String[] fields = line.split(",");

                Student student = createStudent(fields);
                studentList.add(student);
            }

            br.close();
            return studentList;

        } catch (IOException ioe) {
            return null;
        }
    }

    private static Student createStudent (String[] fields) {
        String id = fields.length >= 1 ? fields[0] : "";
        String fullName = fields.length >= 2 ? fields[1] : "";
        double score = fields.length >= 3 ? (fields[2].equals("") ? 0.0 : Double.parseDouble(fields[2])) : 0.0;
        String image = fields.length >= 4 ?  fields[3] : "";
        String address = fields.length >= 5 ? fields[4] : "";
        String note = fields.length >= 6 ? fields[5] : "";

        return new Student(
            id,
            fullName,
            score,
            image,
            address,
            note
        );
    }

    public static String saveStudentListToCSV (String fileName, List<Student> studentList) {
        try {
            FileWriter fw = new FileWriter(fileName);

            fw.append(String.join(",", Arrays.asList("id", "fullName", "score", "image", "address", "note")));
            fw.append("\n");

            for (Student student : studentList) {
                fw.append(String.join(",", student.toArrayList()));
                fw.append("\n");
            }

            fw.flush();
            fw.close();
            return getPathToCSV(fileName);

        } catch (IOException ioe) {
            return null;
        }
    }

    public static String getPathToCSV (String fileName) {
        return Paths.get(fileName).toAbsolutePath().normalize().toString();
    }
}
