import java.util.*;


class Student {
    String name;
    int age;
    int roll;
    String branch;
    static int latest;

    public Student() {
        this.name = "Default Name";
        this.age = 10;
        this.roll = ++latest;
        this.branch = "Default Branch";
    }

    public Student(String name, int age, int roll, String branch) {
        this.name = name;
        this.age = age;
        this.roll = roll;
        this.branch = branch;
        latest = roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
        latest = roll;
    }

    public void display() {
        System.out.print("Name: " + this.name + " ");
        System.out.print("Age: " + this.age + " ");
        System.out.print("Branch: " + this.branch + " ");
        System.out.println("Roll-number: " + this.roll);
    }
}


public class Question1 {
    public static void main(String[] args) {
        Student s1 = new Student();
        s1.name = "Crimge";
        s1.age = 10;
        s1.setRoll(10);
        s1.branch = "CSE";

        Student s2 = new Student("Cringe", 20, 20, "CSD");

        s1.display();
        s2.display();
        System.out.println("Latest Roll Number: " + Student.latest);
    }
}