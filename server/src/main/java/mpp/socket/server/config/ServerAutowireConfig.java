package mpp.socket.server.config;




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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ServerAutowireConfig {

    @Bean
    RmiServiceExporter rmiServiceExporter() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("StudentsService");
        rmiServiceExporter.setServiceInterface(SocketService.class);
        rmiServiceExporter.setService(serviceServer());
        return rmiServiceExporter;
    }

    @Bean
    StudentsService studentsService() {
        return new StudentsService(sortedRepositoryStudent(), sortedRepositoryGrade(), studentValidator(), gradeValidator(), problemsService());
    }

    @Bean
    ProblemsService problemsService() {
        return new ProblemsService(sortedRepositoryProblem(), problemValidator());
    }

    @Bean
    SortedRepository<Integer, Student> sortedRepositoryStudent() {
        return new StudentDBRepository();
    }

    @Bean
    SortedRepository<Integer, Grade> sortedRepositoryGrade() {
        return new GradeDBRepository();
    }

    @Bean
    SortedRepository<Integer, Problem> sortedRepositoryProblem() {
        return new ProblemDBRepository();
    }

    @Bean
    Validator<Student> studentValidator() {
        return new StudentValidator();
    }

    @Bean
    Validator<Problem> problemValidator() {
        return new ProblemValidator();
    }

    @Bean
    Validator<Grade> gradeValidator() {
        return new GradeValidator();
    }

    @Bean
    SocketService serviceServer() {
        return new ServiceServer(executorService(), studentsService(), problemsService());
    }

    @Bean
    ExecutorService executorService() {
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

}
