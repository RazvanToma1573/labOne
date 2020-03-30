package mpp.socket.server;

import Domain.Grade;
import Domain.Problem;
import Domain.Student;
import Domain.Validators.GradeValidator;
import Domain.Validators.ProblemValidator;
import Domain.Validators.StudentValidator;
import Domain.Validators.Validator;
import Repository.GradeDBRepository;
import Repository.ProblemDBRepository;
import Repository.SortedRepository;
import Repository.StudentDBRepository;
import Service.ProblemsService;
import Service.StudentsService;
import mpp.socket.common.Message;
import mpp.socket.common.SocketService;
import mpp.socket.server.Service.ServiceServer;
import mpp.socket.server.TCP.TCPServer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ServerApp {
    public static void main(String[] args) {
        System.out.println("Server started...");
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            Validator<Student> studentValidator = new StudentValidator();
            SortedRepository<Integer, Student> studentRepository = new StudentDBRepository();

            Validator<Problem> problemValidator = new ProblemValidator();
            SortedRepository<Integer, Problem> problemRepository = new ProblemDBRepository();

            Validator<Grade> gradeValidator = new GradeValidator();
            SortedRepository<Integer, Grade> gradeRepository = new GradeDBRepository();

            ProblemsService problemService = new ProblemsService(problemRepository, problemValidator);
            StudentsService studentService = new StudentsService(studentRepository, gradeRepository, studentValidator, gradeValidator, problemService);

            SocketService socketService = new ServiceServer(executorService, studentService, problemService);
            TCPServer tcpServer = new TCPServer(executorService);

            tcpServer.addHandler("ClientRequest", (request) -> {
                String command = request.getHeader() + " " + request.getBody();
                Future<String> future = socketService.command(command);
                try{
                    String result = future.get();
                    return new Message("Done", result);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new Message("Error",e.getMessage());
                }
            });
            tcpServer.start();
            executorService.shutdown();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
