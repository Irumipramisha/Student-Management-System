import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Main {
    private static final String FILE_NAME = "students.txt";

    public static void main(String[] args) {
        ArrayList<Student> students = loadStudents();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            String choiceInput = scanner.nextLine(); // get choice as string
            int choice;
            
            try {
                choice = Integer.parseInt(choiceInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number (1, 2, or 3).");
                continue;
            }

            if (choice == 1) {
                System.out.print("Enter Name: ");
                String name = scanner.nextLine(); 
                
                System.out.print("Enter ID: ");
                String id = scanner.nextLine(); 
                
                System.out.print("Enter GPA: ");
                String gpaInput = scanner.nextLine(); 
                
                try {
                    double gpa = Double.parseDouble(gpaInput);
                    Student s = new Student(name, id, gpa);
                    students.add(s);
                    saveStudentToFile(s);
                    System.out.println("Student saved successfully!");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid GPA format! Student not saved.");
                }

            } else if (choice == 2) {
                System.out.println("\n--- Student List ---");
                if (students.isEmpty()) {
                    System.out.println("No students found.");
                } else {
                    for (Student s : students) {
                        System.out.println(s);
                    }
                }
            } else if (choice == 3) {
                System.out.println("Exiting... Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }

    private static void saveStudentToFile(Student s) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            writer.println(s.getName() + "," + s.getId() + "," + s.getGpa());
        } catch (IOException e) {
            System.out.println("Error saving to file.");
        }
    }

    private static ArrayList<Student> loadStudents() {
        ArrayList<Student> list = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    list.add(new Student(parts[0], parts[1], Double.parseDouble(parts[2])));
                }
            }
        } catch (IOException | NumberFormatException e) {
            // Error loading some data
        }
        return list;
    }
}
