package mpp.socket.server.Service;

import Domain.Grade;
import Domain.Problem;
import Domain.Student;
import Service.ProblemsService;
import Service.StudentsService;
import com.sun.tools.javac.util.Pair;
import mpp.socket.common.SocketService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class ServiceServer implements SocketService {
    private ExecutorService executorService;
    private StudentsService studentsService;
    private ProblemsService problemsService;

    public ServiceServer(ExecutorService executorService, StudentsService studentsService, ProblemsService problemsService) {
        this.executorService = executorService;
        this.studentsService = studentsService;
        this.problemsService = problemsService;
    }

    @Override
    public Future<Object> command(String command) {
        return executorService.submit(() -> {
             int c = Integer.parseInt(command.split("|")[0]);
             String[] params = command.split("|")[1].split("/");
             Student student;
             Problem problem;
             int studentId, problemId, grade;
             List<Student> students = new ArrayList<>();
             List<Problem> problems = new ArrayList<>();
             List<Grade> grades = new ArrayList<>();
             List<Pair<Boolean, String>> criteria = new ArrayList<>();
             switch (c) {
                 case 1:
                     student = new Student(params[1], params[2]);
                     student.setId(Integer.parseInt(params[0]));
                     this.studentsService.add(student);
                     return student.toString() + "successfully added";
                 case 2:
                     studentId = Integer.parseInt(params[0]);
                     this.studentsService.remove(studentId);
                     return "Successfully removed";
                 case 3:
                     students.clear();
                     this.studentsService.get().forEach(students::add);
                     return students.stream().map(st -> st.toString())
                             .reduce("Students:\n",(a,b) -> a + "\n" + b);
                 case 4:
                     problem = new Problem(params[1], params[2]);
                     problem.setId(Integer.parseInt(params[0]));
                     this.problemsService.add(problem);
                     return problem.toString() + "successfully added";
                 case 5:
                     problemId = Integer.parseInt(params[0]);
                     this.problemsService.remove(problemId);
                     return "Successfully removed";
                 case 6:
                     problems.clear();
                     this.problemsService.get().forEach(problems::add);
                     return problems.stream().map(pr -> pr.toString())
                             .reduce("Problems",(a,b) -> a + "\n" + b);
                 case 7:
                    studentId = Integer.parseInt(params[0]);
                    problemId = Integer.parseInt(params[1]);
                    this.studentsService.assignProblem(studentId, problemId);
                    return "Successfully assigned";
                 case 8:
                    grades.clear();
                    this.studentsService.getGrades().forEach(grades::add);
                    return grades.stream().map(gr -> {
                        try {
                            Student stud = this.studentsService.getById(gr.getStudent());
                            Problem prob = this.problemsService.getById(gr.getProblem());
                            return stud + "\n" + prob + "\n" + gr.getActualGrade() + "\n";
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return "";
                    });
                 case 9:
                     studentId = Integer.parseInt(params[0]);
                     problemId = Integer.parseInt(params[1]);
                     grade = Integer.parseInt(params[2]);
                     this.studentsService.assignGrade(studentId, problemId, grade);
                     return "Successfully graded";
                 case 10:
                     students.clear();
                     this.studentsService.filterService(params[0], params[1]).forEach(students::add);
                     return students.stream().map(st -> st.toString())
                             .reduce("Filtered Students:\n", (a,b) -> a + "\n" + b);
                 case 11:
                     switch (Integer.parseInt(params[0])) {
                         case 1:
                             return this.studentsService.getMaxAssignedProblem().toString();
                         case 2:
                             return this.studentsService.getStudentWithMaxGrade().toString();
                         case 3:
                             return this.studentsService.getMostAssignedStudent().toString();
                         case 4:
                             return this.studentsService.getProblemHighestAverage().toString();
                         case 5:
                             return this.studentsService.getStudentHighestAverageHard().toString();
                         default:
                             return "Invalid report index";
                     }
                 case 12:
                     this.studentsService.update(Integer.parseInt(params[0]), params[1], params[2]);
                     return "Successfully updated student";
                 case 13:
                     this.problemsService.update(Integer.parseInt(params[0]), params[1], params[2]);
                     return "Successfully updated problems";
                 case 14:
                    students.clear();
                    criteria.clear();
                    criteria = Arrays.stream(params).map(str -> str.split("-"))
                            .map(str -> new Pair<>(str[0].equals("desc"), str[1]))
                            .collect(Collectors.toList());
                    this.studentsService.getSorted(criteria).forEach(students::add);
                    return students.stream().map(st -> st.toString())
                            .reduce("Students:\n", (a,b) -> a + "\n" + b);
                 case 15:
                    problems.clear();
                    criteria.clear();
                    criteria = Arrays.stream(params).map(str -> str.split("-"))
                             .map(str -> new Pair<>(str[0].equals("desc"), str[1]))
                             .collect(Collectors.toList());
                    this.problemsService.getSorted(criteria).forEach(problems::add);
                    return problems.stream().map(pr -> pr.toString())
                            .reduce("Problems:\n", (a,b) -> a + "\n" + b);
                 case 16:
                     grades.clear();
                     criteria = Arrays.stream(params).map(str -> str.split("-"))
                             .map(str -> new Pair<>(str[0].equals("desc"), str[1]))
                             .collect(Collectors.toList());
                     this.studentsService.getGradesSorted(criteria).forEach(grades::add);
                     return grades.stream().map(gr -> {
                         try {
                             Student stud = this.studentsService.getById(gr.getStudent());
                             Problem prob = this.problemsService.getById(gr.getProblem());
                             return stud + "\n" + prob + "\n" + gr.getActualGrade() + "\n";
                         } catch (Exception e) {
                             e.printStackTrace();
                         }
                         return "";
                     });
                 default:
                     return "Invalid command";
             }
        });
    }
}
