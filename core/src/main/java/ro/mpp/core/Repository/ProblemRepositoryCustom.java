package ro.mpp.core.Repository;

import ro.mpp.core.Domain.Problem;

import java.util.List;

public interface ProblemRepositoryCustom {
    List<Problem> findAllWithGradesAndStudentJPQL();
    List<Problem> findAllWithGradesAndStudentCriteriaAPI();
    List<Problem> findAllWithGradesAndStudentSQL();
    Problem findWithGradesAndStudentJPQL(int id);
    Problem findWithGradesAndStudentCriteriaAPI(int id);
    Problem findWithGradesAndStudentSQL(int id);
    List<Problem> findAllByDescriptionJPQL(String description);
    List<Problem> findAllByDescriptionCriteriaAPI(String description);
    List<Problem> findAllByDescriptionSQL(String description);
}
