package ro.mpp.core.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.mpp.core.Domain.BaseEntity;

import java.io.Serializable;

public interface CloudAppRepository<T extends BaseEntity<ID>, ID extends Serializable>
        extends JpaRepository<T, ID> {
}

