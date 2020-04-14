package mpp.socket.common;

import com.sun.tools.javac.util.Pair;
import mpp.socket.common.Domain.Grade;
import mpp.socket.common.Domain.Problem;
import mpp.socket.common.Domain.Student;
import mpp.socket.common.Domain.Validators.ValidatorException;

import java.util.List;
import java.util.Set;

public interface IServiceStudents {

    void add (Student newStudent) throws ValidatorException, IllegalArgumentException;
    void remove(int id) throws IllegalArgumentException;
    void assignProblem(int studentId, int problemId) throws ValidatorException, IllegalArgumentException;
    Iterable<Student> get();
    Iterable<Grade> getGrades();
    Student getById(int id) throws ValidatorException, IllegalArgumentException;
    void assignGrade(int studentId, int problemId, int grade) throws ValidatorException, IllegalArgumentException;
    Set<Student> filterService(String argument, String type) throws ValidatorException;
    void update (int idStudent, String type, String update) throws ValidatorException, IllegalArgumentException;
    Student getStudentWithMaxGrade();
    Problem getMaxAssignedProblem() throws ValidatorException;
    Student getMostAssignedStudent();
    Problem getProblemHighestAverage() throws ValidatorException;
    Student getStudentHighestAverageHard();
    Iterable<Student> getSorted(List<Pair<Boolean, String>> criteria);
    Iterable<Grade> getGradesSorted(List<Pair<Boolean, String>> criteria);
    
}
