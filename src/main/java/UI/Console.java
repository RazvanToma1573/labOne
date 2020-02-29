package UI;

import Domain.Problem;
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
    private Service<Problem> problemService;

    public Console(Service<Student> studentService, Service<Problem> problemService) {
        this.studentService = studentService;
        this.problemService = problemService;
    }

    public void printTheMenu() {
        System.out.println("Options:");
        System.out.println("\t\t 0 - quit");
        System.out.println("\t\t 1 - add a new student");
        System.out.println("\t\t 2 - remove a  student");
        System.out.println("\t\t 3 - show all students");
        System.out.println("\t\t 4 - add a new lab problem");
        System.out.println("\t\t 5 - remove a lab problem");
        System.out.println("\t\t 6 - show all problems");
    }

    public void menu() {
        System.out.println("Hello!");
        Scanner scanner = new Scanner(System.in);
        this.printTheMenu();
        int choice = -1;
        while (choice != 0) {
            System.out.print("Your choice : ");
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
                else if(choice == 4){
                    this.addNewProblem();
                    System.out.println("Done");
                }
                else if(choice == 5){
                    this.removeProblem();
                    System.out.println("Done");
                }
                else if(choice == 6){
                    this.showAllProblems();
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

    public void addNewProblem(){
        try{
            Problem problem = this.readProblemData();
            this.problemService.add(problem);
        } catch (ValidatorException exception){
            System.out.println("ValidatorException: " + exception.getMessage());
        } catch (RepositoryException exception){
            System.out.println("RepositoryException: " + exception.getMessage());
        }
    }

    public void removeStudent(){
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("ID: ");
            int id = scanner.nextInt();
            this.studentService.remove(id);
        } catch (RepositoryException exception) {
            System.out.println("RepositoryException: " + exception.getMessage());
        }
    }

    public void removeProblem(){
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("ID : ");
            int id = scanner.nextInt();
            this.problemService.remove(id);
        } catch (RepositoryException exception){
            System.out.println("RepositoryException: " + exception.getMessage());
        }
    }

    public void showAllStudents() {
        System.out.println("Students:");
        List<Student> students = this.studentService.get();
        students.stream().forEach(System.out::println);
    }

    public void showAllProblems(){
        System.out.println("Lab Problems:");
        List<Problem> problems = this.problemService.get();
        problems.stream().forEach(System.out::println);
    }

    public Student readStudentData() {
        System.out.println("Insert student data:");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("ID : ");
            int id = Integer.valueOf(bufferRead.readLine());// ...
            System.out.print("First Name : ");
            String firstName = bufferRead.readLine();
            System.out.print("Last Name : ");
            String lastName = bufferRead.readLine();

            Student student = new Student(id, firstName, lastName);
            return student;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Problem readProblemData(){
        System.out.println("Insert problem data:");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try{
            System.out.print("ID : ");
            int id = Integer.valueOf(bufferedReader.readLine());
            System.out.print("Description : ");
            String description = bufferedReader.readLine();
            System.out.print("Difficulty(easy/medium/hard): ");
            String difficulty = bufferedReader.readLine();
            Problem problem = new Problem(id, description, difficulty);
            return problem;
        } catch(IOException exception){
            exception.printStackTrace();
        }
        return null;
    }
}
