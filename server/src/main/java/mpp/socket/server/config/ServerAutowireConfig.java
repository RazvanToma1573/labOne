package mpp.socket.server.config;




import mpp.socket.common.IServiceProblems;
import mpp.socket.common.IServiceStudents;
import mpp.socket.common.SocketService;
import mpp.socket.common.Domain.Grade;
import mpp.socket.common.Domain.Problem;
import mpp.socket.common.Domain.Student;
import mpp.socket.common.Domain.Validators.GradeValidator;
import mpp.socket.common.Domain.Validators.ProblemValidator;
import mpp.socket.common.Domain.Validators.StudentValidator;
import mpp.socket.common.Domain.Validators.Validator;
import mpp.socket.common.Repository.GradeDBRepository;
import mpp.socket.common.Repository.ProblemDBRepository;
import mpp.socket.common.Repository.SortedRepository;
import mpp.socket.common.Repository.StudentDBRepository;
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
    RmiServiceExporter rmiServiceExporterStudent() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("StudentsService");
        rmiServiceExporter.setServiceInterface(IServiceStudents.class);
        rmiServiceExporter.setService(studentsService());
        return rmiServiceExporter;
    }

    @Bean
    RmiServiceExporter rmiServiceExporterProblem() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("ProblemsService");
        rmiServiceExporter.setServiceInterface(IServiceProblems.class);
        rmiServiceExporter.setService(problemsService());
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

    /*
    @Bean
    SocketService serviceServer() {
        return new ServiceServer(executorService(), studentsService(), problemsService());
    }

    @Bean
    ExecutorService executorService() {
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }
    */


}
