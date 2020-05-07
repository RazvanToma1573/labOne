package ro.mpp.core.Service;


import ro.mpp.core.Domain.Grade;
import ro.mpp.core.Domain.Problem;
import ro.mpp.core.Domain.Student;
import ro.mpp.core.Domain.Validators.ValidatorException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IStudentService {
    Student add(Student student) throws ValidatorException, IllegalArgumentException;
    void remove(int id) throws IllegalArgumentException;
    void removeProblem(int id) throws  IllegalArgumentException;
    void assignProblem(int studentId, int problemId) throws ValidatorException, IllegalArgumentException;
    List<Student> get();
    List<Grade> getGrades();
    Student getById(int id) throws ValidatorException, IllegalArgumentException;
    Grade assignGrade(int studentId, int problemId, int grade) throws ValidatorException, IllegalArgumentException;
    Set<Student> filterService(String argument, String type) throws ValidatorException;
    Student update (int idStudent, String firstName, String lastName) throws ValidatorException, IllegalArgumentException;
    Student getStudentWithMaxGrade();
    Problem getMaxAssignedProblem() throws ValidatorException;
    Student getMostAssignedStudent();
    Problem getProblemHighestAverage() throws ValidatorException;
    Student getStudentHighestAverageHard();
    List<Student> getSorted(Map<String, Boolean> criteria);
    List<Grade> getGradesSorted(Map<String, Boolean> criteria);

}
