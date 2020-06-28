package vn.edu.hcmus.fit.sv18120113.BT3;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * vn.edu.hcmus.fit.sv18120113.BT3
 *
 * @created by ncdai3651408 - StudentID : 18120113
 * @Date 5/16/20 - 10:40
 * @Description
 */
public class App {
    String dataFileName = "studentList.bin";
    private StudentList studentList = new StudentList();

    private void syncToBIN () {
        String filePath = BINReader.saveStudentListToBIN(dataFileName, this.studentList.getList());
        if (filePath == null) {
            System.out.println();
            System.out.println("!!! Unable to sync data to " + BINReader.getPathToBIN(dataFileName));
        }
    }

    private void syncFromBIN () {
        List<Student> studentList = BINReader.readStudentListFromBIN(dataFileName);

        if (studentList == null) {
            System.out.println("!!! Unable to sync data from " + BINReader.getPathToBIN(dataFileName));
            System.out.println();
            return;
        }

        this.studentList.setList(studentList);
    }

    private void viewStudentList () {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

            System.out.println("[VIEW STUDENT LIST]");
            System.out.println();

            System.out.println("Sort student list by (1) ID or (2) Score");
            System.out.print("- Enter 1 or 2 : ");
            String sortField = br.readLine();

            System.out.println();

            System.out.println("Sort order (1) Ascending or (2) Descending");
            System.out.print("- Enter 1 or 2 : ");
            String sortType = br.readLine();

            String sortFieldStr = sortField.equals("1") ? "id" : "score";
            String sortTypeStr = sortType.equals("1") ? "ASC" : "DESC";

            System.out.println();

            StudentList sortList = this.studentList;
            sortList.sort(sortFieldStr, sortTypeStr);
            sortList.print();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void addStudent () {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

            System.out.println("[ADD STUDENT]");
            System.out.println();

            System.out.println("Enter new student info");

            String studentId = "";
            boolean idAlreadyExists = false;
            do {
                if (idAlreadyExists) {
                    System.out.println("!!! Student " + studentId + " already exists");
                }

                System.out.print("- Student ID : ");
                studentId = br.readLine();

                idAlreadyExists = this.studentList.idAlreadyExists(studentId);
            } while (studentId.equals("") || idAlreadyExists);

            String fullName = "";
            do {
                System.out.print("- Full Name : ");
                fullName = br.readLine();
            } while (fullName.equals(""));

            String scoreStr = "";
            do {
                System.out.print("- Score : ");
                scoreStr = br.readLine();
            } while (scoreStr.equals(""));

            double score = Double.parseDouble(scoreStr);

            String image = "";
            do {
                System.out.print("- Image : ");
                image = br.readLine();
            } while (image.equals(""));

            String address = "";
            do {
                System.out.print("- Address : ");
                address = br.readLine();
            } while (address.equals(""));

            System.out.print("- Note : ");
            String note = br.readLine();

            Student newStudent = new Student(
                studentId,
                fullName,
                score,
                image,
                address,
                note
            );

            System.out.println();

            boolean success = studentList.add(newStudent);
            if (success) {
                System.out.println("Student " + newStudent.getId() + " has been added!");
                System.out.println();

                this.studentList.print();
                syncToBIN();
                return;
            }

            System.out.println("!!! Student " + newStudent.getId() + " already exists");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void updateStudent () {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

            System.out.println("[UPDATE STUDENT]");
            System.out.println();

            System.out.print("Enter the ID of the student you want to update : ");
            String studentId = br.readLine();

            System.out.println();

            Student student = this.studentList.get(studentId);

            if (student == null) {
                System.out.print("!!! Student ID does not exist");
                return;
            }

            System.out.print("- Full Name (" + student.getFullName() + ") : ");
            String fullName = br.readLine();

            System.out.print("- Score (" + student.getScore() + ") : ");
            String scoreStr = br.readLine();
            double score = !scoreStr.equals("") ? Double.parseDouble(scoreStr) : -1.0;

            System.out.print("- Image (" + student.getImage() + ") : ");
            String image = br.readLine();

            System.out.print("- Address (" + student.getAddress() + ") : ");
            String address = br.readLine();

            System.out.print("- Note (" + student.getNote() + ") : ");
            String note = br.readLine();

            boolean success = this.studentList.update(
                studentId,
                fullName,
                score,
                image,
                address,
                note
            );

            if (success) {
                System.out.println();
                System.out.println("Student " + studentId + " has been updated!");
                System.out.println();

                this.studentList.print();
                syncToBIN();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void removeStudent () {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

            System.out.print("Enter the ID of the student you want to delete : ");
            String studentId = br.readLine();

            System.out.println();

            boolean success = studentList.remove(studentId);
            if (success) {
                System.out.println("Student " + studentId + " has been removed!");
                System.out.println();

                this.studentList.print();
                syncToBIN();
                return;
            }

            System.out.println("!!! Student ID does not exist");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void importCSV (String fileName) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

            System.out.println("Do you want to (1) replace or (2) append data to the list ?");
            System.out.print("- Enter 1 or 2 : ");
            String method = br.readLine();

            System.out.println();

            List<Student> studentList = CSVReader.readStudentListFromCSV(fileName);

            String filePath = CSVReader.getPathToCSV(fileName);

            if (studentList == null) {
                System.out.println("[IMPORT CSV > " + filePath + " > ERROR]");
                System.out.println("!!! Unable to read the file " + filePath);
                return;
            }

            if (studentList.size() == 0) {
                System.out.println("[IMPORT CSV > " + filePath + " > WARNING]");
                System.out.println("!!! The file " + filePath + " has no student data");
                return;
            }

            if (method.equals("1")) {
                // Replace
                this.studentList.setList(studentList);
                System.out.println("[IMPORT CSV > " + filePath + " > REPLACE]");

                this.studentList.print();
                this.syncToBIN();
            }

            if (method.equals("2")) {
                // Append
                System.out.println("[IMPORT CSV > " + filePath + " > APPEND]");
                for (Student student : studentList) {
                    boolean success = this.studentList.add(student);
                    if (success) {
                        System.out.println("Inserted " + student.getId() + "!");
                    } else {
                        System.out.println("!!! Student " + student.getId() + " already exists");
                    }
                }

                this.syncToBIN();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void exportCSV (String fileName) {
        String file = CSVReader.saveStudentListToCSV(fileName, this.studentList.getList());

        System.out.println("[EXPORT CSV]");
        if (file != null) {
            System.out.println("Exported to file " + file + "!");
        } else {
            System.out.println("!!! File export failed");
        }
    }

    private void importOrExportCSV () {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

            System.out.println("[IMPORT / EXPORT CSV]");
            System.out.println();

            System.out.println("Do you want to (1) import or (2) export CSV ?");
            System.out.print("- Enter 1 or 2 : ");
            String action = br.readLine();

            System.out.println();

            System.out.println("File to " + (action.equals("1") ? "import" : "export") + " ?");
            System.out.print(action.equals("1")
                ? "- Enter path to file (*.csv) : "
                : "- Enter file name (*.csv) : "
            );
            String fileName = br.readLine();

            System.out.println();

            if (action.equals("1")) {
                this.importCSV(fileName);
            }

            if (action.equals("2")) {
                this.exportCSV(fileName);
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void run () {
        try {
            syncFromBIN();

            while (true) {
                System.out.println("STUDENT MANAGEMENT APP");
                System.out.println("@Name   : Nguyen Chanh Dai");
                System.out.println("@ID     : 18120113");
                System.out.println("@Email  : 18120113@student.hcmus.edu.vn");
                System.out.println();

                System.out.println("[MENU]");
                System.out.println("1. View Student List");
                System.out.println("2. Add Student");
                System.out.println("3. Update Student");
                System.out.println("4. Remove Student");
                System.out.println("5. Import / Export (CSV)");
                System.out.println("6. Exit");
                System.out.println();

                System.out.print("Enter a number that corresponds to the feature you need : ");

                BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

                String str = br.readLine();
                int index = Integer.parseInt(str);

                System.out.println();

                if (index == 1) {
                    this.viewStudentList();
                }

                if (index == 2) {
                    this.addStudent();
                }

                if (index == 3) {
                    this.updateStudent();
                }

                if (index == 4) {
                    this.removeStudent();
                }

                if (index == 5) {
                    this.importOrExportCSV();
                }

                if (index == 6) {
                    break;
                }

                System.out.println();

                System.out.print("Do you want to continue using app ? (Y)/n : ");
                String loop = br.readLine();

                System.out.println("\n");
                System.out.println("\n");

                if (loop.toUpperCase().equals("N")) {
                    break;
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
