package ro.mpp.core.Repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import ro.mpp.core.Domain.Problem;
import ro.mpp.core.Domain.Student;

import java.util.List;

public interface ProblemRepository extends CatalogRepository<Problem, Integer>, ProblemRepositoryCustom {
    Page<Problem> findAll(Pageable pageable);

    @Query("select distinct p from Problem p")
    @EntityGraph(value = "problemWithGrades", type = EntityGraph.EntityGraphType.LOAD)
    List<Student> findAllWithGrades();

    @Query("select distinct p from Problem p")
    @EntityGraph(value = "problemWithGradesAndStudent", type = EntityGraph.EntityGraphType.LOAD)
    List<Student> findAllWithGradesAndStudent();
}
