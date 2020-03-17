package UI;

import Domain.Grade;
import Domain.Problem;
import Domain.Student;
import Domain.Validators.ValidatorException;
import Service.ProblemsService;
import Service.StudentsService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Console {

    private StudentsService studentService;
    private ProblemsService problemService;

    /**
     * Creates a new Console
     * @param studentService is service for students
     * @param problemService is service for problems
     */
    public Console(StudentsService studentService, ProblemsService problemService) {
        this.studentService = studentService;
        this.problemService = problemService;
    }

    /**
     * Prints the menu with the available commands
     */
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
    }

    /**
     * Reads a command from the console and implement the specific function
     */
    public void menu() {
        System.out.println("Hello!");
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (choice != 0) {
            this.printTheMenu();
            System.out.println();
            System.out.println("-------------------------------------------");
            System.out.println();
            System.out.print("Your choice : ");
            try {
                choice = scanner.nextInt();
                if (choice == 1) {
                    System.out.println();
                    this.addNewStudent();
                    System.out.println();
                    System.out.println("Done...");
                    System.out.println();
                } else if (choice == 2) {
                    System.out.println();
                    this.removeStudent();
                    System.out.println();
                    System.out.println("Done...");
                    System.out.println();
                } else if (choice == 3) {
                    System.out.println();
                    this.showAllStudents();
                    System.out.println();
                    System.out.println("Done...");
                    System.out.println();
                } else if (choice == 4) {
                    System.out.println();
                    this.addNewProblem();
                    System.out.println();
                    System.out.println("Done...");
                    System.out.println();
                } else if (choice == 5) {
                    System.out.println();
                    this.removeProblem();
                    System.out.println();
                    System.out.println("Done...");
                    System.out.println();
                } else if (choice == 6) {
                    System.out.println();
                    this.showAllProblems();
                    System.out.println();
                    System.out.println("Done...");
                    System.out.println();
                } else if (choice == 7) {
                    System.out.println();
                    this.assignProblemToStudent();
                    System.out.println();
                    System.out.println("Done...");
                    System.out.println();
                } else if (choice == 8) {
                    System.out.println();
                    this.showAllGrades();
                    System.out.println();
                    System.out.println("Done...");
                    System.out.println();
                } else if (choice == 9) {
                    System.out.println();
                    this.assignGradeToStudent();
                    System.out.println();
                    System.out.println("Done...");
                    System.out.println();
                } else if (choice == 10) {
                    System.out.println();
                    this.filterStudents();
                    System.out.println();
                    System.out.println("Done...");
                    System.out.println();
                } else if(choice == 11) {
                    System.out.println();
                    this.reports();
                    System.out.println();
                    System.out.println("Done");
                    System.out.println();
                } else if (choice == 12) {
                    System.out.println();
                    this.updateStudent();
                    System.out.println();
                    System.out.println("Done...");
                    System.out.println();
                } else if (choice == 13) {
                    System.out.println();
                    this.updateProblem();
                    System.out.println();
                    System.out.println("Done...");
                    System.out.println();
                } else if (choice == 0) {
                    System.out.println();
                    System.out.println("Done");
                } else {
                    System.out.println("Please insert a valid number for your option!");
                }
            } catch(InputMismatchException exception) {
                System.out.println("Input exception:" + exception.getMessage());
                scanner.nextLine();
            }
        }
    }

    public void reports() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select one report from the list below:");
        System.out.println("\t 1. Problem that was assigned most times");
        System.out.println("\t 2. Student with the highest average grade");
        System.out.println("\t 3. Student with the most assigned problems");
        System.out.println("\t 4. Problem with the highest average grade");
        System.out.println("\t 5. Student with highest average at hard problems");
        try {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println(this.studentService.getMaxAssignedProblem());
                    break;
                case 2:
                    System.out.println(this.studentService.getStudentWithMaxGrade());
                    break;
                case 3:
                    System.out.println(this.studentService.getMostAssignedStudent());
                case 4:
                    System.out.println(this.studentService.getProblemHighestAverage());
                    break;
                case 5:
                    System.out.println(this.studentService.getStudentHighestAverageHard());
                    break;
                default:
                    System.out.println("Invalid command");
            }
        } catch (ValidatorException exception) {
            System.out.println("Validator Exception: " + exception.getMessage());
        }
    }
    /**
     * Function for updating a student
     */
    public void updateStudent(){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        boolean cont = true;
        while(cont) {
            try {
                System.out.print("ID:");
                int idStudentToBeUpdated =  Integer.parseInt(bufferedReader.readLine());
                System.out.println("You can update the Students' first name (FIRSTNAME) or the Students' last name (LASTNAME)");
                System.out.print("TYPE:");
                String type = bufferedReader.readLine();
                if(type.equals("FIRSTNAME")) {
                    System.out.print("new first name:");
                    String firstname = bufferedReader.readLine();
                    this.studentService.update(idStudentToBeUpdated, "FIRST", firstname);
                    cont = false;
                } else if(type.equals("LASTNAME")) {
                    System.out.print("new last name:");
                    String lastname = bufferedReader.readLine();
                    this.studentService.update(idStudentToBeUpdated, "LAST", lastname);
                    cont = false;
                } else {
                    System.out.println("Input exception: Input a valid type! (FIRSTNAME|LASTNAME)");
                }
            } catch (ValidatorException exception) {
                System.out.println("Validator exception:" + exception.getMessage());
                cont = false;
            } catch (IOException exception) {
                System.out.println("Input Exception: " + exception.getMessage());
            }
        }
    }

    /**
     * Function for updatign a problem
     */
    public void updateProblem(){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        boolean cont = true;
        while (cont) {
            try{
                System.out.print("ID:");
                int id = Integer.parseInt(bufferedReader.readLine());
                System.out.println("You can update the Problems' description (DESCRIPTION) or the Problems' difficulty (DIFFICULTY)");
                System.out.print("TYPE:");
                String type = bufferedReader.readLine();
                if (type.equals("DESCRIPTION")) {
                    System.out.print("new description:");
                    String description = bufferedReader.readLine();
                    this.problemService.update(id, "DESCRIPTION", description);
                    cont = false;
                } else if (type.equals("DIFFICULTY")) {
                    System.out.print("new difficulty:");
                    String difficulty = bufferedReader.readLine();
                    this.problemService.update(id, "DIFFICULTY", difficulty);
                    cont = false;
                } else {
                    System.out.println("Input exception: Input a valid type! (DESCRIPTION|DIFFICULTY)");
                }
            } catch (ValidatorException exception) {
                System.out.println("Validator Exception: " + exception.getMessage());
                cont = false;
            } catch (IOException exception) {
                System.out.println("Input Exception: " + exception.getMessage());
            }
        }
    }

    /**
     * Function for adding a new student
     */
    public void addNewStudent(){
        try {
            Student newStudent = this.readStudentData();
            this.studentService.add(newStudent);
        } catch (ValidatorException exception){
            System.out.println("ValidatorException:" + exception.getMessage());
        } catch (IllegalArgumentException exception) {
            System.out.println("IllegalArgumentException:" + exception.getMessage());
        }
    }

    /**
     * Function for adding a new problem
     */
    public void addNewProblem(){
        try{
            Problem problem = this.readProblemData();
            this.problemService.add(problem);
        } catch (ValidatorException exception){
            System.out.println("ValidatorException: " + exception.getMessage());
        } catch (IllegalArgumentException exception) {
            System.out.println("IllegalArgumentException:" + exception.getMessage());
        }
    }

    /**
     * Function for removing a student, by reading its ID
     */
    public void removeStudent(){
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("ID: ");
            int id = scanner.nextInt();
            this.studentService.remove(id);
        } catch (IllegalArgumentException exception) {
            System.out.println("IllegalArgumentException:" + exception.getMessage());
        }
    }

    /**
     * Function for removing a problem, by reading its ID
     */
    public void removeProblem(){
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("ID : ");
            int id = scanner.nextInt();
            this.problemService.remove(id);
        } catch (IllegalArgumentException exception) {
            System.out.println("IllegalArgumentException:" + exception.getMessage());
        }
    }

    /**
     * Function that prints all the students on the screen
     */
    public void showAllStudents() {
        System.out.println("Students:");
        Iterable<Student> students = this.studentService.get();
        students.forEach(System.out::println);
    }

    /**
     * Function that prints all the problems on the screen
     */
    public void showAllProblems(){
        System.out.println("Lab Problems:");
        Iterable<Problem> problems = this.problemService.get();
        problems.forEach(System.out::println);
    }

    /**
     * Function that prints all the grades on the screen
     */
    public void showAllGrades(){
        System.out.println("Students, problems and grades:");
        System.out.println();
        Iterable<Grade> grades = this.studentService.getGrades();
        grades.forEach(
                grade -> {
                    int studentId = Integer.parseInt(grade.toString().split(",")[0]);
                    int problemId = Integer.parseInt(grade.toString().split(",")[1]);
                    int gradeToBeShown = Integer.parseInt(grade.toString().split(",")[2]);
                    try {
                        Student student = this.studentService.getById(studentId);
                        Problem problem = this.problemService.getById(problemId);
                        System.out.print("Student:");
                        System.out.println(student);
                        System.out.print("Problem:");
                        System.out.println(problem);
                        System.out.print("Grade:");
                        System.out.println(gradeToBeShown);
                        System.out.println();
                        System.out.println();
                    } catch (ValidatorException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    /**
     * Function that filters students, by reading the type of filtering
     * and the argument for the filter
     * Students can be filtered by firstname, lastname, problem and grade
     */
    public void filterStudents(){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        List<String> types = new ArrayList<>();
        types.add("FIRSTNAME");
        types.add("LASTNAME");
        types.add("PROBLEM");
        types.add("GRADE");
        try {
            //read the type
            System.out.println("Available filtering types: FIRSTNAME/LASTNAME/PROBLEM/GRADE");
            System.out.print("type:");
            String type = bufferedReader.readLine();
            if(types.contains(type)){
                //read the argument
                System.out.print("filtering argument: ");
                String argument = bufferedReader.readLine();
                Iterable<Student> students = this.studentService.filterService(argument, type);
                students.forEach(System.out::println);
            } else {
                System.out.println("ValidatorException: The type you introduced was not valid!");
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        } catch (ValidatorException exception) {
            System.out.println("ValidatorException: " + exception.getMessage());
        }
    }

    /**
     * Function the reads data for a student
     * @return a Student with the read data
     */
    public Student readStudentData() {
        System.out.println("Insert student data:");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("ID : ");
            int id = Integer.parseInt(bufferRead.readLine());// ...
            System.out.print("First Name : ");
            String firstName = bufferRead.readLine();
            System.out.print("Last Name : ");
            String lastName = bufferRead.readLine();

            Student student = new Student(firstName, lastName);
            student.setId(id);
            return student;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Function that reads data for a probem
     * @return a Problem with the read data
     */
    public Problem readProblemData(){
        System.out.println("Insert problem data:");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try{
            System.out.print("ID : ");
            int id = Integer.parseInt(bufferedReader.readLine());
            System.out.print("Description : ");
            String description = bufferedReader.readLine();
            System.out.print("Difficulty(easy/medium/hard): ");
            String difficulty = bufferedReader.readLine();
            Problem problem = new Problem(description, difficulty);
            problem.setId(id);
            return problem;
        } catch(IOException exception){
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Function that assigns a problem to a student,
     * by reading the ID's of the student and the problem
     */
    public void assignProblemToStudent(){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Student ID : ");
            int studentId = Integer.parseInt(bufferedReader.readLine());
            System.out.print("Problem ID : ");
            int problemId = Integer.parseInt(bufferedReader.readLine());
            this.studentService.assignProblem(studentId, problemId);
        } catch (IOException exception){
            exception.printStackTrace();
        } catch (ValidatorException exception){
            System.out.println("ValidatorException : " + exception.getMessage());
        } catch (IllegalArgumentException exception) {
            System.out.println("IllegalArgumentException:" + exception.getMessage());
        }
    }

    /**
     * Function that assigns a grade to a student for a given problem,
     * by reading the ID's of the student and the problem, and the grade
     */
    public void assignGradeToStudent(){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try{
            System.out.print("Student ID : ");
            int studentId = Integer.parseInt(bufferedReader.readLine());
            System.out.print("Problem ID : ");
            int problemId = Integer.parseInt(bufferedReader.readLine());
            System.out.print("Grade : ");
            int grade = Integer.parseInt(bufferedReader.readLine());
            this.studentService.assignGrade(studentId, problemId, grade);

        } catch (IOException exception){
            exception.printStackTrace();
        } catch (ValidatorException exception){
            System.out.println("ValidatorException: "+exception.getMessage());
        } catch (IllegalArgumentException exception) {
            System.out.println("IllegalArgumentException:" + exception.getMessage());
        }
    }
}
