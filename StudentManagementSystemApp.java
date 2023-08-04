import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Student {
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public String getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Roll Number: " + rollNumber + ", Grade: " + grade;
    }
}

class StudentManagementSystem {
    private List<Student> students;

    public StudentManagementSystem() {
        students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public Student searchStudent(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                return student;
            }
        }
        return null;
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public void saveStudentsToFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Student student : students) {
                writer.println(student.getName() + "," + student.getRollNumber() + "," + student.getGrade());
            }
        } catch (IOException e) {
            System.out.println("Error saving students to file: " + e.getMessage());
        }
    }

    public void loadStudentsFromFile(String fileName) {
        students.clear();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    int rollNumber = Integer.parseInt(parts[1]);
                    String grade = parts[2];
                    Student student = new Student(name, rollNumber, grade);
                    students.add(student);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}

public class StudentManagementSystemApp {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentManagementSystem system = new StudentManagementSystem();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    removeStudent();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    displayAllStudents();
                    break;
                case 5:
                    saveStudentsToFile();
                    break;
                case 6:
                    loadStudentsFromFile();
                    break;
                case 7:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        System.out.println("Exiting the application.");
    }
    private static void printMenu() {
        System.out.println("Student Management System Menu:");
        System.out.println("1. Add a new student");
        System.out.println("2. Remove a student");
        System.out.println("3. Search for a student");
        System.out.println("4. Display all students");
        System.out.println("5. Save students to a file");
        System.out.println("6. Load students from a file");
        System.out.println("7. Exit the application");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice() {
        return scanner.nextInt();
    }

    private static void addStudent() {
        System.out.print("Enter the student's name: ");
        scanner.nextLine();  
        String name = scanner.nextLine();

        System.out.print("Enter the student's roll number: ");
        int rollNumber = scanner.nextInt();

        System.out.print("Enter the student's grade: ");
        scanner.nextLine();  
        String grade = scanner.nextLine();

        Student student = new Student(name, rollNumber, grade);
        system.addStudent(student);
        System.out.println("Student added successfully.");
    }

    private static void removeStudent() {
        System.out.print("Enter the roll number of the student to remove: ");
        int rollNumber = scanner.nextInt();

        Student student = system.searchStudent(rollNumber);
        if (student != null) {
            system.removeStudent(student);
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void searchStudent() {
        System.out.print("Enter the roll number of the student to search: ");
        int rollNumber = scanner.nextInt();

        Student student = system.searchStudent(rollNumber);
        if (student != null) {
            System.out.println("Student found:");
            System.out.println(student);
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void displayAllStudents() {
        List<Student> students = system.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("All students:");
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    private static void saveStudentsToFile() {
        System.out.print("Enter the file name to save the students to: ");
        scanner.nextLine();  
        String fileName = scanner.nextLine();

        system.saveStudentsToFile(fileName);
        System.out.println("Students saved to file successfully.");
    }

    private static void loadStudentsFromFile() {
        System.out.print("Enter the file name to load the students from: ");
        scanner.nextLine();  
        String fileName = scanner.nextLine();

        system.loadStudentsFromFile(fileName);
        System.out.println("Students loaded from file successfully.");
    }
}