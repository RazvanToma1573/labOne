package ro.mpp.core.Repository;

import ro.mpp.core.Domain.Student;

import java.util.List;

public interface StudentRepositoryCustom {
    List<Student> findAllWithGradesAndProblemJPQL();
    List<Student> findAllWithGradesAndProblemCriteriaAPI();
    List<Student> findAllWithGradesAndProblemSQL();
    Student findWithGradesAndProblemJPQL(int id);
    Student findWithGradesAndProblemCriteriaAPI(int id);
    Student findWithGradesAndProblemSQL(int id);
    List<Student> findAllByFirstNameJPQL(String firstName);
    List<Student> findAllByFirstNameCriteriaAPI(String firstName);
    List<Student> findAllByFirstNameSQL(String firstName);
}
