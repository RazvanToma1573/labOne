package ro.mpp.core.Service;


import org.springframework.data.domain.Page;
import ro.mpp.core.Domain.Grade;
import ro.mpp.core.Domain.Problem;
import ro.mpp.core.Domain.Student;
import ro.mpp.core.Domain.Validators.ValidatorException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IStudentService {
    /*
    Student add(Student student) throws IllegalArgumentException;
    void remove(int id) throws IllegalArgumentException;
    void removeProblem(int id) throws  IllegalArgumentException;
    void assignProblem(int studentId, int problemId) throws ValidatorException, IllegalArgumentException;
    void removeGrade(int id);
    Page<Student> get(int page);
    List<Student> get();
    Page<Grade> getGrades(int page);
    List<Grade> getGrades();
    Student getById(int id) throws ValidatorException, IllegalArgumentException;
    Grade assignGrade(int studentId, int problemId, int grade) throws ValidatorException, IllegalArgumentException;
    Page<Student> filterService(int page, String argument, String type) throws ValidatorException;
    Student update (int idStudent, String firstName, String lastName) throws ValidatorException, IllegalArgumentException;
    Student getStudentWithMaxGrade();
    Problem getMaxAssignedProblem() throws ValidatorException;
    Student getMostAssignedStudent();
    Problem getProblemHighestAverage() throws ValidatorException;
    Student getStudentHighestAverageHard();
    Page<Student> getSorted(int page, Map<String, Boolean> criteria);
    Page<Grade> getGradesSorted(int page, Map<String, Boolean> criteria);

     */
    Student add(int id, String firstName, String lastName);
    Student remove(int id);
    List<Student> findAll();
    Student getById(int id);
    Student update(int id, String firstName, String lastName);
    List<Student> findAllByFirstName(String firstName);

    void assignProblem(int studentId, int problemId);
    void assignGrade(int studentId, int problemId, int grade);
    Set<Grade> findAllGrades();
    void removeGrade(int studentId, int problemId);
}
