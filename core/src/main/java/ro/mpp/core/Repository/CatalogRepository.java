package ro.mpp.core.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;
import ro.mpp.core.Domain.BaseEntity;

import java.io.Serializable;

@NoRepositoryBean
@Transactional
public interface CatalogRepository<T extends BaseEntity<ID>, ID extends Serializable> extends JpaRepository<T, ID> {
}
