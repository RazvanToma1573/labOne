package ro.mpp.core.Repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.mpp.core.Domain.Student;

public interface StudentRepository extends CatalogRepository<Student, Integer> {
    Page<Student> findAll(Pageable pageable);
    Page<Student> findByFirstName(String firstName, Pageable pageable);
    Page<Student> findByLastName(String lastName, Pageable pageable);


}
