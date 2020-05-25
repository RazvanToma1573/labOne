package ro.mpp.core.Repository;

import ro.mpp.core.Domain.Student;

import java.util.List;

public interface StudentRepositoryCustom {
    List<Student> findAllWithGradesAndProblemJPQL();
    List<Student> findAllWithGradesAndProblemCriteriaAPI();
}
