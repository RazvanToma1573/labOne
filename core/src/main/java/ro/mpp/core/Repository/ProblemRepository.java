package ro.mpp.core.Repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.mpp.core.Domain.Problem;

public interface ProblemRepository extends CatalogRepository<Problem, Integer> {
    Page<Problem> findAll(Pageable pageable);
}
