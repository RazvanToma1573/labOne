package ro.mpp.client.UI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import ro.mpp.core.Domain.Grade;
import ro.mpp.core.Domain.Problem;
import ro.mpp.core.Domain.Student;
import ro.mpp.web.dto.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Console {
    public static final String URL = "http://localhost:8080/api";

    private RestTemplate restTemplate;

    public Console(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
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
        System.out.println("\t\t 7 - assign a problem to a student");
        System.out.println("\t\t 8 - show all students,problems and grades");
        System.out.println("\t\t 9 - assign a grade to a student");
        System.out.println("\t\t 10 - filter");
        System.out.println("\t\t 11 - reports");
        System.out.println("\t\t 12 - update student");
        System.out.println("\t\t 13 - update problem");
        System.out.println("\t\t 14 - show all students, sorted ");
        System.out.println("\t\t 15 - show all problems, sorted ");
        System.out.println("\t\t 16 - show all grades, sorted ");
    }

    public void run() {
        System.out.println("Hello!");
        int choice = -1;
        Scanner scanner = new Scanner(System.in);
        while(choice != 0) {
            printTheMenu();
            System.out.println("---------------------------------");
            System.out.println("Your choice:");
            try {
                choice = scanner.nextInt();
                if(choice == 1){
                    System.out.println();
                    this.addNewStudent();
                    System.out.println();
                    System.out.println("Done...");
                }
                else if(choice == 2){
                    System.out.println();
                    this.removeStudent();
                    System.out.println();
                    System.out.println("Done...");
                }
                else if(choice == 3){
                    System.out.println();
                    this.showAllStudents();
                    System.out.println();
                    System.out.println("Done...");
                }
                else if(choice == 4){
                    System.out.println();
                    this.addNewProblem();
                    System.out.println();
                    System.out.println("Done...");
                }
                else if(choice == 5){
                    System.out.println();
                    this.removeProblem();
                    System.out.println();
                    System.out.println("Done...");
                }
                else if(choice == 6){
                    System.out.println();
                    this.showAllProblems();
                    System.out.println();
                    System.out.println("Done...");
                }
                else if(choice == 7){
                    System.out.println();
                    this.assignProblem();
                    System.out.println();
                    System.out.println("Done...");
                }
                else if(choice == 8){
                    System.out.println();
                    this.showAllGrades();
                    System.out.println();
                    System.out.println("Done...");
                }
                else if(choice == 9){
                    System.out.println();
                    this.assignGrade();
                    System.out.println();
                    System.out.println("Done...");
                }
                else if(choice == 10){
                    System.out.println();
                    this.filterStudents();
                    System.out.println();
                    System.out.println("Done...");
                }
                else if(choice == 11){
                    System.out.println();
                    this.reports();
                    System.out.println();
                    System.out.println("Done...");
                }
                else if(choice == 12){
                    System.out.println();
                    this.updateStudent();
                    System.out.println();
                    System.out.println("Done...");
                }
                else if(choice == 13){
                    System.out.println();
                    this.updateProblem();
                    System.out.println();
                    System.out.println("Done...");
                }
                else if(choice == 14){
                    System.out.println();
                    this.showAllStudentsSorted();
                    System.out.println();
                    System.out.println("Done...");
                }
                else if(choice == 15){
                    System.out.println();
                    this.showAllProblemsSorted();
                    System.out.println();
                    System.out.println("Done...");
                }
                else if(choice == 16){
                    System.out.println();
                    this.showAllGradesSorted();
                    System.out.println();
                    System.out.println("Done...");
                }
            } catch (Exception e){
                //System.out.println(e.getMessage());
                e.printStackTrace();
            }

        }
    }

    public void addNewStudent() {
        StudentDTO studentDTO = this.readStudentData();
        this.restTemplate.postForObject(URL + "/students",
                studentDTO, StudentDTO.class);
    }

    public StudentDTO readStudentData() {
        Scanner scanner = new Scanner(System.in);
        String firstName, lastName;
        System.out.println("First Name:");
        firstName = scanner.nextLine();
        System.out.println("Last Name:");
        lastName = scanner.nextLine();
        return new StudentDTO(firstName, lastName);
    }

    public void removeStudent(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Student ID:");
        int id = scanner.nextInt();
        this.restTemplate.delete(URL + "/students/{id}", id);
    }

    public void showAllStudents(){
        restTemplate.getForObject(URL + "/students", StudentsDTO.class).getStudents()
                .forEach(student -> System.out.println(student.getId() +
                        "," + student.getFirstName() + "," + student.getLastName()));
    }

    public void addNewProblem(){
        ProblemDTO problemDTO = this.readProblemData();
        this.restTemplate.postForObject(URL + "/problems",
                problemDTO, ProblemDTO.class);
    }

    public ProblemDTO readProblemData() {
        Scanner scanner = new Scanner(System.in);
        String description, difficulty;
        System.out.println("Description:");
        description = scanner.nextLine();
        System.out.println("Difficulty:");
        difficulty = scanner.nextLine();
        return new ProblemDTO(description, difficulty);
    }

    public void removeProblem() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Problem ID:");
        int id = scanner.nextInt();
        this.restTemplate.delete(URL + "/problems/{id}", id);
    }

    public void showAllProblems() {
        restTemplate.getForObject(URL + "/problems", ProblemsDTO.class).getProblems()
                .forEach(problem -> System.out.println(problem.getId() + "," +
                        problem.getDescription() + "," + problem.getDifficulty()));
    }

    public void assignProblem() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Student ID:");
        int studentId = scanner.nextInt();
        System.out.println("Problem ID:");
        int problemId = scanner.nextInt();
        this.restTemplate.postForObject(URL + "/assign-problem",
                new GradeDTO(studentId, problemId, 0),
                GradeDTO.class);
    }

    public void showAllGrades() {
        restTemplate.getForObject(URL + "/grades", GradesDTO.class).getGrades()
                .forEach(grade -> {
                    StudentDTO studentDTO = restTemplate.getForObject(URL + "/student/" + grade.getStudentId(), StudentDTO.class);
                    ProblemDTO problemDTO = restTemplate.getForObject(URL + "/problem/" + grade.getProblemId(), ProblemDTO.class);
                    System.out.println(String.format("%d,%s,%s\n%d,%s,%s\nGrade: %d\n",
                            studentDTO.getId(), studentDTO.getFirstName(), studentDTO.getLastName(),
                            problemDTO.getId(), problemDTO.getDescription(), problemDTO.getDifficulty(),
                            grade.getActualGrade()));
                });
    }

    public void assignGrade() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Student ID");
        int studentId = scanner.nextInt();
        System.out.println("Problem ID");
        int problemId = scanner.nextInt();
        System.out.println("Grade:");
        int grade = scanner.nextInt();
        GradeDTO gradeDTO = restTemplate.getForObject(URL + "/grade/" + studentId + "-" + problemId, GradeDTO.class, studentId, problemId);
        gradeDTO.setActualGrade(grade);
        this.restTemplate.put(URL + "/grades/{id}", gradeDTO, gradeDTO.getId());
    }

    public void filterStudents() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("You want to filter by: (FIRSTNAME/LASTNAME/PROBLEM/GRADE)");
        String type = scanner.nextLine();
        System.out.println("Argument:");
        String argument = scanner.nextLine();
        restTemplate.getForObject(URL + "/students/filter/{type}/{argument}",
                StudentsDTO.class, type, argument).getStudents().stream()
                .forEach(student -> System.out.println(student.getId()
                + "," + student.getFirstName() + "," + student.getLastName()));
    }

    public void reports() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a report");
        System.out.println("\t1. Student with maximum average grade");
        System.out.println("\t2. Student who was assigned with most problems");
        System.out.println("\t3. Student with maximum average grade at hard problems");
        int choice = scanner.nextInt();
        if(choice == 1) {
            StudentDTO student = restTemplate.getForObject(URL + "/reports/student-max-grade", StudentDTO.class);
            System.out.println(student.getId() + "," + student.getFirstName() + "," + student.getLastName());
        }
        else if(choice == 2) {
            StudentDTO student = restTemplate.getForObject(URL + "/reports/student-max-problems", StudentDTO.class);
            System.out.println(student.getId() + "," + student.getFirstName() + "," + student.getLastName());
        }
        else if(choice == 3) {
            StudentDTO student = restTemplate.getForObject(URL + "/reports/student-max-grade-hard", StudentDTO.class);
            System.out.println(student.getId() + "," + student.getFirstName() + "," + student.getLastName());
        }
        else{
            System.out.println("Invalid choice");
        }
    }

    public void updateStudent() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Student ID:");
        int id = Integer.parseInt(bufferedReader.readLine());
        System.out.println("Student First Name:");
        String firstName = bufferedReader.readLine();
        System.out.println("Student Last Name:");
        String lastName = bufferedReader.readLine();
        StudentDTO student = restTemplate.getForObject(URL + "/student/" + id, StudentDTO.class);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        this.restTemplate.put(URL + "/students/{id}", student, student.getId());
    }

    public void updateProblem() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Problem ID:");
        int id = Integer.parseInt(bufferedReader.readLine());
        ProblemDTO problem = restTemplate.getForObject(URL + "/problem/" + id, ProblemDTO.class);
        System.out.println("Problem Description:");
        String desc = bufferedReader.readLine();
        System.out.println("Problem Difficulty:");
        String diff = bufferedReader.readLine();
        problem.setDescription(desc);
        problem.setDifficulty(diff);
        this.restTemplate.put(URL + "/problems/{id}", problem, problem.getId());
    }

    public void showAllStudentsSorted() throws IOException {
        Map<String, Boolean> criteria = new HashMap<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter criteria in the form asc/desc id/firstName/lastName");
        String line = bufferedReader.readLine();
        criteria.put(line.split(" ")[1], line.split(" ")[0].equals("desc"));
        while(true) {
            System.out.println("Any other criteria? Enter 'no' for stop");
            line = bufferedReader.readLine();
            if(line.equals("no"))
                break;
            else
                criteria.put(line.split(" ")[1], line.split(" ")[0].equals("desc"));
        }
        String param = criteria.entrySet().stream()
                .map(entry -> entry.getKey() + "-" + entry.getValue())
                .reduce((a, b) -> a + "&" + b).get();

        this.restTemplate.getForObject(URL + "/students/sorted/{param}", StudentsDTO.class, param)
        .getStudents().forEach(student ->
                System.out.println(student.getId() + "," + student.getFirstName() + "," + student.getLastName()));
    }

    public void showAllProblemsSorted() throws IOException {
        Map<String, Boolean> criteria = new HashMap<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter criteria in the form asc/desc id/description/difficulty");
        String line = bufferedReader.readLine();
        criteria.put(line.split(" ")[1], line.split(" ")[0].equals("desc"));
        while(true) {
            System.out.println("Any other criteria? Enter 'no' for stop");
            line = bufferedReader.readLine();
            if(line.equals("no"))
                break;
            else
                criteria.put(line.split(" ")[1], line.split(" ")[0].equals("desc"));
        }
        String param = criteria.entrySet().stream()
                .map(entry -> entry.getKey() + "-" + entry.getValue())
                .reduce((a, b) -> a + "&" + b).get();

        this.restTemplate.getForObject(URL + "/problems/sorted/{param}", ProblemsDTO.class, param)
                .getProblems().forEach(problem ->
                System.out.println(problem.getId() + "," + problem.getDescription() + "," + problem.getDifficulty()));

    }

    public void showAllGradesSorted() throws IOException {
        Map<String, Boolean> criteria = new HashMap<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter criteria in the form asc/desc studentId/problemId/actualGrade");
        String line = bufferedReader.readLine();
        criteria.put(line.split(" ")[1], line.split(" ")[0].equals("desc"));
        while(true) {
            System.out.println("Any other criteria? Enter 'no' for stop");
            line = bufferedReader.readLine();
            if(line.equals("no"))
                break;
            else
                criteria.put(line.split(" ")[1], line.split(" ")[0].equals("desc"));
        }
        String param = criteria.entrySet().stream()
                .map(entry -> entry.getKey() + "-" + entry.getValue())
                .reduce((a, b) -> a + "&" + b).get();

        this.restTemplate.getForObject(URL + "/grades/sorted/{param}", GradesDTO.class, param)
                .getGrades().forEach(grade -> {
            StudentDTO studentDTO = restTemplate.getForObject(URL + "/student/" + grade.getStudentId(), StudentDTO.class);
            ProblemDTO problemDTO = restTemplate.getForObject(URL + "/problem/" + grade.getProblemId(), ProblemDTO.class);
            System.out.println(String.format("%d,%s,%s\n%d,%s,%s\nGrade: %d\n",
                    studentDTO.getId(), studentDTO.getFirstName(), studentDTO.getLastName(),
                    problemDTO.getId(), problemDTO.getDescription(), problemDTO.getDifficulty(),
                    grade.getActualGrade()));
        });
    }
}