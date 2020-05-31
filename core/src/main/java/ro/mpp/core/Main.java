package ro.mpp.core;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.mpp.core.Domain.Grade;
import ro.mpp.core.Domain.Problem;
import ro.mpp.core.Domain.Student;
import ro.mpp.core.Service.IProblemService;
import ro.mpp.core.Service.IStudentService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("ro.mpp.core.config");

        IStudentService studentService = context.getBean(IStudentService.class);
        IProblemService problemService = context.getBean(IProblemService.class);

        Student student = Student.builder()
                .firstName("Umanschii")
                .lastName("Ianec")
                .build();

        Problem problem = Problem.builder()
                .description("MPP")
                .difficulty("hard")
                .build();

        Grade grade = Grade.builder()
                .actualGrade(10)
                .student(student)
                .problem(problem)
                .build();

        problemService.findAll().stream().forEach(System.out::println);

        ;

        //studentService.remove(94);
        //problemService.remove(95);

        System.out.println("Done");
    }
}
