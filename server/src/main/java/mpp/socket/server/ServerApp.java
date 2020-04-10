package mpp.socket.server;


import mpp.socket.common.Message;
import mpp.socket.common.SocketService;
import mpp.socket.server.Domain.Grade;
import mpp.socket.server.Domain.Problem;
import mpp.socket.server.Domain.Student;
import mpp.socket.server.Domain.Validators.GradeValidator;
import mpp.socket.server.Domain.Validators.ProblemValidator;
import mpp.socket.server.Domain.Validators.StudentValidator;
import mpp.socket.server.Domain.Validators.Validator;
import mpp.socket.server.Repository.GradeDBRepository;
import mpp.socket.server.Repository.ProblemDBRepository;
import mpp.socket.server.Repository.SortedRepository;
import mpp.socket.server.Repository.StudentDBRepository;
import mpp.socket.server.Service.ProblemsService;
import mpp.socket.server.Service.ServiceServer;
import mpp.socket.server.Service.StudentsService;
import mpp.socket.server.TCP.TCPServer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ServerApp {
    public static void main(String[] args) {
        System.out.println("Server started...");
        try {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("mpp.socket.server.config");
            SocketService socketService = (SocketService) context.getBean("serviceServer");
            TCPServer tcpServer = (TCPServer) context.getBean("tcpServer");

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
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
