import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


class Student {
    private final String name;
    private final long roll;
    private float cgpa;
    private final String branch;
    private String status;
    private float currentCTC;                   // Package currently held by the Student
    private ArrayList<Company> applications;    // List of Companies the Student has applied to
    private Company offer;                      // Company with the higher CTC that has offered to the Student
    private Company placedAt;                   // Company where the Student is currently placed at

    public Student(String name, long roll, float cgpa, String branch) {
        this.name = name;
        this.roll = roll;
        this.cgpa = cgpa;
        this.branch = branch;
        this.currentCTC = 0.0f;
        this.status = "Not Applied";
        this.applications = new ArrayList<Company>();
        this.offer = null;
        this.placedAt = null;
    }

    public String toString() {
        return (
              "Name: " + this.name
            + "\nRoll Number: " + this.roll
            + "\nCGPA: " + this.cgpa
            + "\nBranch: " + this.branch
        );
    }

    public String getName() {
        return this.name;
    }

    public long getRoll() {
        return this.roll;
    }

    public float getCurrentCTC() {
        return this.currentCTC;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCGPA(float cgpa) {
        this.cgpa = cgpa;
    }

    public void addApplication(Company company) {
        this.applications.add(company);
    }

    public boolean matches(String name, long roll) {
        return (this.name.equals(name) && this.roll == roll);
    }

    public boolean appliedTo(Company company) {
        return this.applications.contains(company);
    }

    public boolean isEligible(Company company) {
        return (
            !this.status.equals("Placed")
            && this.cgpa >= company.getCGPA()
            && company.getCTC() >= 3*this.currentCTC
        );
    }

    public void getCurrentStatus() {
        if (this.status.equals("Blocked"))
            System.out.println("You are Blocked from further rounds of the Placement Drive!");
        else if (this.status.equals("Applied") || this.status.equals("Not Applied"))
            System.out.println("You are Un-offered by any Company as of yet!");
        else if (this.status.equals("Offered")) {
            System.out.println("You have an Offer from " + this.offer + "! Details of the offer are as follows:");
            this.offer.offerDetails();
        }
        else {
            System.out.println("You are currently Placed at " + this.placedAt + "! Details of the placement are as follows:");
            this.placedAt.offerDetails();
        }
    }

    public void accept() {
        if (this.offer == null)
            System.out.println("You currently do not have any Offers to accept!");
        else {
            Company offer = this.offer;
            System.out.printf("Congratulations " + this.name + "! You have accepted the offer from, and are now ");
            System.out.printf("placed at " + offer.getName() + "with a package of " + offer.getCTC() + "LPA!");
            this.placedAt = this.offer;
            this.currentCTC = this.offer.getCTC();
            offer.addPlaced(this);
        }
    }

    public void reject() {
        if (this.offer == null)
            System.out.println("You currently do not have any Offers to reject!");
        else {
            Company offer = this.offer;
            System.out.println("You have rejected an Offer of " + offer.getCTC() + " LPA from " + offer.getName()+ "!");
            System.out.println("You're now being Blocked from participating in further rounds of the Placement Drive");
            this.status = "Blocked";
        }
    }
}


class Company {
    private final String name;
    private String role;
    private float ctc;
    private float cgpaCriteria;
    private ArrayList<Student> applicants;    // List of Students who have applied to the Company
    private ArrayList<Student> offerings;     // List of Students that the Company has Offered to
    private ArrayList<Student> placed;        // List of Students who are placed at the Company

    public Company(String name, String role, float ctc, float cgpaCriteria) {
        this.name = name;
        this.role = role;
        this.ctc = ctc;
        this.cgpaCriteria = cgpaCriteria;
        this.applicants = new ArrayList<Student>();
        this.offerings = new ArrayList<Student>();
        this.placed = new ArrayList<Student>();
    }

    public String toString() {
        return (
              "Name: " + this.name
            + "\nRole: " + this.role
            + "\nCTC Offering (in LPA): " + this.ctc
            + "\nCGPA Criteria: " + this.cgpaCriteria
        );
    }

    public String getName() {
        return this.name;
    }

    public float getCTC() {
        return this.ctc;
    }

    public float getCGPA() {
        return this.cgpaCriteria;
    }

    public ArrayList<Student> getOfferings() {
        return this.offerings;
    }

    public void addApplicant(Student student) {
        this.applicants.add(student);
    }

    public void addPlaced(Student student) {
        this.placed.add(student);
    }

    public void offerDetails() {
        System.out.println("Offered Role: " + this.role);
        System.out.println("Offered CTC (in LPA): " + this.ctc);
    }

    public boolean offeredTo(Student student) {
        return this.offerings.contains(student);
    }

    public void getSelectedStudents() {}

    public void updateRole(String newRole) {
        this.role = newRole;
    }
    public void updateCTC(float newCTC) {
        this.ctc = newCTC;
    }
    public void updateCGPACriteria(float newCGPA) {
        this.cgpaCriteria = newCGPA;
    }
}


class InstitutePlacementCell {
    private final String name;
    private ArrayList<Student> students;               // Supreme list of all Students in existence
    private ArrayList<Company> companies;              // Supreme list of all Companies in existence
    private ArrayList<Student> registeredStudents;     // List of Students who registered for the Placement Drive
    private ArrayList<Company> registeredCompanies;    // List of Companies who registered for the Placement Drive
    private boolean companyRegsOngoing;                // Boolean to check if company Registrations are in process
    private boolean companyRegsOver;                   // Boolean to check if company Registrations are over
    private LocalDateTime studentStart;                // Start DateTime of Student Registrations
    private LocalDateTime companyStart;                // Start DateTime of Company Registrations
    private LocalDateTime studentEnd;                  // End DateTime of Student Registrations
    private LocalDateTime companyEnd;                  // End DateTime of Company Registrations

    public InstitutePlacementCell(String name) {
        this.name = name;
        this.companyRegsOngoing = false;
        this.companyRegsOver = false;
        this.students = new ArrayList<Student>();
        this.companies = new ArrayList<Company>();
        this.registeredStudents = new ArrayList<Student>();
        this.registeredCompanies = new ArrayList<Company>();
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Company> getCompanies() {
        return this.companies;
    }

    public void setStudentStart(LocalDateTime dateTime) {
        this.studentStart = dateTime;
    }

    public void setStudentEnd(LocalDateTime dateTime) {
        this.studentEnd = dateTime;
    }

    public void setCompanyStart(LocalDateTime dateTime) {
        this.companyStart = dateTime;
    }

    public void setCompanyEnd(LocalDateTime dateTime) {
        this.companyEnd = dateTime;
    }

    public ArrayList<Company> getRegisteredCompanies() {
        return this.registeredCompanies;
    }

    public Company idCompany(String name) {
        for (Company company: this.registeredCompanies) {
            if (name.equals(company.getName()))
                return company;
        }
        System.out.println("Company not Found!");
        return null;
    }

    public Student idStudent(String name, long roll) {
        for (Student student: this.registeredStudents) {
            if (student.getRoll() == roll && name.equals(student.getName()))
                return student;
        }
        System.out.println("Student not Found!");
        return null;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public void registerStudent(Student student, LocalDateTime regDateTime) {
        if (!regDateTime.isAfter(this.studentStart))
            System.out.println("The Student Registrations for the Placement Drive have not started yet!");
        else if (!regDateTime.isBefore(this.studentEnd))
            System.out.println("The Student Registrations for the Placement Drive have ended!");
        else {
            System.out.println(student.getName() + "Registered for Placement Drive successfully! Their details are:");
            System.out.println(student);
            this.registeredStudents.add(student);
        }
    }

    public void apply(Student student, Company company) {
        company.addApplicant(student);
        student.addApplication(company);
        student.setStatus("Applied");
    }

    public void getAvailableCompanies(Student student) {
        if (this.registeredCompanies == null)
            System.out.println("No company is currently registered for the Placement Drive!");
        else {
            System.out.println("The list of all available companies:");
            int counter = 1;
            for (Company company: this.registeredCompanies) {
                if (student.isEligible(company)) {
                    System.out.println(counter + ". " + company);
                    counter++;
                }
            }
        }
    }

    public void updateCGPA(Student student, float newCGPA) {
        if (newCGPA < 0 || newCGPA > 10)
            System.out.println("Invalid CGPA entered!");
        else {
            student.setCGPA(newCGPA);
            System.out.println(student.getName() + "'s CGPA has been updated to " + newCGPA + "!");
        }
    }

    public void addCompany(Company company) {
        this.companies.add(company);
    }

    public void registerCompany(Company company, LocalDateTime regDateTime) {
        if (!regDateTime.isAfter(this.companyStart))
            System.out.println("The Company Registrations for the Placement Drive have not started yet!");
        else if (!regDateTime.isBefore(this.companyEnd))
            System.out.println("The Company Registrations for the Placement Drive have ended!");
        else {
            System.out.println(company.getName() + "Registered for Placement Drive successfully! Its details are:");
            System.out.println(company);
            this.registeredCompanies.add(company);
        }
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
            FutureBuilder.setDateTime(0, this);
            this.companyRegsOngoing = false;
            this.companyRegsOver = true;
        }
    }

    public void openCompanyRegistrations() {
        // Company Registrations must be completed before Student Registrations
        System.out.println(this.name + " is Open for Company Registrations for the Placement Drive!");
        FutureBuilder.setDateTime(1, this);
        this.companyRegsOngoing = true;
    }

    public void getNumberOfStudents() {
        if (this.registeredStudents == null)
            System.out.println("No students are registered for the Placement Drive");
        else
            System.out.println(this.registeredStudents.size() + " students are currently registered for the Placement Drive");
    }

    public void getNumberOfCompanies() {
        if (this.registeredCompanies == null)
            System.out.println("No companies are registered for the Placement Drive");
        else
            System.out.println(this.registeredCompanies.size() + " companies are currently Registered for the Placement Drive");
    }

    public void getCategoryWiseStudents() {
        int unOffered = 0;
        int blocked = 0;
        int offered = 0;

        for (Student student: this.registeredStudents) {
            String status = student.getStatus();
            unOffered += (status.equals("Applied") || status.equals("Not Applied")) ? 1 : 0;
            blocked += status.equals("Blocked") ? 1 : 0;
            offered += (status.equals("Offered") || status.equals("Placed")) ? 1 : 0;
        }

        System.out.println("Un-offered students: " + unOffered);
        System.out.println("Blocked students: " + blocked);
        System.out.println("Offered students: " + offered);
        System.out.println("Details of the Offered students are as follows:");

        for (Student student: this.registeredStudents) {
            if (student.getStatus().equals("offered"))
                System.out.println(student);
        }
    }

    public void getStudentDetails(String name, long roll) {
        Student student = idStudent(name, roll);
        if (student == null)
            return;

        for (Company company: this.registeredCompanies) {
            String cName = company.getName();
            if (student.appliedTo(company))
                System.out.println(name + " applied to " + cName);
            else
                System.out.println(name + " did not apply to " + cName);
            if (company.offeredTo(student))
                System.out.println(cName + " offered to " + name);
            else
                System.out.println(cName + " did not offer to " + name);
        }
    }

    public void getCompanyDetails(String name) {
        Company company = idCompany(name);
        if (company == null)
            return;

        System.out.println("The company details are as follows:");
        System.out.println(company);
        System.out.println(company.getName() + " has offered to the following students:");
        for (Student student: company.getOfferings())
            System.out.println(student);
    }

    public void getAveragePackage() {
        float average = 0.0f;
        int placed = 0;
        for (Student student: this.registeredStudents) {
            String status = student.getStatus();
            if (status.equals("Offered") || status.equals("Placed")) {
                average += student.getCurrentCTC();
                placed ++;
            }
        }
        average /= placed;
        System.out.println("Average Package offered to students of " + this.name + ": " + average);
    }

    public void getCompanyProcessResults(String name) {
        Company company = idCompany(name);
        if (company == null)
            return;

        ArrayList<Student> offers = company.getOfferings();
        if (offers == null)
            System.out.println(name + " has not selected any students!");
        else {
            System.out.println(name + " has selected the following students:");
            for (Student student: offers)
                System.out.println(student.getRoll() + ": " + student.getName());
        }
    }
}


public class FutureBuilder {
    private static DateTimeFormatter format;    // DateTime format specifier

    private static int inputChoice(int upper) {
        Scanner input = new Scanner(System.in);
        System.out.printf(">>> ");
        int choice = input.nextInt();
        while (!(1 <= choice && choice <= upper)) {
            System.out.println("Please enter a number between 1 - " + upper);
            System.out.printf(">>> ");
            choice = input.nextInt();
        }
        return choice;
    }

    public static void setDateTime(int type, InstitutePlacementCell placeCom) {
        Scanner input = new Scanner(System.in);
        System.out.println("Please fill out the following details (" + format + ")");

        System.out.printf("Set the Opening time: ");
        if (type == 0)
            placeCom.setStudentStart(LocalDateTime.parse(input.nextLine(), format));
        else
            placeCom.setCompanyStart(LocalDateTime.parse(input.nextLine(), format));

        System.out.printf("Set the Closing time: ");
        if (type == 0)
            placeCom.setStudentEnd(LocalDateTime.parse(input.nextLine(), format));
        else
            placeCom.setCompanyEnd(LocalDateTime.parse(input.nextLine(), format));
    }

    private static void studentMode(InstitutePlacementCell placeCom) {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the Student Mode!");

        while (true) {
            System.out.println("Please select an action:");
            System.out.println("1. Add new Students");
            System.out.println("2. Log-In as a Student");
            System.out.println("3. Back");
            int choice = inputChoice(3);

            if (choice == 1) {
                System.out.println("Enter the number of students to add: ");
                int num = input.nextInt();
                if (num <= 0) {
                    System.out.println("Invalid input!");
                    continue;
                }
                for (int i=0; i < num; i++) {
                    System.out.println("Enter the Student's Name, Roll Number, CGPA, and Branch (in order):");
                    String name = input.nextLine();
                    long roll = input.nextLong();
                    float cgpa = input.nextFloat();
                    String branch = input.nextLine();
                    placeCom.addStudent(new Student(name, roll, cgpa, branch));
                    System.out.println("Student " + name + " added successfully!");
                }
            }
            else if (choice == 2) {
                System.out.println("Enter the Student's Name and Roll Number (in order): ");
                String name = input.nextLine();
                long roll = input.nextLong();
                Student student = placeCom.idStudent(name, roll);
                studentLogin(placeCom, student);
            }
            else {
                System.out.println("Thanks for using the Student Mode!");
                break;
            }
        }
    }

    private static void studentLogin(InstitutePlacementCell placeCom, Student student) {
        if (student == null)
            return;

        Scanner input = new Scanner(System.in);
        System.out.println("Welcome " + student.getName() + "!");

        while (true) {
            System.out.println("Please select an action:");
            System.out.println("1. Register for Placement Drive");
            System.out.println("2. Register for a Company");
            System.out.println("3. Get a list of all available Companies");
            System.out.println("4. Get current status");
            System.out.println("5. Request to Update CGPA");
            System.out.println("6. Accept the latest offer");
            System.out.println("7. Reject the latest offer");
            System.out.println("8. Back");
            int choice = inputChoice(8);

            if (choice == 1) {
                System.out.printf("Enter the date and time of registration (dd-mm-yyyy hh:mm aa): ");
                placeCom.registerStudent(student, LocalDateTime.parse(input.nextLine(), format));
            }
            else if (choice == 2) {
                System.out.printf("Enter name of the Company you want to register for: ");
                String companyName = input.nextLine();
                for (Company company: placeCom.getRegisteredCompanies()) {
                    if (companyName.equals(company.getName())) {
                        if (student.isEligible(company))
                            placeCom.apply(student, company);
                        else
                            System.out.println("You are not eligible to apply to " + companyName);
                        break;
                    }
                }
            }
            else if (choice == 3) {
                placeCom.getAvailableCompanies(student);
            }
            else if (choice == 4) {
                student.getCurrentStatus();
            }
            else if (choice == 5) {
                System.out.println("Please enter the Updated CGPA: ");
                placeCom.updateCGPA(student, input.nextFloat());
            }
            else if (choice == 6) {
                student.accept();
            }
            else if (choice == 7) {
                student.reject();
            }
            else {
                System.out.println("Logging out...");
                break;
            }
        }
    }

    private static void companyMode(InstitutePlacementCell placeCom) {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the Company Mode!");

        while (true) {
            System.out.println("Please select an action:");
            System.out.println("1. Add a new Company");
            System.out.println("2. Log-In as a Company");
            System.out.println("3. Get a list of all Companies");
            System.out.println("4. Back");
            int choice = inputChoice(4);

            if (choice == 1) {
                System.out.println("Enter the Company's Name, Role, CTC, and CGPA Requirement (in order):");
                String name = input.nextLine();
                String role = input.nextLine();
                float ctc = input.nextFloat();
                float cgpaCriteria = input.nextFloat();
                placeCom.addCompany(new Company(name, role, ctc, cgpaCriteria));
                System.out.println("Company " + name + " added successfully!");
            }
            else if (choice == 2) {
                ArrayList<Company> companies = placeCom.getCompanies();
                if (companies == null)
                    System.out.println("No Companies are currently available!");
                else {
                    System.out.println("Choose a Company to Log-In as:");
                    for (int i = 1; i <= companies.size(); i++)
                        System.out.println(i + ". " + companies.get(i-1).getName());
                    companyLogin(placeCom, companies.get(inputChoice(companies.size()) - 1));
                }
            }
            else if (choice == 3) {
                ArrayList<Company> companies = placeCom.getCompanies();
                if (companies == null)
                    System.out.println("No Company is currently available!");
                else {
                    System.out.println("The following Companies are currently available:");
                    for (int i=1; i <= companies.size(); i++)
                        System.out.println(i + ". " + companies.get(i-1));
                }
            }
            else {
                System.out.println("Thanks for using the Company Mode!");
                break;
            }
        }
    }

    private static void companyLogin(InstitutePlacementCell placeCom, Company company) {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome " + company.getName() + "!");

        while (true) {
            System.out.println("Please select an action:");
            System.out.println("1. Register for Placement Drive");
            System.out.println("2. Update Offered Role");
            System.out.println("3. Update Offered Package CTC");
            System.out.println("4. Update CGPA Criteria");
            System.out.println("5. Back");
            int choice = inputChoice(5);

            if (choice == 1) {
                System.out.printf("Enter the date and time of registration (dd-mm-yyyy hh:mm aa): ");
                placeCom.registerCompany(company, LocalDateTime.parse(input.nextLine(), format));
            }
            else if (choice == 2) {
                System.out.printf("Enter new offered Role: ");
                company.updateRole(input.nextLine());
            }
            else if (choice == 3) {
                System.out.printf("Enter new offered Package (in LPA): ");
                company.updateCTC(input.nextFloat());
            }
            else if (choice == 4) {
                System.out.printf("Enter new CGPA Criteria: ");
                company.updateCGPACriteria(input.nextFloat());
            }
            else {
                System.out.println("Logging out...");
                break;
            }
        }
    }

    private static void placementCellMode(InstitutePlacementCell placeCom) {
        Scanner input = new Scanner(System.in);
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
            int choice = inputChoice(10);

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
                System.out.println("Enter the Roll Number of student: ");
                long roll = input.nextLong();
                placeCom.getStudentDetails(name, roll);
            }
            else if (choice == 7) {
                System.out.println("Enter the Name of company: ");
                String name = input.nextLine();
                placeCom.getCompanyDetails(name);
            }
            else if (choice == 8) {
                placeCom.getAveragePackage();
            }
            else if (choice == 9) {
                System.out.println("Enter the Name of company: ");
                String name = input.nextLine();
                placeCom.getCompanyProcessResults(name);
            }
            else {
                System.out.println("Thanks for using the Placement-Cell Mode!");
                break;
            }
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");

        InstitutePlacementCell placementCell = new InstitutePlacementCell("IIIT-Delhi");

        System.out.println("Welcome to Future-Builder! Together, we build the world!");
        while (true) {
            System.out.println("1. Enter the Application");
            System.out.println("2. Exit the Application");
            int choice = inputChoice(2);

            if (choice == 1) {
                System.out.println("Welcome to Future-Builder!");
                while (true) {
                    System.out.println("Please choose the mode you want to enter in:");
                    System.out.println("1. Enter in Student Mode");
                    System.out.println("2. Enter in Company Mode");
                    System.out.println("3. Enter in Placement-Cell Mode");
                    System.out.println("4. Return to the Main Application");
                    int mode = inputChoice(4);

                    if (mode == 1)
                        studentMode(placementCell);
                    else if (mode == 2)
                        companyMode(placementCell);
                    else if (mode == 3)
                        placementCellMode(placementCell);
                    else {
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