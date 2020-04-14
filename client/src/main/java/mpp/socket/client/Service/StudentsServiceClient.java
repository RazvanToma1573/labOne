package mpp.socket.client.Service;

import com.sun.tools.javac.util.Pair;
import mpp.socket.common.Domain.Grade;
import mpp.socket.common.Domain.Problem;
import mpp.socket.common.Domain.Student;
import mpp.socket.common.Domain.Validators.ValidatorException;
import mpp.socket.common.IServiceStudents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class StudentsServiceClient implements IServiceStudents {
    @Autowired
    private IServiceStudents service;

    @Override
    public void add(Student newStudent) throws ValidatorException, IllegalArgumentException {
        service.add(newStudent);
    }

    @Override
    public void remove(int id) throws IllegalArgumentException {
        service.remove(id);
    }

    @Override
    public void assignProblem(int studentId, int problemId) throws ValidatorException, IllegalArgumentException {
        service.assignProblem(studentId, problemId);
    }

    @Override
    public Iterable<Student> get() {
        return service.get();
    }

    @Override
    public Iterable<Grade> getGrades() {
        return service.getGrades();
    }

    @Override
    public Student getById(int id) throws ValidatorException, IllegalArgumentException {
        return service.getById(id);
    }

    @Override
    public void assignGrade(int studentId, int problemId, int grade) throws ValidatorException, IllegalArgumentException {
        service.assignGrade(studentId, problemId, grade);
    }

    @Override
    public Set<Student> filterService(String argument, String type) throws ValidatorException {
        return service.filterService(argument, type);
    }

    @Override
    public void update(int idStudent, String type, String update) throws ValidatorException, IllegalArgumentException {
        service.update(idStudent, type, update);
    }

    @Override
    public Student getStudentWithMaxGrade() {
        return service.getStudentWithMaxGrade();
    }

    @Override
    public Problem getMaxAssignedProblem() throws ValidatorException {
        return service.getMaxAssignedProblem();
    }

    @Override
    public Student getMostAssignedStudent() {
        return service.getMostAssignedStudent();
    }

    @Override
    public Problem getProblemHighestAverage() throws ValidatorException {
        return service.getProblemHighestAverage();
    }

    @Override
    public Student getStudentHighestAverageHard() {
        return service.getStudentHighestAverageHard();
    }

    @Override
    public Iterable<Student> getSorted(List<Pair<Boolean, String>> criteria) {
        return service.getSorted(criteria);
    }

    @Override
    public Iterable<Grade> getGradesSorted(List<Pair<Boolean, String>> criteria) {
        return service.getGradesSorted(criteria);
    }
}
