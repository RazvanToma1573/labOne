package mpp.socket.client.Ui;

import com.sun.tools.javac.util.Pair;
import mpp.socket.common.Domain.Problem;
import mpp.socket.common.Domain.Student;
import mpp.socket.common.Domain.Validators.ValidatorException;
import mpp.socket.common.IServiceProblems;
import mpp.socket.common.IServiceStudents;
import mpp.socket.common.SocketService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Timer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class Console {

    private ConcurrentHashMap<String, String> results;
    private IServiceProblems problemsService;
    private IServiceStudents studentsService;
    AnnotationConfigApplicationContext context;

    public Console(AnnotationConfigApplicationContext context) {
        this.studentsService = context.getBean(IServiceStudents.class);
        this.problemsService = context.getBean(IServiceProblems.class);
        this.context = context;
    }

    /*
    private void runAsync(String m) {
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            SocketService service = context.getBean(SocketService.class);
            results.put(m, service.command(m));
        });
    }
    */


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
        System.out.println("\t\t 14 - show all students, sorted ");
        System.out.println("\t\t 15 - show all problems, sorted ");
        System.out.println("\t\t 16 - show all grades, sorted ");
    }

    public void runConsole() {
        System.out.println("Hello!");
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (choice != 0) {
            this.printTheMenu();

            System.out.println();
            System.out.println("-------------------------------------------");
            System.out.println();
            try {
                //MyTimer timer = new MyTimer(5*1000, this.results);
                //timer.start();
                choice = scanner.nextInt();
                //timer.stop();
                if (choice == 1) {
                    this.addNewStudent();
                } else if (choice == 2) {
                    this.removeStudent();
                } else if (choice == 3) {
                    this.showAllStudents();
                } else if (choice == 4) {
                    this.addNewProblem();
                } else if (choice == 5) {
                    this.removeProblem();
                } else if (choice == 6) {
                    this.showAllProblems();
                } else if (choice == 7) {
                    this.assignProblemToStudent();
                } else if (choice == 8) {
                    this.showAllGrades();
                } else if (choice == 9) {
                    this.assignGradeToStudent();
                } else if (choice == 10) {
                    this.filterStudents();
                } else if(choice == 11) {
                    this.reports();
                } else if (choice == 12) {
                    this.updateStudent();
                } else if (choice == 13) {
                    this.updateProblem();
                } else if (choice == 14) {
                    this.showStudentsSorted();
                } else if (choice == 15) {
                    this.showProblemsSorted();
                } else if (choice == 16) {
                    this.showGradesSorted();
                } else if (choice == 0) {
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

    public void addNewStudent() {
        String message = "1 ";

        System.out.println("Insert student data:");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("ID : ");
            int id = Integer.parseInt(bufferRead.readLine());// ...
            System.out.println("First Name : ");
            String firstName = bufferRead.readLine();
            System.out.println("Last Name : ");
            String lastName = bufferRead.readLine();

            Student student = new Student(firstName, lastName);
            student.setId(id);
            CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
                //this.studentsService = context.getBean(IServiceStudents.class);
                try {
                    studentsService.add(student);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void removeStudent() {
        String message = "2 ";
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("ID: ");
            int id = scanner.nextInt();

            CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
               //this.studentsService = context.getBean(IServiceStudents.class);
               studentsService.remove(id);
            });
        } catch (IllegalArgumentException exception) {
            System.out.println("IllegalArgumentException:" + exception.getMessage());
        }
    }

    public void updateStudent() {
        String message = "12 ";

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        boolean cont = true;
        while(cont) {
            try {
                System.out.println("ID:");
                int idStudentToBeUpdated =  Integer.parseInt(bufferedReader.readLine());
                System.out.println("You can update the Students' first name (FIRSTNAME) or the Students' last name (LASTNAME)");
                System.out.println("TYPE:");
                String type = bufferedReader.readLine();
                if(type.equals("FIRSTNAME")) {
                    System.out.println("new first name:");
                    String firstname = bufferedReader.readLine();

                    CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
                        //this.studentsService = context.getBean(IServiceStudents.class);
                        try {
                            studentsService.update(idStudentToBeUpdated, "FIRST", firstname);
                        } catch (ValidatorException e) {
                            e.printStackTrace();
                        }
                    });
                } else if(type.equals("LASTNAME")) {
                    System.out.println("new last name:");
                    String lastname = bufferedReader.readLine();

                    CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
                        //this.studentsService = context.getBean(IServiceStudents.class);
                        try {
                            studentsService.update(idStudentToBeUpdated, "LAST", lastname);
                        } catch (ValidatorException e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    System.out.println("Input exception: Input a valid type! (FIRSTNAME|LASTNAME)");
                }
            } catch (IOException exception) {
                System.out.println("Input Exception: " + exception.getMessage());
            }
        }
    }

    public void showAllStudents() {
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            Iterable<Student> students = studentsService.get();
            students.forEach(System.out::println);
        });
    }

    public void filterStudents() {
        String message = "10 ";

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        List<String> types = new ArrayList<>();
        types.add("FIRSTNAME");
        types.add("LASTNAME");
        types.add("PROBLEM");
        types.add("GRADE");
        try {
            //read the type
            System.out.println("Available filtering types: FIRSTNAME/LASTNAME/PROBLEM/GRADE");
            System.out.println("type:");
            String type = bufferedReader.readLine();
            if(types.contains(type)){
                //read the argument
                System.out.println("filtering argument: ");
                String argument = bufferedReader.readLine();

                String parameters = argument + "/" + type;
                message += parameters;

                final String m = message;

                //this.runAsync(m);
            } else {
                System.out.println("ValidatorException: The type you introduced was not valid!");
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void showStudentsSorted() {
        String message = "14 ";

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Give a criteria in the form: asc/desc <id /  firstName / lastName>");
        try {
            String line = bufferedReader.readLine();
            StringBuilder parameters = new StringBuilder(line.split(" ")[0] + "-" + line.split(" ")[1]);
            while(true){
                System.out.println("Any other criteria? (enter no if none)");
                line = bufferedReader.readLine();
                if(line.equals("no"))
                    break;
                parameters.append("/").append(line.split(" ")[0]).append("-").append(line.split(" ")[1]);
            }
            message += parameters;

            final String m = message;

            //this.runAsync(m);
        } catch (IOException | NullPointerException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void addNewProblem() {
        String message = "4 ";

        System.out.println("Insert problem data:");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try{
            System.out.println("ID : ");
            int id = Integer.parseInt(bufferedReader.readLine());
            System.out.println("Description : ");
            String description = bufferedReader.readLine();
            System.out.println("Difficulty(easy/medium/hard): ");
            String difficulty = bufferedReader.readLine();

            Problem problem = new Problem(description, difficulty);
            problem.setId(id);

            CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
                //this.problemsService = context.getBean(IServiceProblems.class);
                try {
                    problemsService.add(problem);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch(IOException exception){
            exception.printStackTrace();
        }
    }

    public void removeProblem() {
        String message = "5 ";

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("ID : ");
            int id = scanner.nextInt();

            CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
                //this.problemsService = context.getBean(IServiceProblems.class);
                problemsService.remove(id);
            });

        } catch (IllegalArgumentException exception) {
            System.out.println("IllegalArgumentException:" + exception.getMessage());
        }
    }

    public void updateProblem() {
        String message = "13 ";

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        boolean cont = true;
        while (cont) {
            try{
                System.out.println("ID:");
                int id = Integer.parseInt(bufferedReader.readLine());
                System.out.println("You can update the Problems' description (DESCRIPTION) or the Problems' difficulty (DIFFICULTY)");
                System.out.println("TYPE:");
                String type = bufferedReader.readLine();
                if (type.equals("DESCRIPTION")) {
                    System.out.println("new description:");
                    String description = bufferedReader.readLine();

                    String parameters = id + "/" + "DESCRIPTION" + "/" + description;
                    message += parameters;

                    final String m = message;

                    //this.runAsync(m);
                    cont = false;
                } else if (type.equals("DIFFICULTY")) {
                    System.out.println("new difficulty:");
                    String difficulty = bufferedReader.readLine();

                    String parameters = id + "/" + "DIFFICULTY" + "/" + difficulty;
                    message += parameters;

                    final String m = message;

                    //this.runAsync(m);
                    cont = false;
                } else {
                    System.out.println("Input exception: Input a valid type! (DESCRIPTION|DIFFICULTY)");
                }
            } catch (IOException exception) {
                System.out.println("Input Exception: " + exception.getMessage());
            }
        }
    }

    public void showAllProblems() {
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            //this.problemsService = context.getBean(IServiceProblems.class);
            problemsService.get().forEach(System.out::println);
        });
    }

    public void showProblemsSorted() {
        String message = "15 ";

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Give a criteria in the form: asc/desc <id / description / difficulty>");
        try {
            String line = bufferedReader.readLine();
            StringBuilder parameters = new StringBuilder(line.split(" ")[0] + "-" + line.split(" ")[1]);
            while(true){
                System.out.println("Any other criteria? (enter no if none)");
                line = bufferedReader.readLine();
                if(line.equals("no"))
                    break;
                parameters.append("/").append(line.split(" ")[0]).append("-").append(line.split(" ")[1]);
            }

            message += parameters;

            final String m = message;

            //this.runAsync(m);
        } catch (IOException | NullPointerException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void assignProblemToStudent() {
        String message = "7 ";

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Student ID : ");
            int studentId = Integer.parseInt(bufferedReader.readLine());
            System.out.println("Problem ID : ");
            int problemId = Integer.parseInt(bufferedReader.readLine());

            CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
                //this.studentsService = context.getBean(IServiceStudents.class);
                try {
                    studentsService.assignProblem(studentId, problemId);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException exception){
            exception.printStackTrace();
        } catch (IllegalArgumentException exception) {
            System.out.println("IllegalArgumentException:" + exception.getMessage());
        }
    }

    public void assignGradeToStudent() {
        String message = "9 ";

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try{
            System.out.println("Student ID : ");
            int studentId = Integer.parseInt(bufferedReader.readLine());
            System.out.println("Problem ID : ");
            int problemId = Integer.parseInt(bufferedReader.readLine());
            System.out.println("Grade : ");
            int grade = Integer.parseInt(bufferedReader.readLine());

            CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
                //this.studentsService = context.getBean(IServiceStudents.class);
                try {
                    studentsService.assignGrade(studentId, problemId, grade);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException exception){
            exception.printStackTrace();
        } catch (IllegalArgumentException exception) {
            System.out.println("IllegalArgumentException:" + exception.getMessage());
        }
    }

    public void showAllGrades() {
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            //this.studentsService = context.getBean(IServiceStudents.class);
            studentsService.getGrades().forEach(System.out::println);
        });
    }

    public void showGradesSorted() {
        String message = "16 ";

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        List<Pair<Boolean, String>> criteria = new ArrayList<>();
        System.out.println("Give a criteria in the form: asc/desc <studentId / problemId / actualGrade>");
        try {
            String line = bufferedReader.readLine();
            StringBuilder params = new StringBuilder("");
            params.append(line.split(" ")[0].equals("desc")).append("-").append(line.split(" ")[1]);
            while(true){
                System.out.println("Any other criteria? (enter no if none)");
                line = bufferedReader.readLine();
                if(line.equals("no"))
                    break;
                params.append("/").append(line.split(" ")[0].equals("desc")).append("-").append(line.split(" ")[1]);
            }

            message += params;

            final String m = message;

            //this.runAsync(m);
        } catch (IOException | NullPointerException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void reports() {
        String message = "11 ";

        Scanner scanner = new Scanner(System.in);
        System.out.println("Select one report from the list below:");
        System.out.println("\t 1. Problem that was assigned most times");
        System.out.println("\t 2. Student with the highest average grade");
        System.out.println("\t 3. Student with the most assigned problems");
        System.out.println("\t 4. Problem with the highest average grade");
        System.out.println("\t 5. Student with highest average at hard problems");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                message += 1;
                break;
            case 2:
                message += 2;
                break;
            case 3:
                message += 3;
            case 4:
                message += 4;
                break;
            case 5:
                message += 5;
                break;
            default:
                System.out.println("Invalid command");
        }

        final String m = message;

        //this.runAsync(m);
    }
    /*
    public void checkResults() {
        results.forEach((key, value) -> {
            if (value.isDone()) {
                System.out.println("Command: " + key);
                System.out.println("Result: ");
                try {
                    String resultToBeParsed = value.get();

                    String[] parsed = resultToBeParsed.split(";");
                    System.out.println();

                    String[] parsedKey = key.split(" ");

                    if (parsedKey[0].equals("16") || parsedKey[0].equals("8")){
                        for (int i = 0; i < parsed.length; i++) {
                            String[] parsedAgain = parsed[i].split("-");
                            for (int j = 0; j < parsedAgain.length; j++) {
                                System.out.println(parsedAgain[j]);
                            }
                        }
                    } else {
                        for (int i = 0; i < parsed.length; i++) {
                            System.out.println(parsed[i]);
                        }
                    }
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println(e.getMessage());
                }
                this.results.remove(key);
            }
        });
    }

     */
}
