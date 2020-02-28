package UI;

import Domain.Student;
import Domain.Validators.ValidatorException;
import Repository.RepositoryException;
import Service.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class Console {

    private Service<Student> studentService;

    public Console(Service<Student> studentService) {
        this.studentService = studentService;
    }

    public void printTheMenu() {
        System.out.println("Options:");
        System.out.println("\t\t 0 - quit");
        System.out.println("\t\t 1 - add a new student");
        System.out.println("\t\t 2 - remove a  student");
        System.out.println("\t\t 3 - show all students");
    }

    public void menu() {
        System.out.println("Hello!");
        Scanner scanner = new Scanner(System.in);
        this.printTheMenu();
        int choice = -1;
        while (choice != 0) {
            if (scanner.hasNextInt()){
                choice = scanner.nextInt();
                if (choice == 1) {
                    this.addNewStudent();
                    System.out.println("Done");
                }
                else if (choice == 2) {
                    this.removeStudent();
                    System.out.println("Done");
                }
                else if (choice == 3) {
                    this.showAllStudents();
                    System.out.println("Done");
                }
                else if (choice == 0)
                    System.out.println("Execution over...");
                else{
                    System.out.println("Please insert a valid number for your option!");
                }
            } else {
                System.out.println("Please insert a number for your option!");
            }
        }
    }

    public void addNewStudent(){
        try {
            Student newStudent = this.readStudentData();
            this.studentService.add(newStudent);
        } catch (ValidatorException exception){
            System.out.println("ValidatorException:" + exception.getMessage());
        } catch (RepositoryException exception) {
            System.out.println("RepositoryException:" + exception.getMessage());
        }
    }

    public void removeStudent(){
        try {
            Student studentToBeRemoved = this.readStudentData();
            this.studentService.remove(studentToBeRemoved);
        } catch (ValidatorException exception) {
            System.out.println("ValidatorException: " + exception.getMessage());
        } catch (RepositoryException exception) {
            System.out.println("RepositoryException:" + exception.getMessage());
        }
    }

    public void showAllStudents() {
        System.out.println("Students:");
        List<Student> students = this.studentService.get();
        students.stream().forEach(System.out::println);
    }

    public Student readStudentData() {
        System.out.println("Insert student data:");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            int id = Integer.valueOf(bufferRead.readLine());// ...
            String firstName = bufferRead.readLine();
            String lastName = bufferRead.readLine();

            Student student = new Student(id, firstName, lastName);
            return student;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
