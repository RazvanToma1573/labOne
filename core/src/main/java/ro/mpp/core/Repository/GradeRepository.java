package ro.mpp.core.Repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.mpp.core.Domain.Grade;

public interface GradeRepository extends CatalogRepository<Grade, Integer> {
    Page<Grade> findAll(Pageable pageable);
}
