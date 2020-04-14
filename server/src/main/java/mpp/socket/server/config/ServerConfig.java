package mpp.socket.server.config;

import mpp.socket.common.Domain.Grade;
import mpp.socket.common.Domain.Problem;
import mpp.socket.common.Domain.Student;
import mpp.socket.common.Domain.Validators.GradeValidator;
import mpp.socket.common.Domain.Validators.ProblemValidator;
import mpp.socket.common.Domain.Validators.StudentValidator;
import mpp.socket.common.Domain.Validators.Validator;
import mpp.socket.common.IServiceProblems;
import mpp.socket.common.IServiceStudents;
import mpp.socket.server.Repository.*;
import mpp.socket.server.Service.ProblemsService;
import mpp.socket.server.Service.StudentsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

@Configuration
public class ServerConfig {

    @Bean
    RmiServiceExporter rmiServiceExporterStudent() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServicePort(1099);
        rmiServiceExporter.setServiceName("StudentsService");
        rmiServiceExporter.setServiceInterface(IServiceStudents.class);
        rmiServiceExporter.setService(studentService());

        return rmiServiceExporter;
    }

    @Bean
    RmiServiceExporter rmiServiceExporterProblem() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServicePort(1099);
        rmiServiceExporter.setServiceName("ProblemsService");
        rmiServiceExporter.setServiceInterface(IServiceProblems.class);
        rmiServiceExporter.setService(problemService());

        return rmiServiceExporter;
    }

    @Bean
    IServiceStudents studentService() {
        return new StudentsService();
    }

    @Bean
    SortedRepository<Integer, Student> studentSortedRepository() {
        return new StudentDBRepository();
    }

    @Bean
    SortedRepository<Integer, Problem> problemSortedRepository() {
        return new ProblemDBRepository();
    }

    @Bean
    SortedRepository<Integer, Grade> gradeSortedRepository() {
        return new GradeDBRepository();
    }

    @Bean
    Validator<Student> studentValidator() {
        return new StudentValidator();
    }

    @Bean
    Validator<Problem> problemValidator(){
        return new ProblemValidator();
    }

    @Bean
    Validator<Grade> gradeValidator() {
        return new GradeValidator();
    }

    @Bean
    IServiceProblems problemService() { return new ProblemsService(); }

}
