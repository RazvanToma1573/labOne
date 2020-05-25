package ro.mpp.core.Repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.mpp.core.Domain.Student;

import java.util.List;

public interface StudentRepository extends CatalogRepository<Student, Integer>, StudentRepositoryCustom {
    Page<Student> findAll(Pageable pageable);
    Page<Student> findByFirstName(String firstName, Pageable pageable);
    Page<Student> findByLastName(String lastName, Pageable pageable);

    @Query("select distinct s from Student s")
    @EntityGraph(value = "studentWithGrades", type = EntityGraph.EntityGraphType.LOAD)
    List<Student> findAllWithGrades();

    @Query("select distinct s from Student s")
    @EntityGraph(value = "studentWithGradesAndProblem", type = EntityGraph.EntityGraphType.LOAD)
    List<Student> findAllWithGradesAndProblem();
}
