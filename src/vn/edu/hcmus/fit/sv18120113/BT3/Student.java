package vn.edu.hcmus.fit.sv18120113.BT3;

import java.util.Arrays;
import java.util.List;

/**
 * vn.edu.hcmus.fit.sv18120113.BT3
 *
 * @created by ncdai3651408 - StudentID : 18120113
 * @Date 5/15/20 - 20:20
 * @Description
 */
public class Student {
    private String id;
    private String fullName;
    private double score;
    private String image;
    private String address;
    private String note;

    public String getId () {
        return this.id;
    }

    public String getFullName () {
        return this.fullName;
    }

    public double getScore () {
        return this.score;
    }

    public String getImage () {
        return this.image;
    }

    public String getAddress () {
        return this.address;
    }

    public String getNote () {
        return this.note;
    }

    public Student (String id, String fullName, double score, String image, String address, String note) {
        this.id = id;
        this.fullName = fullName;
        this.score = score;
        this.image = image;
        this.address = address;
        this.note = note;
    }

    public void update (String fullName, double score, String image, String address, String note) {
        if (!fullName.equals("")) {
            this.fullName = fullName;
        }
        if (score >= 0.0) {
            this.score = score;
        }
        if (!image.equals("")) {
            this.image = image;
        }
        if (!address.equals("")) {
            this.address = address;
        }
        if (!note.equals("")) {
            this.note = note;
        }
    }

    public void print () {
        System.out.printf("%-16s", this.id);
        System.out.printf("%-32s", this.fullName);
        System.out.printf("%-16s", this.score);
        System.out.printf("%-32s", this.image);
        System.out.printf("%-64s", this.address);
        System.out.printf("%s", this.note);
    }

    public List<String> toArrayList () {
        return Arrays.asList(this.id, this.fullName, "" + this.score + "", this.image, this.address, this.note);
    }
}
