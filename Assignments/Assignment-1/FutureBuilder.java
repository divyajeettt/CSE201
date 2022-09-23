import java.util.*;


class Student {
    private final String name;
    private final int roll;
    private float cgpa;
    private final String branch;
    private String status;
    private ArrayList<Company> appliedTo;

    public Student(String name, int roll, float cgpa, String branch) {
        this.name = name;
        this.roll = roll;
        this.cgpa = cgpa;
        this.branch = branch;
        this.status = "Not Applied";
        this.appliedTo = new ArrayList<Company>();
    }

    public String getStatus() {
        return this.status;
    }

    public String toString() {
        return (
            "The Student details are as follows:"
            + "\nName: " + this.name
            + "\nRoll Number: " + this.roll
            + "\nCGPA: " + this.cgpa
            + "\nBranch: " + this.branch
        );
    }

    public String getName() {
        return this.name;
    }

    public int getRoll() {
        return this.roll;
    }

    public void register() {}
    public void registerForCompany() {}
    public void getAvailableCompanies() {}
    public void updateCGPA() {}
    public void decideOnOffer() {}
    public void getCurrentStatus() {}
}


class Company {
    private String name;
    private String role;
    private float ctc;
    private float cgpaCriteria;
    private String dateTime;

    public Company(String name, String role, float ctc, float cgpaCriteria, String dateTime) {
        this.name = name;
        this.role = role;
        this.ctc = ctc;
        this.cgpaCriteria = cgpaCriteria;
        this.dateTime = dateTime;
    }

    public void register() {}
    public void getSelectedStudents() {}
    public void updateRole() {}
    public void updateCTC() {}
    public void updateCGPACriteria() {}
}


class InstitutePlacementCell {
    private String name;
    private ArrayList<Student> students;
    private ArrayList<Company> companies;
    private boolean studentRegsOngoing;
    private boolean companyRegsOngoing;
    private boolean companyRegsOver;
    private String studentStart;
    private String companyStart;
    private String studentEnd;
    private String companyEnd;

    public InstitutePlacementCell(String name) {
        this.name = name;
        this.studentRegsOngoing = false;
        this.companyRegsOngoing = false;
        this.companyRegsOver = false;
    }

    public String getName() {
        return this.name;
    }

    private void setDateTime(int type) {
        String start, end;
        String format = "dd-mm-yyyy hh:mm aa";
        System.out.println("Please fill out the following details (" + format + "):");

        System.out.println("Set the Opening time:");
        start = input.nextLine();
        if (type == 0)
            this.studentStart = input.nextLine();
        else
            this.companyStart = input.nextLine();

        System.out.println("Set the Closing time:");
        end = input.nextLine();
        if (type == 0)
            this.studentEnd = input.nextLine();
        else
            this.companyEnd = input.nextLine();
    }

    public void openStudentRegistrations() {
        // Student Registrations can only be opened if Company Registrations have been completed
        if (companyRegsOngoing) {
            System.out.println("Company Registrations are currently going on!");
            System.out.println("Student Registrations must be started after Company Registrations have ended!");
        }
        else if (!companyRegsOver) {
            System.out.println("Company Registrations have not been completed yet!");
            System.out.println("Student Registrations must be started after Company Registrations have ended!");
        }
        else {
            System.out.println(this.name + " is Open for Student Registrations for the Placement Drive!");
            this.setDateTime(0);
            this.studentRegsOngoing = true;
            this.companyRegsOngoing = false;
            this.companyRegsOver = true;
            this.students = new ArrayList<Student>();
        }
    }

    public void openCompanyRegistrations() {
        // Company Registrations must be completed before Student Registrations
        System.out.println(this.name + " is Open for Company Registrations for the Placement Drive!");
        this.setDateTime(1);
        this.companyRegsOngoing = true;
        this.companies = new ArrayList<Company>();
    }

    public void getNumberOfStudents() {
        System.out.println(this.students.size() + " Students are currently Registered for the Placement Drive");
    }

    public void getNumberOfCompanies() {
        System.out.println(this.companies.size() + " Students are currently Registered for the Placement Drive");
    }

    public void getCategoryWiseStudents() {
        int unoffered = 0;
        int blocked = 0;
        int offered = 0;

        for (Student student: this.students) {
            String status = student.getStatus();
            unoffered += (status.equals("Applied") || status.equals("Not Applied")) ? 1 : 0;
            blocked += status.equals("Blocked") ? 1 : 0;
            offered += status.equals("Offered") ? 1 : 0;
        }

        System.out.println("Un-offered students: " + unoffered);
        System.out.println("Blocked students: " + unoffered);
        System.out.println("Offered students: " + unoffered);
        System.out.println("Details of the Offered students are as follows:");

        for (Student student: this.students) {
            if (student.getStatus().equals("offered"))
                System.out.println(student);
        }
    }

    public void getStudentDetails(String name, int roll) {
        for (Student student: this.students) {
            if (student.getName().equals(name) && student.getRoll() == roll) {

            }
        }
    }

    public void getCompanyDetails() {}
    public void getAveragePackage() {}
    public void getCompanyProcessResults() {}
}


public class FutureBuilder {
    public static Scanner input;

    private static void prompt() {
        System.out.printf(">>> ");
    }

    private static boolean notInRange(int choice, int upper) {
        return !(1 <= choice && choice <= upper);
    }

    private static int mainMenu() {
        System.out.println("1. Enter the Application");
        System.out.println("2. Exit the Application");
        prompt();
        int choice = input.nextInt();
        while (notInRange(choice, 2)) {
            System.out.println("Please enter 1. or 2.");
            prompt();
            choice = input.nextInt();
        }
        return choice;
    }

    private static int chooseMode() {
        System.out.println("Please choose the mode you want to enter in:");
        System.out.println("1. Enter in Student Mode");
        System.out.println("2. Enter in Company Mode");
        System.out.println("3. Enter in Placement-Cell Mode");
        System.out.println("4. Return to the Main Application");
        prompt();
        int choice = input.nextInt();
        while (notInRange(choice, 4)) {
            System.out.println("Please enter a number between 1 - 4");
            prompt();
            choice = input.nextInt();
        }
        return choice;
    }

    private static void StudentMode() {}
    private static void CompanyMode() {}

    private static void PlacementCellMode(InstitutePlacementCell placeCom) {
        System.out.println("Welcome to " + placeCom.getName() + " Placement-Cell!");

        while (true) {
            System.out.println("Please select an action:");
            System.out.println("1. Open Student Registrations");
            System.out.println("2. Open Company Registrations");
            System.out.println("3. Get number of Student Registrations");
            System.out.println("4. Get number of Company Registrations");
            System.out.println("5. Get number of Offered/Un-Offered/Blocked Students");
            System.out.println("6. Get Student details");
            System.out.println("7. Get Company details");
            System.out.println("8. Get Current Average Package");
            System.out.println("9. Get Company Process Results");
            System.out.println("10. Back");

            prompt();
            int choice = input.nextInt();
            while (notInRange(choice, 10)) {
                System.out.println("Please enter a number between 1 - 10");
                prompt();
                choice = input.nextInt();
            }

            if (choice == 1) {
                placeCom.openStudentRegistrations();
            }
            else if (choice == 2) {
                placeCom.openCompanyRegistrations();
            }
            else if (choice == 3) {
                placeCom.getNumberOfStudents();
            }
            else if (choice == 4) {
                placeCom.getNumberOfCompanies();
            }
            else if (choice == 5) {
                placeCom.getCategoryWiseStudents();
            }
            else if (choice == 6) {
                System.out.println("Enter the Name of student: ");
                String name = input.nextLine();
                System.out.println("Enter the Name of student: ");
                int roll = input.nextInt();

                placeCom.getStudentDetails(name, roll);
            }
            else if (choice == 7) {}
            else if (choice == 8) {}
            else if (choice == 9) {}
            else {
                System.out.println("Thanks for using the Placement-Cell Mode.");
                break;
            }
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        InstitutePlacementCell placeCom = new InstitutePlacementCell("IIIT-Delhi");

        System.out.println("Welcome to Future-Builder! Together, we build the world!");
        while (true) {
            int choice = FutureBuilder.mainMenu();
            if (choice == 1) {
                System.out.println("Welcome to Future-Builder!");
                while (true) {
                    int mode = chooseMode();
                    if (mode == 1) {
                        StudentMode();
                    } else if (mode == 2) {
                        CompanyMode();
                    } else if (mode == 3) {
                        PlacementCellMode(placeCom);
                    } else {
                        System.out.println("Returning to Main Application...");
                        break;
                    }
                }
            } else {
                System.out.println("Thanks for using Future-Builder! We hope to see you again!");
                break;
            }
        }

        input.close();
    }
}
