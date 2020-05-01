package ro.mpp.spring.Service;

import ro.mpp.spring.Domain.Grade;
import ro.mpp.spring.Domain.Problem;
import ro.mpp.spring.Domain.Student;
import ro.mpp.spring.Domain.Validators.ValidatorException;

import java.util.Map;
import java.util.Set;

public interface IStudentService {
    void add(Student student) throws ValidatorException, IllegalArgumentException;
    void remove(int id) throws IllegalArgumentException;
    void removeProblem(int id) throws  IllegalArgumentException;
    void assignProblem(int studentId, int problemId) throws ValidatorException, IllegalArgumentException;
    Iterable<Student> get();
    Iterable<Grade> getGrades();
    Student getById(int id) throws ValidatorException, IllegalArgumentException;
    void assignGrade(int studentId, int problemId, int grade) throws ValidatorException, IllegalArgumentException;
    Set<Student> filterService(String argument, String type) throws ValidatorException;
    void update (int idStudent, String firstName, String lastName) throws ValidatorException, IllegalArgumentException;
    Student getStudentWithMaxGrade();
    Problem getMaxAssignedProblem() throws ValidatorException;
    Student getMostAssignedStudent();
    Problem getProblemHighestAverage() throws ValidatorException;
    Student getStudentHighestAverageHard();
    Iterable<Student> getSorted(Map<String, Boolean> criteria);
    Iterable<Grade> getGradesSorted(Map<String, Boolean> criteria);

}
