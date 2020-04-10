package mpp.socket.server.Service;

import com.sun.tools.javac.util.Pair;
import mpp.socket.common.SocketService;
import mpp.socket.server.Domain.Grade;
import mpp.socket.server.Domain.Problem;
import mpp.socket.server.Domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Component
public class ServiceServer implements SocketService {
    private ExecutorService executorService;
    private StudentsService studentsService;
    private ProblemsService problemsService;

    @Autowired
    public ServiceServer(ExecutorService executorService, StudentsService studentsService, ProblemsService problemsService) {
        this.executorService = executorService;
        this.studentsService = studentsService;
        this.problemsService = problemsService;
    }

    @Override
    public String command(String command)  {
        try {
            int c = Integer.parseInt(command.split(" ")[0]);
            String[] params;
            if(command.split(" ").length > 1) {
                params = command.split(" ")[1].split("/");
            }
            else {
                params = new String[1];
            }
            Student student;
            Problem problem;
            int studentId, problemId, grade;
            List<Student> students = new ArrayList<>();
            List<Problem> problems = new ArrayList<>();
            List<Grade> grades = new ArrayList<>();
            List<Pair<Boolean, String>> criteria = new ArrayList<>();
            String result;
            switch (c) {
                case 1:
                    student = new Student(params[1], params[2]);
                    student.setId(Integer.parseInt(params[0]));
                    this.studentsService.add(student);
                    result = student.toString() + "successfully added";
                    break;
                case 2:
                    studentId = Integer.parseInt(params[0]);
                    this.studentsService.remove(studentId);
                    result = "Successfully removed";
                    break;
                case 3:
                    students.clear();
                    this.studentsService.get().forEach(students::add);
                    result = students.stream().map(st -> st.toString())
                            .reduce("Students:",(a,b) -> a + ";" + b);
                    break;
                case 4:
                    problem = new Problem(params[1], params[2]);
                    problem.setId(Integer.parseInt(params[0]));
                    this.problemsService.add(problem);
                    result = problem.toString() + "successfully added";
                    break;
                case 5:
                    problemId = Integer.parseInt(params[0]);
                    this.problemsService.remove(problemId);
                    result = "Successfully removed";
                    break;
                case 6:
                    problems.clear();
                    this.problemsService.get().forEach(problems::add);
                    result = problems.stream().map(pr -> pr.toString())
                            .reduce("Problems:",(a,b) -> a + ";" + b);
                    break;
                case 7:
                    studentId = Integer.parseInt(params[0]);
                    problemId = Integer.parseInt(params[1]);
                    this.studentsService.assignProblem(studentId, problemId);
                    result = "Successfully assigned";
                    break;
                case 8:
                    grades.clear();
                    this.studentsService.getGrades().forEach(grades::add);
                    result = grades.stream().map(gr -> {
                        try {
                            Student stud = this.studentsService.getById(gr.getStudent());
                            Problem prob = this.problemsService.getById(gr.getProblem());
                            return stud + "-" + prob + "-" + gr.getActualGrade() + ";";
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return "";
                    }).reduce("Grades:;", (a,b) -> a+b);
                    break;
                case 9:
                    studentId = Integer.parseInt(params[0]);
                    problemId = Integer.parseInt(params[1]);
                    grade = Integer.parseInt(params[2]);
                    this.studentsService.assignGrade(studentId, problemId, grade);
                    result =  "Successfully graded";
                    break;
                case 10:
                    students.clear();
                    this.studentsService.filterService(params[0], params[1]).forEach(students::add);
                    result =  students.stream().map(st -> st.toString())
                            .reduce("Filtered Students:", (a,b) -> a + ";" + b);
                    break;
                case 11:
                    switch (Integer.parseInt(params[0])) {
                        case 1:
                            result = this.studentsService.getMaxAssignedProblem().toString();
                            break;
                        case 2:
                            result = this.studentsService.getStudentWithMaxGrade().toString();
                            break;
                        case 3:
                            result = this.studentsService.getMostAssignedStudent().toString();
                            break;
                        case 4:
                            result = this.studentsService.getProblemHighestAverage().toString();
                            break;
                        case 5:
                            result = this.studentsService.getStudentHighestAverageHard().toString();
                            break;
                        default:
                            result =  "Invalid report index";
                            break;
                    }
                case 12:
                    this.studentsService.update(Integer.parseInt(params[0]), params[1], params[2]);
                    result =  "Successfully updated student";
                    break;
                case 13:
                    this.problemsService.update(Integer.parseInt(params[0]), params[1], params[2]);
                    result = "Successfully updated problems";
                    break;
                case 14:
                    students.clear();
                    criteria.clear();
                    criteria = Arrays.stream(params).map(str -> str.split("-"))
                            .map(str -> new Pair<>(str[0].equals("desc"), str[1]))
                            .collect(Collectors.toList());
                    this.studentsService.getSorted(criteria).forEach(students::add);
                    result = students.stream().map(st -> st.toString())
                            .reduce("Students:", (a,b) -> a + ";" + b);
                    break;
                case 15:
                    problems.clear();
                    criteria.clear();
                    criteria = Arrays.stream(params).map(str -> str.split("-"))
                            .map(str -> new Pair<>(str[0].equals("desc"), str[1]))
                            .collect(Collectors.toList());
                    this.problemsService.getSorted(criteria).forEach(problems::add);
                    result = problems.stream().map(pr -> pr.toString())
                            .reduce("Problems:", (a,b) -> a + ";" + b);
                    break;
                case 16:
                    grades.clear();
                    criteria = Arrays.stream(params).map(str -> str.split("-"))
                            .map(str -> new Pair<>(str[0].equals("desc"), str[1]))
                            .collect(Collectors.toList());
                    this.studentsService.getGradesSorted(criteria).forEach(grades::add);
                    result = grades.stream().map(gr -> {
                        try {
                            Student stud = this.studentsService.getById(gr.getStudent());
                            Problem prob = this.problemsService.getById(gr.getProblem());
                            return stud + "-" + prob + "-" + gr.getActualGrade() + ";";
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return "";
                    }).reduce("Grades:;", (a,b) -> a+b);
                    break;
                default:
                    result = "Invalid command";
                    break;
            }
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
