package mpp.socket.client.Ui;

import mpp.socket.common.SocketService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Console {

    private ConcurrentHashMap<String, Future<String>> results;

    private SocketService socketService;

    public Console(SocketService socketService) {
        this.socketService = socketService;
        results = new ConcurrentHashMap<>();
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
            System.out.print("Your choice : ");
            try {
                choice = scanner.nextInt();
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
                checkResults();
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
            System.out.print("ID : ");
            int id = Integer.parseInt(bufferRead.readLine());// ...
            System.out.print("First Name : ");
            String firstName = bufferRead.readLine();
            System.out.print("Last Name : ");
            String lastName = bufferRead.readLine();

            String parameters = id + "/" + firstName + "/" + lastName;
            message += parameters;

            Future<String> commandResult = socketService.command(message);

            this.results.put(message, commandResult);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void removeStudent() {
        String message = "2 ";
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("ID: ");
            int id = scanner.nextInt();

            String parameters = Integer.toString(id);
            message += parameters;

            Future<String> commandResult = socketService.command(message);

            this.results.put(message, commandResult);
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
                System.out.print("ID:");
                int idStudentToBeUpdated =  Integer.parseInt(bufferedReader.readLine());
                System.out.println("You can update the Students' first name (FIRSTNAME) or the Students' last name (LASTNAME)");
                System.out.print("TYPE:");
                String type = bufferedReader.readLine();
                if(type.equals("FIRSTNAME")) {
                    System.out.print("new first name:");
                    String firstname = bufferedReader.readLine();

                    String parameters = idStudentToBeUpdated + "/" + "FIRST" + "/" + firstname;
                    message += parameters;

                    Future<String> commandResult = socketService.command(message);
                    this.results.put(message, commandResult);

                    cont = false;
                } else if(type.equals("LASTNAME")) {
                    System.out.print("new last name:");
                    String lastname = bufferedReader.readLine();

                    String parameters = Integer.toString(idStudentToBeUpdated) + "/" + "LAST" + "/" + lastname;
                    message += parameters;

                    Future<String> commandResult = socketService.command(message);
                    this.results.put(message, commandResult);

                    cont = false;
                } else {
                    System.out.println("Input exception: Input a valid type! (FIRSTNAME|LASTNAME)");
                }
            } catch (IOException exception) {
                System.out.println("Input Exception: " + exception.getMessage());
            }
        }
    }

    public void showAllStudents() {
        String message = "3 ";

        Future<String> commandResult = socketService.command(message);
        this.results.put(message, commandResult);
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
            System.out.print("type:");
            String type = bufferedReader.readLine();
            if(types.contains(type)){
                //read the argument
                System.out.print("filtering argument: ");
                String argument = bufferedReader.readLine();

                String parameters = argument + "/" + type;
                message += parameters;

                Future<String> commandResult = socketService.command(message);
                this.results.put(message, commandResult);
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

            Future<String> commandResult = socketService.command(message);

            this.results.put(message, commandResult);
        } catch (IOException | NullPointerException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void addNewProblem() {
        String message = "4 ";

        System.out.println("Insert problem data:");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try{
            System.out.print("ID : ");
            int id = Integer.parseInt(bufferedReader.readLine());
            System.out.print("Description : ");
            String description = bufferedReader.readLine();
            System.out.print("Difficulty(easy/medium/hard): ");
            String difficulty = bufferedReader.readLine();

            String parameters = id + "/" + description + "/" + difficulty;
            message += parameters;

            Future<String> commandResult = socketService.command(message);

            this.results.put(message, commandResult);
        } catch(IOException exception){
            exception.printStackTrace();
        }
    }

    public void removeProblem() {
        String message = "5 ";

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("ID : ");
            int id = scanner.nextInt();

            String parameters = Integer.toString(id);
            message += parameters;

            Future<String> commandResult = socketService.command(message);

            this.results.put(message, commandResult);
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
                System.out.print("ID:");
                int id = Integer.parseInt(bufferedReader.readLine());
                System.out.println("You can update the Problems' description (DESCRIPTION) or the Problems' difficulty (DIFFICULTY)");
                System.out.print("TYPE:");
                String type = bufferedReader.readLine();
                if (type.equals("DESCRIPTION")) {
                    System.out.print("new description:");
                    String description = bufferedReader.readLine();

                    String parameters = id + "/" + "DESCRIPTION" + "/" + description;
                    message += parameters;

                    Future<String> commandResult = socketService.command(message);

                    this.results.put(message, commandResult);
                    cont = false;
                } else if (type.equals("DIFFICULTY")) {
                    System.out.print("new difficulty:");
                    String difficulty = bufferedReader.readLine();

                    String parameters = id + "/" + "DIFFICULTY" + "/" + difficulty;
                    message += parameters;

                    Future<String> commandResult = socketService.command(message);

                    this.results.put(message, commandResult);
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
        String message = "6 ";

        Future<String> commandResult = socketService.command(message);

        this.results.put(message, commandResult);
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

            Future<String> commandResult = socketService.command(message);

            this.results.put(message, commandResult);
        } catch (IOException | NullPointerException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void assignProblemToStudent() {
        String message = "7 ";

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Student ID : ");
            int studentId = Integer.parseInt(bufferedReader.readLine());
            System.out.print("Problem ID : ");
            int problemId = Integer.parseInt(bufferedReader.readLine());

            String parameters = studentId + "/" + problemId;
            message += parameters;

            Future<String> commandResult = socketService.command(message);

            this.results.put(message, commandResult);
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
            System.out.print("Student ID : ");
            int studentId = Integer.parseInt(bufferedReader.readLine());
            System.out.print("Problem ID : ");
            int problemId = Integer.parseInt(bufferedReader.readLine());
            System.out.print("Grade : ");
            int grade = Integer.parseInt(bufferedReader.readLine());

            String parameters = studentId + "/" + problemId + "/" + grade;
            message += parameters;

            Future<String> commandResult = socketService.command(message);

            this.results.put(message, commandResult);
        } catch (IOException exception){
            exception.printStackTrace();
        } catch (IllegalArgumentException exception) {
            System.out.println("IllegalArgumentException:" + exception.getMessage());
        }
    }

    public void showAllGrades() {
        String message = "8 ";

        Future<String> commandResult = socketService.command(message);

        this.results.put(message, commandResult);
    }

    public void showGradesSorted() {
        String message = "16 ";

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try{
            System.out.print("Student ID : ");
            int studentId = Integer.parseInt(bufferedReader.readLine());
            System.out.print("Problem ID : ");
            int problemId = Integer.parseInt(bufferedReader.readLine());
            System.out.print("Grade : ");
            int grade = Integer.parseInt(bufferedReader.readLine());

            String parameters = studentId + "/" + problemId + "/" + grade;
            message += parameters;

            Future<String> commandResult = socketService.command(message);

            this.results.put(message, commandResult);
        } catch (IOException exception){
            exception.printStackTrace();
        } catch (IllegalArgumentException exception) {
            System.out.println("IllegalArgumentException:" + exception.getMessage());
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

        Future<String> commandResult = socketService.command(message);
        this.results.put(message, commandResult);
    }

    public void checkResults() {
        results.forEach((key, value) -> {
            if (value.isDone()) {
                System.out.println("Command: " + key);
                System.out.println("Result: ");
                try {
                    System.out.println(value.get());
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }
}
